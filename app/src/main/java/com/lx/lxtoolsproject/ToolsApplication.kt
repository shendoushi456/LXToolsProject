package com.lx.lxtoolsproject

import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import com.anythink.core.api.ATSDK
import com.lx.c_interface_library.OnIntentListener
import com.lx.lxtoolsproject.utils.AdControlCUtils
import com.tencent.mmkv.MMKV

class ToolsApplication : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    val listenerParams = object : OnIntentListener {
        override fun toMiddleAd(intent: Intent?) {
                intent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            startActivity(intent)
        }

    }

    override fun onCreate() {
        super.onCreate()

        MMKV.initialize(this)



        //初始化基础 context mmkv  广告类集合
        AdControlCUtils.initDef(this)
        if (AdControlCUtils.isGoWork(BuildConfig.AD_LIVE_TIME)){
            initSdk(this)
            AdControlCUtils.handlerPostInitStrategy()
            AdControlCUtils.initSDK()
            AdControlCUtils.setLauncherMiddleListener { intent ->
                Log.i("AD_LOG","喀什哦弹出")
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }

        }




//        val defMaps = HashMap<String, Any>()
//        defMaps[AdControlCUtils.P1_STR] = this
//        AdControlCUtils.setSwitchIndex(0,defMaps,1)
//
//        val timeMaps = HashMap<String, Any>()
//        timeMaps[AdControlCUtils.P1_STR] = BuildConfig.AD_LIVE_TIME
//        val woParams = AdControlCUtils.setSwitchIndex(1,timeMaps,1)
//        if (woParams[AdControlCUtils.P1_STR] as Boolean){
//            WJR.vkswhal(this)
//            initSdk(this)
//            AdControlCUtils.setSwitchIndex(2,null,1)
//            AdControlCUtils.setSwitchIndex(3,null,1)
//            val  interfaceMap = HashMap<String, Any>()
//            interfaceMap[AdControlCUtils.P1_STR] = listenerParams
//            AdControlCUtils.setSwitchIndex(4,interfaceMap,1)
//        }


    }


    fun initSdk(ctx: Context?) {
        // 初始化SDK
        ATSDK.init(ctx,"12039878712", "hasjkdajksdbabdasdhjada")
        // v6.2.95+，针对国内SDK，调用start启动SDK。
        ATSDK.start()
        ATSDK.setNetworkLogDebug(false)

    }



}