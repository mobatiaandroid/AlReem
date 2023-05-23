package com.nas.alreem.activity.settings

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
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
import com.nas.alreem.activity.settings.model.TermsOfServiceResponseModel
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.DialogFunctions
import com.nas.alreem.constants.PreferenceManager
import com.nas.alreem.rest.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TermsOfServiceActivity  : AppCompatActivity(){
    lateinit var mContext: Context
    lateinit var webView: WebView
    lateinit var heading: TextView
    lateinit var logoClickImgView: ImageView
    lateinit var progressDialogAdd: ProgressBar
    lateinit var backRelative: RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms_of_service)
        mContext=this
        initUI()
        if (ConstantFunctions.internetCheck(mContext))
        {
            progressDialogAdd.visibility=View.VISIBLE
            callTermsOfService()

        }
        else
        {
            DialogFunctions.showInternetAlertDialog(mContext)
        }
        getSettings()

    }
    fun initUI()
    {

        webView=findViewById(R.id.webView)
        progressDialogAdd=findViewById(R.id.progressDialogAdd)
        heading=findViewById(R.id.heading)
        logoClickImgView=findViewById(R.id.logoClickImgView)
        backRelative=findViewById(R.id.backRelative)
        heading.text="Terms of Service"
        logoClickImgView.setOnClickListener(View.OnClickListener {
            val intent = Intent(mContext, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        })

        backRelative.setOnClickListener(View.OnClickListener {
            finish()
        })


    }

    fun getSettings()
    {
        webView.settings.javaScriptEnabled = true
        webView.settings.setSupportZoom(false)
        webView.settings.cacheMode= WebSettings.LOAD_NO_CACHE
        webView.settings.javaScriptCanOpenWindowsAutomatically = true
        webView.settings.domStorageEnabled = true
        webView.settings.databaseEnabled = true
        webView.settings.defaultTextEncodingName = "utf-8"
        webView.settings.loadsImagesAutomatically = true
        webView.settings.cacheMode = WebSettings.LOAD_NO_CACHE
        webView.settings.allowFileAccess = true
        webView.setBackgroundColor(Color.TRANSPARENT)
        webView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null)


        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                progressDialogAdd.visibility = View.VISIBLE
                println("testing2")
                if (newProgress == 100)
                {
                    println("testing1")
                    progressDialogAdd.visibility = View.GONE

                }
            }
        }
    }

    fun callTermsOfService()
    {
        val call: Call<TermsOfServiceResponseModel> = ApiClient.getClient.termsOfService("Bearer "+PreferenceManager.getaccesstoken(mContext))
        call.enqueue(object : Callback<TermsOfServiceResponseModel> {
            override fun onFailure(call: Call<TermsOfServiceResponseModel>, t: Throwable) {
                Log.e("Failed", t.localizedMessage)
                progressDialogAdd.visibility=View.GONE
            }
            override fun onResponse(call: Call<TermsOfServiceResponseModel>, response: Response<TermsOfServiceResponseModel>) {
                val responsedata = response.body()
                progressDialogAdd.visibility=View.GONE
                if (responsedata != null) {
                    try {

                        if (response.body()!!.status==100)
                        {

                            var title=response.body()!!.responseArray!!.terms_of_service!!.title
                            var description=response.body()!!.responseArray!!.terms_of_service!!.description
                            var mLoadData = """
                                <!DOCTYPE html>
                                <html>
                                <head>
                                <style>
                                
                                @font-face {
                                font-family: SourceSansPro-Semibold;src: url(SourceSansPro-Semibold.otf);font-family: SourceSansPro-Regular;src: url(SourceSansPro-Regular.otf);}.title {font-family: SourceSansPro-Regular;font-size:16px;text-align:left;color:	#46C1D0;text-align: ####TEXT_ALIGN####;}.description {font-family: SourceSansPro-Light;text-align:justify;font-size:14px;color: #000000;text-align: ####TEXT_ALIGN####;}</style>
                                </head><body><p class='title'>$title</p><p class='description'>$description</p></body>
                                </html>
                                """.trimIndent()

                            webView.loadDataWithBaseURL("file:///android_asset/fonts/",mLoadData,"text/html; charset=utf-8", "utf-8", "about:blank")

                        }
                        else
                        {

                            DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert), ConstantFunctions.commonErrorString(response.body()!!.status), mContext)
                        }


                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

        })
    }

}