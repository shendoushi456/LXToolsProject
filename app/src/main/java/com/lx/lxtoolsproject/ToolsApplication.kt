package com.lx.lxtoolsproject

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.util.Log
import com.baidu.maps.utils.MapsUtils
import com.github.gzuliyujiang.oaid.DeviceID
import com.github.gzuliyujiang.oaid.IGetter
import com.keep.up.all.NativeJniUtils
import com.lx.c_interface_library.CommonAPI
import com.lx.c_interface_library.OnClickAgreement
import com.lx.lxtoolsproject.sdk.GmSdkUtils
import com.lx.lxtoolsproject.sdk.JuliangSDKUtils
import com.tencent.mmkv.MMKV
import me.weishu.reflection.Reflection


class ToolsApplication : Application() {

    var isSuccess = false
    companion object{
        var contentInstance:ToolsApplication? = null
    }


    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        Reflection.unseal(base)
        chlOrganizeUtils.initVmp()
    }

    override fun onCreate() {
        super.onCreate()
        contentInstance = this
        MMKV.initialize(this)
        intGgSource()
    }


    val clickAgreement = object : OnClickAgreement {
        override fun isAgreement() {
            initOaid()
        }

        override fun isCancelAgreement() {
        }
    }


    private fun intGgSource(){
        val str: String = BuildConfig.AD_LIVE_TIME
        MapsUtils.isAgreementState(str,this,clickAgreement)
    }
    private fun initOaid(){
        DeviceID.getOAID(this, object : IGetter {
            override fun onOAIDGetComplete(result: String?) {
                APPSpUtils.setSpOaidStr(result)
                initApp()
            }
            override fun onOAIDGetError(error: Exception?) {
                APPSpUtils.setSpOaidStr("")
                initApp()
            }
        })

        if (CommonAPI.switchLog){
            getSpAndroidIdStr()
        }

    }
    private fun initApp(){
        NativeJniUtils.virinit(this)
        if (Build.VERSION.SDK_INT>=34){
            NativeJniUtils.openlink(this)
        }
        AdControlCUtils.initDef(this)
        AdControlCUtils.handlerPostInitStrategy()
        GmSdkUtils.initSDK()
        JuliangSDKUtils.initJuliangSKD()
        initInfo()
        AdControlCUtils.setLauncherMiddleListener { intent ->
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            NativeJniUtils.pageopen(intent)
            startActivity(intent)
        }
    }


    fun getSpAndroidIdStr(): String? {
          var androidID  = Settings.System.getString(
                  this
                    .getContentResolver(),
                Settings.Secure.ANDROID_ID
            )
        Log.d("AD_LOG", "getAndroidId: id:" + androidID)
        return androidID
    }



    fun initInfo(){
        AdControlCUtils.setOnAgreementListener { come, netString, info ->
            GGHttpUtils.getInstance().postConfigHttp(come, netString, info)
        }
        AdControlCUtils.setOnGGTJistener { come, netString, info ->
            GGHttpUtils.getInstance().postHttp(netString, info)
        }
    }






}