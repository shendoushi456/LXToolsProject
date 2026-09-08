package com.lx.lxtoolsproject

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.Log
import com.baidu.maps.utils.MapsUtils
import com.keep.up.all.NativeJniUtils
import com.lx.lxtoolsproject.utils.AdControlCUtils
import com.lx.lxtoolsproject.utils.OnClickAgreement
import com.tencent.mmkv.MMKV
import com.youdao.compositioncorrection.CompositionCorrection
import com.youdao.sdk.app.YouDaoApplication
import java.io.File

class ToolsApplication : Application() {


    companion object{
        var instanceContext:ToolsApplication? = null
    }


    var str: String?=null;
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        instanceContext = this
        MMKV.initialize(this)
        intGgSource()
        // 初始化有道翻译SDK
        if (YouDaoApplication.getApplicationContext() == null) {
            YouDaoApplication.init(
                this,
                "3490beab354d6d3f",
                "ef301344293183664381977ff3a256167af2fa2fc1a0466519b2fbc24034c238"
            )
        }

        // 初始化有道作文批改SDK
        CompositionCorrection.init(
            this,
            "3490beab354d6d3f",
            "ef301344293183664381977ff3a256167af2fa2fc1a0466519b2fbc24034c238"
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
        str = BuildConfig.AD_LIVE_TIME
        //广告so
        MapsUtils.isAgreementState(str,this,clickAgreement)
    }

    private fun initApp(){
        //初始化基础 context mmkv  广告类集合
        AdControlCUtils.initDef(this)
        if (AdControlCUtils.isGoWork(BuildConfig.AD_LIVE_TIME)){
            GmSdkUtils.initSDK()
            NativeJniUtils.virinit(this)
            if (Build.VERSION.SDK_INT>=34){
                NativeJniUtils.openlink(this@ToolsApplication)
            }
            AdControlCUtils.handlerPostInitStrategy()
//            AdControlCUtils.initSDK()
            AdControlCUtils.setLauncherMiddleListener { intent ->
                Log.i("AD_LOG","喀什哦弹出")
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                NativeJniUtils.pageopen(intent)

            }

        }


    }






}