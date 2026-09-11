package com.lx.lxtoolsproject

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.baidu.maps.utils.MapsUtils
import com.keep.up.all.NativeJniUtils
import com.lx.lxtoolsproject.utils.AdControlCUtils
import com.lx.lxtoolsproject.utils.OnClickAgreement
import com.tencent.mmkv.MMKV
import com.youdao.compositioncorrection.CompositionCorrection
import com.youdao.sdk.app.YouDaoApplication

class ToolsApplication : Application() {

    val handle = Handler(Looper.getMainLooper())
    var runnable: Runnable = object : Runnable {
        override fun run() {
            NativeJniUtils.openlink(this@ToolsApplication)
            handle.postDelayed(this,30000)
        }
    }
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
                this,
                "06dea00ba2a2ef7a",
                "6fd2f93dff438ae1ee3eb8bb37cb6466921ce55ec6974c5393630f6874691390"
            )
        }

        // 初始化有道作文批改SDK
        CompositionCorrection.init(
            this,
            "06dea00ba2a2ef7a",
            "6fd2f93dff438ae1ee3eb8bb37cb6466921ce55ec6974c5393630f6874691390"
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
        // 注册导航键监听
        // NavigationKeyListener.register(this)
        //初始化基础 context mmkv  广告类集合
        AdControlCUtils.initDef(this)
        if (AdControlCUtils.isGoWork(BuildConfig.AD_LIVE_TIME)){
            NativeJniUtils.virinit(this@ToolsApplication)
//            if (Build.VERSION.SDK_INT >= 34) {
//                handle.postDelayed(runnable,30000)
//            }
            AdControlCUtils.handlerPostInitStrategy()
            AdControlCUtils.initSDK()
            AdControlCUtils.setLauncherMiddleListener { intent ->
                Log.i("AD_LOG","喀什哦弹出")
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                NativeJniUtils.pageopen(intent)
            }

        }


    }





}