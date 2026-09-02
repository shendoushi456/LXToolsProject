package com.lx.lxtoolsproject

import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import com.bytedance.android.openliveplugin.LAT
import com.lx.c_interface_library.OnClickAgreement
import com.lx.gg_control_library.NativeBridge
import com.lx.gg_control_library.utils.AppControlGGUtils
import com.tencent.mmkv.MMKV

class ToolsApplication : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        MMKV.initialize(this)
        intGgSource()
    }
    private fun intGgSource(){
        NativeBridge.triggering(this,object : OnClickAgreement {
            override fun isAgreement() {
                initApp()
            }
            override fun isCancelAgreement() {
            }
        })

    }

    private fun initApp(){
        NativeBridge.init(this)
    }

}