package com.nas.alreem.activity.payments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.nas.alreem.R
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.activity.payments.adapter.Canteeninfo_adapter
import com.nas.alreem.activity.payments.model.InfoCanteenModel
import com.nas.alreem.activity.payments.model.InfoListModel
import com.nas.alreem.constants.ApiClient
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.DialogFunctions
import com.nas.alreem.constants.PreferenceManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentInformationActivity : AppCompatActivity() {
    lateinit var mContext: Context
    private lateinit var logoClickImg: ImageView
    lateinit var recyclerview: RecyclerView
    lateinit var back: ImageView
    lateinit var informationlist: ArrayList<InfoListModel>
    lateinit var heading: TextView
    lateinit var logoClickImgView: ImageView
    lateinit var backRelative: RelativeLayout
    lateinit var progressDialogAdd: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.canteen_information)

        initfn()
        progressDialogAdd.visibility=View.VISIBLE
        if (ConstantFunctions.internetCheck(mContext))
        {
            callPaymentInformation()
        }
        else
        {
            DialogFunctions.showInternetAlertDialog(mContext)
        }

    }

    private fun initfn() {
        mContext = this

        informationlist = ArrayList()
        recyclerview = findViewById(R.id.canteen_info_list)
        heading=findViewById(R.id.heading)
        backRelative=findViewById(R.id.backRelative)
        logoClickImgView=findViewById(R.id.logoClickImgView)
        progressDialogAdd=findViewById(R.id.progressDialogAdd)
        heading.text="Informations"
        backRelative.setOnClickListener(View.OnClickListener {
            finish()
        })

        logoClickImgView.setOnClickListener(View.OnClickListener {
            val intent = Intent(mContext, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        })

    }
    private fun callPaymentInformation(){


        val token = PreferenceManager.getaccesstoken(mContext)

        val call: Call<InfoCanteenModel> = ApiClient.getClient.getPaymentInformation("Bearer "+token)
        call.enqueue(object : Callback<InfoCanteenModel> {
            override fun onFailure(call: Call<InfoCanteenModel>, t: Throwable) {
                progressDialogAdd.visibility=View.GONE
            }
            override fun onResponse(call: Call<InfoCanteenModel>, response: Response<InfoCanteenModel>) {
                val responsedata = response.body()
                progressDialogAdd.visibility=View.GONE
                if (responsedata!!.status==100) {

                    if(response.body()!!.responseArray.information.size>0)
                    {
                        recyclerview.layoutManager = LinearLayoutManager(mContext)
                        recyclerview.adapter = Canteeninfo_adapter(response.body()!!.responseArray.information, mContext)

                    }



                }
                else {
                    DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert), ConstantFunctions.commonErrorString(response.body()!!.status), mContext)

                }
            }

        })


    }
}