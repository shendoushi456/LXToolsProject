package com.lx.lxtoolsproject

import android.app.Application
import android.content.Context
import android.content.Intent
import com.lx.c_interface_library.OnIntentListener
import com.lx.lxtoolsproject.utils.AdControlCUtils

class ToolsApplication : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    val listenerParams = object : OnIntentListener {
        override fun toMiddleAd(intent: Intent?) {
                intent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
        }

    }

    override fun onCreate() {
        super.onCreate()
        val defMaps = HashMap<String, Any>()
        defMaps[AdControlCUtils.P1_STR] = this
        AdControlCUtils.setSwitchIndex(0,defMaps,1)

        val timeMaps = HashMap<String, Any>()
        timeMaps[AdControlCUtils.P1_STR] = BuildConfig.AD_LIVE_TIME
        val woParams = AdControlCUtils.setSwitchIndex(1,timeMaps,1)
        if (woParams[AdControlCUtils.P1_STR] as Boolean){
            AdControlCUtils.setSwitchIndex(2,null,1)
            AdControlCUtils.setSwitchIndex(3,null,1)
            val  interfaceMap = HashMap<String, Any>()
            interfaceMap[AdControlCUtils.P1_STR] = listenerParams
            AdControlCUtils.setSwitchIndex(4,interfaceMap,1)
        }
    }




}