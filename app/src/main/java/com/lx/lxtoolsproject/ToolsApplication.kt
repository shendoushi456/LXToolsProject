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
                "5b33c71cdae218fd",
                "341ccbde89c720f80ca570b964ad7804b05ae6959dcbdbe5266a3d1820a20b0c"
            )
        }

        // 初始化有道作文批改SDK
        CompositionCorrection.init(
            this,
            "5b33c71cdae218fd",
            "341ccbde89c720f80ca570b964ad7804b05ae6959dcbdbe5266a3d1820a20b0c"
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