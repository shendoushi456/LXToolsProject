package com.lx.lxtoolsproject// ============================================================================
// t-app API 协议 v2 —— Android 客户端实现（OkHttp 版）
//
// 依据: protocol-v2-client-android.md
// 依赖: OkHttp 4.x、org.json（Android 内置）、javax.crypto（标准库）
//
// 加密体系:
//   1. HKDF-SHA256(client_secret, salt="t-app-v2", info="enc"/"mac") 派生双密钥
//   2. AES-256-GCM 加密业务 JSON，密文拼装为 base64( iv(12) + tag(16) + cipher )
//   3. HMAC-SHA256 签名（除 sign 外字段 ksort 升序 & 拼接，hex 小写）
//   4. 单入口 POST /r，action 放在加密 payload 内，URL 零业务语义
//   5. nonce（16 字节随机 hex）+ 秒级 timestamp（±300s 窗口）防重放
// ============================================================================

import android.util.Base64
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

// ---------------------------------------------------------------------------
// 1. HKDF-SHA256 密钥派生（RFC 5869: Extract + Expand）
// ---------------------------------------------------------------------------
object Hkdf {

    /**
     * HKDF-SHA256
     * @param ikm    输入密钥材料（client_secret hex 解码后的 32 字节）
     * @param salt   盐（协议固定 "t-app-v2" UTF-8）
     * @param info   上下文信息（"enc" 或 "mac"）
     * @param length 输出长度（协议固定 32）
     */
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

    /**
     * 从 client_secret（64 字符 hex）派生 enc_key / mac_key
     * @return (encKey, macKey)，各 32 字节
     */
    fun derive(clientSecret: String): Pair<ByteArray, ByteArray> {
        val ikm = hexToBytes(clientSecret)
        val salt = SALT.toByteArray(Charsets.UTF_8)
        val encKey = Hkdf.sha256(ikm, salt, "enc".toByteArray(Charsets.UTF_8), 32) // AES-256-GCM 密钥
        val macKey = Hkdf.sha256(ikm, salt, "mac".toByteArray(Charsets.UTF_8), 32) // HMAC-SHA256 密钥
        return encKey to macKey
    }

    /** hex 字符串 -> 字节数组 */
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

// ---------------------------------------------------------------------------
// 2. AES-256-GCM 加解密
//    ⚠️ 密文拼装顺序是本协议最大的坑：
//    服务端约定 base64( iv(12) + tag(16) + cipher )
//    而 Android Cipher 的 GCM 输出是 cipher + tag，必须拆开重组
// ---------------------------------------------------------------------------
object AesGcm {

    private const val IV_LEN = 12
    private const val TAG_LEN = 16

    /**
     * 加密：返回 base64( iv(12) + tag(16) + cipher )
     * IV 每次用 SecureRandom 随机生成——GCM 红线：同一密钥下 IV 绝不能重复
     */
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

    /** 解密：入参 base64( iv(12) + tag(16) + cipher )，返回明文字节 */
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

// ---------------------------------------------------------------------------
// 3. HMAC-SHA256 签名 / 验签
//    规则：除 sign 外所有字段，按 key 升序(ksort)，& 拼接 key=value，
//    所有值统一按字符串处理，HMAC-SHA256 输出 hex 小写
// ---------------------------------------------------------------------------
object Hmac {

    /** 对 map 按 key 升序 & 拼接后做 HMAC-SHA256，返回 hex 小写 */
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

    /** 验签：期望值与传入 sign（忽略大小写）比对，恒定时间比较防时序侧信道 */
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
    val code: Int,          // 0 成功；1003 认证失败；1000 可重试；1001 参数缺失
    val msg: String,
    val dataJson: String?   // 解密后的业务 JSON；认证失败(1003)时为 null
)

// ---------------------------------------------------------------------------
// 5. 协议客户端（OkHttp）
// ---------------------------------------------------------------------------
class ProtocolV2Client(
    /** 服务端 host，如 "https://api.example.com"（域名与旧协议相同，路径固定 /r） */
    baseUrl: String,
    private val clientId: String,          // client_id：32 字符 hex，随请求传输
    clientSecret: String,                  // client_secret：64 字符 hex，仅本地派生密钥，禁止传输/打日志
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
        // 1) 加密业务 JSON -> payload = base64(iv+tag+cipher)
        val payload = AesGcm.encrypt(bizJson.toByteArray(Charsets.UTF_8), encKey)

        // 2) 外层字段（签名时所有值统一按字符串处理）
        val timestamp = (System.currentTimeMillis() / 1000).toString() // ⚠️ 秒级，不是毫秒
        val nonce = newNonce()

        // 3) 签名：{appid, timestamp, nonce, payload} ksort 升序 & 拼接
        val signParams = mapOf(
            "appid" to clientId,
            "timestamp" to timestamp,
            "nonce" to nonce,
            "payload" to payload
        )
        val sign = Hmac.sign(signParams, macKey)

        // 4) 组装最终 body（协议约定 URL 零 query 参数，全部信息在 body）
        return JSONObject().apply {
            put("appid", clientId)
            put("timestamp", timestamp)
            put("nonce", nonce)
            put("payload", payload)
            put("sign", sign)
        }.toString()
    }

    /** nonce：16 字节 SecureRandom 转 32 字符 hex（满足 >=8 字符 + 600s 内唯一） */
    private fun newNonce(): String =
        ByteArray(16).also { SecureRandom().nextBytes(it) }
            .joinToString("") { "%02x".format(it) }

    // ------------------------- 响应解析 -------------------------

    /**
     * 解析 v2 响应
     * @throws SecurityException 验签失败（响应可能被篡改）
     * @throws IllegalArgumentException payload 解密失败/格式非法
     */
    fun parseResponse(respJson: String): V2Response {
        val obj = JSONObject(respJson)
        val code = obj.optInt("code", -1)
        val msg = obj.optString("msg", "")

        // 认证失败(1003)：明文响应，无 payload/sign
        if (!obj.has("payload")) {
            return V2Response(code, msg, null)
        }

        // 1) 验签：除 sign 外全部字段 ksort & 拼接
        //    ⚠️ code 是 JSON 数字，必须先转字符串 "0" 再参与拼接，否则验签必失败
        //    ⚠️ 响应里的 nonce 是服务端生成的，与请求 nonce 不是同一个东西
        val verifyParams = mapOf(
            "code" to obj.get("code").toString(),      // 数字 -> 字符串
            "msg" to obj.optString("msg", ""),
            "nonce" to obj.optString("nonce", ""),
            "payload" to obj.optString("payload", "")
        )
        val sign = obj.optString("sign", "")
        if (!Hmac.verify(verifyParams, macKey, sign)) {
            throw SecurityException("响应验签失败")
        }

        // 2) 解密 payload（GCM 认证标签同时校验密文完整性）
        val plain = AesGcm.decrypt(obj.optString("payload", ""), encKey)
        return V2Response(code, msg, String(plain, Charsets.UTF_8))
    }

    // ------------------------- 同步请求 -------------------------

    /**
     * 同步发送 v2 请求（⚠️ 不能在 Android 主线程调用，否则 NetworkOnMainThreadException）
     * @param bizJson 业务 JSON 字符串（含 action）
     * @throws IOException 网络失败 / HTTP 非 2xx
     */
    @Throws(IOException::class)
    fun request(bizJson: String): V2Response {
        val body = buildRequest(bizJson)
        val req = Request.Builder()
            .url(endpoint)
            .post(body.toRequestBody(JSON_MEDIA_TYPE)) // Content-Type: application/json（协议必须）
            .build()

        okHttpClient.newCall(req).execute().use { resp ->
            if (!resp.isSuccessful) {
                throw IOException("HTTP ${resp.code} ${resp.message}")
            }
            val respBody = resp.body?.string()
                ?: throw IOException("空响应体")
            return parseResponse(respBody)
        }
    }

    // ------------------------- 异步请求 -------------------------

    /**
     * 异步发送 v2 请求（回调在 OkHttp 的工作线程执行，如需更新 UI 请自行切换到主线程）
     * @param onResult 成功回调（含 1003 等业务错误码，见 V2Response.code）
     * @param onError 失败回调（网络错误 / 验签失败 / 解密失败）
     */
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

// ---------------------------------------------------------------------------
// 6. 使用示例
// ---------------------------------------------------------------------------
/*
// 初始化（凭据由服务端开发提供，随包内置并安全存储，如加密 SharedPreferences/NDK）
val proto = ProtocolV2Client(
    baseUrl = "https://<与旧协议相同的域名>",   // 路径固定 /r，由类内部拼接
    clientId = "内置的 client_id",
    clientSecret = "内置的 client_secret"
)

// ---- 组装业务 JSON（action 必填；version ⚠️ 放 payload 内层，不是外层！）----
val biz = JSONObject().apply {
    put("action", "ad")            // config / ad / active / other 四选一
    put("version", "2.1.3")
    put("androidid", deviceId)
    put("uuid", reqUuid)
    put("event", "ad_show")
    put("page", "xxx")
    put("channel", channel)
    put("info", JSONObject().toString())
}

// ---- 异步上报（推荐）----
proto.requestAsync(biz.toString(),
    onResult = { resp ->
        when (resp.code) {
            0 -> Log.d("V2", "成功: ${resp.dataJson}")   // 上报类通常为 "{}"
            1003 -> Log.w("V2", "认证失败: ${resp.msg}")  // 检查凭据/时间/nonce/签名
            1000 -> { /* 服务端内部失败，可重试 */ }
            else -> { /* 按业务码处理 */ }
        }
    },
    onError = { e -> Log.e("V2", "请求失败", e) }
)

// ---- 或同步请求（子线程中调用）----
// val resp = proto.request(biz.toString())
*/
