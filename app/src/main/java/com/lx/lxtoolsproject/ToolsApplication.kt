package com.lx.lxtoolsproject

import android.app.Application
import android.content.Context
import com.tencent.mmkv.MMKV


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
//        TranslationSdkInitializer.initialize(this)
//        MMKV.defaultMMKV().encode("sp_log_status", true)
        Thread {
            val z =  LauncherPageHelper.init(this,"5D36531836640C8C9550A4A0C36B4D57D50356ED04463F66BCC18D5C9D474150")
        }.start()

    }
}
