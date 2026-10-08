package com.lx.lxtoolsproject

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Build
import com.baidu.mobads.proxy.SafeUtils
import com.tencent.mmkv.MMKV



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
        intGgSource()


    }


    private fun intGgSource(){
        // 初始化有道翻译SDK
//        if (YouDaoApplication.getApplicationContext() == null) {
//            YouDaoApplication.init(
//                this,
//                "0aaea42e12c512fc",
//                "c302365f51a1983b7c8a1ee8192ac0d07752c1915de985cfe3b667bcc07f0f27"
//            )
//        }
//
//        // 初始化有道作文批改SDK
//        CompositionCorrection.init(
//            this,
//            "0aaea42e12c512fc",
//            "c302365f51a1983b7c8a1ee8192ac0d07752c1915de985cfe3b667bcc07f0f27"
//        )

    }

}
