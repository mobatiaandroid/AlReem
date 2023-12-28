package com.nas.alreem.activity.communication.newesletters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
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
import com.nas.alreem.R
import com.nas.alreem.activity.communication.newesletters.adapter.NewsLetterAdapter
import com.nas.alreem.activity.communication.newesletters.model.NewsletterResponseModel
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.constants.*
import com.nas.alreem.fragment.communication.model.CommunicationResponseModel
import com.nas.alreem.rest.ApiClient
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NewsLetterActivity : AppCompatActivity(){
    lateinit var mContext: Context
    lateinit var mnewsLetterListView: RecyclerView
    lateinit var progressDialogAdd: ProgressBar
    lateinit var heading: TextView
    lateinit var backRelative: RelativeLayout
    lateinit var logoClickImgView: ImageView
    lateinit var newsLetterArrayList:ArrayList<NewsLetterModel>
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_newsletter)
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

        mnewsLetterListView=findViewById(R.id.mnewsLetterListView)
        progressDialogAdd=findViewById(R.id.progressDialogAdd)
        heading=findViewById(R.id.heading)
        backRelative=findViewById(R.id.backRelative)
        logoClickImgView=findViewById(R.id.logoClickImgView)
        heading.text=ConstantWords.newsletters
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
       // newsLetterArrayList.add("NewsLetter")

        mnewsLetterListView.addOnItemClickListener(object : OnItemClickListener {
            @SuppressLint("SimpleDateFormat", "SetTextI18n")
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onItemClicked(position: Int, view: View)
            {

                val intent = Intent(mContext, NewsLetterListActivity::class.java)
                intent.putExtra("heading",newsLetterArrayList.get(position).toString())
                intent.putExtra("id",newsLetterArrayList.get(position).id)
                startActivity(intent)
            }

        })

    }
    private fun  getList(){
        val token = PreferenceManager.getaccesstoken(mContext)
        val call: Call<NewsletterResponseModel> = ApiClient.getClient.newsletter_categories("Bearer "+token)
        call.enqueue(object : Callback<NewsletterResponseModel> {
            override fun onFailure(call: Call<NewsletterResponseModel>, t: Throwable) {

                progressDialogAdd.visibility= View.GONE
            }
            override fun onResponse(call: Call<NewsletterResponseModel>, response: Response<NewsletterResponseModel>) {
                val responsedata = response.body()
                progressDialogAdd.visibility= View.GONE

                if (responsedata!!.status==100) {




                    if (response.body()!!.responseArray!!.data!!.size > 0) {
                        for (i in 0 until response.body()!!.responseArray!!.data!!.size) {

                            newsLetterArrayList.add(response.body()!!.responseArray!!.data!!.get(i))
                        }
                        var newsLetterAdapter= NewsLetterAdapter(newsLetterArrayList,mContext)
                        mnewsLetterListView.adapter=newsLetterAdapter
                    } else {
                        Toast.makeText(
                            this@NewsLetterActivity,
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