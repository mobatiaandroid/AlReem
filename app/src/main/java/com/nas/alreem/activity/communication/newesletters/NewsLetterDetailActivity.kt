package com.nas.alreem.activity.communication.newesletters

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.activity.communication.newesletters.adapter.NewsLetterAdapter
import com.nas.alreem.activity.communication.newesletters.adapter.NewsLetterDetailAdapter
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.ConstantWords
import com.nas.alreem.constants.OnItemClickListener
import com.nas.alreem.constants.addOnItemClickListener

class NewsLetterDetailActivity : AppCompatActivity(){
    lateinit var mContext: Context
    lateinit var mnewsLetterListView: RecyclerView
    lateinit var progressDialogAdd: ProgressBar
    lateinit var heading: TextView
    lateinit var backRelative: RelativeLayout
    lateinit var logoClickImgView: ImageView
    lateinit var newsLetterArrayList:ArrayList<String>
    lateinit var activity: Activity

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_newsletter_list)
        mContext=this
        activity=this
        initUI()

    }
    fun initUI()
    {

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
        newsLetterArrayList.add("NewsLetter Detail")
        var newsLetterAdapter= NewsLetterDetailAdapter(newsLetterArrayList,mContext)
        mnewsLetterListView.adapter=newsLetterAdapter
        mnewsLetterListView.addOnItemClickListener(object : OnItemClickListener {
            @SuppressLint("SimpleDateFormat", "SetTextI18n")
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onItemClicked(position: Int, view: View)
            {

            }

        })

    }
    override fun onResume() {
        super.onResume()
        if (!ConstantFunctions.runMethod.equals("Dev")) {
            if (ConstantFunctions().isDeveloperModeEnabled(mContext)) {
                ConstantFunctions().showDeviceIsDeveloperPopUp(activity)
            }
        }
    }

}