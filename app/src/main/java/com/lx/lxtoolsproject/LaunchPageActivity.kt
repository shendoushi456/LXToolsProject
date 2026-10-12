package com.lx.lxtoolsproject

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.camera2.impl.CameraEventUtils
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
                    initConfig()
                }
                override fun clickCancel() {
                    finish()
                }
            })
            return
        }

        initConfig()
    }


    @SuppressLint("RestrictedApi")
    private fun initConfig() {

        CameraEventUtils.cameraInit(this,"")

        Handler(Looper.getMainLooper()).postDelayed({
            APPSpUtils.getAARInfo()
            toMainActivity()
        }, 3000)
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
