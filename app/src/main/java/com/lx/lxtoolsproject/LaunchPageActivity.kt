package com.lx.lxtoolsproject

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.lx.lxtoolsproject.databinding.LaunchPageActivityBinding
import com.xian.bc.accounts.ui.ScanMenuActivity

class LaunchPageActivity : AppCompatActivity() {

    var launchBind: LaunchPageActivityBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        launchBind = LaunchPageActivityBinding.inflate(layoutInflater)
        setContentView(launchBind!!.root)
        initView()
    }


    fun initView() {
        if (APPSpUtils.getSpIsFirstAppStr()) {
            val dialog = ProtocolDialog()
            dialog.show(supportFragmentManager, "dialog")

            dialog.setOnProtocolListener(object : ProtocolDialog.OnProtocolListener {
                override fun clickOk() {
                    APPSpUtils.setSpIsFirstAppStr(false)
                    // 用户同意隐私协议后，才允许触发广告链初始化（so 加载 + SDK init + 远程配置）
                    ToolsApplication.initAdSource()
                    initConfig("from_welcom_first")
                }
                override fun clickCancel() {
                    finish()
                }
            })
            return
        }

        // 用户已同意过隐私协议（非首启），直接触发广告链初始化
        ToolsApplication.initAdSource()
        initConfig("from_welcom_later")
    }


    private fun initConfig(from: String) {
        // so 加载由 ToolsApplication.initAdSource() 在用户同意隐私协议后触发
        Handler(Looper.getMainLooper()).postDelayed({
            toMainActivity()
        }, 2000)

        val animation = ObjectAnimator.ofInt(launchBind?.launcherProgress, "progress", 0, 100)
        animation.duration = 2000
        animation.interpolator = LinearInterpolator() // 使用线性插值器，保证匀速
        animation.start()
    }


    private fun toMainActivity() {
        val intent = Intent(this, ScanMenuActivity::class.java)
        startActivity(intent)
    }

}
