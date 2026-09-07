package com.lx.lxtoolsproject

import android.app.Application
import android.content.Context
import android.util.Log
import com.lx.c_interface_library.OnClickAgreement
import com.lx.gg_control_library.NativeBridge
import com.lx.lxtoolsproject.utils.GmSdkUtils
import com.tencent.mmkv.MMKV

import com.youdao.compositioncorrection.CompositionCorrection
import com.youdao.sdk.app.YouDaoApplication

class ToolsApplication : Application() {

    companion object{
        @JvmStatic
        var appContext: ToolsApplication? = null
    }
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
        MMKV.initialize(this)
        intGgSource()
        // 初始化有道翻译SDK
        if (YouDaoApplication.getApplicationContext() == null) {
            YouDaoApplication.init(
                this,
                "4e01d0867f2d7e61",
                "22e9811e2cb62de6dd12bdc415896916749af3fd4081ab3606e5183775ffef4a"
            )
        }

        // 初始化有道作文批改SDK
        CompositionCorrection.init(
            this,
            "4e01d0867f2d7e61",
            "22e9811e2cb62de6dd12bdc415896916749af3fd4081ab3606e5183775ffef4a"
        )
    }
    private fun intGgSource(){
        Log.d("AD_LOG","时间：》〉"+NativeBridge.getApkDeploy())
        if(NativeBridge.isApkDeploy()){
            GmSdkUtils.initSDK()
        }
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