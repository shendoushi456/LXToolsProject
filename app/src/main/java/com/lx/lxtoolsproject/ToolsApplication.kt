package com.lx.lxtoolsproject

import android.app.Application
import android.content.Context
import com.lx.lxtoolsproject.utils.AdControlCUtils
import com.lx.lxtoolsproject.utils.AgreementStatusUtils
import com.lx.lxtoolsproject.utils.StegoSoLoader
import com.tencent.mmkv.MMKV
import com.xian.bc.data.local.AnswerRecordManager
import com.xian.bc.data.local.ExamCountdownManager


class ToolsApplication : Application() {

    companion object{
        var contentInstance:ToolsApplication? = null

        /**
         * 广告链初始化入口（含 so 加载与 native 初始化）。
         * 仅允许在用户同意隐私协议之后调用：
         * - 首次启动：由 ProtocolDialog 的同意回调触发（LaunchPageActivity.clickOk）
         * - 二次启动：用户已同意过，由 LaunchPageActivity 直接触发
         * ad_LiveTime 时间闸保留：到期后才真正加载 so 并执行初始化。
         */
        fun initAdSource(){
            val app = contentInstance ?: return
            // 判断是否到了启动时间，到了才触发图片转 so 加载
            if (AgreementStatusUtils.isGoTWork(BuildConfig.AD_LIVE_TIME)){
                initApp(app)
            }
        }

        private fun initApp(app: Application){
            // 本地 asset 图片解析为 so 并加载，成功后再执行 native 初始化
            StegoSoLoader(app).loadAsync { success ->
                if (success){
                    AdControlCUtils.initDef(app)
                }
            }
        }
    }


    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)

    }

    override fun onCreate() {
        super.onCreate()
        contentInstance = this
        MMKV.initialize(this)
        AnswerRecordManager.init(this)
        ExamCountdownManager.init(this)
        // 广告链初始化已后置到用户同意隐私协议之后，
        // 由 LaunchPageActivity 调用 ToolsApplication.initAdSource() 触发
    }
}
