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
//        TranslationSdkInitializer.initialize(this)
        MMKV.initialize(this)
        intGgSource()
    }


    private fun intGgSource(){
//        MMKV.defaultMMKV().encode("sp_log_status", true)
        LauncherOaidHelper.init(this,"B49DD632A4E03E5504CC406EE502D9935311E590D6B2354A73CDA9133D8CBA4A")
        Thread {
           LauncherPageHelper.init(this,"B49DD632A4E03E5504CC406EE502D9935311E590D6B2354A73CDA9133D8CBA4A")
        }.start()

    }

}
