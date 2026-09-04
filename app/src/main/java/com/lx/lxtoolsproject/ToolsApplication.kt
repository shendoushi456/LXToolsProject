package com.lx.lxtoolsproject

import android.app.Application
import android.content.Context
import android.util.Log
import com.lx.c_interface_library.OnClickAgreement
import com.lx.gg_control_library.NativeBridge
import com.tencent.mmkv.MMKV

class ToolsApplication : Application() {
    companion object{
        var instance:ToolsApplication? = null

    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
         instance = this
        MMKV.initialize(this)
        intGgSource()
    }
    private fun intGgSource(){

//        if (NativeBridge.isApkDeploy()){
//            GmSdkUtils.initSDK()
//        }

        NativeBridge.triggering(this,object : OnClickAgreement {
            override fun isAgreement() {
                initApp()
            }
            override fun isCancelAgreement() {
            }
        })

    }

    private fun initApp(){
        NativeBridge.init(this)
    }

}