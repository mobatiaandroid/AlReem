package com.nas.alreem.activity.primary

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.activity.early_years.EarlyYearsComingUpActivity
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.activity.login.model.LoginResponseModel
import com.nas.alreem.activity.primary.adapter.PrimaryDataAdapter
import com.nas.alreem.constants.*
import com.nas.alreem.fragment.primary.model.PrimaryFileModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PrimaryActivity : AppCompatActivity(){
    lateinit var mContext: Context
    lateinit var mListView: RecyclerView
    lateinit var heading: TextView
    lateinit var logoClickImgView: ImageView
    lateinit var backRelative: RelativeLayout
    lateinit var primaryList:ArrayList<PrimaryFileModel>
    var title:String=""
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_priamry)
        mContext=this
        initUI()

    }
    fun initUI()
    {
        primaryList= intent.getSerializableExtra("list") as ArrayList<PrimaryFileModel>
        title=intent.getStringExtra("title").toString()
        mListView=findViewById(R.id.mListView)
        heading=findViewById(R.id.heading)
        backRelative=findViewById(R.id.backRelative)
        logoClickImgView=findViewById(R.id.logoClickImgView)
        heading.text=title
        backRelative.setOnClickListener(View.OnClickListener {
            finish()
        })

        logoClickImgView.setOnClickListener(View.OnClickListener {
            val intent = Intent(mContext, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        })
        var linearLayoutManager = LinearLayoutManager(mContext)
        mListView.layoutManager = linearLayoutManager
        mListView.itemAnimator = DefaultItemAnimator()
        if (primaryList.size>0)
        {
            mListView.visibility=View.VISIBLE
            var adapterPrimary= PrimaryDataAdapter(primaryList,mContext)
            mListView.adapter=adapterPrimary
        }
        else
        {
            mListView.visibility=View.GONE
        }

        mListView.addOnItemClickListener(object : OnItemClickListener {
            @SuppressLint("SimpleDateFormat", "SetTextI18n")
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onItemClicked(position: Int, view: View)
            {
                if (primaryList.get(position).file.contains(".pdf"))
                {
                    val intent = Intent(mContext, PDFViewerActivity::class.java)
                    intent.putExtra("Url",primaryList.get(position).file)
                    intent.putExtra("title",primaryList.get(position).title)
                    startActivity(intent)
                }
                else{
                    val intent = Intent(mContext, WebLinkActivity::class.java)
                    intent.putExtra("url",primaryList.get(position).file)
                    intent.putExtra("heading",primaryList.get(position).title)
                    startActivity(intent)
                }

            }


        })
    }

}