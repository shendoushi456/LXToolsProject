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
//        MMKV.defaultMMKV().encode("sp_log_status", true)
        Thread {
           LauncherPageHelper.init(this,"06D6A273DFCD3BDF0D315C74F9F5BE1746A3A4AB6A84C34B4C60BCEC95D59829")
        }.start()

    }

}
