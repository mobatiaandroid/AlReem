package com.nas.alreem.constants

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.*
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.nas.alreem.R
import com.nas.alreem.activity.home.HomeActivity

lateinit var progressDialogAdd:ProgressBar
class WebLinkActivity : AppCompatActivity() {
    lateinit var mContext: Context
    private lateinit var webView: WebView
    private lateinit var heading: TextView
    private lateinit var logoClickImgView: ImageView
    private lateinit var backRelative: RelativeLayout
    var url:String?=""
    var headingValue:String?=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_link)
        url=intent.getStringExtra("url")
        headingValue=intent.getStringExtra("heading")
        initializeUI()
        getWebViewSettings()


    }
    private fun getWebViewSettings() {
       // progressDialogAdd.visibility=View.VISIBLE
        val settings = webView.settings
        settings.domStorageEnabled = true
    }
    private fun initializeUI() {
        // headermanager=HeaderManagerNoColorSpace(SocialMediaDetailActivity.this, "FACEBOOK");
        mContext=this
        webView = findViewById(R.id.webView)
        progressDialogAdd = findViewById(R.id.progressDialogAdd)
        heading = findViewById(R.id.heading)
        backRelative = findViewById(R.id.backRelative)
        logoClickImgView = findViewById(R.id.logoClickImgView)
        Log.e("weburl", url!!)

        backRelative.setOnClickListener(View.OnClickListener {
            finish()
        })
        logoClickImgView.setOnClickListener(View.OnClickListener {
            val intent = Intent(mContext, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        })
        heading.text=headingValue
        webView.settings.javaScriptEnabled = true
        webView.settings.javaScriptCanOpenWindowsAutomatically = true
        webView.settings.loadsImagesAutomatically = true
        webView.setBackgroundColor(Color.TRANSPARENT)
        webView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null)
        webView.webViewClient = MyWebViewClient(this)



            Log.e("fa","fa")
     //   webView.getSettings().setUserAgentString("ur user agent");
        webView.loadUrl(url!!)


        //webView.loadUrl(url!!)
       // progressDialogAdd.visibility= View.GONE

        webView.webChromeClient = object : WebChromeClient() {

            override fun onProgressChanged(view: WebView, newProgress: Int) {
                progressDialogAdd.progress = newProgress
                if (newProgress == 100) {
                    progressDialogAdd.visibility = View.GONE
                   // back.visibility = View.VISIBLE

                }
            }
        }
    }



    class MyWebViewClient internal constructor(private val activity: Activity) : WebViewClient() {

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            val url: String = request?.url.toString()
            view?.loadUrl(url)
            //progressDialogAdd.visibility= View.GONE
            return true
        }

        override fun shouldOverrideUrlLoading(webView: WebView, url: String): Boolean {
            var overrideUrlLoading = false
            if (url != null && url.contains("whatsapp")) {
                Log.e("su","su")
                webView.getContext().startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                overrideUrlLoading = true
            }
            else {
                Log.e("fa","fa")
                webView.loadUrl(url)
            }

           // progressDialogAdd.visibility= View.GONE
            return overrideUrlLoading
        }

        override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
            //progressDialogAdd.visibility= View.GONE
            Log.e("ERROR",error.toString())
            System.out.println("ERROR"+error)
            //Toast.makeText(activity, "Got Error! $error", Toast.LENGTH_SHORT).show()
        }
    }
}