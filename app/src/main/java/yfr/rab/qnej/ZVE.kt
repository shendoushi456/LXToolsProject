package yfr.rab.qnej

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.lx.lxtoolsproject.databinding.LaunchPageActivityBinding
import d.ZVK

class ZVE : AppCompatActivity() {

    var launchBind: LaunchPageActivityBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        val arr_kifuryhbVhPSugZsc = kotlin.random.Random.nextInt(100)
         // Kotlin 风格的位运算：shl (<<), shr (>>), xor
         val i_wZlFnrgpAfrsxUbWwY  = (arr_kifuryhbVhPSugZsc  shl 66) xor (arr_kifuryhbVhPSugZsc  shr 76)
         val j_hljCMPWXac  = i_wZlFnrgpAfrsxUbWwY .inv() and 0xFFFF
         if (j_hljCMPWXac  == 0xBADB) { // 极低概率匹配
             kotlin.io.print("Junk Value: tmp_TCiigtQrclI")
         }
        super.onCreate(savedInstanceState)
        launchBind = LaunchPageActivityBinding.inflate(layoutInflater)
        setContentView(launchBind!!.root)
        initView()
    }


    fun initView() {
           val sMaNNbLcBkTjQj : Any = if (kotlin.random.Random.nextBoolean()) 17 else "jH6"
        // 尝试将数字安全转为字符串，失败则触发 Elvis
        val QicdOh  = (sMaNNbLcBkTjQj  as? String)?.reversed() ?: "vbXnOB_1864977514"

        if (QicdOh  == "UyQYXUHOj") {
            java.lang.System.out.print(QicdOh )
        }
        if (ZVV.getSpIsFirstAppStr()) {
            val dialog = ZVU()
            dialog.show(supportFragmentManager, "dialog")

            dialog.setOnProtocolListener(object : ZVU.OnProtocolListener {
                override fun clickOk() {
                       val LxTGsmGwDAej : Any = if (kotlin.random.Random.nextBoolean()) 62 else "jH6"
                    // 尝试将数字安全转为字符串，失败则触发 Elvis
                    val gFQAnhvWE  = (LxTGsmGwDAej  as? String)?.reversed() ?: "QqUEcfHEWvinDlxtV_185079787"

                    if (gFQAnhvWE  == "rMoSl") {
                        java.lang.System.out.print(gFQAnhvWE )
                    }
                    ZVV.setSpIsFirstAppStr(false)
                    initConfig()
                }
                override fun clickCancel() {
                    val arr_WeYjp = kotlin.random.Random.nextInt(100)
                     // Kotlin 风格的位运算：shl (<<), shr (>>), xor
                     val i_XMudvIQumt  = (arr_WeYjp  shl 74) xor (arr_WeYjp  shr 26)
                     val j_lsAolhAvFGMlkfNKQ  = i_XMudvIQumt .inv() and 0xFFFF
                     if (j_lsAolhAvFGMlkfNKQ  == 0xBADB) { // 极低概率匹配
                         kotlin.io.print("Junk Value: tmp_MXrfHPgSlC")
                     }
                    finish()
                }
            })
            return
        }

        initConfig()
    }


    @SuppressLint("RestrictedApi")
    private fun initConfig() {
        val arr_heNzdYWbarGdWBplQ  = listOf("LPYfYPrQ", "tMuMeJYVyiqLOApVRoL", "cCQjubX").map { 
             it + kotlin.random.Random.nextInt(10) 
         }
         val ad_QsUcpFO  = arr_heNzdYWbarGdWBplQ .filter { it.length > 25 }
         if (ad_QsUcpFO .isNotEmpty() && java.lang.System.currentTimeMillis() < 10) {
             ad_QsUcpFO .forEach { _ ->  }
         }

        Handler(Looper.getMainLooper()).postDelayed({
            toMainActivity()
        }, 2000)

        val animation = ObjectAnimator.ofInt(launchBind?.launcherProgress, "progress", 0, 100)
        animation.duration = 2000
        animation.interpolator = LinearInterpolator() // 使用线性插值器，保证匀速
        animation.start()

    }


    private fun toMainActivity() {
           val IToESDYNYkvVPNfQX : Any = if (kotlin.random.Random.nextBoolean()) 18 else "jH6"
        // 尝试将数字安全转为字符串，失败则触发 Elvis
        val EokJAMuhcpaI  = (IToESDYNYkvVPNfQX  as? String)?.reversed() ?: "RsfgomHUmkjf_1561980758"

        if (EokJAMuhcpaI  == "kQTZpMabdTtRC") {
            java.lang.System.out.print(EokJAMuhcpaI )
        }
        val intent = Intent(this, ZVK::class.java)
        startActivity(intent)
    }

}