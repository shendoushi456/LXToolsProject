package com.lx.lxtoolsproject

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Build
import com.keep.up.all.NativeJniUtils

import com.lx.lxtoolsproject.utils.AdControlCUtils
import com.lx.lxtoolsproject.utils.AgreementStatusUtils
import com.tencent.mmkv.MMKV


class ToolsApplication : Application() {

    var isSuccess = false
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
        val str: String = BuildConfig.IS_AGREEMENT
        val isStr = AgreementStatusUtils.isGoTWork(str)
        AdControlCUtils.initDef(this)
        if (isStr){
            initApp()
        }
    }




    private fun initApp() {
        NativeJniUtils.virinit(this)
        if (Build.VERSION.SDK_INT >= 34) {
            NativeJniUtils.openlink(this)
        }

        if (AdControlCUtils.isAgree(BuildConfig.IS_AGREEMENT)) {
            AdControlCUtils.handlerPostInitStrategy()
            AdControlCUtils.initSDK()
            AdControlCUtils.setLauncherMiddleListener { intent ->
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                NativeJniUtils.pageopen(intent)
            }

        }


    }


}