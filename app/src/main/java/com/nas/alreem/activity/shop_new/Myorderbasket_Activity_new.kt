package com.nas.alreem.activity.shop_new

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.nas.alreem.BuildConfig
import com.nas.alreem.R
import com.nas.alreem.activity.canteen.CanteenPaymentActivity
import com.nas.alreem.activity.canteen.model.DateModel
import com.nas.alreem.activity.canteen.model.canteen_cart.CanteenCartApiModel
import com.nas.alreem.activity.canteen.model.canteen_cart.CartItemsListModel
import com.nas.alreem.activity.canteen.model.canteen_cart.ItemsModel
import com.nas.alreem.activity.canteen.model.preorder.CanteenPreorderApiModel
import com.nas.alreem.activity.canteen.model.preorder.CanteenPreorderModel
import com.nas.alreem.activity.canteen.model.wallet.WalletBalanceApiModel
import com.nas.alreem.activity.canteen.model.wallet.WalletBalanceModel
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.activity.lost_card.LostCardPaymentActivity
import com.nas.alreem.activity.lost_card.model.GetShopCartResponseModel
import com.nas.alreem.activity.lost_card.model.ShopCartResModel
import com.nas.alreem.activity.lost_card.model.StudentLostCardResponseModel
import com.nas.alreem.activity.payments.model.payment_gateway.PaymentGatewayApiModel
import com.nas.alreem.activity.payments.model.payment_gateway.PaymentGatewayModel
import com.nas.alreem.activity.payments.model.payment_token.PaymentTokenApiModel
import com.nas.alreem.activity.payments.model.payment_token.PaymentTokenModel
import com.nas.alreem.activity.shop_new.adapter.BasketItemsAdapter_new
import com.nas.alreem.activity.shop_new.adapter.DatesBasketAdapter_new
import com.nas.alreem.activity.shop_new.model.ItemsModelShop
import com.nas.alreem.activity.shop_new.model.OrdersModelShop
import com.nas.alreem.activity.shop_new.model.StudentShopCardResponseModel
import com.nas.alreem.constants.ApiClient
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.DialogFunctions
import com.nas.alreem.constants.PreferenceManager
import payment.sdk.android.PaymentClient
import payment.sdk.android.cardpayment.CardPaymentData
import payment.sdk.android.cardpayment.CardPaymentRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Myorderbasket_Activity_new : AppCompatActivity() {
    lateinit var nContext: Context
    lateinit var tab_type: String
    lateinit var relativeHeader: RelativeLayout
    lateinit var progress: RelativeLayout
    var ordered_user_type = ""
    var student_id = ""
    var parent_id = ""
    var staff_id = ""
    var cartTotalAmount=0
    var apiCall: Int = 0
    var cartTotalItem = 0
    lateinit var activity: Activity
    var orderReff:String = ""

    lateinit var mDateArrayList: ArrayList<DateModel>
    lateinit var cart_list: ArrayList<ShopCartResModel>
    lateinit var cart_items_list: ArrayList<CartItemsListModel>
    lateinit var dateRecyclerView: RecyclerView
    lateinit var noItemTxt: ImageView
    lateinit var itemLinear: LinearLayout
    lateinit var amountTxt: TextView
    lateinit var itemTxt: TextView
    lateinit var itemArray: ArrayList<ItemsModelShop>
    lateinit var orderArray: ArrayList<OrdersModelShop?>
    lateinit var homeBannerUrlImageArray: ArrayList<String>
    var walletAmountString = "0"
    var WalletAmount = 0
    //var CartTotalAmount = 0
    var BalanceWalletAmount = 0
    var BalanceConfirmWalletAmount = 0
    var TotalOrderedAmount = 0
    var invoice_ref:String=""
    var payAmount = ""

    private lateinit var logoClickImg: ImageView
    lateinit var back: ImageView
    lateinit var id:String
    lateinit var title: TextView
    lateinit var progressDialogAdd: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.canteen_myorderbasket_new)

        initfn()

    }

    private fun initfn() {
        //date = intent.getStringExtra("date").toString()
        nContext = this
        activity=this
        progress=findViewById(R.id.progressDialog)
        logoClickImg=findViewById(R.id.logoclick)
        back = findViewById(R.id.back)
        title = findViewById(R.id.textViewtitle)
        dateRecyclerView =
            findViewById(R.id.dateRecyclerView)
        noItemTxt =
            findViewById(R.id.noItemTxt)
        amountTxt =
            findViewById(R.id.amountTxt)
        itemTxt =
            findViewById(R.id.itemTxt)
        itemLinear =
            findViewById(R.id.itemLinear)
        progressDialogAdd =
            findViewById(R.id.progressDialogAdd)
        mDateArrayList=ArrayList()
        cart_list=ArrayList()
        cart_items_list=ArrayList()
        itemArray=ArrayList()
        orderArray=ArrayList()
        id = PreferenceManager.getStudentID(nContext).toString()
        noItemTxt = findViewById(R.id.noItemTxt)

        title.text = "Basket"

        back.setOnClickListener {
            finish()
        }
        logoClickImg.setOnClickListener {
            val intent = Intent(nContext, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
        progressDialogAdd.visibility= View.VISIBLE
        if (ConstantFunctions.internetCheck(nContext)) {
//            progressDialog.visibility= View.VISIBLE
          //  wallet_details()
            getcanteen_cart()
        } else {
            DialogFunctions.showInternetAlertDialog(nContext)
        }


        dateRecyclerView.layoutManager = LinearLayoutManager(nContext)
       /* dateRecyclerView.adapter =
            DatesBasketAdapter_new(
                cart_list,
                nContext,
                ordered_user_type,
                student_id,
                parent_id,
                staff_id,
                itemTxt,
                amountTxt,
                itemLinear,
                noItemTxt,
                dateRecyclerView,
                progressDialogAdd,
                cartTotalAmount
            )*/
        itemLinear.setOnClickListener(
            View.OnClickListener {
                var isNoItemAvailableFound = false
                var basketPos = 0
                var basketDetailPos = 0





                orderArray = ArrayList()
                    for (i in cart_list.indices) {



                            val nModel = OrdersModelShop()
                            nModel.id=cart_list[i].id.toString()
                            orderArray.add(nModel)


                    }
                 var cartIDList =ArrayList<String>()
                for (i in orderArray.indices){
                    cartIDList.add(orderArray[i]!!.id)
                }
                Log.e("data1", cartIDList.toString())
                    val gson = Gson()
                    val Data = gson.toJson(orderArray)
                    Log.e("data",Data)
                    val JSON =
                        "{\"student_id\":\"" + PreferenceManager.getStudentID(nContext) + "\"," +
                                "\"orders\":" + Data + "}"
                    //get pre order
                    progressDialogAdd.visibility= View.VISIBLE

                val intent = Intent(
                    nContext,
                    ShopCardPaymentActivity::class.java
                )
                intent.putExtra("fromDate", "2023-10-21")
                intent.putExtra("studentId", PreferenceManager.getStudentID(nContext))
                intent.putExtra("studentname", PreferenceManager.getStudentName(nContext))
                Log.e("amount", cartTotalAmount.toString())
                intent.putExtra("amount",cartTotalAmount)
                intent.putExtra("data",cartIDList.toString() )

                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
               // et_amount!!.text = "Lost Card Date"
                startActivity(intent)
                  /*  if (ConstantFunctions.internetCheck(nContext)) {
                        CallForPaymentToken(Data)
                    } else {
                        DialogFunctions.showInternetAlertDialog(nContext)
                    }*/



            })

    }
    private fun wallet_details(){
        val token = PreferenceManager.getaccesstoken(nContext)
        var canteenCart= WalletBalanceApiModel(PreferenceManager.getStudentID(nContext).toString())
        val call: Call<WalletBalanceModel> = ApiClient.getClient.get_wallet_balance(canteenCart,"Bearer "+token)
        call.enqueue(object : Callback<WalletBalanceModel> {
            override fun onFailure(call: Call<WalletBalanceModel>, t: Throwable) {

            }
            override fun onResponse(call: Call<WalletBalanceModel>, response: Response<WalletBalanceModel>) {
                val responsedata = response.body()

                if (responsedata!!.status==100) {
                    WalletAmount=response!!.body()!!.responseArray.wallet_balance

                }  else
                {

                    DialogFunctions.commonErrorAlertDialog(nContext.resources.getString(R.string.alert), ConstantFunctions.commonErrorString(response.body()!!.status), nContext)
                }
            }

        })
    }
    private fun getcanteen_cart(){
        cart_list = ArrayList()
        cartTotalAmount=0
        cartTotalItem=0
        progressDialogAdd.visibility= View.VISIBLE
        val token = PreferenceManager.getaccesstoken(nContext)
        var canteenCart= CanteenCartApiModel(PreferenceManager.getStudentID(nContext).toString())
        val call: Call<GetShopCartResponseModel> = ApiClient.getClient.get_shop_cart(canteenCart,"Bearer "+token)
        call.enqueue(object : Callback<GetShopCartResponseModel> {
            override fun onFailure(call: Call<GetShopCartResponseModel>, t: Throwable) {

                progressDialogAdd.visibility= View.GONE
            }
            override fun onResponse(call: Call<GetShopCartResponseModel>, response: Response<GetShopCartResponseModel>) {
                val responsedata = response.body()
                progressDialogAdd.visibility= View.GONE

                if (responsedata!!.status==100) {
                    progress.visibility = View.GONE
                    cart_list=response!!.body()!!.responseArray.data


                        cartTotalAmount=cartTotalAmount + responsedata.responseArray.total_amount

                    if (cartTotalAmount==0){
                        //bottomview.visibility=View.GONE
                    }else{
                        dateRecyclerView.setVisibility(
                            View.VISIBLE
                        )
                        itemLinear.setVisibility(
                            View.VISIBLE
                        )
                        noItemTxt.setVisibility(
                            View.GONE
                        )
                        //bottomview.visibility=View.VISIBLE
                        for (i in cart_list.indices){


                                cartTotalItem=cartTotalItem + cart_list[i].quantity

                        }
                        itemTxt.setText(cartTotalItem.toString() + "Items")
                        amountTxt.setText(cartTotalAmount.toString() + "AED")
                    }
                    Log.e("gfjg", cart_list.size.toString())

                    dateRecyclerView.layoutManager = LinearLayoutManager(nContext)
                    var adptr= BasketItemsAdapter_new(cart_list,
                        nContext, ordered_user_type,
                        student_id,
                        parent_id,
                        staff_id,
                        itemTxt,amountTxt,itemLinear,noItemTxt,dateRecyclerView,progressDialogAdd)
                    dateRecyclerView.adapter=adptr
                    /*dateRecyclerView.adapter =
                        DatesBasketAdapter_new(cart_list, nContext,ordered_user_type,student_id,parent_id,staff_id,
                            itemTxt,amountTxt,itemLinear,noItemTxt,dateRecyclerView,progressDialogAdd,cartTotalAmount)
*/
                }
                else if (response.body()!!.status==132)
                {
                    dateRecyclerView.setVisibility(
                        View.GONE
                    )
                    itemLinear.setVisibility(
                        View.GONE
                    )
                    noItemTxt.setVisibility(
                        View.VISIBLE
                    )
                }
                else
                {

                    DialogFunctions.commonErrorAlertDialog(nContext.resources.getString(R.string.alert), ConstantFunctions.commonErrorString(response.body()!!.status), nContext)
                }
            }

        })
    }
    private fun getPreOrder(data:String,itemArray:ArrayList<ItemsModel>){
        val token = PreferenceManager.getaccesstoken(nContext)
        progressDialogAdd.visibility= View.VISIBLE
        var canteenCart= CanteenPreorderApiModel(PreferenceManager.getStudentID(nContext).toString(),itemArray)
        val call: Call<CanteenPreorderModel> = ApiClient.getClient.canteen_preorder(canteenCart,"Bearer "+token)
        call.enqueue(object : Callback<CanteenPreorderModel> {
            override fun onFailure(call: Call<CanteenPreorderModel>, t: Throwable) {
                progressDialogAdd.visibility= View.GONE
            }
            override fun onResponse(call: Call<CanteenPreorderModel>, response: Response<CanteenPreorderModel>) {
                val responsedata = response.body()
                progressDialogAdd.visibility= View.GONE
                if (responsedata!!.status==100) {
                    showDialogAlertDismiss(
                        nContext,
                        "Thank You!",
                        "Your order has been successfully placed",
                        R.drawable.tick,
                        R.drawable.round
                    )

                    dateRecyclerView.setVisibility(
                        View.GONE
                    )
                    itemLinear.setVisibility(
                        View.GONE
                    )
                    noItemTxt.setVisibility(
                        View.VISIBLE
                    )

                }  else
                {

                    DialogFunctions.commonErrorAlertDialog(nContext.resources.getString(R.string.alert), ConstantFunctions.commonErrorString(response.body()!!.status), nContext)
                }
            }

        })
    }


    fun showInsufficientBal(
        activity: Context?,
        msgHead: String?,
        msg: String?,
        ico: Int,
        bgIcon: Int
    ) {
        val dialog = Dialog(activity!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_ok_cancel)
        val icon = dialog.findViewById<View>(R.id.iconImageView) as ImageView
        icon.setBackgroundResource(bgIcon)
        icon.setImageResource(ico)
        val text = dialog.findViewById<View>(R.id.text_dialog) as TextView
        val textHead = dialog.findViewById<View>(R.id.alertHead) as TextView
        text.text = msg
        textHead.text = msgHead
        val dialogButton = dialog.findViewById<View>(R.id.btn_Ok) as Button
        dialogButton.setOnClickListener {
            val intent = Intent(
                nContext,
                CanteenPaymentActivity::class.java
            )
            dialog.dismiss()
            finish()
            startActivity(intent)
            /*if (PreferenceManager.getUserId(com.mobatia.naisapp.activities.canteen_new.BasketListActivity.mContext)
                    .equalsIgnoreCase("")
            ) {
                val intent = Intent(
                    com.mobatia.naisapp.activities.canteen_new.BasketListActivity.mContext,
                    CanteenFirstStaffActivity::class.java
                )
                intent.putExtra("tab_type", "Canteen")
                intent.putExtra("email", "")
                intent.putExtra("isFrom", "Preorder")
                dialog.dismiss()
                finish()
                startActivity(intent)
            } else {
                val intent = Intent(
                    com.mobatia.naisapp.activities.canteen_new.BasketListActivity.mContext,
                    CanteenFirstActivity::class.java
                )
                intent.putExtra("tab_type", "Canteen")
                intent.putExtra("email", "")
                intent.putExtra("isFrom", "Preorder")
                dialog.dismiss()
                finish()
                startActivity(intent)
            }*/
        }
        val dialogButtonCancel = dialog.findViewById<View>(R.id.btn_Cancel) as Button
        dialogButtonCancel.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }
    fun showDialogAlertDismiss(
        context: Context,
        msgHead: String?,
        msg: String?,
        ico: Int,
        bgIcon: Int
    ) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_common_error_alert)
        val icon = dialog.findViewById<View>(R.id.iconImageView) as ImageView
        icon.setBackgroundResource(bgIcon)
        icon.setImageResource(ico)
        val text = dialog.findViewById<View>(R.id.messageTxt) as TextView
        val textHead = dialog.findViewById<View>(R.id.alertHead) as TextView
        text.text = msg
        textHead.text = msgHead
        val dialogButton = dialog.findViewById<View>(R.id.btn_Ok) as Button
        dialogButton.setOnClickListener { dialog.dismiss() }
        //		Button dialogButtonCancel = (Button) dialog.findViewById(R.id.btn_Cancel);
//		dialogButtonCancel.setVisibility(View.GONE);
//		dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				dialog.dismiss();
//			}
//		});
        dialog.show()
    }

    private fun CallForPaymentToken(Data: String) {
        progressDialogAdd.visibility=View.VISIBLE
        val token = PreferenceManager.getaccesstoken(nContext)
        val paymentTokenBody = PaymentTokenApiModel( PreferenceManager.getStudentID(nContext).toString(),"shop")
        val call: Call<PaymentTokenModel> =
            ApiClient.getClient.payment_token(paymentTokenBody, "Bearer " + token)
        call.enqueue(object : Callback<PaymentTokenModel> {
            override fun onFailure(call: Call<PaymentTokenModel>, t: Throwable) {
                progressDialogAdd.visibility = View.GONE
            }

            override fun onResponse(call: Call<PaymentTokenModel>, response: Response<PaymentTokenModel>) {
                val responsedata = response.body()
                progressDialogAdd.visibility = View.GONE
                if (responsedata != null) {
                    try {

                        if (response.body()!!.status==100) {
                            var payment_token=responsedata.responseArray.access_token
                            val tsLong = System.currentTimeMillis() / 1000
                            val ts = tsLong.toString()
                            invoice_ref="NASSHOPAND"
                            var mechantorderRef=invoice_ref+"-"+ts

                            val amountDouble: Double = WalletAmount.toDouble() * 100
                            val amuntInt = amountDouble.toInt()
                            val strDoubleAmount = amuntInt.toString()
                            //order_id= "BISAD" + id + "S" + studentId
                            payAmount= cartTotalAmount.toString()
                            var amt:Int=payAmount.toInt() * 100
                            progressDialogAdd.visibility=View.VISIBLE
                            if (ConstantFunctions.internetCheck(nContext)) {
                                callForPayment(payment_token,amt.toString(),Data)
                            } else {
                                DialogFunctions.showInternetAlertDialog(nContext)
                            }



                        }else
                        {

                            DialogFunctions.commonErrorAlertDialog(nContext.resources.getString(R.string.alert), ConstantFunctions.commonErrorString(response.body()!!.status), nContext)
                        }


                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

        })


    }

    private fun callForPayment(paymentToken: String, amount: String, Data: String) {
        progressDialogAdd.visibility=View.VISIBLE
        val tsLong = System.currentTimeMillis() / 1000
        val ts = tsLong.toString()
        var mechantorderRef=invoice_ref+"-"+ts
        Log.e("mechantorderRef",mechantorderRef)
        val token = PreferenceManager.getaccesstoken(nContext)
        val paymentGatewayBody = PaymentGatewayApiModel(amount,PreferenceManager.getEmailId(nContext).toString(),
            mechantorderRef, PreferenceManager.getStudentName(nContext)!!,"","NAS","","Abu Dhabi",
            paymentToken,"wallet_topup")
        val call: Call<PaymentGatewayModel> =
            ApiClient.getClient.payment_gateway(paymentGatewayBody, "Bearer " + token)
        call.enqueue(object : Callback<PaymentGatewayModel> {
            override fun onFailure(call: Call<PaymentGatewayModel>, t: Throwable) {
                progressDialogAdd.visibility = View.GONE
            }

            override fun onResponse(call: Call<PaymentGatewayModel>, response: Response<PaymentGatewayModel>) {
                val responsedata = response.body()
                progressDialogAdd.visibility = View.GONE
                if (responsedata != null) {
                    try {

                        if (response.body()!!.status==100) {
                            progressDialogAdd.visibility=View.VISIBLE
                            orderReff=responsedata.responseArray.order_reference
                            var orderPageUrl=responsedata.responseArray.order_paypage_url
                            var auth=responsedata.responseArray.authorization
                            val Code: String = orderPageUrl.split("=").toTypedArray().get(1)

                            progressDialogAdd.visibility = View.GONE
                            val request: CardPaymentRequest = CardPaymentRequest.Builder().gatewayUrl(auth).code(Code).build()

                            val paymentClient = PaymentClient(activity, "fdhasfd")
                            paymentClient.launchCardPayment(request, 101)
                            CallWalletSubmission(Data)


                        }else
                        {

                            DialogFunctions.commonErrorAlertDialog(nContext.resources.getString(R.string.alert), ConstantFunctions.commonErrorString(response.body()!!.status), nContext)
                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

        })
    }
    private fun CallWalletSubmission(data: String) {
        val deviceBrand = Build.MANUFACTURER
        val deviceModel = Build.MODEL
        val osVersion = Build.VERSION.RELEASE
        val devicename = "$deviceBrand $deviceModel $osVersion"
        //  int versionCode= BuildConfig.VERSION_NAME;
        val version: String = BuildConfig.VERSION_NAME
        progressDialogAdd.visibility = View.VISIBLE
        val paramObject = JsonObject()
        paramObject.addProperty("student_id", PreferenceManager.getStudentName(nContext))
        paramObject.addProperty("shop_cart_id",data )
        paramObject.addProperty("total_amount",cartTotalAmount )
        paramObject.addProperty("order_reference", orderReff)
        paramObject.addProperty("device_type", "2")
        paramObject.addProperty("device_name", devicename)
        paramObject.addProperty("app_version", version)
        val call: Call<StudentShopCardResponseModel> =
            ApiClient.getClient.shop_order_submit("Bearer " + PreferenceManager.getaccesstoken(nContext), paramObject)
        call.enqueue(object : Callback<StudentShopCardResponseModel> {
            override fun onFailure(call: Call<StudentShopCardResponseModel>, t: Throwable) {
                progressDialogAdd.visibility = View.GONE
            }

            override fun onResponse(
                call: Call<StudentShopCardResponseModel>,
                response: Response<StudentShopCardResponseModel>
            ) {
                val responsedata = response.body()
                progressDialogAdd.visibility = View.GONE
                if (responsedata != null) {
                    try {

                        if (responsedata.responsecode.equals("200")) {
                            if (responsedata.response.statuscode.equals("303")) {
                                showDialogAlertDismissOk(
                                    nContext as Activity?,
                                    "Payment Successful",
                                    "Thank You!",
                                    R.drawable.tick,
                                    R.drawable.round
                                )
                            }
                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

        })
    }
    fun showDialogAlertDismissOk(
        activity: Activity?,
        msgHead: String?,
        msg: String?,
        ico: Int,
        bgIcon: Int
    ) {
        val dialog = Dialog(activity!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_common_error_alert)
        val icon = dialog.findViewById<View>(R.id.iconImageView) as ImageView
        icon.setBackgroundResource(bgIcon)
        icon.setImageResource(ico)
        val text = dialog.findViewById<View>(R.id.messageTxt) as TextView
        val textHead = dialog.findViewById<View>(R.id.alertHead) as TextView
        text.text = msg
        textHead.text = msgHead
        val dialogButton = dialog.findViewById<View>(R.id.btn_Ok) as Button
        dialogButton.setOnClickListener {
            dialog.dismiss()
            finish()
        }

        dialog.show()
    }
    fun showSuccessAlertnew(context: Context, message: String, msgHead: String) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_common_error_alert)
        var iconImageView = dialog.findViewById(R.id.iconImageView) as ImageView
        var alertHead = dialog.findViewById(R.id.alertHead) as TextView
        var text_dialog = dialog.findViewById(R.id.text_dialog) as TextView
        var btn_Ok = dialog.findViewById(R.id.btn_Ok) as Button
        text_dialog.text = message
        alertHead.text = msgHead
        iconImageView.setImageResource(R.drawable.exclamationicon)
        btn_Ok.setOnClickListener()
        {
            dialog.dismiss()
        }
        dialog.show()
    }
}