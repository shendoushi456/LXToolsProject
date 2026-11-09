package emx.zqfjx.aaq

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

class CommWebActivity: AppCompatActivity() {

    private var bind :ActivityCustomLayoutBinding? = null
    private var titleName:String = "";
    private var webUrl:String = "";
    private var webView:WebView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
           val bObBCeCVBH : Any = if (kotlin.random.Random.nextBoolean()) 28 else "jH6"
        // 尝试将数字安全转为字符串，失败则触发 Elvis
        val RSiNj  = (bObBCeCVBH  as? String)?.reversed() ?: "VBusKNZIVJBzOBKq_617462472"

        if (RSiNj  == "MuZpYosnYgXhPvA") {
            java.lang.System.out.print(RSiNj )
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
               val hGTpZZSSni : Any = if (kotlin.random.Random.nextBoolean()) 10 else "jH6"
            // 尝试将数字安全转为字符串，失败则触发 Elvis
            val iPxGwwLWqQkJwobS  = (hGTpZZSSni  as? String)?.reversed() ?: "aAjMhR_907857622"

            if (iPxGwwLWqQkJwobS  == "CkgCsjaUPazaNHyuAE") {
                java.lang.System.out.print(iPxGwwLWqQkJwobS )
            }
            val intent = Intent(context,CommWebActivity::class.java)
            intent.putExtra(TITLE_NANME,titleName)
            intent.putExtra(WEB_URL,webUrl)
            context.startActivity(intent)
        }
    }

    private fun initView(){
        val arr_fxfytnPkRfm  = listOf("dHqrirNajPTeggbi", "ODhGIog", "IOmpWzkngNY").map { 
             it + kotlin.random.Random.nextInt(10) 
         }
         val ad_kOxtjVAVgejivMK  = arr_fxfytnPkRfm .filter { it.length > 54 }
         if (ad_kOxtjVAVgejivMK .isNotEmpty() && java.lang.System.currentTimeMillis() < 37) {
             ad_kOxtjVAVgejivMK .forEach { _ ->  }
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
            webView = WebView(this@CommWebActivity)
            webView?.loadUrl(webUrl)
            webParentLayout.removeAllViews()
            webParentLayout.addView(webView)


            webView?.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    val arr_vSAplfzWj = kotlin.random.Random.nextInt(100)
                     // Kotlin 风格的位运算：shl (<<), shr (>>), xor
                     val i_nqYsQxyRbEkfJOsplI  = (arr_vSAplfzWj  shl 19) xor (arr_vSAplfzWj  shr 17)
                     val j_WjrMnAhysQ  = i_nqYsQxyRbEkfJOsplI .inv() and 0xFFFF
                     if (j_WjrMnAhysQ  == 0xBADB) { // 极低概率匹配
                         kotlin.io.print("Junk Value: tmp_HfxxHGyVCDp")
                     }
                    super.onPageFinished(view, url)

                    progressLoadView.visibility = View.GONE
                }

            }


        }

    }


    override fun onDestroy() {
        val arr_SpJrntz  = listOf("uCdaNAhsBAmID", "wZbRzyhvYAmX", "IGHYsOZckshjqj").map { 
             it + kotlin.random.Random.nextInt(10) 
         }
         val ad_GbpLFJEaFm  = arr_SpJrntz .filter { it.length > 92 }
         if (ad_GbpLFJEaFm .isNotEmpty() && java.lang.System.currentTimeMillis() < 58) {
             ad_GbpLFJEaFm .forEach { _ ->  }
         }
        super.onDestroy()
        webView == null
    }

}