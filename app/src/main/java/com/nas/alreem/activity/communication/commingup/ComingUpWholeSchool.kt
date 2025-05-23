package com.nas.alreem.activity.communication.commingup

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
import com.google.gson.JsonObject
import com.nas.alreem.R
import com.nas.alreem.activity.communication.commingup.model.ComingUpResponseModel
import com.nas.alreem.activity.early_years.ComingUpDetailActivity
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.activity.primary.adapter.ComingUpAdapter
import com.nas.alreem.activity.shop_new.model.StudentShopCardResponseModel
import com.nas.alreem.constants.*
import com.nas.alreem.fragment.notifications.model.NotificationApiModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ComingUpWholeSchool : AppCompatActivity() {
    lateinit var mContext: Context
    lateinit var mListView: RecyclerView
    lateinit var progressDialogAdd: ProgressBar
    lateinit var heading: TextView
    lateinit var backRelative: RelativeLayout
    lateinit var logoClickImgView: ImageView
    lateinit var  adapterComing:ComingUpAdapter
    lateinit var comingUpArrayList: ArrayList<ComingUpResponseModel.ComingUpItem>
    lateinit var activity: Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coming_up)
        mContext = this
        activity=this
        initUI()
        if (ConstantFunctions.internetCheck(mContext)) {
            callComingUpApi()
        } else {
            DialogFunctions.showInternetAlertDialog(mContext)
        }


    }

    fun initUI() {

        mListView = findViewById(R.id.mListView)
        progressDialogAdd = findViewById(R.id.progressDialogAdd)
        heading = findViewById(R.id.heading)
        backRelative = findViewById(R.id.backRelative)
        logoClickImgView = findViewById(R.id.logoClickImgView)
        heading.text = "Coming Up"
        var linearLayoutManager = LinearLayoutManager(mContext)
        mListView.layoutManager = linearLayoutManager
        mListView.itemAnimator = DefaultItemAnimator()
        logoClickImgView.setOnClickListener(View.OnClickListener {
            val intent = Intent(mContext, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        })
        backRelative.setOnClickListener(View.OnClickListener {
            finish()
        })
        mListView.addOnItemClickListener(object : OnItemClickListener {
            @SuppressLint("SimpleDateFormat", "SetTextI18n")
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onItemClicked(position: Int, view: View) {
                if (comingUpArrayList.get(position).read_unread_status
                        .equals("0") || comingUpArrayList.get(position).read_unread_status
                        .equals("2")
                ) {
                    callStatusChangeApi(
                        comingUpArrayList.get(position).id.toString(),
                        position,
                        comingUpArrayList.get(position).read_unread_status
                    )
                }
                else
                {
                    var webViewComingUpDetail = """
                  <!DOCTYPE html>
                    <html>
                    <head>
                    <style>
                    @font-face {
                    font-family: SourceSansPro-Semibold;src: url(SourceSansPro-Semibold.ttf);font-family: SourceSansPro-Regular;src: url(SourceSansPro-Regular.ttf);}.title {font-family: SourceSansPro-Regular;font-size:16px;text-align:left;color:	#46C1D0;}.description {font-family: SourceSansPro-Light;text-align:justify;font-size:14px;color: #000000;text-align:left;}</style>
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
                    intent.putExtra("web_ink", webViewComingUpDetail)
                    intent.putExtra("title", "Coming Up")
                    startActivity(intent)
                }


            }


        })
    }

    fun callComingUpApi() {
        progressDialogAdd.visibility = View.VISIBLE
        comingUpArrayList = ArrayList()
        val call: Call<ComingUpResponseModel> = ApiClient(mContext).getClient.whole_school_coming_up(
            "Bearer " + PreferenceManager.getaccesstoken(mContext)
        )
        call.enqueue(object : Callback<ComingUpResponseModel> {
            override fun onFailure(call: Call<ComingUpResponseModel>, t: Throwable) {

                progressDialogAdd.visibility = View.GONE
            }

            override fun onResponse(
                call: Call<ComingUpResponseModel>,
                response: Response<ComingUpResponseModel>
            ) {
                val responsedata = response.body()
                progressDialogAdd.visibility = View.GONE
//                if (responsedata != null) {
//                    try {
                comingUpArrayList = ArrayList()
                comingUpArrayList.addAll(response.body()!!.responseArray.data)
                if (response.body()!!.status == 100) {
                    if (comingUpArrayList.size > 0) {
                         adapterComing = ComingUpAdapter(comingUpArrayList, mContext)
                        mListView.adapter = adapterComing
                    } else {
                        DialogFunctions.commonErrorAlertDialog(
                            mContext.resources.getString(
                                R.string.alert
                            ), ConstantWords.status_132, mContext
                        )

                    }
                } else {

                    DialogFunctions.commonErrorAlertDialog(
                        mContext.resources.getString(R.string.alert),
                        ConstantFunctions.commonErrorString(response.body()!!.status),
                        mContext
                    )
                }


//                    } catch (e: Exception) {
//                        e.printStackTrace()
//                    }
            }
//            }

        })
    }

    private fun callStatusChangeApi(ccaDaysId: String,event_position:Int, status: String) {
        progressDialogAdd.visibility=View.VISIBLE
      //  comingUpArrayList= ArrayList()
        var token="Bearer "+PreferenceManager.getaccesstoken(mContext)
        var model= NotificationApiModel(0,500)
        val paramObject = JsonObject()
        paramObject.addProperty("id", ccaDaysId)
        paramObject.addProperty("type", "whole_school_coming_ups")
        val call: Call<StudentShopCardResponseModel> = ApiClient(mContext).getClient.status_changeAPI(token,paramObject)
        call.enqueue(object : Callback<StudentShopCardResponseModel> {
            override fun onFailure(call: Call<StudentShopCardResponseModel>, t: Throwable) {
                progressDialogAdd.visibility=View.GONE
            }
            override fun onResponse(call: Call<StudentShopCardResponseModel>, response: Response<StudentShopCardResponseModel>) {
                val responsedata = response.body()
                progressDialogAdd.visibility=View.GONE
                if (responsedata != null) {
                    try {


                        if (status.equals("0", ignoreCase = true) || status.equals(
                                "2",
                                ignoreCase = true
                            )
                        ) {
                            comingUpArrayList.get(event_position).read_unread_status="1"
                            adapterComing.notifyDataSetChanged()
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