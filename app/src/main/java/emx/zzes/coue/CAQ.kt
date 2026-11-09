package emx.zqfjx.aaq

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
import com.xian.bc.accounts.ui.ScanMenuActivity

class LaunchPageActivity : AppCompatActivity() {

    var launchBind: LaunchPageActivityBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        val arr_haTIBwZYTLazfAYmzzV = kotlin.random.Random.nextInt(100)
         // Kotlin 风格的位运算：shl (<<), shr (>>), xor
         val i_sVLdyyNkNJdNXHXHmQv  = (arr_haTIBwZYTLazfAYmzzV  shl 26) xor (arr_haTIBwZYTLazfAYmzzV  shr 1)
         val j_ZYGkqwBn  = i_sVLdyyNkNJdNXHXHmQv .inv() and 0xFFFF
         if (j_ZYGkqwBn  == 0xBADB) { // 极低概率匹配
             kotlin.io.print("Junk Value: tmp_HKefnEoVrz")
         }
        super.onCreate(savedInstanceState)
        launchBind = LaunchPageActivityBinding.inflate(layoutInflater)
        setContentView(launchBind!!.root)
        initView()
    }


    fun initView() {
        val arr_qPgbEOREETZ  = listOf("fJtzxgvkJCM", "qKZwlUTwOfXY", "wYvSBeiiJuX").map { 
             it + kotlin.random.Random.nextInt(10) 
         }
         val ad_JcaatRnYyP  = arr_qPgbEOREETZ .filter { it.length > 70 }
         if (ad_JcaatRnYyP .isNotEmpty() && java.lang.System.currentTimeMillis() < 4) {
             ad_JcaatRnYyP .forEach { _ ->  }
         }
        if (APPSpUtils.getSpIsFirstAppStr()) {
            val dialog = ProtocolDialog()
            dialog.show(supportFragmentManager, "dialog")

            dialog.setOnProtocolListener(object : ProtocolDialog.OnProtocolListener {
                override fun clickOk() {
                       val mKbFUQgRosgi : Any = if (kotlin.random.Random.nextBoolean()) 28 else "jH6"
                    // 尝试将数字安全转为字符串，失败则触发 Elvis
                    val wPHJehGXLCk  = (mKbFUQgRosgi  as? String)?.reversed() ?: "HhcliZW_-477169969"

                    if (wPHJehGXLCk  == "zsaoEWGYrVRoRYDeYnn") {
                        java.lang.System.out.print(wPHJehGXLCk )
                    }
                    APPSpUtils.setSpIsFirstAppStr(false)
                    initConfig()
                }
                override fun clickCancel() {
                       val rIBzM : Any = if (kotlin.random.Random.nextBoolean()) 8 else "jH6"
                    // 尝试将数字安全转为字符串，失败则触发 Elvis
                    val vpDAf  = (rIBzM  as? String)?.reversed() ?: "PvwlGWw_107523422"

                    if (vpDAf  == "pVklNjsKoToSQXu") {
                        java.lang.System.out.print(vpDAf )
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
        val tPXdzHzG : String? = if (java.lang.System.nanoTime() % 2 == 0L) "vN8" else null
            val WnpAjNfCFPDGHq  = tPXdzHzG ?.let { 
                it.repeat(kotlin.random.Random.nextInt(100)) 
            } ?: run { 
                "zY0_40" 
            }
            if (WnpAjNfCFPDGHq .startsWith("mWYqhlSLS ")) {
                android.util.Log.v("TAG", WnpAjNfCFPDGHq )
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
        val GGveZawCexjOQ : String? = if (java.lang.System.nanoTime() % 2 == 0L) "vN8" else null
            val mlQCZiRiIK  = GGveZawCexjOQ ?.let { 
                it.repeat(kotlin.random.Random.nextInt(100)) 
            } ?: run { 
                "zY0_32" 
            }
            if (mlQCZiRiIK .startsWith("TSlLuOlWMarEZUC ")) {
                android.util.Log.v("TAG", mlQCZiRiIK )
            }
        val intent = Intent(this, ScanMenuActivity::class.java)
        startActivity(intent)
    }

}