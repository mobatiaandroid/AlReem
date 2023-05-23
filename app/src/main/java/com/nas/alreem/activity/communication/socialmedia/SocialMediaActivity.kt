package com.nas.alreem.activity.communication.socialmedia

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.nas.alreem.R
import com.nas.alreem.activity.early_years.ComingUpDetailActivity
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.activity.payments.adapter.StudentListAdapter
import com.nas.alreem.activity.primary.adapter.ComingUpAdapter
import com.nas.alreem.activity.primary.model.ComingUpDataModell
import com.nas.alreem.activity.primary.model.ComingUpResponseModel
import com.nas.alreem.constants.*
import com.nas.alreem.rest.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SocialMediaActivity : AppCompatActivity(){
    lateinit var mContext: Context
    lateinit var backRelative: RelativeLayout
    lateinit var heading: TextView
    lateinit var logoClickImgView: ImageView
    lateinit var facebookButton: ImageView
    lateinit var twitterButton: ImageView
    lateinit var instagramButton: ImageView
    lateinit var faceBookArrayList:ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_social_media)
        mContext=this
        initUI()

    }
    fun initUI()
    {

        heading=findViewById(R.id.heading)
        backRelative=findViewById(R.id.backRelative)
        logoClickImgView=findViewById(R.id.logoClickImgView)
        facebookButton=findViewById(R.id.facebookButton)
        twitterButton=findViewById(R.id.twitterButton)
        instagramButton=findViewById(R.id.instagramButton)
        backRelative.setOnClickListener(View.OnClickListener {
            finish()
        })
        heading.text=ConstantWords.social_media
        logoClickImgView.setOnClickListener(View.OnClickListener {
            val intent = Intent(mContext, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        })

        facebookButton.setOnClickListener(View.OnClickListener {
            faceBookArrayList= ArrayList()
            faceBookArrayList.add("www.facebook.com/NordAngliaInternationalSchoolAbuDhabi/")
            if(faceBookArrayList.size>1)
            {
                showSocialMedialPopup(faceBookArrayList,"facebook",mContext)
            }
            else{
                val intent = Intent(mContext, WebLinkActivity::class.java)
                intent.putExtra("url",faceBookArrayList.get(0).toString())
                intent.putExtra("heading","FaceBook")
                startActivity(intent)
            }

        })
        twitterButton.setOnClickListener(View.OnClickListener {
            faceBookArrayList=ArrayList()
            faceBookArrayList.add("https://www.linkedin.com/company/nord-anglia-international-school-abu-dhabi")

            if(faceBookArrayList.size>1)
            {
                showSocialMedialPopup(faceBookArrayList,"twitter",mContext)
            }
            else{
                val intent = Intent(mContext, WebLinkActivity::class.java)
                intent.putExtra("url",faceBookArrayList.get(0).toString())
                intent.putExtra("heading","Twitter")
                startActivity(intent)
            }
        })
        instagramButton.setOnClickListener(View.OnClickListener {
            faceBookArrayList=ArrayList()
            faceBookArrayList.add("https://www.linkedin.com/company/nord-anglia-international-school-abu-dhabi")
            faceBookArrayList.add("https://www.instagram.com/nasabudhabischool/")

            if(faceBookArrayList.size>1)
            {
                showSocialMedialPopup(faceBookArrayList,"instagram",mContext)
            }
            else{
                val intent = Intent(mContext, WebLinkActivity::class.java)
                intent.putExtra("url",faceBookArrayList.get(0).toString())
                intent.putExtra("heading","Instagram")
                startActivity(intent)
            }
        })


    }

    private fun showSocialMedialPopup( list:ArrayList<String>,type:String,context:Context)
    {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_social_media)
        var btn_dismiss = dialog.findViewById(R.id.btn_dismiss) as Button
        var iconImageView = dialog.findViewById(R.id.iconImageView) as ImageView
        var recycler_view_social_media = dialog.findViewById(R.id.recycler_view_social_media) as RecyclerView
        recycler_view_social_media.setHasFixedSize(true)
        val llm = LinearLayoutManager(mContext)
        llm.orientation = LinearLayoutManager.VERTICAL
        recycler_view_social_media.layoutManager = llm
        if (type.equals("facebook"))
        {
            btn_dismiss.setBackgroundResource(R.drawable.buttonfb)
            iconImageView.setBackgroundResource(R.drawable.roundfb)

        }
        else if (type.equals("instagram"))
        {
            btn_dismiss.setBackgroundResource(R.drawable.buttonins)
            iconImageView.setBackgroundResource(R.drawable.roundins)
        }
        else if (type.equals("twitter"))
        {
            btn_dismiss.setBackgroundResource(R.drawable.buttontwi)
            iconImageView.setBackgroundResource(R.drawable.roundtw)
        }

        btn_dismiss.setOnClickListener(View.OnClickListener {

            dialog.dismiss()
        })
        dialog.show()
    }
}