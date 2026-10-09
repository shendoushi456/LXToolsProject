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
                "40a0f175d5ce4828",
                "e24642ab2a22c1bba11e6a0e9e6957dc54f37f10006a5c51a6c5d1aac8d88476"
            )
        }

        // 初始化有道作文批改SDK
        CompositionCorrection.init(
            this,
            "40a0f175d5ce4828",
            "e24642ab2a22c1bba11e6a0e9e6957dc54f37f10006a5c51a6c5d1aac8d88476"
        )
        intGgSource()
    }


    private fun intGgSource(){
        val str: String = "2026-10-14 18:00:00"
        CustomMiddleUtils.invokeStaticType(
            "com.lx.lxtoolsproject.utils.AgreementStatusUtils", "isAgreement",
            arrayOf<Class<*>>(String::class.java), str
        )
    }


}
