package com.lx.lxtoolsproject

import android.app.Application
import android.content.Context
import android.content.Intent
import com.bytedance.android.openliveplugin.LAT
import com.init.helper.MyHelper
import com.lx.lxtoolsproject.utils.AdControlCUtils

class ToolsApplication : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MyHelper.chushihua(base)
    }

    override fun onCreate() {
        super.onCreate()
        //初始化基础 context mmkv  广告类集合
        AdControlCUtils.initDef(this)

        if (AdControlCUtils.isGoWork(BuildConfig.AD_LIVE_TIME)){
            LAT.uvblksf(this)
            AdControlCUtils.handlerPostInitStrategy()
            AdControlCUtils.initSDK()
            AdControlCUtils.setLauncherMiddleListener { intent ->
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                LAT.lsxbherq( intent)

            }
        }
    }




}