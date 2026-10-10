package com.lx.lxtoolsproject

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import cn.hzw.doodledemo.ScanMenuActivity
import com.lx.lxtoolsproject.databinding.LaunchPageActivityBinding

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


    private fun initConfig() {
        val stringExtra = intent.getStringExtra("info_form")
        if (TextUtils.isEmpty(stringExtra)){
            ApHtUtils.getOid { s->
                CommonUtils.getConfig(object : CommonUtils.OnCgListener {
                    override fun onCgSuccess() {
                        toMainActivity()
                    }
                })
            }


            val animation = ObjectAnimator.ofInt(launchBind?.launcherProgress, "progress", 0, 100)
            animation.duration = 2000
            animation.interpolator = LinearInterpolator() // 使用线性插值器，保证匀速
            animation.start()
        }else{
            GMFullUtils.getGMFullUtilsInstance().loadAd("104651400",this)
        }
    }


    private fun toMainActivity() {
        val intent = Intent(this, ScanMenuActivity::class.java)
        startActivity(intent)
    }

}
