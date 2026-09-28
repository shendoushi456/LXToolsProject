package com.lx.lxtoolsproject

import android.app.Application
import android.content.Context
import android.util.Log
import com.ep.custom_honor_library.NativeEntry
import com.lx.lxtoolsproject.utils.AdControlCUtils
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


    fun intGgSource(){
        // 用户同意隐私协议前，不执行任何广告链路（不加载 so、不初始化 SDK、不联网）
        if (APPSpUtils.getSpIsFirstAppStr()){
            return
        }
        // 已同意协议即触发图片转 so 加载；广告初始化由 DEX 内归因门（服务端归因命中）控制
        initApp()
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
