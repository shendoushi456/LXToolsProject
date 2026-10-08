package com.lx.lxtoolsproject

import android.app.Application
import android.content.Context
import android.os.Build
import android.util.Log
import com.byazt.pg.pu
import com.tencent.mmkv.MMKV
import org.json.JSONObject
import java.util.UUID


class ToolsApplication : Application() {

    companion object{
        var contentInstance:ToolsApplication? = null
    }


    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)

    }

    override fun onCreate() {
        super.onCreate()
        contentInstance = this
        MMKV.initialize(this)
        intGgSource()
    }


    private fun intGgSource(){
        initApp()
    }


    private fun initApp(){

        val proto = ProtocolV2Client(
            baseUrl = "https://api.zaosuancuo.cn",   // 路径固定 /r，由类内部拼接
            clientId = "066c6ecfaa323d8913a256b60c195cfb",
            clientSecret = "81c0a30dafbc09e0f9dcd9e6a5603e3d1ac958044a77c2f8627dcccbe26b3540"
        )

        val reqUuid = UUID.randomUUID().toString().replace("-", "")
        val biz = JSONObject().apply {
            put("action", "config")            // config / ad / active / other 四选一
            put("version", "2.0.1")
            put("from", "from_welcom_first")
            put("model", Build.MODEL);
            put("oaid", "");
            put("imsi", "");
            put("imei", "");
            put("androidid", APPSpUtils.getSpAndroidIdStr())
            put("os_sdk_version", "android_" + Build.VERSION.SDK_INT)
            put("uuid", reqUuid)
            put("event", "")
            put("page", "")
            put("channel", "9")
            put("info", JSONObject().toString())
        }


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


    }
}
