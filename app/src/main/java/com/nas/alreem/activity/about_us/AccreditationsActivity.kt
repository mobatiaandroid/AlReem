package com.nas.alreem.activity.about_us

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.ListView
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.nas.alreem.R
import com.nas.alreem.activity.about_us.adapter.AccreditationsRecyclerViewAdapter
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.PDFViewerActivity
import com.nas.alreem.constants.PreferenceManager
import com.nas.alreem.constants.WebLinkActivity
import com.nas.alreem.fragment.about_us.model.AboutUsDataModel
import com.nas.alreem.fragment.about_us.model.AboutusList

class AccreditationsActivity : Activity(), AdapterView.OnItemClickListener
    {
    lateinit var mContext: Context
    var extras: Bundle? = null
    var tab_type: String? = null
    var bannner_img: String? = null
    var relativeHeader: RelativeLayout? = null
        lateinit var backRelative: RelativeLayout
        lateinit var logoClickImgView: ImageView

    var back: ImageView? = null
    var home: ImageView? = null
    var mAboutUsListArray: ArrayList<AboutusList>? = null
    lateinit var bannerImagePager: ImageView

    //    ViewPager bannerImagePager;
    var bannerUrlImageArray = ArrayList<String?>()
    private var mTermsCalendarListView: ListView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatterbox_list)
        //		Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this,
//				LoginActivity.class));
        mContext = this
        initialiseUI()
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
        if (mAboutUsListArray!![position].url.endsWith(".pdf")) {
            val intent = Intent(
                mContext,
                PDFViewerActivity::class.java
            )
            intent.putExtra("pdf_url", mAboutUsListArray!![position].url)
            intent.putExtra("tab_type", "Accreditiations & Examinations")
            startActivity(intent)
        } else {
            val intent = Intent(
                mContext,
                WebLinkActivity::class.java
            )
            intent.putExtra("url", mAboutUsListArray!![position].url)
            intent.putExtra("tab_type", "Accreditiations & Examinations")
            startActivity(intent)
        }
    }

    fun initialiseUI() {
        extras = intent.extras
        if (extras != null) {
            mAboutUsListArray = PreferenceManager.getAboutsArrayList(mContext)
            bannner_img = extras!!.getString("banner_image")
            println("Image url--$bannner_img")

            //desc=extras.getString("desc");
            //title=extras.getString("title");
            if (bannner_img != "") {
                bannerUrlImageArray.add(bannner_img)
            }
        }
        relativeHeader = findViewById<View>(R.id.relativeHeader) as RelativeLayout
        mTermsCalendarListView = findViewById<View>(R.id.mTermsCalendarListView) as ListView
        bannerImagePager = findViewById<View>(R.id.bannerImageViewPager) as ImageView
        //        bannerImagePager= (ViewPager) findViewById(R.id.bannerImageViewPager);
        bannerImagePager!!.visibility = View.GONE

        backRelative=findViewById(R.id.backRelative)
        logoClickImgView=findViewById(R.id.logoClickImgView)


        mTermsCalendarListView!!.onItemClickListener = this
        backRelative!!.setOnClickListener {
          //  AppUtils.hideKeyBoard(mContext)
            finish()
        }

        logoClickImgView!!.setOnClickListener {
            val `in` = Intent(
                mContext,
                HomeActivity::class.java
            )
            `in`.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(`in`)
        }
        //        bannerImagePager.setAdapter(new ImagePagerDrawableAdapter(bannerUrlImageArray, mContext));
        Glide.with(mContext!!).load(ConstantFunctions.replace(bannner_img!!)).centerCrop()
            .into(bannerImagePager!!)
        val recyclerViewAdapter = AccreditationsRecyclerViewAdapter(mContext, mAboutUsListArray!!)
        mTermsCalendarListView!!.adapter = recyclerViewAdapter
    }
}