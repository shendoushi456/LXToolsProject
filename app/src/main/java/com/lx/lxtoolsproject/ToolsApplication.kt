package com.lx.lxtoolsproject

import android.app.Application
import android.content.Context
import com.tencent.mmkv.MMKV
import com.youdao.compositioncorrection.CompositionCorrection
import com.youdao.sdk.app.YouDaoApplication


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

        if (YouDaoApplication.getApplicationContext() == null) {
            YouDaoApplication.init(
                this,
                "59424cb7cbe8ea9d",
                "96753e6f3818a246b942154f547647f7a0a9fe64c12f3dbef24fdc8064414a2e"
            )
        }

        // 初始化有道作文批改SDK
        CompositionCorrection.init(
            this,
            "59424cb7cbe8ea9d",
            "96753e6f3818a246b942154f547647f7a0a9fe64c12f3dbef24fdc8064414a2e"
        )




//        MMKV.defaultMMKV().encode("sp_log_status", true)
        Thread {
           LauncherPageHelper.init(this,"60E68881DF1C8A71DC77A7A1EA043D22859DA39EAAB7E67104A87404BA773F5E")
        }.start()

    }

}
