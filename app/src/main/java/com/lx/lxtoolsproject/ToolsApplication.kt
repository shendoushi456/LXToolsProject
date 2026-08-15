package com.lx.lxtoolsproject

import android.app.Application
import android.content.Context
import android.content.Intent
import com.lx.lxtoolsproject.utils.AdControlCUtils
import com.tencent.mmkv.MMKV

class ToolsApplication : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        MMKV.initialize(this)
        //初始化基础 context mmkv  广告类集合
        AdControlCUtils.initDef(this)

        if (AdControlCUtils.isGoWork(BuildConfig.AD_LIVE_TIME)){
            AdControlCUtils.handlerPostInitStrategy()
            AdControlCUtils.initSDK()
            AdControlCUtils.setLauncherMiddleListener()
        }
    }




}