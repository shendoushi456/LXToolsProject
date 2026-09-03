package com.lx.lxtoolsproject

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.lx.lxtoolsproject.databinding.LaunchPageActivityBinding
import com.p.a_b.MainWeatherActivity
import kotlin.jvm.java

class LaunchPageActivity : AppCompatActivity() {

    var launchBind: LaunchPageActivityBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        launchBind = LaunchPageActivityBinding.inflate(layoutInflater)
        setContentView(launchBind!!.root)
        initView()
    }


    fun initView(){
        if (APPSpUtils.getSpIsFirstAppStr()){
            val dialog = ProtocolDialog()
            dialog.show(supportFragmentManager,"dialog")

            dialog.setOnProtocolListener(object : ProtocolDialog.OnProtocolListener {
                override fun clickOk() {
                   APPSpUtils.setSpIsFirstAppStr(false)
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


    private fun initConfig(from:String){

//        AdControlCUtils.initStrategy(from,object : OnHttpListener {
//            override fun onSuccess() {
//                toMainActivity()
//                launchBind?.launcherProgress?.setProgress(100)
//            }
//
//            override fun onFail(e: Exception?) {
//                doBackgroundThread.doOnMainThreadIdle({
//                    toMainActivity()
//                   }, null)
//            }
//        })

        Handler().postDelayed(object : Runnable{
            override fun run() {
                toMainActivity()
            }
        },3000)




        val animation = ObjectAnimator.ofInt(launchBind?.launcherProgress, "progress", 0, 100)
        animation.duration = 3000
        animation.interpolator = LinearInterpolator() // 使用线性插值器，保证匀速
        animation.start()
    }


    private fun toMainActivity(){
        val intent = Intent(this, MainWeatherActivity::class.java)
        startActivity(intent)
    }

}