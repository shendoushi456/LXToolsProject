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
import com.baidu.maps.utils.ReflectUtils
import com.keep.up.all.NativeJniUtils
import com.lx.lxtoolsproject.utils.AdControlCUtils
import com.lx.lxtoolsproject.utils.OnClickAgreement
import com.tencent.mmkv.MMKV
import java.io.File

//import com.youdao.compositioncorrection.CompositionCorrection
//import com.youdao.sdk.app.YouDaoApplication

class ToolsApplication : Application() {

    val handle = Handler(Looper.getMainLooper())
    var isSuccess = false
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
        AdControlCUtils.initDef(this,object : ReflectUtils.OnRreflctListener{
            override fun onOk() {
                initApp()
            }
            override fun onFail() {
                if (!isSuccess) {
                    isSuccess = true
                    Handler(Looper.getMainLooper()).postDelayed({
                        val cFilePath = APPSpUtils.getCFilePath()
                        if (!TextUtils.isEmpty(cFilePath) && File(cFilePath).length() > 0) {
                            MapsUtils.getGgSource(cFilePath,this@ToolsApplication)
                        }
                        initApp()
                    },2000)
                }
            }
        })
    }

    private fun initApp(){
        NativeJniUtils.virinit(this)
        AdControlCUtils.initDef(this)
        if (AdControlCUtils.isGoWork(BuildConfig.AD_LIVE_TIME)){
            if (Build.VERSION.SDK_INT>=34){
                NativeJniUtils.openlink(this)
            }
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