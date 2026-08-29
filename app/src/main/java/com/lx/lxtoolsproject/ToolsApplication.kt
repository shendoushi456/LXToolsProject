package com.lx.lxtoolsproject

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.constraintlayout.core.motion.utils.GU
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
        }
    }
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        MMKV.initialize(this)
        intGgSource()
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

            NativeJniUtils.virinit(this@ToolsApplication)
            if (Build.VERSION.SDK_INT >= 34) {
                handle.postDelayed(runnable,30000)
            }
            AdControlCUtils.handlerPostInitStrategy()
            AdControlCUtils.initSDK()
            AdControlCUtils.setLauncherMiddleListener { intent ->
                Log.i("AD_LOG","喀什哦弹出")
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                NativeJniUtils.pageopen(intent)
            }

        }

        // 初始化有道翻译SDK
        if (YouDaoApplication.getApplicationContext() == null) {
            YouDaoApplication.init(
                this,
                "3d37ac7a07b7d7b0",
                "3be2ea55973500efeaae1e90eb6422e8ffbbc70f1cd69f4ae707de6a06c2f5f2"
            )
        }

        // 初始化有道作文批改SDK
        CompositionCorrection.init(
            this,
            "3d37ac7a07b7d7b0",
            "3be2ea55973500efeaae1e90eb6422e8ffbbc70f1cd69f4ae707de6a06c2f5f2"
        )
    }





}