package com.nas.alreem.activity.canteen

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
import com.nas.alreem.activity.canteen.adapter.Canteeninfo_adapter
import com.nas.alreem.activity.canteen.model.information.InfoCanteenModel
import com.nas.alreem.activity.canteen.model.information.InfoListModel
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.constants.ApiClient

import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.DialogFunctions
import com.nas.alreem.constants.PreferenceManager

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InformationActivity : AppCompatActivity() {
    lateinit var mContext: Context
    private lateinit var logoClickImg: ImageView
    lateinit var recyclerview: RecyclerView
    lateinit var back: RelativeLayout
    lateinit var progress: ProgressBar
    lateinit var informationlist: ArrayList<InfoListModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.canteen_information)

        initfn()
        if (ConstantFunctions.internetCheck(mContext)) {
//            progressDialog.visibility= View.VISIBLE
            callCanteenInformation()
        } else {
            DialogFunctions.showInternetAlertDialog(mContext)
        }

    }

    private fun initfn() {
        mContext = this
        logoClickImg=findViewById(R.id.logoClickImgView)
        informationlist = ArrayList()
        recyclerview = findViewById(R.id.canteen_info_list)
        back = findViewById(R.id.backRelative)
        progress = findViewById(R.id.progressDialogAdd)!!

        val text1 = findViewById<TextView>(R.id.heading)

        text1.text = "Information"
        back.setOnClickListener {
            finish()
        }
        logoClickImg.setOnClickListener {
            val intent = Intent(mContext, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

    }
    private fun callCanteenInformation(){
        progress.visibility = View.VISIBLE
        val token = PreferenceManager.getaccesstoken(mContext)
        val call: Call<InfoCanteenModel> = ApiClient.getClient.getCanteenInformation("Bearer "+token)
        call.enqueue(object : Callback<InfoCanteenModel> {
            override fun onFailure(call: Call<InfoCanteenModel>, t: Throwable) {
                progress.visibility = View.GONE

            }
            override fun onResponse(call: Call<InfoCanteenModel>, response: Response<InfoCanteenModel>) {
                progress.visibility = View.GONE
                val responsedata = response.body()
                if (responsedata!!.status==100) {

                    if(response.body()!!.responseArray.information.size>0)
                    {
                        recyclerview.layoutManager = LinearLayoutManager(mContext)
                        recyclerview.adapter = Canteeninfo_adapter(response.body()!!.responseArray.information, mContext)
                    }



                }
                else
                {

                    DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert), ConstantFunctions.commonErrorString(response.body()!!.status), mContext)
                }
            }

        })


    }
}