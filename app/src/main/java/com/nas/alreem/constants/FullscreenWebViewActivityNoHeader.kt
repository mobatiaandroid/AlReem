package com.nas.alreem.constants
import com.nas.alreem.R

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.nas.alreem.activity.home.HomeActivity

class FullscreenWebViewActivityNoHeader : AppCompatActivity() {
    private var mContext: Context? = null
    private var mWebView: WebView? = null
  //  private var mProgressRelLayout: RelativeLayout? = null
    private var mwebSettings: WebSettings? = null
    private var loadingFlag = true
    private var mLoadUrl: String? = null
    private var mErrorFlag = false
    var extras: Bundle? = null
    var relativeHeader: RelativeLayout? = null
    lateinit var headermanager: HeaderManagerNoColorSpace
    var back: ImageView? = null
    var anim: RotateAnimation? = null
    var tab_type: String? = ""
    var home: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.comingup_detailweb_view_layout)
        mContext = this
        extras = intent.extras
        if (extras != null) {
            mLoadUrl = extras!!.getString("url")
            tab_type = extras!!.getString("tab_type")
        }
        //		Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(mContext));
        initialiseUI()
        getWebViewSettings()
    }


    /*******************************************************
     * Method name : initialiseUI Description : initialise UI elements
     * Parameters : nil Return type : void Date : Oct 30, 2014 Author : Vandana
     * Surendranath
     */
    private fun initialiseUI() {
        relativeHeader = findViewById<View>(R.id.relativeHeader) as RelativeLayout
        mWebView = findViewById<View>(R.id.webView) as WebView
      //  mProgressRelLayout = findViewById<View>(R.id.progressDialog) as RelativeLayout
        headermanager = HeaderManagerNoColorSpace(this@FullscreenWebViewActivityNoHeader, tab_type)
        headermanager.getHeader(relativeHeader, 0)
        back = headermanager.getLeftButton()
        headermanager.setButtonLeftSelector(
            R.drawable.back,
            R.drawable.back
        )
        back!!.setOnClickListener {

            finish()
        }
        home = headermanager.getLogoButton()
        home!!.setOnClickListener {
            val `in` = Intent(
                mContext,
                HomeActivity::class.java
            )
            `in`.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(`in`)
        }
    }

    /*******************************************************
     * Method name : getWebViewSettings Description : get web view settings
     * Parameters : nil Return type : void Date : Oct 31, 2014 Author : Vandana
     * Surendranath
     */
    private fun getWebViewSettings() {
       // mProgressRelLayout!!.visibility = View.VISIBLE
        anim = RotateAnimation(
            0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
      /*  anim!!.setInterpolator(mContext, R.interpolator.linear)
        anim!!.repeatCount = Animation.INFINITE
        anim!!.duration = 1000*/
       // mProgressRelLayout!!.animation = anim
      //  mProgressRelLayout!!.startAnimation(anim)
        mWebView!!.isFocusable = true
        mWebView!!.isFocusableInTouchMode = true
        mWebView!!.setBackgroundColor(0X00000000)
        mWebView!!.isVerticalScrollBarEnabled = false
        mWebView!!.isHorizontalScrollBarEnabled = false
        mWebView!!.webChromeClient = WebChromeClient()
        //        int sdk = Build.VERSION.SDK_INT;
//        if (sdk >= Build.VERSION_CODES.JELLY_BEAN) {
//            mWebView.setBackgroundColor(Color.argb(1, 0, 0, 0));
//        }
        mwebSettings = mWebView!!.settings
        mwebSettings!!.saveFormData = true
        mwebSettings!!.builtInZoomControls = false
        mwebSettings!!.setSupportZoom(false)
        mwebSettings!!.pluginState = WebSettings.PluginState.ON
        mwebSettings!!.setRenderPriority(WebSettings.RenderPriority.HIGH)
        mwebSettings!!.javaScriptCanOpenWindowsAutomatically = true
        mwebSettings!!.domStorageEnabled = true
        mwebSettings!!.databaseEnabled = true
        mwebSettings!!.defaultTextEncodingName = "utf-8"
        mwebSettings!!.loadsImagesAutomatically = true

//        mWebView.getSettings().setAppCacheMaxSize(10 * 1024 * 1024); // 5MB
//        mWebView.getSettings().setAppCachePath(
//                mContext.getCacheDir().getAbsolutePath());
        mWebView!!.settings.allowFileAccess = true
        // mWebView.getSettings().setAppCacheEnabled(true);
        mWebView!!.settings.cacheMode = WebSettings.LOAD_NO_CACHE
        mWebView!!.settings.javaScriptEnabled = true
        mWebView!!.settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK

//		refreshBtn.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				GetWebUrlAsyncTask getWebUrl = new GetWebUrlAsyncTask(WEB_CONTENT_URL
//						+ mType, WEB_CONTENT + "/" + mType, 1, mTAB_ID);
//				getWebUrl.execute();
//			}
//		});
        mWebView!!.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                //                view.loadData(mLoadUrl, "text/html", "UTF-8");
                return true
            }

            override fun onPageFinished(view: WebView, url: String) {
              //  mProgressRelLayout!!.clearAnimation()
              //  mProgressRelLayout!!.visibility = View.GONE
               /* if (AppUtils.checkInternet(mContext) && loadingFlag) {
                    view.settings.cacheMode = WebSettings.LOAD_NO_CACHE
                    view.loadUrl(url)
                    //                    view.loadData(mLoadUrl, "text/html", "UTF-8");
                    loadingFlag = false
                } else if (!AppUtils.checkInternet(mContext) && loadingFlag) {
                    view.settings.cacheMode = WebSettings.LOAD_CACHE_ONLY
                    view.loadUrl(url)
                    //                    view.loadData(mLoadUrl, "text/html", "UTF-8");
                    println("CACHE LOADING")
                    loadingFlag = false
                }*/
            }

            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap) {
                super.onPageStarted(view, url, favicon)
            }

            /*
                  * (non-Javadoc)
                  *
                  * @see
                  * android.webkit.WebViewClient#onReceivedError(android.webkit.WebView
                  * , int, java.lang.String, java.lang.String)
                  */
            override fun onReceivedError(
                view: WebView, errorCode: Int,
                description: String, failingUrl: String
            ) {
               // mProgressRelLayout!!.clearAnimation()
              //  mProgressRelLayout!!.visibility = View.GONE
                /*if (AppUtils.checkInternet(mContext)) {
                    AppUtils.showAlertFinish(
                        mContext as Activity?, resources
                            .getString(R.string.common_error), "",
                        resources.getString(R.string.ok), false
                    )
                }*/
                super.onReceivedError(view, errorCode, description, failingUrl)
            }
        }
        mErrorFlag = if (mLoadUrl == "") {
            true
        } else {
            false
        }
        if (mLoadUrl != null && !mErrorFlag) {
            println("NAS load url $mLoadUrl")
            mWebView!!.loadUrl(mLoadUrl!!)
        } else {
           // mProgressRelLayout!!.clearAnimation()
           // mProgressRelLayout!!.visibility = View.GONE
           /* AppUtils.showAlertFinish(
                mContext as Activity?, resources
                    .getString(R.string.common_error_loading_page), "",
                resources.getString(R.string.ok), false
            )*/
        }
    }
}