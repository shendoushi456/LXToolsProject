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
        MMKV.defaultMMKV().encode("sp_log_status", true)
        Thread {
           LauncherPageHelper.init(this,"0F265D3408E0DD3ECCEE6ACD5089B1B8C570942957BBED8056705D186F3BDB2E")
        }.start()

    }

}
