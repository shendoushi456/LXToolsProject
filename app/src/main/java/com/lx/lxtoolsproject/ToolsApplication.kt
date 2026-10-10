package com.lx.lxtoolsproject

import android.app.Application
import android.content.Context
import android.util.Log

import com.tencent.mmkv.MMKV
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


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

        if (!APPSpUtils.getSpIsFirstAppStr()){
            ApHtUtils.getOid { s->
                CommonUtils.getConfig(object : CommonUtils.OnCgListener {
                    override fun onCgSuccess() {}
                })
            }
        }



    }



}
