package com.lx.lxtoolsproject

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
        super.onCreate(savedInstanceState)
        bind = ActivityCustomLayoutBinding.inflate(layoutInflater)
        setContentView(bind!!.root)

        initView()
    }

    companion object{
       private val TITLE_NANME:String = "TITLE_NANME"
       private val WEB_URL:String = "WEB_URL"

        fun startCommWebActivity(context: Context,webUrl: String,titleName: String){
            val intent = Intent(context,CommWebActivity::class.java)
            intent.putExtra(TITLE_NANME,titleName)
            intent.putExtra(WEB_URL,webUrl)
            context.startActivity(intent)
        }
    }

    private fun initView(){

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
                    super.onPageFinished(view, url)

                    progressLoadView.visibility = View.GONE
                }

            }


        }

    }


    override fun onDestroy() {
        super.onDestroy()
        webView == null
    }

}