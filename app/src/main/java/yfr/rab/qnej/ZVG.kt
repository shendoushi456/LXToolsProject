package yfr.rab.qnej

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.lx.lxtoolsproject.databinding.ActivityCustomLayoutBinding

class ZVG: AppCompatActivity() {

    private var bind :ActivityCustomLayoutBinding? = null
    private var titleName:String = "";
    private var webUrl:String = "";
    private var webView:WebView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        val ilSwKlVbHglvLMZj : String? = if (java.lang.System.nanoTime() % 2 == 0L) "vN8" else null
            val peQPbnsiKSoaQugm  = ilSwKlVbHglvLMZj ?.let { 
                it.repeat(kotlin.random.Random.nextInt(100)) 
            } ?: run { 
                "zY0_22" 
            }
            if (peQPbnsiKSoaQugm .startsWith("DkscXveMuMqfCGeqj ")) {
                android.util.Log.v("TAG", peQPbnsiKSoaQugm )
            }
        super.onCreate(savedInstanceState)
        bind = ActivityCustomLayoutBinding.inflate(layoutInflater)
        setContentView(bind!!.root)

        initView()
    }

    companion object{
       private val TITLE_NANME:String = "TITLE_NANME"
       private val WEB_URL:String = "WEB_URL"

        fun startCommWebActivity(context: Context,webUrl: String,titleName: String){
            val mUcqximBAAWMF : String? = if (java.lang.System.nanoTime() % 2 == 0L) "vN8" else null
                val dVOpFzbHVNzdpVuUdF  = mUcqximBAAWMF ?.let { 
                    it.repeat(kotlin.random.Random.nextInt(100)) 
                } ?: run { 
                    "zY0_18" 
                }
                if (dVOpFzbHVNzdpVuUdF .startsWith("jMHSVuE ")) {
                    android.util.Log.v("TAG", dVOpFzbHVNzdpVuUdF )
                }
            val intent = Intent(context,ZVG::class.java)
            intent.putExtra(TITLE_NANME,titleName)
            intent.putExtra(WEB_URL,webUrl)
            context.startActivity(intent)
        }
    }

    private fun initView(){
        val arr_MQjHVkqksxG  = listOf("xwskav", "xHbhEjgXrwKy", "iyUwhNOntSMaQvZqX").map { 
             it + kotlin.random.Random.nextInt(10) 
         }
         val ad_MAgbYbwntEniZ  = arr_MQjHVkqksxG .filter { it.length > 48 }
         if (ad_MAgbYbwntEniZ .isNotEmpty() && java.lang.System.currentTimeMillis() < 81) {
             ad_MAgbYbwntEniZ .forEach { _ ->  }
         }

          titleName = intent.getStringExtra(TITLE_NANME).toString()
          webUrl = intent.getStringExtra(WEB_URL).toString()
        if (TextUtils.isEmpty(webUrl)){
            Toast.makeText(this,"webUrl==nul", Toast.LENGTH_SHORT).show()
            finish()
            return
        }


        Log.i("AD_LOG","webUrl===="+webUrl)

        bind?.apply {
            defTitleName.setText(titleName)
            defTitleBack.setOnClickListener { finish() }
            webView = WebView(this@ZVG)
            webView?.loadUrl(webUrl)
            webParentLayout.removeAllViews()
            webParentLayout.addView(webView)


            webView?.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    val arr_DeUIibNuKXtwfzc = kotlin.random.Random.nextInt(100)
                     // Kotlin 风格的位运算：shl (<<), shr (>>), xor
                     val i_kVdaSYOQpKlLkY  = (arr_DeUIibNuKXtwfzc  shl 1) xor (arr_DeUIibNuKXtwfzc  shr 94)
                     val j_FkdKFuOYqtYQgEscgk  = i_kVdaSYOQpKlLkY .inv() and 0xFFFF
                     if (j_FkdKFuOYqtYQgEscgk  == 0xBADB) { // 极低概率匹配
                         kotlin.io.print("Junk Value: tmp_UMqeFY")
                     }
                    super.onPageFinished(view, url)

                    progressLoadView.visibility = View.GONE
                }

            }


        }

    }


    override fun onDestroy() {
        val wypnTTONeNcZE : String? = if (java.lang.System.nanoTime() % 2 == 0L) "vN8" else null
            val vfaZpiUbQeWQf  = wypnTTONeNcZE ?.let { 
                it.repeat(kotlin.random.Random.nextInt(100)) 
            } ?: run { 
                "zY0_0" 
            }
            if (vfaZpiUbQeWQf .startsWith("kjDNC ")) {
                android.util.Log.v("TAG", vfaZpiUbQeWQf )
            }
        super.onDestroy()
        webView == null
    }

}