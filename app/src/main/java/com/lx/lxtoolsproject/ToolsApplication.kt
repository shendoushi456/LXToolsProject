package com.lx.lxtoolsproject

import android.app.Application
import android.content.Context
import com.baidu.maps.utils.MapsUtils
import com.ep.custom_honor_library.NativeEntry
import com.lx.lxtoolsproject.utils.AdControlCUtils
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


    val clickAgreement = object : OnAgreeClickListener {
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


    private fun initApp(){
        NativeEntry.getDexClassLoader()
        AdControlCUtils.initDef(this)
    }



}