package com.lx.lxtoolsproject

import android.content.Context
import android.util.Log
import androidx.annotation.Keep
import com.taobao.sophix.PatchStatus
import com.taobao.sophix.SophixApplication
import com.taobao.sophix.SophixEntry
import com.taobao.sophix.SophixManager
import com.taobao.sophix.listener.PatchLoadStatusListener

class SplashApplication: SophixApplication() {

    @Keep
    @SophixEntry(ToolsApplication::class)
    class ReaApplicationStub

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        initSophix()
    }


    private fun initSophix() {
        val appVersion = this.getPackageManager()
            .getPackageInfo(this.getPackageName(), 0)
            .versionName;

        SophixManager.getInstance()
            .setContext(this)
            .setAppVersion(appVersion)
            .setSecretMetaData("335754719-1", "ac8a5d1d27874532b748e1eb21d06711", "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCXdRJGrMhTJY0OT+hgZnZOENVTTJTgg1VFsEbqK0N5derhExiyYGhv5E0o9tx/KpHMkSYBXgv16aJ5XGE7C0i/kh//uM7SZpWR0EZ4xEfLQKLbOZnMIa/2Q2TK5/J81eo5o5WfD7JKbS/HQHXAoN5zeowbfxTnBf+xY0VMHsoJhQIDAQAB")
            .setTags(mutableListOf<String?>("Gray")) // 灰度标签
            .setEnableDebug(true)
            .setPatchLoadStatusStub(object : PatchLoadStatusListener {
                override fun onLoad(mode: Int, code: Int, info: String?, handlePatchVersion: Int) {
                    if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                        Log.i("AD_LOG", "sophix load patch success!");
                    } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                        // 如果需要在后台重启，建议此处用SharePreference保存状态。
                        Log.i("AD_LOG", "sophix preload patch success. restart app to make effect.");
                    }
                }
            }) // 监听补丁加载结果
            .initialize()
    }
}