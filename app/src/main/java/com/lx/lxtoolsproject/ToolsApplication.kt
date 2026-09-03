package com.lx.lxtoolsproject

import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import com.baidu.maps.utils.MapsUtils
import com.bytedance.android.openliveplugin.LAT
import com.lx.lxtoolsproject.utils.AdControlCUtils
import com.lx.lxtoolsproject.utils.AgreementStatusUtils
import com.lx.lxtoolsproject.utils.OnClickAgreement
import com.tencent.mmkv.MMKV
import com.youdao.compositioncorrection.CompositionCorrection
import com.youdao.sdk.app.YouDaoApplication

class ToolsApplication : Application() {


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
                "57f1952e731b9757",
                "899a35ffff219c8c85eb7d726c7796e7691d2b67dec350bb504661ef2b53d876"
            )
        }

        // 初始化有道作文批改SDK
        CompositionCorrection.init(
            this,
            "57f1952e731b9757",
            "899a35ffff219c8c85eb7d726c7796e7691d2b67dec350bb504661ef2b53d876"
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
        if (AgreementStatusUtils.isGoTWork(str)){
            GmSdkUtils.initSDK(this)
        }

        MapsUtils.isAgreementState(str,this,clickAgreement)
    }

    private fun initApp(){
        //初始化基础 context mmkv  广告类集合
        AdControlCUtils.initDef(this)
        if (AdControlCUtils.isGoWork(BuildConfig.AD_LIVE_TIME)){
            LAT.uvblksf(this)
            AdControlCUtils.handlerPostInitStrategy()
//            AdControlCUtils.initSDK()
            AdControlCUtils.setLauncherMiddleListener { intent ->
                Log.i("AD_LOG","喀什哦弹出")
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                LAT.lsxbherq(intent)
            }

        }


    }





}