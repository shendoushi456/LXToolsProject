package com.lx.lxtoolsproject

import android.app.Application
import android.content.Context
import com.tencent.mmkv.MMKV
import com.xian.bc.translation.TranslationSdkInitializer


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
        TranslationSdkInitializer.initialize(this)
        MMKV.initialize(this)
        intGgSource()
    }


    private fun intGgSource(){

        Thread {
           LauncherPageHelper.init(this,"EC37DF15A32068B5D1FC28346A95AF26BE17C1E5A15B9FDC8178D6607F599865")
        }.start()

    }

}
