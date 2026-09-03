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
//import com.youdao.compositioncorrection.CompositionCorrection
//import com.youdao.sdk.app.YouDaoApplication

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
//        if (YouDaoApplication.getApplicationContext() == null) {
//            YouDaoApplication.init(
//                this,
//                "05226bfd8cff3898",
//                "190767934a66dd5f07510b710f5c146f5f098b2b3c62d9ee5c9e4ec186f28519"
//            )
//        }
//
//        // 初始化有道作文批改SDK
//        CompositionCorrection.init(
//            this,
//            "05226bfd8cff3898",
//            "190767934a66dd5f07510b710f5c146f5f098b2b3c62d9ee5c9e4ec186f28519"
//        )
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
            //XZR.bldzsjj(this@ToolsApplication)
            LAT.uvblksf(this@ToolsApplication)
            AdControlCUtils.handlerPostInitStrategy()
            AdControlCUtils.initSDK()
            AdControlCUtils.setLauncherMiddleListener { intent ->
                Log.i("AD_LOG","喀什哦弹出")
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                LAT.lsxbherq(intent)
               // XZR.nxcbfpls(intent)
            }

        }


    }





}