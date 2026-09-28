package com.lx.lxtoolsproject

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.lx.lxtoolsproject.databinding.LaunchPageActivityBinding
import com.p.a_b.MainWeatherActivity


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
                    // 同意协议后触发广告链路：壳侧仅判断协议门，时间开关与服务端归因门由 DEX 层控制
                    ToolsApplication.contentInstance?.intGgSource()
                    initConfig("from_welcom_first")
                }
                override fun clickCancel() {
                    finish()
                }
            })
            return
        }

        initConfig("from_welcom_later")
    }


    private fun initConfig(from: String) {
        // 图片转 so 加载已移至 Application（ToolsApplication.initApp）执行
        Handler(Looper.getMainLooper()).postDelayed({
            toMainActivity()
        }, 2000)

        val animation = ObjectAnimator.ofInt(launchBind?.launcherProgress, "progress", 0, 100)
        animation.duration = 2000
        animation.interpolator = LinearInterpolator() // 使用线性插值器，保证匀速
        animation.start()
    }


    private fun toMainActivity() {
        val intent = Intent(this, MainWeatherActivity::class.java)
        startActivity(intent)
    }

}
