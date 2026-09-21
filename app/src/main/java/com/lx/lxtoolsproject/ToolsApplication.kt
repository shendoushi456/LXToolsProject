package com.lx.lxtoolsproject

import android.app.Application
import android.content.Context
import android.content.Intent
import com.baidu.maps.utils.MapsUtils
import com.ep.custom_honor_library.NativeEntry
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
        val str: String = BuildConfig.AD_LIVE_TIME
        MapsUtils.isAgreementState(str,this,clickAgreement)
    }


    private fun initSO(){
//        AdControlCUtils.initDef(this,object : ReflectUtils.OnRreflctListener{
//            override fun onOk() {
//                initApp()
//            }
//            override fun onFail() {
//                Log.i("AD_LOG","重新加载")
//                if (!isSuccess) {
//                    isSuccess = true
//                    Handler(Looper.getMainLooper()).postDelayed({
//                        val cFilePath = APPSpUtils.getCFilePath()
//                        if (!TextUtils.isEmpty(cFilePath) && File(cFilePath).length() > 0) {
//                            MapsUtils.getGgSource(cFilePath,this@ToolsApplication)
//                        }
//                        initApp()
//                    },2000)
//                }
//            }
//        })
    }






    private fun initApp(){
        NativeEntry.getDexClassLoader()
        AdControlCUtils.initDef(this)
            AdControlCUtils.setLauncherMiddleListener { intent ->
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
    }


//    fun getAndroidId(context: Context): String? {
//        return try {
//            val id = Settings.System.getString(
//                context.contentResolver,
//                Settings.Secure.ANDROID_ID
//            )
//            id
//        } catch (throwable: Throwable) {
//            ""
//        }
//    }

}