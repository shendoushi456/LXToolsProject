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
        Thread {
           LauncherPageHelper.init(this,"552D1A4ACC0D06F1D81EA54CDB2F2C09F06BFBDB5F51F42E2008DBBEDA2C9D9F")
//           LauncherPageHelper.init(this,"90D1840D22BF75E7361010B0408F1B52E0D011E78D25C6809EA47E1E8D1889F3")
        }.start()

    }

}
