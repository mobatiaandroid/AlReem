package com.nas.alreem.activity.canteen

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.activity.canteen.adapter.PreorderDatesAdapter
import com.nas.alreem.activity.canteen.model.order_history.OrderHistoryApiModel
import com.nas.alreem.activity.canteen.model.order_history.OrderHistoryDataModel
import com.nas.alreem.activity.canteen.model.order_history.OrderHistoryResponseModel
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.constants.ApiClient
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.DialogFunctions
import com.nas.alreem.constants.PreferenceManager

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderhistoryActivity : AppCompatActivity() {
    lateinit var nContext: Context
    private lateinit var logoClickImg: ImageView
    lateinit var recyclerview: RecyclerView
    lateinit var back: ImageView
    lateinit var progress: ProgressBar
    private var id: String? = null
    lateinit var title: TextView
    lateinit var basket: ImageView
    lateinit var preorderhis_list: ArrayList<OrderHistoryDataModel>
    lateinit var preorderhis_itemlist: ArrayList<String>
    var studentID:String=""
    lateinit var activity: Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.canteen_orderhistory)

        initfn()
        if (ConstantFunctions.internetCheck(nContext)) {
//            progressDialog.visibility= View.VISIBLE
            callOderHistory()
        } else {
            DialogFunctions.showInternetAlertDialog(nContext)
        }


    }

    private fun initfn() {
        nContext = this
        activity = this
        studentID=intent.getStringExtra("StudentId").toString()
        logoClickImg=findViewById(R.id.logoclick)
        back = findViewById(R.id.back)
        title = findViewById(R.id.textViewtitle)
        id = PreferenceManager.getStudentID(nContext).toString()
        basket = findViewById(R.id.basket)
        progress = findViewById(R.id.progress)!!

        preorderhis_list = ArrayList()
        preorderhis_itemlist = ArrayList()
        recyclerview = findViewById(R.id.date_rec)
        title.text = "Order History"
        back.setOnClickListener {
            finish()
        }
        logoClickImg.setOnClickListener {
            val intent = Intent(nContext, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
        basket.setOnClickListener {
            val i = Intent(nContext, Myorderbasket_Activity::class.java)
            nContext.startActivity(i)
        }
        recyclerview.layoutManager = LinearLayoutManager(nContext)

    }

    fun callOderHistory()
    {
        progress.visibility=View.VISIBLE
        val token = PreferenceManager.getaccesstoken(nContext)
        var model= OrderHistoryApiModel(studentID,"0","100")
        val call: Call<OrderHistoryResponseModel> = ApiClient(nContext).getClient.canteen_order_history(model,"Bearer "+token)
        call.enqueue(object : Callback<OrderHistoryResponseModel> {
            override fun onFailure(call: Call<OrderHistoryResponseModel>, t: Throwable) {
                progress.visibility=View.GONE


            }
            override fun onResponse(call: Call<OrderHistoryResponseModel>, response: Response<OrderHistoryResponseModel>) {
                progress.visibility=View.GONE
                val responsedata = response.body()

                if (responsedata!!.status==100) {

                    preorderhis_list=response.body()!!.responseArray.data
                    recyclerview.adapter = PreorderDatesAdapter(preorderhis_list, nContext)

                }
                else
                {

                    DialogFunctions.commonErrorAlertDialog(nContext.resources.getString(R.string.alert), ConstantFunctions.commonErrorString(response.body()!!.status), nContext)
                }
            }

        })



    }
    override fun onResume() {
        super.onResume()
        if (!ConstantFunctions.runMethod.equals("Dev")) {
            if (ConstantFunctions().isDeveloperModeEnabled(nContext)) {
                ConstantFunctions().showDeviceIsDeveloperPopUp(activity)
            }
        }
    }
}