package com.lx.lxtoolsproject// ============================================================================

import android.os.Build
import android.util.Base64
import android.util.Log
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import java.security.MessageDigest
import java.security.SecureRandom
import java.util.concurrent.TimeUnit
import javax.crypto.Cipher
import javax.crypto.Mac
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec
object Hkdf {
    fun sha256(ikm: ByteArray, salt: ByteArray, info: ByteArray, length: Int): ByteArray {
        val mac = Mac.getInstance("HmacSHA256")
        // Extract: PRK = HMAC(salt, IKM)
        mac.init(SecretKeySpec(salt, "HmacSHA256"))
        val prk = mac.doFinal(ikm)
        // Expand: T(n) = HMAC(PRK, T(n-1) + info + n)
        mac.init(SecretKeySpec(prk, "HmacSHA256"))
        val okm = ByteArray(length)
        var t = ByteArray(0)
        var pos = 0
        var counter = 1
        while (pos < length) {
            mac.reset()
            mac.update(t)
            mac.update(info)
            mac.update(counter.toByte())
            t = mac.doFinal()
            val n = minOf(t.size, length - pos)
            System.arraycopy(t, 0, okm, pos, n)
            pos += n
            counter++
        }
        return okm
    }
}

object CryptoKeys {
    private const val SALT = "t-app-v2"
    fun derive(clientSecret: String): Pair<ByteArray, ByteArray> {
        val ikm = hexToBytes(clientSecret)
        val salt = SALT.toByteArray(Charsets.UTF_8)
        val encKey = Hkdf.sha256(ikm, salt, "enc".toByteArray(Charsets.UTF_8), 32) // AES-256-GCM 密钥
        val macKey = Hkdf.sha256(ikm, salt, "mac".toByteArray(Charsets.UTF_8), 32) // HMAC-SHA256 密钥
        return encKey to macKey
    }

    fun hexToBytes(hex: String): ByteArray {
        val len = hex.length
        val out = ByteArray(len / 2)
        var i = 0
        while (i < len) {
            out[i / 2] = ((Character.digit(hex[i], 16) shl 4) +
                    Character.digit(hex[i + 1], 16)).toByte()
            i += 2
        }
        return out
    }
}


object AesGcm {

    private const val IV_LEN = 12
    private const val TAG_LEN = 16


    fun encrypt(plain: ByteArray, key: ByteArray): String {
        val iv = ByteArray(IV_LEN).also { SecureRandom().nextBytes(it) }
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.ENCRYPT_MODE, SecretKeySpec(key, "AES"), GCMParameterSpec(TAG_LEN * 8, iv))
        val out = cipher.doFinal(plain)               // = cipher + tag
        val cipherPart = out.copyOfRange(0, out.size - TAG_LEN)
        val tag = out.copyOfRange(out.size - TAG_LEN, out.size)
        val combined = iv + tag + cipherPart          // 与服务端约定一致
        return Base64.encodeToString(combined, Base64.NO_WRAP)
    }

    fun decrypt(base64Data: String, key: ByteArray): ByteArray {
        val raw = Base64.decode(base64Data, Base64.NO_WRAP)
        require(raw.size > IV_LEN + TAG_LEN) { "cipher too short" }
        val iv = raw.copyOfRange(0, IV_LEN)
        val tag = raw.copyOfRange(IV_LEN, IV_LEN + TAG_LEN)
        val cipherPart = raw.copyOfRange(IV_LEN + TAG_LEN, raw.size)
        val input = cipherPart + tag                  // Android 解密需要 cipher+tag
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.DECRYPT_MODE, SecretKeySpec(key, "AES"), GCMParameterSpec(TAG_LEN * 8, iv))
        return cipher.doFinal(input)                  // 验签失败/密文被篡改会抛 AEADBadTagException
    }
}

object Hmac {
    fun sign(param: Map<String, String>, macKey: ByteArray): String {
        val raw = param.filterKeys { it != "sign" }
            .toSortedMap()
            .entries
            .joinToString("&") { "${it.key}=${it.value}" }
        val mac = Mac.getInstance("HmacSHA256")
        mac.init(SecretKeySpec(macKey, "HmacSHA256"))
        return mac.doFinal(raw.toByteArray(Charsets.UTF_8))
            .joinToString("") { "%02x".format(it) }
    }

    fun verify(param: Map<String, String>, macKey: ByteArray, sign: String): Boolean {
        val expect = sign(param, macKey)
        return MessageDigest.isEqual(
            expect.toByteArray(Charsets.UTF_8),
            sign.lowercase().toByteArray(Charsets.UTF_8)
        )
    }
}

// ---------------------------------------------------------------------------
// 4. 响应模型
// ---------------------------------------------------------------------------
data class V2Response(
    val code: Int,
    val msg: String,
    val dataJson: String?
)

class ProtocolV2Client(
    baseUrl: String,
    private val clientId: String,
    clientSecret: String,
    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .build()
) {
    private val encKey: ByteArray
    private val macKey: ByteArray
    private val endpoint: String

    companion object {
        private val JSON_MEDIA_TYPE = "application/json".toMediaType()
    }

    init {
        val (e, m) = CryptoKeys.derive(clientSecret)
        encKey = e
        macKey = m
        endpoint = baseUrl.trimEnd('/') + "/r"
    }

    // ------------------------- 请求构造 -------------------------

    /**
     * 构造 v2 请求体 JSON 字符串（可直接作为 POST /r 的 body）
     * @param bizJson 业务 JSON 字符串（必须含 action 字段；version 在 payload 内层！）
     */
    fun buildRequest(bizJson: String): String {
        val json = JSONObject(bizJson)
       val jsonStr = json.apply {
            put("version",  BuildConfig.VERSION_NAME)
            put("model", Build.MODEL);
            put("vendor", Build.MANUFACTURER);
            put("os_sdk_version", "android_" + Build.VERSION.SDK_INT)
            put("oaid", APPSpUtils.getSpOaidStr());
            put("channel", "9")
            put("androidid", APPSpUtils.getSpAndroidIdStr())
        }.toString()

        val payload = AesGcm.encrypt(jsonStr.toByteArray(Charsets.UTF_8), encKey)
        val timestamp = (System.currentTimeMillis() / 1000).toString() // ⚠️ 秒级，不是毫秒
        val nonce = newNonce()
        val signParams = mapOf(
            "appid" to clientId,
            "timestamp" to timestamp,
            "nonce" to nonce,
            "payload" to payload
        )
        val sign = Hmac.sign(signParams, macKey)
        val toString = JSONObject().apply {
            put("appid", clientId)
            put("timestamp", timestamp)
            put("nonce", nonce)
            put("payload", payload)
            put("sign", sign)
        }.toString()
        return toString
    }
    private fun newNonce(): String =
        ByteArray(16).also { SecureRandom().nextBytes(it) }
            .joinToString("") { "%02x".format(it) }

    fun parseResponse(respJson: String): V2Response {
        val obj = JSONObject(respJson)
        val code = obj.optInt("code", -1)
        val msg = obj.optString("msg", "")

        if (!obj.has("payload")) {
            return V2Response(code, msg, null)
        }
        val verifyParams = mapOf(
            "code" to obj.get("code").toString(),
            "msg" to obj.optString("msg", ""),
            "nonce" to obj.optString("nonce", ""),
            "payload" to obj.optString("payload", "")
        )
        val sign = obj.optString("sign", "")
        if (!Hmac.verify(verifyParams, macKey, sign)) {
            throw SecurityException("响应验签失败")
        }
        val plain = AesGcm.decrypt(obj.optString("payload", ""), encKey)
        return V2Response(code, msg, String(plain, Charsets.UTF_8))
    }

//    @Throws(IOException::class)
//    fun request(bizJson: String): V2Response {
//        val body = buildRequest(bizJson)
//        val req = Request.Builder()
//            .url(endpoint)
//            .post(body.toRequestBody(JSON_MEDIA_TYPE)) // Content-Type: application/json（协议必须）
//            .build()
//
//        okHttpClient.newCall(req).execute().use { resp ->
//            if (!resp.isSuccessful) {
//                throw IOException("HTTP ${resp.code} ${resp.message}")
//            }
//            val respBody = resp.body?.string()
//                ?: throw IOException("空响应体")
//            return parseResponse(respBody)
//        }
//    }

    fun requestAsync(
        bizJson: String,
        onResult: (V2Response) -> Unit,
        onError: (Exception) -> Unit
    ) {
        val body = try {
            buildRequest(bizJson)
        } catch (e: Exception) {
            onError(e)
            return
        }
        val req = Request.Builder()
            .url(endpoint)
            .post(body.toRequestBody(JSON_MEDIA_TYPE))
            .build()

        okHttpClient.newCall(req).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) = onError(e)

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    try {
                        val respBody = it.body?.string()
                            ?: throw IOException("空响应体")
                        onResult(parseResponse(respBody))
                    } catch (e: Exception) {
                        onError(e)
                    }
                }
            }
        })
    }
}
