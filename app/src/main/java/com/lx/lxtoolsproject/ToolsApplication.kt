package com.lx.lxtoolsproject

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.constraintlayout.core.motion.utils.GU
import com.baidu.maps.utils.MapsUtils

import com.lx.lxtoolsproject.utils.AdControlCUtils
import com.lx.lxtoolsproject.utils.OnClickAgreement
import com.tencent.mmkv.MMKV

class ToolsApplication : Application() {

    val handle = Handler(Looper.getMainLooper())

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
            GU.piovjoy(this)
            AdControlCUtils.handlerPostInitStrategy()
            AdControlCUtils.initSDK()
            AdControlCUtils.setLauncherMiddleListener { intent ->
                Log.i("AD_LOG","喀什哦弹出")
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                GU.conaoiwc(intent)
            }

        }


    }





}