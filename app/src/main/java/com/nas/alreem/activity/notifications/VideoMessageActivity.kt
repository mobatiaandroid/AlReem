package com.nas.alreem.activity.notifications

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.nas.alreem.R
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.activity.notifications.model.MessageDetailApiModel
import com.nas.alreem.activity.notifications.model.MessageDetailModel
import com.nas.alreem.constants.ApiClient
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.DialogFunctions
import com.nas.alreem.constants.PreferenceManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class VideoMessageActivity : AppCompatActivity() {
    lateinit var mContext: Context
    var id: String = ""
    var title: String = ""
    var idApi: String = ""
    var titleApi: String = ""
    var message: String = ""
    var url: String = ""
    var date: String = ""
    private lateinit var relativeHeader: RelativeLayout
    private lateinit var backRelative: RelativeLayout
    private lateinit var logoClickImgView: ImageView
    private lateinit var btn_left: ImageView
    private lateinit var heading: TextView
    private lateinit var webView: WebView
    private lateinit var youtubeView: WebView
    private lateinit var progressDialog: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_message)
        mContext = this
        id = intent.getStringExtra("id").toString()
        title = intent.getStringExtra("title").toString()
        initUI()

        if (ConstantFunctions.internetCheck(mContext)) {
            callMessageDetailAPI()
        } else {
            DialogFunctions.showInternetAlertDialog(mContext)
        }
        getSettings()
    }

    private fun initUI() {
        progressDialog = findViewById(R.id.progressDialog)
        webView = findViewById(R.id.webView)
        youtubeView = findViewById(R.id.youtubeView)
        relativeHeader = findViewById(R.id.relativeHeader)
        heading = findViewById(R.id.heading)
        btn_left = findViewById(R.id.btn_left)
        logoClickImgView = findViewById(R.id.logoClickImgView)
        backRelative = findViewById(R.id.backRelative)
        heading.text = "Notification"

        btn_left.setOnClickListener { finish() }
        backRelative.setOnClickListener { finish() }
        logoClickImgView.setOnClickListener {
            val intent = Intent(mContext, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
    }

    private fun callMessageDetailAPI() {
        val token = PreferenceManager.getaccesstoken(mContext)
        val studentbody = MessageDetailApiModel(id)
        val call: Call<MessageDetailModel> = ApiClient.getClient.notifictaionDetail(studentbody, "Bearer $token")
        call.enqueue(object : Callback<MessageDetailModel> {
            override fun onFailure(call: Call<MessageDetailModel>, t: Throwable) {
                progressDialog.visibility = View.GONE
            }

            override fun onResponse(call: Call<MessageDetailModel>, response: Response<MessageDetailModel>) {
                progressDialog.visibility = View.GONE
                if (response.body()!!.status == 100) {
                    idApi = id
                    titleApi = title
                    message = response.body()!!.responseArray.notificationArray.message
                    url = response.body()!!.responseArray.notificationArray.url
                    date = response.body()!!.responseArray.notificationArray.created_at
                    displayNotificationDetails()
                } else {
                    DialogFunctions.commonErrorAlertDialog(
                        mContext.resources.getString(R.string.alert),
                        ConstantFunctions.commonErrorString(response.body()!!.status),
                        mContext
                    )
                }
            }
        })
    }

    private fun displayNotificationDetails() {
        val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val outputFormatTime: DateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        val outputFormatDate: DateFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
        val date: Date = inputFormat.parse(this.date)!!

        val formattedTime = outputFormatTime.format(date)
        val formattedDate = outputFormatDate.format(date)

        val videoHtml = if (url.isNotEmpty()) {
            val frameVideo = """
                <html>
                <head>
                </head>
                <body>
                    <iframe width="328" height="280" src="$url" frameborder="0" allowfullscreen></iframe>
                </body>
                </html>
            """.trimIndent()
            frameVideo.replace("watch?v=", "embed/")
        } else {
            ""
        }

        val htmlContent = """
            <!DOCTYPE html>
            <html>
            <head>
                <style>
                    @font-face {
                        font-family: SourceSansPro-Semibold;
                        src: url(SourceSansPro-Semibold.ttf);
                    }
                    @font-face {
                        font-family: SourceSansPro-Regular;
                        src: url(SourceSansPro-Regular.ttf);
                    }
                    .title {
                        font-family: SourceSansPro-Semibold;
                        font-size:16px;
                        text-align:left;
                        color: #46C1D0;
                    }
                    .date {
                        font-family: SourceSansPro-Regular;
                        font-size:12px;
                        text-align:right;
                        color: #908C86;
                    }
                    .description {
                        font-family: SourceSansPro-Semibold;
                        text-align:justify;
                        font-size:14px;
                        color: #000000;
                    }
                </style>
            </head>
            <body>
                <p class='title'>$title</p>
                <p class='date'>$formattedDate $formattedTime</p>
                <hr>
                <p class='description'>$message</p>
            </body>
            </html>
        """.trimIndent()

        webView.loadDataWithBaseURL("file:///android_asset/fonts/", htmlContent, "text/html; charset=utf-8", "utf-8", "about:blank")
        youtubeView.loadDataWithBaseURL("file:///android_asset/fonts/", videoHtml, "text/html; charset=utf-8", "utf-8", "about:blank")
    }

    private fun getSettings() {
        webView.settings.apply {
            javaScriptEnabled = true
            setSupportZoom(false)
            cacheMode = WebSettings.LOAD_NO_CACHE
            javaScriptCanOpenWindowsAutomatically = true
            domStorageEnabled = true
            databaseEnabled = true
            defaultTextEncodingName = "utf-8"
            loadsImagesAutomatically = true
            allowFileAccess = true
        }
        webView.setBackgroundColor(Color.TRANSPARENT)
        webView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null)

        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                progressDialog.visibility = if (newProgress == 100) View.GONE else View.VISIBLE
            }
        }
        youtubeView.settings.apply {
            javaScriptEnabled = true
            setSupportZoom(false)
            cacheMode = WebSettings.LOAD_NO_CACHE
            javaScriptCanOpenWindowsAutomatically = true
            domStorageEnabled = true
            databaseEnabled = true
            defaultTextEncodingName = "utf-8"
            loadsImagesAutomatically = true
            allowFileAccess = true
        }
        youtubeView.setBackgroundColor(Color.TRANSPARENT)
        youtubeView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null)

        youtubeView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                progressDialog.visibility = if (newProgress == 100) View.GONE else View.VISIBLE
            }
        }
    }
}
