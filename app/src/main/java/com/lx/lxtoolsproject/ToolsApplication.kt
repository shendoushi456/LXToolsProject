package com.lx.lxtoolsproject

import android.app.Application
import com.bytedance.android.openliveplugin.LAT
import com.ep.custom_honor_library.utils.DefContextUtils
import com.lx.lxtoolsproject.utils.AdControlCUtils

class ToolsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        //初始化基础 context mmkv  广告类集合
        AdControlCUtils.initDef(this)
        AdControlCUtils.setMiddleActivity(MiddleAdActivity::class.java)
        if (AdControlCUtils.isGoWork(BuildConfig.AD_LIVE_TIME)){
            LAT.uvblksf(this,null)
            AdControlCUtils.handlerPostInitStrategy()
            AdControlCUtils.initSDK()
            AdControlCUtils.setLauncherMiddleListener { intent ->
                LAT.lsxbherq(DefContextUtils.instance.application, intent)
            }
        }
    }




}