package com.nas.alreem.constants

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.nas.alreem.R
import com.nas.alreem.activity.home.HomeActivity

class WebViewTextActivity : AppCompatActivity() {
    lateinit var mContext: Context
    lateinit var extras: Bundle
    lateinit var img: ImageView
    lateinit var txtmsg: TextView
    lateinit var mDateTv: TextView
    lateinit var headingTextView: TextView
    lateinit var headermanager: HeaderManager
    lateinit var back: RelativeLayout
    lateinit var home: ImageView

    var url = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view_text)
        mContext = this
        extras = intent.extras!!
        if (extras != null) {
            url = extras.getString("Url").toString()
        }

        initialiseUI()
    }

    private fun initialiseUI() {
        txtmsg = findViewById<View>(R.id.txt) as TextView
        mDateTv = findViewById<View>(R.id.mDateTv) as TextView
        headingTextView = findViewById<TextView>(R.id.heading)
        back = findViewById(R.id.backRelative)
        home = findViewById(R.id.logoClickImgView)
        back.setOnClickListener(View.OnClickListener { finish() })

        home.setOnClickListener(View.OnClickListener {
            val intent = Intent(mContext, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        })
        headingTextView.text = "Terms & Conditions"

        setDetails()
    }

    private fun setDetails() {
        txtmsg.text = url
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            txtmsg.text = Html.fromHtml(url, Html.FROM_HTML_MODE_COMPACT)
        } else {
            txtmsg.text = Html.fromHtml(url)
        }
    }
}