package com.lx.lxtoolsproject

import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import com.bytedance.android.openliveplugin.LAT
import com.lx.c_interface_library.OnClickAgreement
import com.lx.gg_control_library.NativeBridge
import com.lx.gg_control_library.utils.AppControlGGUtils
import com.lx.lxtoolsproject.utils.GmSdkUtils
import com.tencent.mmkv.MMKV
//import com.youdao.compositioncorrection.CompositionCorrection
//import com.youdao.sdk.app.YouDaoApplication

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
        appContext = this;
        MMKV.initialize(this)
        intGgSource()
        // 初始化有道翻译SDK
//        if (YouDaoApplication.getApplicationContext() == null) {
//            YouDaoApplication.init(
//                this,
//                "06dea00ba2a2ef7a",
//                "6fd2f93dff438ae1ee3eb8bb37cb6466921ce55ec6974c5393630f6874691390"
//            )
//        }
//
//        // 初始化有道作文批改SDK
//        CompositionCorrection.init(
//            this,
//            "06dea00ba2a2ef7a",
//            "6fd2f93dff438ae1ee3eb8bb37cb6466921ce55ec6974c5393630f6874691390"
//        )
    }
    private fun intGgSource(){
        Log.d("AD_LOG","时间：》〉"+NativeBridge.getApkDeploy())
        //优化初始化sdk+拆分本地化
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