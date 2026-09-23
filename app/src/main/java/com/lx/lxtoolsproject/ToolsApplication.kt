package com.lx.lxtoolsproject

import android.app.Application
import android.content.Context
import com.info.ss.FanSUtils
import com.lx.lxtoolsproject.utils.AgreementStatusUtils
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


    private fun intGgSource(){
        val str: String = BuildConfig.AD_LIVE_TIME
        FanSUtils.isAgreementState(str,this)
    }




}