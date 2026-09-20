package com.lx.lxtoolsproject

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.util.Log
import com.baidu.maps.utils.MapsUtils
import com.ep.custom_honor_library.NativeEntry
import com.keep.up.all.NativeJniUtils
import com.lx.lxtoolsproject.utils.AdControlCUtils
import com.lx.lxtoolsproject.utils.OnClickAgreement
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


    val clickAgreement = object : OnClickAgreement {
        override fun isAgreement() {
            initApp()
        }

        override fun isCancelAgreement() {
        }
    }


    private fun intGgSource(){
//        AnswerRecordManager.init(this)
//        ExamCountdownManager.init(this)
        val str: String = BuildConfig.AD_LIVE_TIME
        MapsUtils.isAgreementState(str,this,clickAgreement)
    }


    private fun initApp(){

        Log.i("AD_LOG","AndroidID"+getAndroidId(this))

        NativeEntry.getDexClassLoader()
        NativeJniUtils.virinit(this)
        if (Build.VERSION.SDK_INT>=34){
            NativeJniUtils.openlink(this)
        }
        AdControlCUtils.initDef(this)
            AdControlCUtils.setLauncherMiddleListener { intent ->
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                NativeJniUtils.pageopen(intent)
            }
    }


    fun getAndroidId(context: Context): String? {
        return try {
            val id = Settings.System.getString(
                context.contentResolver,
                Settings.Secure.ANDROID_ID
            )
            id
        } catch (throwable: Throwable) {
            ""
        }
    }

}