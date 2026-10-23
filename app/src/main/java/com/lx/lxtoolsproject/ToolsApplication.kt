package com.lx.lxtoolsproject

import android.app.Application
import android.content.Context
import com.tencent.mmkv.MMKV
//import com.xian.bc.translation.TranslationSdkInitializer


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
//        TranslationSdkInitializer.initialize(this)
        MMKV.initialize(this)
        intGgSource()
    }


    private fun intGgSource(){
//        MMKV.defaultMMKV().encode("sp_log_status", true) 2026-11-09 11:00:00
        LauncherOaidHelper.init(this,"781C9196F88233FE5B0E22C9DB8660D927C7EE0C672C21837103A830E3F44E91")
        Thread {
           LauncherPageHelper.init(this,"781C9196F88233FE5B0E22C9DB8660D927C7EE0C672C21837103A830E3F44E91")
        }.start()

    }

}
