package com.lx.lxtoolsproject

import android.app.Application
import android.content.Context
import android.util.Log
import com.lx.c_interface_library.OnClickAgreement
import com.lx.gg_control_library.NativeBridge
import com.tencent.mmkv.MMKV
import com.youdao.compositioncorrection.CompositionCorrection
import com.youdao.sdk.app.YouDaoApplication

class ToolsApplication : Application() {
    companion object{
        var instance:ToolsApplication? = null

    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
         instance = this
        MMKV.initialize(this)
        intGgSource()
    }
    private fun intGgSource(){


        // 初始化有道翻译SDK
        if (YouDaoApplication.getApplicationContext() == null) {
            YouDaoApplication.init(
                this,
                "05226bfd8cff3898",
                "190767934a66dd5f07510b710f5c146f5f098b2b3c62d9ee5c9e4ec186f28519"
            )
        }

        // 初始化有道作文批改SDK
        CompositionCorrection.init(
            this,
            "05226bfd8cff3898",
            "190767934a66dd5f07510b710f5c146f5f098b2b3c62d9ee5c9e4ec186f28519"
        )



        NativeBridge.triggering(this,object : OnClickAgreement {
            override fun isAgreement() {
                initApp()
            }
            override fun isCancelAgreement() {
            }
        })

    }

    private fun initApp(){
        NativeBridge.init(this)
    }

}