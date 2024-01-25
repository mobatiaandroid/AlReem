package com.nas.alreem.activity.communication.newesletters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonObject
import com.nas.alreem.R
import com.nas.alreem.activity.communication.newesletters.adapter.NewsLetterAdapter
import com.nas.alreem.activity.communication.newesletters.adapter.NewsLetterListAdapter
import com.nas.alreem.activity.communication.socialmedia.SocialMediaActivity
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.constants.ApiClient
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.ConstantWords
import com.nas.alreem.constants.DialogFunctions
import com.nas.alreem.constants.OnItemClickListener
import com.nas.alreem.constants.PDFViewerActivity
import com.nas.alreem.constants.PreferenceManager
import com.nas.alreem.constants.WebLinkActivity
import com.nas.alreem.constants.addOnItemClickListener
import com.nas.alreem.fragment.communication.model.CommunicationDataModel
import com.nas.alreem.fragment.communication.model.CommunicationResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsLetterListActivity : AppCompatActivity(){
    lateinit var mContext: Context
    lateinit var mnewsLetterListView: RecyclerView
    lateinit var progressDialogAdd: ProgressBar
    lateinit var heading: TextView
    lateinit var backRelative: RelativeLayout
    lateinit var logoClickImgView: ImageView
    lateinit var newsLetterArrayList:ArrayList<CommunicationDataModel>
    lateinit var extras: Bundle
    var category_id:String ?=""

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_newsletter_list)
        mContext=this
        initUI()
        if (ConstantFunctions.internetCheck(mContext))
        {
            getList()
        }
        else
        {
            DialogFunctions.showInternetAlertDialog(mContext)
        }
    }
    fun initUI()
    {
        extras = intent.extras!!
        if (extras != null) {
            // tab_type = extras.getString("tab_type")
            category_id = extras.getString("id")
        }
        mnewsLetterListView=findViewById(R.id.mnewsLetterListView)
        progressDialogAdd=findViewById(R.id.progressDialogAdd)
        heading=findViewById(R.id.heading)
        backRelative=findViewById(R.id.backRelative)
        logoClickImgView=findViewById(R.id.logoClickImgView)
        heading.text= ConstantWords.newsletters
        var linearLayoutManager = LinearLayoutManager(mContext)
        mnewsLetterListView.layoutManager = linearLayoutManager
        mnewsLetterListView.itemAnimator = DefaultItemAnimator()
        logoClickImgView.setOnClickListener(View.OnClickListener {
            val intent = Intent(mContext, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        })
        backRelative.setOnClickListener(View.OnClickListener {
            finish()
        })
        newsLetterArrayList= ArrayList()
        //  newsLetterArrayList.add("NewsLetter List")

        mnewsLetterListView.addOnItemClickListener(object : OnItemClickListener {
            @SuppressLint("SimpleDateFormat", "SetTextI18n")
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onItemClicked(position: Int, view: View)
            {
                if (newsLetterArrayList.get(position).filename.endsWith(".pdf")) {
                    val intent = Intent(mContext, PDFViewerActivity::class.java)
                    intent.putExtra("pdf_url", newsLetterArrayList!![position].filename)
                    intent.putExtra("title",newsLetterArrayList.get(position).submenu)
                    startActivity(intent)
                } else {
                    val intent = Intent(
                        mContext,
                        WebLinkActivity::class.java
                    )
                    Log.e("filename",newsLetterArrayList!![position].filename)
                    intent.putExtra("url", newsLetterArrayList!![position].filename)
                    intent.putExtra("heading",newsLetterArrayList.get(position).submenu)
                    startActivity(intent)
                }
            }

        })

    }
    private fun  getList(){
        val token = PreferenceManager.getaccesstoken(mContext)
        val paramObject = JsonObject()
        paramObject.addProperty("category_id", category_id)
        val call: Call<CommunicationResponseModel> = ApiClient.getClient.newsletter("Bearer "+token,paramObject)
        call.enqueue(object : Callback<CommunicationResponseModel> {
            override fun onFailure(call: Call<CommunicationResponseModel>, t: Throwable) {

                progressDialogAdd.visibility= View.GONE
            }
            override fun onResponse(call: Call<CommunicationResponseModel>, response: Response<CommunicationResponseModel>) {
                val responsedata = response.body()
                progressDialogAdd.visibility= View.GONE

                if (responsedata!!.status==100) {




                    if (response.body()!!.responseArray!!.data!!.size > 0) {
                        for (i in 0 until response.body()!!.responseArray!!.data!!.size) {

                            newsLetterArrayList.add(response.body()!!.responseArray!!.data!!.get(i))
                        }
                        var newsLetterAdapter= NewsLetterListAdapter(newsLetterArrayList,mContext)
                        mnewsLetterListView.adapter=newsLetterAdapter
                    } else {
                        Toast.makeText(
                            this@NewsLetterListActivity,
                            "No data found",
                            Toast.LENGTH_SHORT
                        ).show()
                    }



                }
                else {
                    DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert), ConstantFunctions.commonErrorString(response.body()!!.status), mContext)

                }
            }

        })


    }

}