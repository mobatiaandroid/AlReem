package com.nas.alreem.activity.shop_new

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.activity.canteen.Myorderbasket_Activity
import com.nas.alreem.activity.canteen.adapter.PreorderDatesAdapter
import com.nas.alreem.activity.canteen.model.order_history.OrderHistoryApiModel
import com.nas.alreem.activity.canteen.model.order_history.OrderHistoryResponseModel
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.activity.lost_card.model.ShopHistoryModel
import com.nas.alreem.activity.shop_new.adapter.PreorderDatesAdapter_new
import com.nas.alreem.activity.shop_new.model.PaymentShopWalletHistoryModel
import com.nas.alreem.activity.shop_new.model.ShopHistoryResponseModel
import com.nas.alreem.activity.shop_new.model.ShopModel
import com.nas.alreem.constants.ApiClient
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.DialogFunctions
import com.nas.alreem.constants.PreferenceManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderhistoryActivityNew  : AppCompatActivity(){
    lateinit var nContext: Context
    private lateinit var logoClickImg: ImageView
    lateinit var recyclerview: RecyclerView
    lateinit var back: ImageView
    lateinit var progress: ProgressBar
    private var id: String? = null
    lateinit var title: TextView
    lateinit var basket: ImageView
    lateinit var preorderhis_list: ArrayList<PaymentShopWalletHistoryModel>
    lateinit var order_summery: ArrayList<ShopModel>
    lateinit var preorderhis_itemlist: ArrayList<String>
    var studentID:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.canteen_orderhistory)

        initfn()
        if (ConstantFunctions.internetCheck(nContext)) {
//            progressDialog.visibility= View.VISIBLE
            getPaymentHistory(PreferenceManager.getStudentID(nContext))
        } else {
            DialogFunctions.showInternetAlertDialog(nContext)
        }


    }

    private fun initfn() {
        nContext = this
        studentID=intent.getStringExtra("StudentId").toString()
        logoClickImg=findViewById(R.id.logoclick)
        back = findViewById(R.id.back)
        title = findViewById(R.id.textViewtitle)
        id = PreferenceManager.getStudentID(nContext).toString()
        basket = findViewById(R.id.basket)
        progress = findViewById(R.id.progress)!!

        preorderhis_list = ArrayList()
        order_summery = ArrayList()
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
    private fun getPaymentHistory(studentIdStr: String?) {
        progress.visibility= View.VISIBLE

        val studentbody= ShopHistoryModel(studentIdStr!!,"0","20")

        val call: Call<ShopHistoryResponseModel> = ApiClient.getClient.get_shop_orders_history(studentbody,"Bearer "+ PreferenceManager.getaccesstoken(nContext))
        call.enqueue(object : Callback<ShopHistoryResponseModel> {
            override fun onResponse(
                call: Call<ShopHistoryResponseModel?>,
                response: Response<ShopHistoryResponseModel?>
            ) {
                progress.visibility= View.GONE

                val responsedata = response.body()
                if (responsedata != null) {
                    try {
                        if(responsedata.status==100)
                        {

                            // Log.e("size", response.body()!!.response.data.size.toString())
                            if (response.body()!!.response.order_history.size > 0) {
                                preorderhis_list=response.body()!!.response.order_history
                                for (i in preorderhis_list.indices)
                                {
                                    order_summery=response.body()!!.response.order_history.get(i).order_summery
                                }
                                recyclerview.adapter = PreorderDatesAdapter_new(preorderhis_list, nContext,order_summery)
                            } else {



                            }

                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<ShopHistoryResponseModel?>, t: Throwable) {
                progress.visibility= View.GONE




            }
        })

    }
   /* fun callOderHistory()
    {
        progress.visibility= View.VISIBLE
        val token = PreferenceManager.getaccesstoken(nContext)
        var model= OrderHistoryApiModel(studentID,"0","100")
        val call: Call<OrderHistoryResponseModel> = ApiClient.getClient.canteen_order_history(model,"Bearer "+token)
        call.enqueue(object : Callback<OrderHistoryResponseModel> {
            override fun onFailure(call: Call<OrderHistoryResponseModel>, t: Throwable) {
                progress.visibility= View.GONE


            }
            override fun onResponse(call: Call<OrderHistoryResponseModel>, response: Response<OrderHistoryResponseModel>) {
                progress.visibility= View.GONE
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



    }*/
   override fun onBackPressed() {

       finish()

   }
}