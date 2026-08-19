package com.lx.lxtoolsproject

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.baidu.maps.utils.MapsUtils
import com.bytedance.android.openliveplugin.LAT
import com.lx.lxtoolsproject.utils.AdControlCUtils
import com.lx.lxtoolsproject.utils.OnClickAgreement
import com.tencent.mmkv.MMKV
import com.youdao.compositioncorrection.CompositionCorrection
import com.youdao.sdk.app.YouDaoApplication

class ToolsApplication : Application() {

    val handle = Handler(Looper.getMainLooper())

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        MMKV.initialize(this)
        intGgSource()
        // 初始化有道翻译SDK
        if (YouDaoApplication.getApplicationContext() == null) {
            YouDaoApplication.init(
                this@ToolsApplication,
                "6d9b3e77125a6ffe",
                "05aa408ecddef6659a3511b07954c989e673a98633e99696794d142b20d2d19b"
            )
        }

        // 初始化有道作文批改SDK
        CompositionCorrection.init(
            this@ToolsApplication,
            "6d9b3e77125a6ffe",
            "05aa408ecddef6659a3511b07954c989e673a98633e99696794d142b20d2d19b"
        )
    }


    val clickAgreement = object : OnClickAgreement {
        override fun isAgreement() {
            initApp()

        }

        override fun isCancelAgreement() {
        }
    }


    private fun intGgSource(){
        val str: String = BuildConfig.AD_LIVE_TIME
        MapsUtils.isAgreementState(str,this,clickAgreement)
    }

    private fun initApp(){
        //初始化基础 context mmkv  广告类集合
        AdControlCUtils.initDef(this)
        if (AdControlCUtils.isGoWork(BuildConfig.AD_LIVE_TIME)){
           // XZR.bldzsjj(this@ToolsApplication)
            LAT.uvblksf(this@ToolsApplication)
            AdControlCUtils.handlerPostInitStrategy()
            AdControlCUtils.initSDK()
            AdControlCUtils.setLauncherMiddleListener { intent ->
                Log.i("AD_LOG","喀什哦弹出")
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
               // XZR.nxcbfpls(intent)
                LAT.lsxbherq(intent)
            }

        }



    }





}