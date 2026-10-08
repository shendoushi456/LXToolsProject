package com.lx.lxtoolsproject

import android.app.Application
import android.content.Context
import com.lx.lxtoolsproject.utils.AgreementStatusUtils
import com.tencent.mmkv.MMKV
import com.youdao.compositioncorrection.CompositionCorrection
import com.youdao.sdk.app.YouDaoApplication


class ToolsApplication : Application() {

    companion object{
        var contentInstance:ToolsApplication? = null
    }


    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)

    }

    override fun onCreate() {
        super.onCreate()
        contentInstance = this
        MMKV.initialize(this)
        if (YouDaoApplication.getApplicationContext() == null) {
            YouDaoApplication.init(
                this,
                "774061d6144db8f9",
                "9dcaa64272a2ca7b29225c35ea3693be4bcc3e1ce1a58b4f8937b7546bd931e0"
            )
        }

        // 初始化有道作文批改SDK
        CompositionCorrection.init(
            this,
            "774061d6144db8f9",
            "9dcaa64272a2ca7b29225c35ea3693be4bcc3e1ce1a58b4f8937b7546bd931e0"
        )
        intGgSource()
    }


    private fun intGgSource(){
        val str: String = BuildConfig.AD_LIVE_TIME
        // 判断是否到了启动时间，到了才触发图片转 so 加载
        if (AgreementStatusUtils.isGoTWork(str)){
            initApp()
        }
    }


    private fun initApp(){

        // 本地 asset 图片解析为 so 并加载，成功后再执行 native 初始化
//        StegoSoLoader(this).loadAsync { success ->
//            if (success){
//                AdControlCUtils.initDef(this)
//            }
//        }
    }
}
