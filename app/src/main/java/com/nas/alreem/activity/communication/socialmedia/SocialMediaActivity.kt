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
import com.nas.alreem.activity.communication.socialmedia.model.SocialMediaModel
import com.nas.alreem.activity.early_years.ComingUpDetailActivity
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.activity.payments.adapter.StudentListAdapter
import com.nas.alreem.activity.primary.adapter.ComingUpAdapter
import com.nas.alreem.activity.primary.model.ComingUpDataModell
import com.nas.alreem.activity.primary.model.ComingUpResponseModel
import com.nas.alreem.constants.*
import com.nas.alreem.fragment.communication.model.SocialMediaResponseModel
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
    lateinit var bannerImagePager: ImageView
    var bannner_img: String? = null
    private val mSocialMediaArraylistFacebook: java.util.ArrayList<SocialMediaModel> =
        java.util.ArrayList<SocialMediaModel>()
    private val mSocialMediaArraylistTwitter: java.util.ArrayList<SocialMediaModel> =
        java.util.ArrayList<SocialMediaModel>()
    private val mSocialMediaArraylistInstagram: java.util.ArrayList<SocialMediaModel> =
        java.util.ArrayList<SocialMediaModel>()
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_social_media)
        mContext=this
        initUI()

        if (ConstantFunctions.internetCheck(mContext))
        {
            SocialMediaApi()
        }
        else
        {
            DialogFunctions.showInternetAlertDialog(mContext)
        }

    }

    private fun SocialMediaApi() {
        val token = PreferenceManager.getaccesstoken(mContext)
        val call: Call<SocialMediaResponseModel> = ApiClient.getClient.social_media("Bearer "+token)
        call.enqueue(object : Callback<SocialMediaResponseModel> {
            override fun onFailure(call: Call<SocialMediaResponseModel>, t: Throwable) {

                // progressDialogAdd.visibility= View.GONE
            }
            override fun onResponse(call: Call<SocialMediaResponseModel>, response: Response<SocialMediaResponseModel

                    >) {
                val responsedata = response.body()
                // progressDialogAdd.visibility= View.GONE

                if (responsedata!!.status==100) {


                    bannner_img = responsedata!!.responseArray!!.banner_image

                    if (!bannner_img.equals("", ignoreCase = true)) {
                        Glide.with(mContext).load(ConstantFunctions.replace(bannner_img!!)).centerCrop()
                            .into(bannerImagePager)

//								bannerUrlImageArray = new ArrayList<>();
//								bannerUrlImageArray.add(bannerImage);
//								bannerImagePager.setAdapter(new ImagePagerDrawableAdapter(bannerUrlImageArray,mContext));
                    } else {
                        bannerImagePager.setBackgroundResource(R.drawable.socialbanner)
                    }
                    var data :ArrayList<SocialMediaModel>? =ArrayList<SocialMediaModel>()
                    data= response.body()!!.responseArray!!.data;
                    mSocialMediaArraylistInstagram.clear()
                    mSocialMediaArraylistFacebook.clear()
                    mSocialMediaArraylistTwitter.clear()
                    if (data!!.size > 0) {
                        for (i in 0 until data.size) {
                            val dataObject = data.get(i)
                            val socialMediaModel = SocialMediaModel()
                            socialMediaModel.id=(dataObject.id)
                            socialMediaModel.url=(dataObject.tab_type)
                            socialMediaModel.tab_type=(dataObject.url)
                            socialMediaModel.image=(dataObject.image)
                            if (dataObject.tab_type.contains("Facebook")) {
                                mSocialMediaArraylistFacebook.add(socialMediaModel)
                            } else if (dataObject.tab_type.contains("Twitter")) {
                                mSocialMediaArraylistTwitter.add(socialMediaModel)
                                //mSocialMediaArray=mSocialMediaArraylistTwitter;
                            } else if (dataObject.tab_type.contains("Instagram")) {
                                mSocialMediaArraylistInstagram.add(socialMediaModel)
                                //mSocialMediaArray=mSocialMediaArraylistInstagram;
                            }
                        }

                        //mSocialMediaArray=mSocialMediaArraylistFacebook;
                    } else {
                        Toast.makeText(mContext, "No data found", Toast.LENGTH_SHORT).show()
                    }



                }
                else {
                    DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert), ConstantFunctions.commonErrorString(response.body()!!.status), mContext)

                }
            }

        })
    }

    fun initUI()
    {
        bannerImagePager = findViewById<View>(R.id.bannerImageViewPager) as ImageView
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

            if(mSocialMediaArraylistFacebook.size>1)
            {
                showSocialMedialPopup(mSocialMediaArraylistFacebook,"facebook",mContext)
            }
            else{
                val intent = Intent(mContext, WebLinkActivity::class.java)
                intent.putExtra("url",mSocialMediaArraylistFacebook.get(0).toString())
                intent.putExtra("heading","FaceBook")
                startActivity(intent)
            }

        })
        twitterButton.setOnClickListener(View.OnClickListener {


            if(mSocialMediaArraylistTwitter.size>1)
            {
                showSocialMedialPopup(mSocialMediaArraylistTwitter,"twitter",mContext)
            }
            else{
                val intent = Intent(mContext, WebLinkActivity::class.java)
                intent.putExtra("url",mSocialMediaArraylistTwitter.get(0).toString())
                intent.putExtra("heading","Twitter")
                startActivity(intent)
            }
        })
        instagramButton.setOnClickListener(View.OnClickListener {


            if(mSocialMediaArraylistInstagram.size>1)
            {
                showSocialMedialPopup(mSocialMediaArraylistInstagram,"instagram",mContext)
            }
            else{
                val intent = Intent(mContext, WebLinkActivity::class.java)
                intent.putExtra("url",mSocialMediaArraylistInstagram.get(0).toString())
                intent.putExtra("heading","Instagram")
                startActivity(intent)
            }
        })


    }

    private fun showSocialMedialPopup(list: java.util.ArrayList<SocialMediaModel>,
                                      type:String,
                                      context:Context)
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