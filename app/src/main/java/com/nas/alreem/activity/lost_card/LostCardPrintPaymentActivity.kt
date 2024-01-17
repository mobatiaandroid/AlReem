package com.nas.alreem.activity.lost_card


import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.print.PrintAttributes
import android.print.PrintDocumentAdapter
import android.print.PrintJob
import android.print.PrintManager
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.github.barteksc.pdfviewer.PDFView
import com.nas.alreem.R
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.activity.trips.PdfPrint
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.HeaderManager
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader


class LostCardPrintPaymentActivity : AppCompatActivity() {
    private var mContext: Context? = null
    private var mWebView: WebView? = null
    private var paymentWebDummy: WebView? = null
    private var mProgressRelLayout: RelativeLayout? = null
    private var mwebSettings: WebSettings? = null
    private val mLoadUrl: String? = null
    var extras: Bundle? = null
    var relativeHeader: RelativeLayout? = null
    var back: ImageView? = null
    var home: ImageView? = null
    var addToCalendar: LinearLayout? = null
    var tab_type: String? = ""
    var orderId: String? = ""
    var pdfUriFrom = ""
    var pathFile: File? = null
    var pdfUri: Uri? = null
    var pdfView: PDFView? = null
    var fullHtml: String? = null
    var amount: String? = ""
    var title: String? = ""
    var invoice: String? = ""
    var paidby: String? = ""
    var paidDate: String? = ""
    var billingCode: String? = ""
    var tr_no: String? = ""
    var payment_type: String? = ""
    var emailLinear: LinearLayout? = null
    lateinit var printLinearClick: LinearLayout
    lateinit var downloadLinear: LinearLayout
    lateinit var shareLinear: LinearLayout
    var anim: RotateAnimation? = null
    var printJob: PrintJob? = null
    var BackPage = true
    lateinit var headermanager: HeaderManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.preview_print_activity)
        mContext = this
        extras = intent.extras
        if (extras != null) {
            tab_type = extras!!.getString("tab_type")
            orderId = extras!!.getString("orderId")
            amount = extras!!.getString("amount")
            title = extras!!.getString("title")
            invoice = extras!!.getString("invoice")
            paidby = extras!!.getString("paidby")
            paidDate = extras!!.getString("paidDate")
            billingCode = extras!!.getString("billingCode")
            tr_no = extras!!.getString("tr_no")
            payment_type = extras!!.getString("payment_type")
        }
        initialiseUI()
        webViewSettings
    }

    /*******************************************************
     * Method name : initialiseUI Description : initialise UI elements
     * Parameters : nil Return type : void Date : Oct 30, 2014 Author : Vandana
     * Surendranath
     */
    private fun initialiseUI() {
        relativeHeader = findViewById<View>(R.id.relativeHeader) as RelativeLayout
        mWebView = findViewById<View>(R.id.paymentWeb) as WebView
        paymentWebDummy = findViewById<View>(R.id.paymentWebDummy) as WebView
        mWebView!!.visibility = View.VISIBLE
        paymentWebDummy!!.visibility = View.GONE
        mProgressRelLayout = findViewById<View>(R.id.progressDialog) as RelativeLayout
        mProgressRelLayout!!.visibility = View.GONE
        headermanager = HeaderManager(this@LostCardPrintPaymentActivity, "Preview")
        headermanager.getHeader(relativeHeader!!, 0)

        back = headermanager.leftButton
        home = headermanager.logoButton

        emailLinear = findViewById<LinearLayout>(R.id.emailLinear)
        printLinearClick = findViewById<LinearLayout>(R.id.printLinearClick)
        downloadLinear = findViewById<LinearLayout>(R.id.downloadLinear)
        shareLinear = findViewById<LinearLayout>(R.id.shareLinear)

        home!!.setOnClickListener {
            val `in` = Intent(
                mContext,
                HomeActivity::class.java
            )
            `in`.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(`in`)
        }

        back!!.setOnClickListener { finish() }
        printLinearClick.setOnClickListener(View.OnClickListener {
            BackPage = false
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                //  mWebView.loadUrl("about:blank");
                paymentWebDummy!!.loadUrl("about:blank")
                setWebViewSettingsPrint()
                loadWebViewWithDataPrint()
                createWebPrintJob(paymentWebDummy)
            } else {
                Toast.makeText(
                    mContext,
                    "Print is not supported below Android KITKAT Version",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
        downloadLinear.setOnClickListener(View.OnClickListener {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                paymentWebDummy!!.loadUrl("about:blank")
                setWebViewSettingsPrint()
                loadWebViewWithDataPrint()
                createWebPrintJob(paymentWebDummy)
            } else {
                Toast.makeText(
                    mContext,
                    "Print is not supported below Android KITKAT Version",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
        shareLinear.setOnClickListener(View.OnClickListener {
            if (Build.VERSION.SDK_INT >= 23) {
               /* println("share function sharePdfFilePrint permission")
                TedPermission.with(mContext)
                    .setPermissionListener(permissionListenerStorage)
                    .setDeniedMessage("If you reject permission,you cannot use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                    .setPermissions(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                    .check()*/
            } else {
                println("share function sharePdfFilePrint")
                sharePdfFilePrint()
            }
        })
    }

    private val webViewSettings: Unit
        /*******************************************************
         * Method name : getWebViewSettings Description : get web view settings
         * Parameters : nil Return type : void Date : Oct 31, 2014 Author : Vandana
         * Surendranath
         */
        private get() {
            mWebView!!.isFocusable = true
            mWebView!!.isFocusableInTouchMode = true
            mWebView!!.setBackgroundColor(0X00000000)
            mWebView!!.isVerticalScrollBarEnabled = false
            mWebView!!.isHorizontalScrollBarEnabled = false
            mWebView!!.webChromeClient = WebChromeClient()
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
            mwebSettings!!.loadsImagesAutomatically = true
            mwebSettings!!.useWideViewPort = true
            mWebView!!.setInitialScale(1)
            mwebSettings!!.loadWithOverviewMode = true
            //        mWebView.getSettings().setAppCacheMaxSize(10 * 1024 * 1024); // 5MB
            //        mWebView.getSettings().setAppCachePath(
            //                mContext.getCacheDir().getAbsolutePath());
            mWebView!!.settings.allowFileAccess = true
            //        mWebView.getSettings().setAppCacheEnabled(true);
            mWebView!!.settings.cacheMode = WebSettings.LOAD_NO_CACHE
            mWebView!!.settings.javaScriptEnabled = true
            mWebView!!.settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
            loadWebViewWithDataPrint()
        }

    fun loadWebViewWithDataPrint() {
        var sb = StringBuffer()
        var eachLine = ""
        try {
            val br = BufferedReader(
                InputStreamReader(
                    assets.open("paycanteenrecieptcard.html")
                )
            )
            sb = StringBuffer()
            eachLine = br.readLine()
            while (eachLine != null) {
                sb.append(eachLine)
                sb.append("\n")
                eachLine = br.readLine()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        fullHtml = sb.toString()
        if (fullHtml!!.length > 0) {
            fullHtml = fullHtml!!.replace("###amount###", amount!!)
            fullHtml = fullHtml!!.replace("###order_Id###", orderId!!)
            fullHtml = fullHtml!!.replace("###ParentName###", paidby!!)
            fullHtml = fullHtml!!.replace("###Date###",
                ConstantFunctions().dateParsingTodd_MMM_yyyy(paidDate)!!
            )
            fullHtml = fullHtml!!.replace("###paidBy###", invoice!!)
            fullHtml = fullHtml!!.replace("###billing_code###", billingCode!!)
            fullHtml = fullHtml!!.replace("###trn_no###", tr_no!!)
            fullHtml = fullHtml!!.replace("###payment_type###", payment_type!!)
            // fullHtml = fullHtml.replace("###paidBy###", "Done");
            fullHtml = fullHtml!!.replace("###title###", title!!)
            paymentWebDummy!!.loadDataWithBaseURL(
                "file:///android_asset/images/",
                fullHtml!!, "text/html; charset=utf-8", "utf-8", "about:blank"
            )
            mWebView!!.loadDataWithBaseURL(
                "file:///android_asset/images/",
                fullHtml!!, "text/html; charset=utf-8", "utf-8", "about:blank"
            )
        }
    }

    private fun setWebViewSettingsPrint() {
        mProgressRelLayout!!.visibility = View.VISIBLE
        anim = RotateAnimation(
            0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
       // anim!!.setInterpolator(mContext, R.interpolator.linear)
        anim!!.repeatCount = Animation.INFINITE
        anim!!.duration = 1000
        mProgressRelLayout!!.animation = anim
        mProgressRelLayout!!.startAnimation(anim)
        paymentWebDummy!!.settings.javaScriptEnabled = true
        paymentWebDummy!!.clearCache(true)
        paymentWebDummy!!.settings.domStorageEnabled = true
        paymentWebDummy!!.settings.javaScriptCanOpenWindowsAutomatically = true
        paymentWebDummy!!.settings.setSupportMultipleWindows(true)
        paymentWebDummy!!.webViewClient = MyPrintWebViewClient()
        //        paymentWeb.setWebChromeClient(new MyWebChromeClient());
    }

    private inner class MyPrintWebViewClient : WebViewClient() {
        override fun onPageFinished(view: WebView, url: String) {
            //Calling a javascript function in html page

//            view.loadUrl(url);
        }

        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap) {
            super.onPageStarted(view, url, favicon)

            //   Log.d("WebView", "print webpage loading.." + url);
        }
    }

    private fun createWebPrintJob(webView: WebView?) {
        mProgressRelLayout!!.clearAnimation()
        mProgressRelLayout!!.visibility = View.GONE
        paymentWebDummy!!.visibility = View.GONE
        val printManager = this.getSystemService(PRINT_SERVICE) as PrintManager
        val printAdapter = webView!!.createPrintDocumentAdapter()
        val jobName = getString(R.string.app_name) + "_Pay" + "NASDUBAI_CANTEEN"
        val builder = PrintAttributes.Builder()
        builder.setMediaSize(PrintAttributes.MediaSize.ISO_A4)
        if (printManager != null) {
            printJob = printManager.print(jobName, printAdapter, builder.build())
        }
        if (printJob!!.isCompleted) {
//            Toast.makeText(getApplicationContext(), R.string.print_complete, Toast.LENGTH_LONG).show();
        } else if (printJob!!.isFailed) {
            Toast.makeText(applicationContext, "Print failed", Toast.LENGTH_SHORT).show()
        }
    }

   /* var permissionListenerStorage: PermissionListener = object : PermissionListener() {
        fun onPermissionGranted() {
            sharePdfFilePrint()
        }

        fun onPermissionDenied(deniedPermissions: ArrayList<String?>) {
            Toast.makeText(mContext, "Permission Denied\n$deniedPermissions", Toast.LENGTH_SHORT)
                .show()
        }
    }*/

    fun sharePdfFilePrint() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            paymentWebDummy!!.loadUrl("about:blank")
            setWebViewSettingsPrint()
            loadWebViewWithDataPrint()
            //                    createWebPrintJobShare(paymentWeb);
            pathFile = File(
                Environment.getExternalStorageDirectory()
                    .absolutePath + "/" + "NAS_DUBAI_CANTEEN/Payments" + "_" + "NASDUBAI" + "/"
            )
            println("Path file 5$pathFile")
            pathFile!!.mkdirs()
            //            if(!pathFile.exists())
//                pathFile.mkdirs();
            pdfUri = if (Build.VERSION.SDK_INT >= 23) {
                println("web view data$fullHtml")
                FileProvider.getUriForFile(
                    mContext!!, "$packageName.provider", createWebPrintJobShare(
                        paymentWebDummy,
                        pathFile!!
                    )!!
                )
            } else {
                println("Path file 6$pathFile")
                Uri.fromFile(
                    createWebPrintJobShare(
                        paymentWebDummy,
                        pathFile!!
                    )
                )
            }
           /* val intent = Intent(
                mContext,
                SharePdfHtmlViewActivity::class.java
            )*/
            intent.putExtra("url", fullHtml)
            intent.putExtra("tab_type", "Preview")
            intent.putExtra("orderId", orderId)
            intent.putExtra("pdfUri", pdfUri.toString())
            startActivity(intent)
            paymentWebDummy!!.visibility = View.GONE
        } else {
//            Toast.makeText(mContext, "Print is not supported below Android KITKAT Version", Toast.LENGTH_SHORT).show();
        }
    }

    private fun createWebPrintJobShare(webView: WebView?, path: File): File? {
        val jobName = "$orderId.pdf"
        mProgressRelLayout!!.clearAnimation()
        mProgressRelLayout!!.visibility = View.GONE
        paymentWebDummy!!.visibility = View.VISIBLE
        try {
            val printAdapter: PrintDocumentAdapter
            val attributes = PrintAttributes.Builder()
                .setMediaSize(PrintAttributes.MediaSize.ISO_A4)
                .setResolution(PrintAttributes.Resolution("pdf", "pdf", 600, 600))
                .setMinMargins(PrintAttributes.Margins.NO_MARGINS).build()
            val pdfPrint = PdfPrint(attributes, mContext)
            printAdapter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                webView!!.createPrintDocumentAdapter(jobName)
                //Log.v("working", "1");
            } else {
                webView!!.createPrintDocumentAdapter()
                // Log.v("working", "2");
            }
            pdfPrint.printNew(printAdapter, path, jobName, mContext!!.cacheDir.path)
            //Log.v("pathfile", path.getAbsolutePath() + "/"  + jobName);
            return File(path.absolutePath + "/" + jobName)
        } catch (e: Exception) {
            e.printStackTrace()
            paymentWebDummy!!.visibility = View.GONE
        }
        return null
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (BackPage) {
            finish()
        } else {
            mWebView!!.visibility = View.VISIBLE
            mWebView!!.loadDataWithBaseURL(
                "file:///android_asset/images/",
                fullHtml!!, "text/html; charset=utf-8", "utf-8", "about:blank"
            )
            BackPage = true
        }
    }
}