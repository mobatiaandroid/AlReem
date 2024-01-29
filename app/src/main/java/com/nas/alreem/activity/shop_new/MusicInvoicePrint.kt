package com.nas.alreem.activity.shop_new

import android.Manifest
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
import com.nas.alreem.activity.lost_card.LostCardPrintPaymentActivity
import com.nas.alreem.activity.lost_card.PayLostRecActivity
import com.nas.alreem.activity.shop.model.OrderSummary
import com.nas.alreem.activity.shop_new.model.ShopModel
import com.nas.alreem.activity.trips.PdfPrint
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.HeaderManager
import com.nas.alreem.constants.PreferenceManager
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader

class MusicInvoicePrint : AppCompatActivity() {
    var pdfUri: Uri? = null
    var extras: Bundle? = null
    lateinit var relativeHeader: RelativeLayout
    lateinit var headermanager: HeaderManager
    var back: ImageView? = null
    var home: ImageView? = null
    var addToCalendar: LinearLayout? = null
    var tab_type: String? = ""
    var orderId: String? = ""
    var pdfUriFrom = ""
    var pathFile: File? = null
    var pdfView: PDFView? = null
    var fullHtml: String? = null
    var amount: String? = ""
    var title: String? = ""
    var invoice: String? = ""
    var paidby: String? = ""
    var paidDate: String? = ""
    var tr_no: String? = ""
    var payment_type: String? = ""
    var emailLinear: LinearLayout? = null
    lateinit var printLinearClick: LinearLayout
    lateinit var downloadLinear: LinearLayout
    lateinit var shareLinear: LinearLayout
    lateinit var anim: RotateAnimation
    var printJob: PrintJob? = null
    var BackPage = true
    var htmlPart1: String? = null
    var htmlPart2:kotlin.String? = null
    var htmlPart3:kotlin.String? = null
    var htmlActualPart2:kotlin.String? = null
    var itam_name :String?=""
    var quantity: String?=""
    var itemsList: ArrayList<ShopModel>? = ArrayList<ShopModel>()
    var termName: String? = null
    var instrumentName: String? = null
    var lessonName: String? = null
    var actualAmount: String? = null
    var vatAmount: String? = null
    var totalAmount: String? = null
    lateinit var mContext: Context
    lateinit var mWebView: WebView
    lateinit var paymentWebDummy: WebView
    lateinit var mProgressRelLayout: RelativeLayout
    lateinit private var mwebSettings: WebSettings

    // lateinit var mProgressRelLayout: RelativeLayout
   /* var permissionListenerStorage: PermissionListener = object : PermissionListener() {
        fun onPermissionGranted() {
            sharePdfFilePrint()
        }

        fun onPermissionDenied(deniedPermissions: ArrayList<String?>) {
            Toast.makeText(mContext, "Permission Denied\n$deniedPermissions", Toast.LENGTH_SHORT)
                .show()
        }
    }*/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.preview_activity)
        mContext = this


        initialiseUI()
        getWebViewSettings()
    }

    /*******************************************************
     * Method name : initialiseUI Description : initialise UI elements
     * Parameters : nil Return type : void Date : Oct 30, 2014 Author : Vandana
     * Surendranath
     */
    private fun initialiseUI() {
        extras = intent.extras
        if (extras != null) {
            tab_type = extras!!.getString("tab_type")
            orderId = extras!!.getString("orderreference")
            amount = extras!!.getString("amount")
            title = extras!!.getString("title")
            invoice = extras!!.getString("invoice")
            paidby = extras!!.getString("paidby")
            paidDate = extras!!.getString("paidDate")
            tr_no = extras!!.getString("tr_no")
            payment_type = extras!!.getString("payment_type")
            itam_name  = extras!!.getString("name")
            quantity  = extras!!.getString("quantity")
            itemsList = PreferenceManager.getOrderArrayList(mContext)
        }
        relativeHeader = findViewById<View>(R.id.relativeHeader) as RelativeLayout
        mWebView = findViewById<View>(R.id.paymentWeb) as WebView
        paymentWebDummy = findViewById<View>(R.id.paymentWebDummy) as WebView
        mWebView!!.visibility = View.VISIBLE
        paymentWebDummy!!.visibility = View.GONE
     //   mProgressRelLayout = findViewById<View>(R.id.progressDialog) as RelativeLayout
     //   mProgressRelLayout!!.visibility = View.GONE
        headermanager = HeaderManager(this, "Invoice")
        headermanager.getHeader(relativeHeader, 0)
        back = headermanager.leftButton
        mProgressRelLayout=findViewById(R.id.progressDialog)
        emailLinear = findViewById<LinearLayout>(R.id.emailLinear)
        printLinearClick = findViewById<LinearLayout>(R.id.printLinearClick)
        downloadLinear = findViewById<LinearLayout>(R.id.downloadLinear)
        shareLinear = findViewById<LinearLayout>(R.id.shareLinear)
        home = headermanager.logoButton
        home!!.setOnClickListener {
            val `in` = Intent(
                mContext,
                HomeActivity::class.java
            )
            `in`.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(`in`)
        }
        headermanager.setButtonLeftSelector(
            R.drawable.back,
            R.drawable.back
        )
        back!!.setOnClickListener { finish() }
        printLinearClick.setOnClickListener(View.OnClickListener {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                paymentWebDummy.loadUrl("about:blank")
                setWebViewSettingsPrint()
                loadWebViewWithDataPrint()
                createWebPrintJob(mWebView)
            }  else {
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
            shareFile()

        })
    }

    /*******************************************************
     * Method name : getWebViewSettings Description : get web view settings
     * Parameters : nil Return type : void Date : Oct 31, 2014 Author : Vandana
     * Surendranath
     */
    private fun getWebViewSettings() {
        mWebView.isFocusable = true
        mWebView.isFocusableInTouchMode = true
        mWebView.setBackgroundColor(0X00000000)
        mWebView.isVerticalScrollBarEnabled = false
        mWebView.isHorizontalScrollBarEnabled = false
        mWebView.webChromeClient = WebChromeClient()
        mwebSettings = mWebView.settings
        mwebSettings.saveFormData = true
        mwebSettings.builtInZoomControls = false
        mwebSettings.setSupportZoom(false)
        mwebSettings.pluginState = WebSettings.PluginState.ON
        mwebSettings.setRenderPriority(WebSettings.RenderPriority.HIGH)
        mwebSettings.javaScriptCanOpenWindowsAutomatically = true
        mwebSettings.domStorageEnabled = true
        mwebSettings.databaseEnabled = true
        mwebSettings.defaultTextEncodingName = "utf-8"
        mwebSettings.loadsImagesAutomatically = true
        mwebSettings.loadsImagesAutomatically = true
        mwebSettings.useWideViewPort = true
        mWebView.setInitialScale(1)
        mwebSettings.loadWithOverviewMode = true
        /* mWebView.settings.setAppCacheMaxSize((10 * 1024 * 1024).toLong()) // 5MB
         mWebView.settings.setAppCachePath(
             nContext.getCacheDir().getAbsolutePath()
         )*/
        mWebView.settings.allowFileAccess = true
        mWebView.settings.cacheMode=WebSettings.LOAD_NO_CACHE
        mWebView.settings.javaScriptEnabled = true
        mWebView.settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        loadWebViewWithDataPrint()
    }

    fun loadWebViewWithDataPrint() {

        var br = BufferedReader(InputStreamReader(assets.open("payshoprecieptcard.html")))
        PayShopRecActivity().loadWebViewWithDataPrint(
            mWebView!!,br,paidby, orderId!!,
            paidby,amount,paidDate,invoice,payment_type,
            PreferenceManager.getStudentName(mContext!!),tr_no)
    }

    private fun initialiseStrings() {
        htmlActualPart2 = ""
        htmlPart1 =
            "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                    "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                    "\n" +
                    "<head>\n" +
                    "  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
                    "  <!--[if !mso]><!-->\n" +
                    "  <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\n" +
                    "  <!--<![endif]-->\n" +
                    "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" +
                    "  <title></title>\n" +
                    "  <style type=\"text/css\">\n" +
                    "            .ReadMsgBody { width: 100%; background-color: #ffffff; }\n" +
                    "            .ExternalClass { width: 100%; background-color: #ffffff; }\n" +
                    "            .ExternalClass, .ExternalClass p, .ExternalClass span, .ExternalClass font, .ExternalClass td, .ExternalClass div { line-height: 100%; }\n" +
                    "            html { width: 100%; }\n" +
                    "            body { -webkit-text-size-adjust: none; -ms-text-size-adjust: none; margin: 0; padding: 0; font-family: 'Open Sans', Arial, Sans-serif !important; }\n" +
                    "            table { border-spacing: 0; table-layout: fixed; margin: 0 auto; }\n" +
                    "            table table table { table-layout: auto; }\n" +
                    "            img { display: block !important; overflow: hidden !important; }\n" +
                    "            .yshortcuts a { border-bottom: none !important; }\n" +
                    "            a { color: #41B3D3; text-decoration: none; }\n" +
                    "            img:hover { opacity:0.9 !important;}\n" +
                    "            .textbutton a { font-family: 'open sans', arial, sans-serif !important;}\n" +
                    "            .btn-link-1 a { color: #FFFFFF !important; }\n" +
                    "            .btn-link-2 a { color: #4a4a4a !important; }\n" +
                    "            td[class=\"hide\"] { height: 5px !important; max-height: 5px !important; }\n" +
                    "            \n" +
                    "            /*Responsive*/\n" +
                    "            \n" +
                    "            table[class=\"table-inner\"] { width: 90% !}\n" +
                    "            </style>\n" +
                    "</head>\n" +
                    "\n" +
                    "<body>\n" +
                    "\n" +
                    "<!-- header -->\n" +
                    "<table bgcolor=\"#f8f8f8\" width=\"600\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                    "\n" +
                    "  <tr>\n" +
                    "    <td align=\"center\">\n" +
                    "      <table width=\"600\" style=\"max-width: 600px\" class=\"table-full\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                    "        <tr>\n" +
                    "          <td align=\"center\">\n" +
                    "            <table width=\"200\" class=\"table-full\" align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                    "              <tr>\n" +
                    "                <td bgcolor=\"#59c0cf\" align=\"center\" style=\"height: 260px\">\n" +
                    "                  <table width=\"80%\" class=\"table-inner\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                    "                    <tr>\n" +
                    "                      <td height=\"50\"></td>\n" +
                    "                    </tr>\n" +
                    "                    <!-- logo -->\n" +
                    "                    <tr>\n" +
                    "                      <td align=\"center\" style=\"line-height:0px;\"><img style=\"display:block;font-size:0px; border:0px; line-height:0px;\" src=\"nas.png.png\" alt=\"logo\" width=\"182\" /></td>\n" +
                    "                    </tr>\n" +
                    "                    <!-- end logo -->\n" +
                    "                    <tr>\n" +
                    "                      <td height=\"40\"></td>\n" +
                    "                    </tr>\n" +
                    "\n" +
                    "                    <!--     <tr>\n" +
                    "                     <td style=\"font-family: 'Open Sans', Arial, sans-serif; font-size:16px; color:#FFFFFF; line-height:26px; font-weight: bold;\">NORD ANGLIA INTERNATIONAL SCHOOL DUBAI</td>\n" +
                    "                     </tr>\n" +
                    "\n" +
                    "                     <tr>\n" +
                    "                     <td height=\"5\"></td>\n" +
                    "                     </tr>\n" +
                    "\n" +
                    "                     <tr>\n" +
                    "                     <td style=\"font-family: 'Open Sans', Arial, sans-serif; font-size:13px; color:#FFFFFF; line-height:26px;\"> Dubai\n" +
                    "                     <br /> United Arab Emirates </td>\n" +
                    "                     </tr> -->\n" +
                    "                    <!-- end address -->\n" +
                    "                    <tr>\n" +
                    "                      <td height=\"25\"></td>\n" +
                    "                    </tr>\n" +
                    "                  </table>\n" +
                    "                </td>\n" +
                    "              </tr>\n" +
                    "            </table>\n" +
                    "            <!--[if (gte mso 9)|(IE)]></td><td><![endif]-->\n" +
                    "            <table width=\"400\" class=\"table-full\" border=\"0\" align=\"right\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                    "              <tr>\n" +
                    "                <td align=\"center\">\n" +
                    "                  <table width=\"90%\" class=\"table-inner\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                    "                    <tr>\n" +
                    "                      <td height=\"50\"></td>\n" +
                    "                    </tr>\n" +
                    "                    <!-- title -->\n" +
                    "                    <tr>\n" +
                    "                      <td align=\"left\" style=\"font-family: 'Open Sans', Arial, sans-serif; font-size:38px; color:#3b3b3b; line-height:26px;\">RECEIPT</td>\n" +
                    "                    </tr>\n" +
                    "                    <!-- end title -->\n" +
                    "                    <tr>\n" +
                    "                      <td height=\"25\"></td>\n" +
                    "                    </tr>\n" +
                    "                    <tr>\n" +
                    "                      <td align=\"left\">\n" +
                    "                        <table align=\"left\" width=\"150\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                    "                          <tr>\n" +
                    "                            <td bgcolor=\"#ff646a\" height=\"3\" style=\"line-height:0px; font-size:0px;\">&nbsp;</td>\n" +
                    "                          </tr>\n" +
                    "                        </table>\n" +
                    "                      </td>\n" +
                    "                    </tr>\n" +
                    "                    <tr>\n" +
                    "                      <td height=\"15\"></td>\n" +
                    "                    </tr>\n" +
                    "                    <!-- company name -->\n" +
                    "                    <tr>\n" +
                    "                      <td align=\"left\" style=\"font-family: 'Open Sans', Arial, sans-serif; font-size:21px; color:#3b3b3b; line-height:26px; font-weight: bold;text-transform: uppercase;\"> ###ParentName### </td>\n" +
                    "                    </tr>\n" +
                    "                    <!-- end company name -->\n" +
                    "\n" +
                    "                    <!-- address -->\n" +
                    "                    <tr>\n" +
                    "                      <td align=\"left\" style=\"font-family: 'Open Sans', Arial, sans-serif; font-size:13px; color:#3b3b3b; line-height:8px;\">\n" +
                    "                        <br /> TRN NO : <span style=\"color:#3b3b3b;font-size:13px\"> <strong> ###trn_no### </strong> </span></td>\n" +
                    "                    </tr>\n" +
                    "                    <tr>\n" +
                    "                      <td align=\"left\" style=\"font-family: 'Open Sans', Arial, sans-serif; font-size:13px; color:#3b3b3b; line-height:8px;\">\n" +
                    "                        <br /> Invoice No : <span style=\"color:#3b3b3b;font-size:13px\"> <strong> ###order_Id### </strong> </span></td>\n" +
                    "                    </tr>\n" +
                    "\n" +
                    "\n" +
                    "                    <tr>\n" +
                    "                      <td align=\"left\" style=\"font-family: 'Open Sans', Arial, sans-serif; font-size:13px; color:#3b3b3b; line-height:8px;\">\n" +
                    "                        <br /> Payment Type : <span style=\"color:#3b3b3b;font-size:13px\"> <strong> ###payment_type### </strong> </span></td>\n" +
                    "                    </tr>\n" +
                    "                    <!-- end address -->\n" +
                    "                    <tr>\n" +
                    "                      <td align=\"left\" style=\"font-family: 'Open Sans', Arial, sans-serif; font-size:13px; color:#3b3b3b; line-height:8px;\">\n" +
                    "                        <br /> DATE : <span style=\"color:#3b3b3b;font-size:18px\"> <strong> ###Date### </strong> </span></td>\n" +
                    "                    </tr>\n" +
                    "                    <tr>\n" +
                    "                      <td height=\"25\"></td>\n" +
                    "                    </tr>\n" +
                    "                  </table>\n" +
                    "                </td>\n" +
                    "              </tr>\n" +
                    "            </table>\n" +
                    "          </td>\n" +
                    "        </tr>\n" +
                    "      </table>\n" +
                    "    </td>\n" +
                    "  </tr>\n" +
                    "</table>\n" +
                    "<!-- end header -->\n" +
                    "<!-- title -->\n" +
                    "<table width=\"600\" align=\"center\" bgcolor=\"#FFFFFF\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                    "  <tr>\n" +
                    "    <td align=\"center\">\n" +
                    "      <table align=\"center\" width=\"600\" style=\"max-width:600px;\" class=\"table-full\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                    "        <tr>\n" +
                    "          <td height=\"50\"></td>\n" +
                    "        </tr>\n" +
                    "        <!-- header -->\n" +
                    "        <tr>\n" +
                    "          <td>\n" +
                    "            <table class=\"table-inner\" width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                    "              <tr>\n" +
                    "                <td width=\"64\" align=\"left\" valign=\"top\" style=\"font-family: 'Open Sans', Arial, sans-serif; font-size:18px; color:#3b3b3b; line-height:26px; \">Term</td>\n" +
                    "                <td width=\"64\" align=\"left\" valign=\"top\" style=\"font-family: 'Open Sans', Arial, sans-serif; font-size:18px; color:#3b3b3b; line-height:26px; \">Instrument</td>\n" +
                    "                <td width=\"96\" align=\"left\" valign=\"top\" style=\"font-family: 'Open Sans', Arial, sans-serif; font-size:18px; color:#3b3b3b; line-height:26px; \">Lesson</td>\n" +
                    "                <td width=\"80\" align=\"center\" valign=\"top\" style=\"font-family: 'Open Sans', Arial, sans-serif; font-size:18px; color:#3b3b3b; line-height:26px;\">Amount</td>\n" +
                    "                <td width=\"80\" align=\"center\" valign=\"top\" style=\"font-family: 'Open Sans', Arial, sans-serif; font-size:18px; color:#3b3b3b; line-height:26px; \">VAT</td>\n" +
                    "                <td width=\"80\" align=\"center\" valign=\"top\" style=\"font-family: 'Open Sans', Arial, sans-serif; font-size:18px; color:#3b3b3b; line-height:26px; font-weight: bold;\">Total</td>\n" +
                    "              </tr>\n" +
                    "            </table>\n" +
                    "          </td>\n" +
                    "        </tr>\n" +
                    "        <!-- end header -->\n" +
                    "        <tr>\"\n" +
                    "          <td height=\"10\" style=\"border-bottom:3px solid #3b3b3b;\"></td>\n" +
                    "        </tr>\n" +
                    "      </table>\n" +
                    "    </td>\n" +
                    "  </tr>\n" +
                    "</table>\n" +
                    "<!-- end title -->\n" +
                    "<!-- list -->\n" +
                    "<table align=\"center\" width=\"600\" bgcolor=\"#FFFFFF\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                    "  <tr>\n" +
                    "    <td align=\"center\">\n" +
                    "      <table width=\"600\" style=\"max-width: 600px;\" class=\"table-full\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                    "        <tr>\n" +
                    "          <td height=\"35\"></td>\n" +
                    "        </tr>\n" +
                    "        <tr>\n" +
                    "          <td align=\"center\">\n" + "            <table width=\"100%\" class=\"table-inner\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
        htmlPart2 = """<tr>
                <td width="64" align="left" valign="top" style="font-family: 'Open Sans', Arial, sans-serif; font-size:16px; color:#3b3b3b; line-height:26px;">###term###</td>
                <td width="64" align="left" valign="top" style="font-family: 'Open Sans', Arial, sans-serif; font-size:16px; color:#3b3b3b; line-height:26px;">###instrument###</td>
                <td width="96" align="left" valign="top" style="font-family: 'Open Sans', Arial, sans-serif; font-size:16px; color:#3b3b3b; line-height:26px;">###lesson###</td>
                <td width="80" align="center" valign="top" style="font-family: 'Open Sans', Arial, sans-serif; font-size:16px; color:#3b3b3b; line-height:26px;">###amount###</td>
                <td width="80" align="center" valign="top" style="font-family: 'Open Sans', Arial, sans-serif; font-size:16px; color:#3b3b3b; line-height:26px;  font-weight: bold;">###vat###</td>

                <td width="80" align="right" valign="top" style="font-family: 'Open Sans', Arial, sans-serif; font-size:17px; color:#3b3b3b; line-height:26px;  font-weight: bold;">###total### AED</td>
              </tr>"""
        htmlPart3 = """</table>
          </td>
        </tr>
        <!--                        <tr>-->
        <!--                            <td height="5" style="border-bottom:1px solid #ecf0f1;"></td>-->
        <!--                        </tr>-->
        <tr>
          <td height="5"></td>
        </tr>
        <!-- detail -->
        <tr>
          <td align="center">
            <table class="table-inner" width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
              </tr>
            </table>
          </td>
        </tr>
        <!-- end detail -->
      </table>
    </td>
  </tr>
</table>
<!-- end list -->
<!-- total -->
<table align="center" width="100%" bgcolor="#FFFFFF" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="center">
      <table width="600" class="table-full" style="max-width: 600px;" align="center" border="0" cellpadding="0" cellspacing="0">

        <tr>
          <td height="35" style="border-bottom:3px solid #3b3b3b;"></td>
        </tr>
        <tr>
          <td height="15"></td>
        </tr>
        <tr>
<td align="right" style="font-family: 'Open Sans', Arial, sans-serif; font-size:20px;font-weight: bold; color:#3b3b3b; line-height:26px;"> Grand Total : ###grand_total### AED</td>
        </tr>
        <tr>
          <td align="center" style="font-family: 'Open Sans', Arial, sans-serif; font-size:20px; color:#3b3b3b;  line-height:26px;"> Thank You </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<!-- end total -->
<!-- note -->
<table align="center" width="600" bgcolor="#FFFFFF" border="0" cellspacing="0" cellpadding="0" >
  <tr>
    <td align="center">
      <table align="center" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="600" align="center">
            <table align="center" width="100%" class="table-inner" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="20"></td>
              </tr>

              <!-- content -->
              <tr>
                <td align="left" style="font-family: 'Open Sans', Arial, sans-serif; font-size:13px; color:#3b3b3b; line-height:26px;"> ###paidBy### </td>
              </tr>
              <tr>
                <td height="20"></td>
              </tr>
              <tr>
                <td height="15" style="border-bottom:2px solid #bcbcbc;"></td>
              </tr>
              <tr>
                <td height="10"></td>
              </tr>
              <tr>
                <td align="left" style="font-family: 'Open Sans', Arial, sans-serif; font-size:13px; color:#3b3b3b; line-height:26px;"> Nord Anglia International School Dubai, Hessa Street, Al Barsha 3 South, Dubai, United Arab Emirates</td>
              </tr>
              <!-- end content -->
            </table>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<!-- end note -->

<!-- footer -->
<table align="center" width="600" bgcolor="#FFFFFF" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td align="center" style="border-bottom:10px solid #ecf0f1;">
      <table width="600" style="max-width: 600px;" class="table-full" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td height="5"></td>
        </tr>

        <tr>
          <td></td>
          <td>
            <!--left-->
            <table width="180" class="table-full" align="left" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td align="">
                  <table width="90%"  border="0" cellpadding="0" cellspacing="0">
                    <tr>
                      <td align="right" style="line-height:0px;"><img style="display:block;font-size:0px; border:0px; line-height:0px;" src="phone.png" alt="img" /></td>
                      <td align="" style="font-family: 'Open Sans', Arial, sans-serif; font-size:13px; color:#3b3b3b; line-height:26px; padding-left:5px;"> +971 (0)42199999</td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
            <!--end left-->
          </td>
          <td>
            <table width="180" class="table-full" align="left" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td align="">
                  <table width="90%" align="" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                      <td align="right" style="line-height:0px;"><img style="display:block;font-size:0px; border:0px; line-height:0px;" src="mail.png" /></td>
                      <td style="font-family: 'Open Sans', Arial, sans-serif; font-size:13px; color:#3b3b3b; line-height:26px;padding-left: 15px;">finance@nasdubai.ae</td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
          </td>
          <td></td>
        </tr>
        <tr>
          <td height="25"></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<!-- end footer -->
</body>

</html>"""
    }

    private fun setWebViewSettingsPrint() {
        mProgressRelLayout.setVisibility(View.VISIBLE)
        anim = RotateAnimation(
            0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        anim.setInterpolator(mContext, android.R.interpolator.linear)
        anim.setRepeatCount(Animation.INFINITE)
        anim.setDuration(1000)
        mProgressRelLayout.setAnimation(anim)
        mProgressRelLayout.startAnimation(anim)
        paymentWebDummy.settings.javaScriptEnabled = true
        paymentWebDummy.clearCache(true)
        paymentWebDummy.settings.domStorageEnabled = true
        paymentWebDummy.settings.javaScriptCanOpenWindowsAutomatically = true
        paymentWebDummy.settings.setSupportMultipleWindows(true)
        paymentWebDummy.webViewClient =MyPrintWebViewClient()
    }

    private fun createWebPrintJob(webView: WebView) {
        mProgressRelLayout.clearAnimation()
        mProgressRelLayout.visibility = View.GONE
        paymentWebDummy.visibility = View.GONE
        val printManager = this.getSystemService(PRINT_SERVICE) as PrintManager
        val printAdapter = webView.createPrintDocumentAdapter()
        val jobName = getString(R.string.app_name) + "_Pay" + "BISAD"
        val builder = PrintAttributes.Builder()
        builder.setMediaSize(PrintAttributes.MediaSize.ISO_A4)
        if (printManager != null) {
            printJob = printManager.print(jobName, printAdapter, builder.build())
        }
        if (printJob!!.isCompleted()) {
//            Toast.makeText(getApplicationContext(), R.string.print_complete, Toast.LENGTH_LONG).show();
        } else if (printJob!!.isFailed()) {
            Toast.makeText(applicationContext, "Print failed", Toast.LENGTH_SHORT).show()
        }
    }
    fun shareFile(){
        startdownloadingforshare()
        val aName = intent.getStringExtra("iName")
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.putExtra(Intent.EXTRA_STREAM,  uriFromFile(mContext,File(this.getExternalFilesDir(pdfUri.toString()
        )?.absolutePath.toString(), "$aName")))
        shareIntent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        shareIntent.type = "application/pdf"
        startActivity(Intent.createChooser(shareIntent, "share.."))

        /* val intentShareFile = Intent(Intent.ACTION_SEND)
         val fileWithinMyDir = File(getFilepath(payment_type + "docs.pdf"))
         if (fileWithinMyDir.exists()) {
             intentShareFile.type = "application/pdf"
             intentShareFile.putExtra(
                 Intent.EXTRA_STREAM,
                 Uri.parse("file://" + getFilepath(payment_type + "docs.pdf"))
             )
             startActivity(Intent.createChooser(intentShareFile, "Share File"))
         } else {
             startdownloadingforshare()

             intentShareFile.type = "application/pdf"
             intentShareFile.putExtra(
                 Intent.EXTRA_STREAM,
                 Uri.parse("file://" + getFilepath(payment_type + "docs.pdf"))
             )
             startActivity(Intent.createChooser(intentShareFile, "Share File"))

         }*/
    }
    fun uriFromFile(context:Context, file:File):Uri {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            return FileProvider.getUriForFile(context, "com.nas.alreem" + ".provider", file)
        }
        else
        {
            return Uri.fromFile(file)
        }
    }
    private fun startdownloadingforshare() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            paymentWebDummy.loadUrl("about:blank")
            setWebViewSettingsPrint()
            loadWebViewWithDataPrint()
            createWebPrintJob(mWebView)
        }
        else {
            Toast.makeText(
                mContext,
                "Print is not supported below Android KITKAT Version",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    fun sharePdfFilePrint() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            paymentWebDummy!!.loadUrl("about:blank")
            setWebViewSettingsPrint()
            loadWebViewWithDataPrint()
            //                    createWebPrintJobShare(paymentWeb);
            pathFile = File(
                Environment.getExternalStorageDirectory()
                    .absolutePath + "/" + "NAS_DUBAI_MUSIC_ACADEMY/Payments" + "_" + "NASDUBAI" + "/"
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
                Uri.fromFile(createWebPrintJobShare(paymentWebDummy, pathFile!!))
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
       // mProgressRelLayout!!.clearAnimation()
      //  mProgressRelLayout!!.visibility = View.GONE
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


    private class MyPrintWebViewClient : WebViewClient() {
        override fun onPageFinished(view: WebView, url: String) {
            //Calling a javascript function in html page

//            view.loadUrl(url);
        }

        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap) {
            super.onPageStarted(view, url, favicon)

            //   Log.d("WebView", "print webpage loading.." + url);
        }
    }

}