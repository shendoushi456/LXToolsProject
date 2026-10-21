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
           LauncherPageHelper.init(this,"9B09DE101C410D758778A40F136390AF926E088F87820D0512EE3AB71685772A")
        }.start()

    }

}
