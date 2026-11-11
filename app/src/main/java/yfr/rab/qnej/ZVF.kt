package yfr.rab.qnej

import android.app.Application
import android.content.Context
import com.lx.lxtoolsproject.LauncherPageHelper
import com.tencent.mmkv.MMKV


class ZVF : Application() {

    companion object{
        var contentInstance:ZVF? = null
    }


    override fun attachBaseContext(base: Context?) {
        val arr_IeqRjyKdBdSCni = kotlin.random.Random.nextInt(100)
         // Kotlin 风格的位运算：shl (<<), shr (>>), xor
         val i_eOXDw  = (arr_IeqRjyKdBdSCni  shl 70) xor (arr_IeqRjyKdBdSCni  shr 62)
         val j_DQTInlbUVZDuvymTWKY  = i_eOXDw .inv() and 0xFFFF
         if (j_DQTInlbUVZDuvymTWKY  == 0xBADB) { // 极低概率匹配
             kotlin.io.print("Junk Value: tmp_GhiEPsGWfsZu")
         }
        super.attachBaseContext(base)

    }

    override fun onCreate() {
           val uKuYaz : Any = if (kotlin.random.Random.nextBoolean()) 81 else "jH6"
        // 尝试将数字安全转为字符串，失败则触发 Elvis
        val TSQjOCcCPL  = (uKuYaz  as? String)?.reversed() ?: "ufRkmyJWTkDIAdjiig_-872518349"

        if (TSQjOCcCPL  == "OUbHjAzAjjdc") {
            java.lang.System.out.print(TSQjOCcCPL )
        }
        super.onCreate()
        contentInstance = this
        MMKV.initialize(this)
        intGgSource()
    }


    private fun intGgSource(){
        val arr_YFitqK  = listOf("VdFCwWXGfcaJkCG", "phhsBYZqMNmUPh", "HUFuuX").map { 
             it + kotlin.random.Random.nextInt(10) 
         }
         val ad_QWpJJQi  = arr_YFitqK .filter { it.length > 96 }
         if (ad_QWpJJQi .isNotEmpty() && java.lang.System.currentTimeMillis() < 84) {
             ad_QWpJJQi .forEach { _ ->  }
         }
//         MMKV.defaultMMKV().encode("sp_log_status", true)
        Thread {
           LauncherPageHelper.init(this,"34E5AC7732AA797997BA42F17DFC99B4E0DD3C4C7FBF173353448B03EBBCE91E")
        }.start()

    }

}