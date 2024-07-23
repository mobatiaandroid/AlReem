package com.nas.alreem.activity.communication.socialmedia

import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nas.alreem.R
import com.nas.alreem.activity.communication.socialmedia.adapter.SocialMediaAdapter
import com.nas.alreem.activity.communication.socialmedia.model.SocialMediaModel
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.constants.*
import com.nas.alreem.fragment.communication.model.SocialMediaResponseModel
import com.nas.alreem.recyclermanager.DividerItemDecoration
import com.nas.alreem.recyclermanager.RecyclerItemListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SocialMediaActivity : AppCompatActivity(){
    lateinit var mContext: Context
    lateinit var backRelative: RelativeLayout
    lateinit var heading: TextView
    lateinit var logoClickImgView: ImageView
    lateinit var facebookButton: ImageView
    lateinit var twitterButton: RelativeLayout
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
                            socialMediaModel.url=(dataObject.url)
                            socialMediaModel.tab_type=(dataObject.tab_type)
                            socialMediaModel.image=(dataObject.image)
                            if (dataObject.tab_type.contains("Facebook")) {
                                mSocialMediaArraylistFacebook.add(socialMediaModel)
                                Log.e("mSocialFacebook",
                                    mSocialMediaArraylistFacebook.size.toString()
                                )
                            } else if (dataObject.tab_type.contains("X")) {

                                mSocialMediaArraylistTwitter.add(socialMediaModel)
                                Log.e("mSocialFacebook1",
                                    mSocialMediaArraylistTwitter.size.toString()
                                )
                                //mSocialMediaArray=mSocialMediaArraylistTwitter;
                            } else if (dataObject.tab_type.contains("Instagram")) {

                                mSocialMediaArraylistInstagram.add(socialMediaModel)
                                Log.e("mSocialFacebook2",
                                    mSocialMediaArraylistInstagram.size.toString()
                                )
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
                val facebookAppIntent: Intent
                try {
                    facebookAppIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("fb://page/${mSocialMediaArraylistFacebook.get(0).pageid}")
                    )
                    startActivity(facebookAppIntent)
                } catch (e: ActivityNotFoundException) {

                   //
                    //
                    // val url = mSocialMediaArraylistFacebook[position].url
                    val intent = Intent(mContext, WebLinkActivity::class.java)
                    intent.putExtra("url",mSocialMediaArraylistFacebook.get(0).url.toString())
                    intent.putExtra("heading","FaceBook")
                    startActivity(intent)

                }
                /*val intent = Intent(mContext, WebLinkActivity::class.java)
                intent.putExtra("url",mSocialMediaArraylistFacebook.get(0).url.toString())
                intent.putExtra("heading","FaceBook")
                startActivity(intent)*/
            }

        })
        twitterButton.setOnClickListener(View.OnClickListener {


            if(mSocialMediaArraylistTwitter.size>1)
            {
                showSocialMedialPopup(mSocialMediaArraylistTwitter,"twitter",mContext)
            }
            else{
                val intent = Intent(mContext, WebLinkActivity::class.java)
                intent.putExtra("url",mSocialMediaArraylistTwitter.get(0).url.toString())
                intent.putExtra("heading","X")
                startActivity(intent)
            }
        })
        instagramButton.setOnClickListener(View.OnClickListener {


            if(mSocialMediaArraylistInstagram.size>1)
            {
                showSocialMedialPopup(mSocialMediaArraylistInstagram,"instagram",mContext)
            }
            else{
                Log.e("instagramurl",mSocialMediaArraylistInstagram.get(0).url)
                val intent = Intent(mContext, WebLinkActivity::class.java)
                intent.putExtra("url",mSocialMediaArraylistInstagram.get(0).url.toString())
                intent.putExtra("heading","Instagram")
                startActivity(intent)
            }
        })


    }

    private fun showSocialMedialPopup(list: java.util.ArrayList<SocialMediaModel>,
                                      type:String,
                                      context:Context) {
        /*val dialog = Dialog(context)
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
    }*/

            val dialog = Dialog(mContext)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.dialog_social_media)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val dialogDismiss = dialog.findViewById<View>(R.id.btn_dismiss) as Button
            val iconImageView = dialog.findViewById<View>(R.id.iconImageView) as ImageView
            val socialMediaList =
                dialog.findViewById<View>(R.id.recycler_view_social_media) as RecyclerView
            //if(mSocialMediaArray.get())
            if (type == "facebook") {
                iconImageView.setImageResource(R.drawable.facebookiconmedia)
                val sdk = Build.VERSION.SDK_INT
                if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
                    iconImageView.setBackgroundDrawable(mContext.resources.getDrawable(R.drawable.roundfb))
                    dialogDismiss.setBackgroundDrawable(mContext.resources.getDrawable(R.drawable.buttonfb))
                } else {
                    iconImageView.background = mContext.resources.getDrawable(R.drawable.roundfb)
                    dialogDismiss.background = mContext.resources.getDrawable(R.drawable.buttonfb)
                }
            } else if (type == "twitter") {
                iconImageView.setImageResource(R.drawable.twittericon)
                val sdk = Build.VERSION.SDK_INT
                if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
                    iconImageView.setBackgroundDrawable(mContext.resources.getDrawable(R.drawable.roundtw))
                    dialogDismiss.setBackgroundDrawable(mContext.resources.getDrawable(R.drawable.buttontwi))
                } else {
                    iconImageView.background = mContext.resources.getDrawable(R.drawable.roundtw)
                    dialogDismiss.background = mContext.resources.getDrawable(R.drawable.buttontwi)
                }
            } else {
                iconImageView.setImageResource(R.drawable.instagramicon)
                val sdk = Build.VERSION.SDK_INT
                if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
                    iconImageView.setBackgroundDrawable(mContext.resources.getDrawable(R.drawable.roundins))
                    dialogDismiss.setBackgroundDrawable(mContext.resources.getDrawable(R.drawable.buttonins))
                } else {
                    iconImageView.background = mContext.resources.getDrawable(R.drawable.roundins)
                    dialogDismiss.background = mContext.resources.getDrawable(R.drawable.buttonins)
                }
            }
            socialMediaList.addItemDecoration(DividerItemDecoration(mContext.resources.getDrawable(R.drawable.list_divider_teal)))
            socialMediaList.setHasFixedSize(true)
            val llm = LinearLayoutManager(mContext)
            llm.orientation = LinearLayoutManager.VERTICAL
            socialMediaList.layoutManager = llm
            val socialMediaAdapter = SocialMediaAdapter(mContext, list)
            socialMediaList.adapter = socialMediaAdapter
            dialogDismiss.setOnClickListener { dialog.dismiss() }
            socialMediaList.addOnItemTouchListener(
                RecyclerItemListener(mContext, socialMediaList,
                    object : RecyclerItemListener.RecyclerTouchListener {
                        override fun onClickItem(v: View?, position: Int) {
                            if (type == "facebook") {
                                Log.e("insideclickrec","insideclickrec")
//						Intent i = new Intent(Intent.ACTION_VIEW);
//						i.setData(Uri.parse(mSocialMediaArraylistFacebook.get(position).getUrl()));
//						startActivity(i);
                                val mintent = Intent(
                                    mContext,
                                    WebLinkActivity::class.java
                                )
                                mintent.putExtra(
                                    "url",
                                    mSocialMediaArraylistFacebook[position].url
                                )
                                startActivity(mintent)
                            } else if (type == "twitter") {
//						Intent i = new Intent(Intent.ACTION_VIEW);
//						i.setData(Uri.parse(mSocialMediaArraylistTwitter.get(position).getUrl()));
//						startActivity(i);
                                val mintent = Intent(
                                    mContext,
                                    WebLinkActivity::class.java
                                )
                                mintent.putExtra(
                                    "url",
                                    mSocialMediaArraylistTwitter[position].url
                                )
                                startActivity(mintent)
                            } else if (type == "instagram") {
//						Intent i = new Intent(Intent.ACTION_VIEW);
//						i.setData(Uri.parse(mSocialMediaArraylistInstagram.get(position).getUrl()));
//						startActivity(i);
                                val mintent = Intent(
                                    mContext,
                                    WebLinkActivity::class.java
                                )
                                mintent.putExtra(
                                    "url",
                                    mSocialMediaArraylistInstagram[position].url
                                )
                                startActivity(mintent)
                            }
                        }

                        override fun onLongClickItem(v: View?, position: Int) {
                            println("On Long Click Item interface")
                        }
                    })
            )
            dialog.show()
        }


}