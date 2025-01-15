package com.nas.alreem.activity.secondary

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
import com.nas.alreem.activity.early_years.ComingUpDetailActivity
import com.nas.alreem.activity.early_years.adapter.ComingUpAdapterCommon
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.activity.primary.adapter.ComingUpAdapter
import com.nas.alreem.activity.primary.model.ComingUpDataModell
import com.nas.alreem.activity.primary.model.ComingUpResponseModel
import com.nas.alreem.constants.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SecondaryComingUpActivity : AppCompatActivity(){
    lateinit var mContext: Context
    lateinit var mListView: RecyclerView
    lateinit var progressDialogAdd: ProgressBar
    lateinit var heading: TextView
    lateinit var backRelative: RelativeLayout
    lateinit var logoClickImgView: ImageView
    lateinit var comingUpArrayList: ArrayList<ComingUpDataModell>
    lateinit var activity: Activity

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coming_up)
        mContext=this
        activity=this
        initUI()
        if (ConstantFunctions.internetCheck(mContext))
        {
            callComingUpApi()
        }
        else
        {
            DialogFunctions.showInternetAlertDialog(mContext)
        }

    }
    fun initUI()
    {
        mListView=findViewById(R.id.mListView)
        progressDialogAdd=findViewById(R.id.progressDialogAdd)
        var linearLayoutManager = LinearLayoutManager(mContext)
        mListView.layoutManager = linearLayoutManager
        mListView.itemAnimator = DefaultItemAnimator()
        heading=findViewById(R.id.heading)
        backRelative=findViewById(R.id.backRelative)
        logoClickImgView=findViewById(R.id.logoClickImgView)
        heading.text="Coming Up"
        backRelative.setOnClickListener(View.OnClickListener {
            finish()
        })

        logoClickImgView.setOnClickListener(View.OnClickListener {
            val intent = Intent(mContext, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        })

        mListView.addOnItemClickListener(object : OnItemClickListener {
            @SuppressLint("SimpleDateFormat", "SetTextI18n")
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onItemClicked(position: Int, view: View)
            {
                var webViewComingUpDetail = """
                  <!DOCTYPE html>
                    <html>
                    <head>
                    <style>
                    @font-face {
                    font-family: SourceSansPro-Semibold;
                    src: url(SourceSansPro-Semibold.ttf);
                    font-family: SourceSansPro-Regular;
                    src: url(SourceSansPro-Regular.ttf);}.title {font-family: SourceSansPro-Regular;font-size:16px;text-align:left;color:	#46C1D0;}.description {font-family: SourceSansPro-Light;text-align:justify;font-size:14px;color: #000000;text-align:left;}</style>
                    </head><body><p class='title'>${
                    comingUpArrayList.get(position).title
                }
                    """.trimIndent()
                if (!comingUpArrayList.get(position).image.equals("")) {
                    webViewComingUpDetail =
                        webViewComingUpDetail + "<center><img src='" + comingUpArrayList.get(
                            position
                        ).image + "'width='100%', height='auto'>"
                }
                webViewComingUpDetail = """
                    $webViewComingUpDetail<p class='description'>${
                    comingUpArrayList.get(position).description
                }</p></body>
                    </html>
                    """.trimIndent()

                val intent = Intent(mContext, ComingUpDetailActivity::class.java)
                intent.putExtra("web_ink",webViewComingUpDetail)
                intent.putExtra("title","Coming Up")
                startActivity(intent)

            }


        })
    }
    fun callComingUpApi()
    {
        progressDialogAdd.visibility=View.VISIBLE
        comingUpArrayList= ArrayList()
        val call: Call<ComingUpResponseModel> = ApiClient(mContext).getClient.secondaryComingUp()
        call.enqueue(object : Callback<ComingUpResponseModel> {
            override fun onFailure(call: Call<ComingUpResponseModel>, t: Throwable) {
                progressDialogAdd.visibility=View.GONE
            }
            override fun onResponse(call: Call<ComingUpResponseModel>, response: Response<ComingUpResponseModel>) {
                val responsedata = response.body()
                progressDialogAdd.visibility=View.GONE
                if (responsedata != null) {
                    try {

                        if (response.body()!!.status==100)
                        {
                           comingUpArrayList=response.body()!!.responseArray!!.data!!
                            if (comingUpArrayList.size>0)
                            {
                                var adapterComing= ComingUpAdapterCommon(comingUpArrayList,mContext)
                                mListView.adapter=adapterComing
                            }
                            else
                            {
                                DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert),ConstantWords.status_132, mContext)

                            }
                        }
                        else
                        {

                            DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert), ConstantFunctions.commonErrorString(response.body()!!.status), mContext)
                        }


                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
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