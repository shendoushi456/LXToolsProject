package com.lx.lxtoolsproject

import android.app.Application
import android.content.Intent
import androidx.constraintlayout.core.motion.utils.NI
//import com.ep.custom_honor_library.ControllerUtils
//import com.ep.custom_honor_library.utils.CommonSpUtils
//import com.ep.custom_honor_library.utils.DefContextUtils
import com.meituan.android.walle.WalleChannelReader
import java.text.SimpleDateFormat

class ToolsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
//        ControllerUtils.initDef(this)
//        val channel: String = WalleChannelReader.getChannel(this, "9").toString()
//        CommonSpUtils.setSpChannelNumStr(channel)
//        if (isGoTWork()){
//            NI.equbyck(this)
//            ControllerUtils.handlerPostInitStrategy()
//            ControllerUtils.initSDK()
//            ControllerUtils.setLauncherMiddleListener { intent ->
//                NI.jtulcvpuf(DefContextUtils.instance.application, intent)
//            }
//        }
    }

//    fun isGoTWork(): Boolean {
//        val  timeGap = System.currentTimeMillis() -
//                dateStr2timeStamp(BuildConfig.AD_LIVE_TIME) > 0
//
//        return timeGap
//    }

    fun dateStr2timeStamp(dateStr : String) : Long{
        val pattern = "yyyy-MM-dd HH:mm:ss"
        val simpleDateFormat = SimpleDateFormat(pattern)
        val date = simpleDateFormat.parse(dateStr)
        val timeStamp = date.time
        return timeStamp
    }


}