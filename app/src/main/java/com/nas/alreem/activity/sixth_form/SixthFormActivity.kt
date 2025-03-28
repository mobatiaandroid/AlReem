package com.nas.alreem.activity.sixth_form

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.activity.primary.adapter.PrimaryDataAdapter
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.OnItemClickListener
import com.nas.alreem.constants.PDFViewerActivity
import com.nas.alreem.constants.WebLinkActivity
import com.nas.alreem.constants.addOnItemClickListener
import com.nas.alreem.fragment.primary.model.PrimaryFileModel

class SixthFormActivity : AppCompatActivity(){
    lateinit var mContext: Context
    lateinit var mListView: RecyclerView
    lateinit var heading: TextView
    lateinit var logoClickImgView: ImageView
    lateinit var backRelative: RelativeLayout
    lateinit var primaryList:ArrayList<PrimaryFileModel>
    var title:String=""
    lateinit var activity: Activity

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_priamry)
        mContext=this
        activity=this
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
            mListView.visibility= View.VISIBLE
            var adapterPrimary= PrimaryDataAdapter(primaryList,mContext)
            mListView.adapter=adapterPrimary
        }
        else
        {
            mListView.visibility= View.GONE
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
    override fun onResume() {
        super.onResume()
        if (!ConstantFunctions.runMethod.equals("Dev")) {
            if (ConstantFunctions().isDeveloperModeEnabled(mContext)) {
                ConstantFunctions().showDeviceIsDeveloperPopUp(activity)
            }
        }
    }

}