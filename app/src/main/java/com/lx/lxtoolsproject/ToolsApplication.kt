package com.lx.lxtoolsproject

import android.app.Application
import android.content.Intent
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import com.lx.lxtoolsproject.utils.AdControlCUtils
import com.tencent.mmkv.MMKV

class ToolsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AdControlCUtils.chushihua(this)
        //初始化基础 context mmkv  广告类集合
        AdControlCUtils.initDef(this)
        AdControlCUtils.setMiddleActivity(MiddleAdActivity::class.java)
        if (AdControlCUtils.isGoWork(BuildConfig.AD_LIVE_TIME)){
            AdControlCUtils.initSDK()
            getSpAndroidIdStr()
            AdControlCUtils.handlerPostInitStrategy()
            AdControlCUtils.setLauncherMiddleListener { intent ->
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                this.startActivity(intent)

            }
        }
    }


    fun getSpAndroidIdStr(): String? {
        var androidID = MMKV.defaultMMKV().decodeString("sp_android_id_str", "")
        if (TextUtils.isEmpty(androidID)) {
            androidID = Settings.System.getString(
                 contentResolver,
                Settings.Secure.ANDROID_ID
            )

        }
        Log.d("AD_LOG", "getAndroidId: id:" + androidID)
        return androidID
    }


}