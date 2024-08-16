package com.nas.alreem.activity.bus_service

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
import com.nas.alreem.BuildConfig
import com.nas.alreem.R
import com.nas.alreem.activity.bus_service.model.PaymentGatewayApiModelBus
import com.nas.alreem.activity.bus_service.model.PaymentSubmitBusApiModel
import com.nas.alreem.activity.bus_service.model.PaymentSuccessResponseBusModel
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.activity.payments.model.StudentList
import com.nas.alreem.activity.payments.model.payment_gateway.PaymentGatewayModel
import com.nas.alreem.activity.payments.model.payment_token.PaymentTokenApiModel
import com.nas.alreem.activity.payments.model.payment_token.PaymentTokenModel
import com.nas.alreem.activity.shop_new.MusicInvoicePrintNew
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

class BusServiceDetailsNew : AppCompatActivity() {
    lateinit var mContext: Context
    lateinit var activity: Activity

    lateinit var paybutton:Button
    lateinit var progressDialogAdd: ProgressBar
    var invoice_ref:String=""
    var WalletAmount:Int=0
    var amount :String=""
    var name :String=""
    var class_name :String=""
    var parent_name :String=""
    var mobile_no :String=""
    var email_te :String=""
    var pickup_point :String=""
    var drop_point :String=""
    var address :String=""
    var status : String=""
    var title : String=""
    var type : String=""

    var orderReff:String = ""
    var order_id : Int =0
    var id:String=""
    var refrenceorder:String=""
    var paidDate:String=""
    var invoice_notes:String=""
lateinit var student_name:TextView
lateinit var classname:TextView
    lateinit var parentname:TextView
    lateinit var mobileno:TextView
    lateinit var email:TextView
    lateinit var amounttext:TextView
    lateinit var pickup:TextView
    lateinit var droppoint:TextView
    lateinit var Address:TextView
    lateinit var heading: TextView
    lateinit var btn_left:ImageView
    lateinit var logoClickImgView:ImageView
    lateinit var titlevalue:TextView
    lateinit var amount_text:TextView
    lateinit var amountlayout:LinearLayout
    lateinit var droptext:TextView
    lateinit var droplayout:LinearLayout
    lateinit var datetext:TextView
    lateinit var datevalue:TextView
    lateinit var datelayout:LinearLayout
     var eapdatesarray = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bus_service_details_page)
        mContext = this
        activity=this
        initfn()
        // progressDialogAdd.visibility = View.VISIBLE
        if (ConstantFunctions.internetCheck(mContext)) {
            //  callPaymentInformation()
        } else {
            //  DialogFunctions.showInternetAlertDialog(mContext)
        }
    }

    private fun initfn() {
        id= intent.getIntExtra("id",0).toString()
        Log.e("id",id)
        amount=intent.getStringExtra("amount").toString()
        status=intent.getStringExtra("status").toString()
        name=intent.getStringExtra("name").toString()
        class_name=intent.getStringExtra("class").toString()
        parent_name=intent.getStringExtra("parentname").toString()
        mobile_no=intent.getStringExtra("mobileno").toString()
        email_te=intent.getStringExtra("email").toString()
        pickup_point=intent.getStringExtra("pickup").toString()
        drop_point=intent.getStringExtra("droppoint").toString()
        address=intent.getStringExtra("address").toString()
        title=intent.getStringExtra("title").toString()
        refrenceorder=intent.getStringExtra("invoice").toString()
        paidDate=intent.getStringExtra("paidDate").toString()
        invoice_notes=intent.getStringExtra("invoicenotes").toString()
        type=intent.getStringExtra("type").toString()
        if (type.equals("EAP"))
        {
            eapdatesarray= intent.getStringArrayListExtra("eaparray")!!
            Log.e("Array", eapdatesarray.toString())

        }
        else{

        }

        heading=findViewById(R.id.heading)
        heading.text= "Registration Summery"
        paybutton=findViewById(R.id.paybutton)
        titlevalue = findViewById(R.id.titlevalue)
        progressDialogAdd=findViewById(R.id.progressDialogAdd)
        student_name=findViewById(R.id.stnameValue)
        classname=findViewById(R.id.studClassValue)
        parentname=findViewById(R.id.leaveDateFromValue)
        mobileno=findViewById(R.id.leaveDateToValue)
        email=findViewById(R.id.emailvalue)
        amounttext=findViewById(R.id.amountvalue)
        pickup=findViewById(R.id.pickupvalue)
        droppoint=findViewById(R.id.dropvalue)
        Address=findViewById(R.id.addressvalue)
        btn_left=findViewById(R.id.btn_left)
        logoClickImgView=findViewById(R.id.logoClickImgView)
        datetext=findViewById(R.id.datetext)
        datevalue=findViewById(R.id.datevalue)
        datelayout=findViewById(R.id.datelayout)

        amount_text=findViewById(R.id.amounttext)
        amountlayout=findViewById(R.id.amountlayout)
        droptext=findViewById(R.id.droptext)
        droplayout=findViewById(R.id.droplayout)
        if (amount.equals("0"))
        {
            amount_text.visibility=View.GONE
            amountlayout.visibility=View.GONE

        }
        else
        {
            amount_text.visibility=View.VISIBLE
            amountlayout.visibility=View.VISIBLE
        }
        if (drop_point.equals(""))
        {
            droptext.visibility=View.GONE
            droplayout.visibility=View.GONE
        }
        else{
            droptext.visibility=View.VISIBLE
            droplayout.visibility=View.VISIBLE
        }
        if (type.equals("EAP"))
        {
            datetext.visibility=View.VISIBLE
            datelayout.visibility=View.VISIBLE
            val cleanedString = eapdatesarray.toString().replace("[", "").replace("]", "")

            datevalue.text=cleanedString
        }
        else{
            datetext.visibility=View.GONE
            datelayout.visibility=View.GONE
        }
         classname.text=class_name
        parentname.text=parent_name
        mobileno.text=mobile_no
        email.text=email_te
        amounttext.text=amount
        pickup.text=pickup_point
        droppoint.text=drop_point
        Address.text=address
        titlevalue.text=title
if (status.equals("0"))
{
    paybutton.setText("PENDING")
    paybutton.setBackgroundResource(R.color.list_bg)
}
        else if (status.equals("1"))
{
    paybutton.setText("PAY")
}

else if (status.equals("2"))
{
    paybutton.setText("REJECTED")
    paybutton.setBackgroundResource(R.color.red)

}
else if (status.equals("3"))
{
    paybutton.setText("Show Invoice")
   // relativepaid.visibility=View.VISIBLE
   // paybutton.visibility=View.GONE
}
        paybutton.setOnClickListener {
            if (status.equals("1"))
            {
                CallForPaymentToken()
            }
            else if (status.equals("3")){
                val intent = Intent(mContext, BusserviceInvoicePrintNew::class.java)
                intent.putExtra("title", title)
                // intent.putExtra("key", newsLetterModelArrayList[position].order_summery)
                intent.putExtra("orderreference", refrenceorder)
                intent.putExtra("invoice", invoice_notes)
                intent.putExtra("amount", amount)
                intent.putExtra("paidby", name)
                intent.putExtra("paidDate", paidDate)
                intent.putExtra("tr_no", "")


                intent.putExtra(
                    "payment_type",
                    "online"
                )

                mContext!!.startActivity(intent)

            }
else{

            }
        }
        btn_left.setOnClickListener(View.OnClickListener {
            finish()
        })
        logoClickImgView.setOnClickListener(View.OnClickListener {
            val intent = Intent(mContext, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        })
    }

    private fun paySuccessApi(){
        progressDialogAdd.visibility= View.VISIBLE

        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL
        var device = manufacturer + model
        val versionName: String = BuildConfig.VERSION_NAME
        val token =PreferenceManager.getaccesstoken(mContext)
        val paymentSuccessBody = PaymentSubmitBusApiModel(PreferenceManager.getStudentID(mContext).toString(),
            id,orderReff,"2",device,versionName)
        val call: Call<PaymentSuccessResponseBusModel> =
            ApiClient.getClient.submit_busservice_payment(paymentSuccessBody, "Bearer " + token)
        call.enqueue(object : Callback<PaymentSuccessResponseBusModel> {
            override fun onFailure(call: Call<PaymentSuccessResponseBusModel>, t: Throwable) {
                progressDialogAdd.visibility= View.GONE

            }

            override fun onResponse(call: Call<PaymentSuccessResponseBusModel>, response: Response<PaymentSuccessResponseBusModel>) {
                val responsedata = response.body()
                progressDialogAdd.visibility= View.GONE
                if (responsedata != null) {
                    try {
if (response.body()!!.status==100)

{
    showDialogueWithOkSuccess(mContext,"Payment Successfully Submitted","Success",response.body()!!.responseArray.status)

}


                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

        })
    }
    private fun callForPayment(paymentToken: String, amount: String) {
        progressDialogAdd.visibility= View.VISIBLE
        val tsLong = System.currentTimeMillis() / 1000
        val ts = tsLong.toString()
        var mechantorderRef=invoice_ref+"-"+ts
        val token = PreferenceManager.getaccesstoken(mContext)
        val paymentGatewayBody = PaymentGatewayApiModelBus(amount,
            PreferenceManager.getEmailId(mContext).toString(),
            mechantorderRef, PreferenceManager.getStudentName(mContext)!!,"","NAS","","Abu Dhabi",
            paymentToken,"bus_service", id,PreferenceManager.getStudentID(mContext).toString())
        val call: Call<PaymentGatewayModel> =
            ApiClient.getClient.payment_gateway_bus(paymentGatewayBody, "Bearer " + token)
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
                            progressDialogAdd.visibility= View.VISIBLE
                            orderReff=responsedata.responseArray.order_reference
                            var orderPageUrl=responsedata.responseArray.order_paypage_url
                            var auth=responsedata.responseArray.authorization
                            val Code: String = orderPageUrl.split("=").toTypedArray().get(1)
                            order_id = responsedata.responseArray.order_id
                            progressDialogAdd.visibility = View.GONE
                            val request: CardPaymentRequest = CardPaymentRequest.Builder().gatewayUrl(auth).code(Code).build()

                            val paymentClient = PaymentClient(activity, "fdhasfd")
                            paymentClient.launchCardPayment(request, 101)


                        }else
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
    private fun CallForPaymentToken() {
        progressDialogAdd.visibility= View.VISIBLE
        val token = PreferenceManager.getaccesstoken(mContext)
        val paymentTokenBody = PaymentTokenApiModel( PreferenceManager.getStudentID(mContext).toString(),"bus_service")
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
                            invoice_ref="NASBUSAND"
                            var mechantorderRef=invoice_ref+"-"+ts

                            val amountDouble: Double = WalletAmount.toDouble() * 100
                            val amuntInt = amountDouble.toInt()
                            val strDoubleAmount = amuntInt.toString()
                            //order_id= "BISAD" + id + "S" + studentId
                            var amt:Int=amount.toInt() * 100
                            progressDialogAdd.visibility= View.VISIBLE
                            if (ConstantFunctions.internetCheck(mContext)) {
                                Log.e("id",id)
                                Log.e("amt",amt.toString())
                                Log.e("payment_token",payment_token)

                              callForPayment(payment_token,amt.toString())
                            } else {
                                DialogFunctions.showInternetAlertDialog(mContext)
                            }



                        }else
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
    fun showDialogueWithOkSuccess(
        context: Context,
        message: String,
        msgHead: String,
        status: String
    )
    {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_common_error_alert)
        var iconImageView = dialog.findViewById(R.id.iconImageView) as? ImageView
        var alertHead = dialog.findViewById(R.id.alertHead) as? TextView
        var text_dialog = dialog.findViewById(R.id.messageTxt) as? TextView
        var btn_Ok = dialog.findViewById(R.id.btn_Ok) as Button
        text_dialog?.text = message
        alertHead?.text = msgHead
        if (status.equals("3"))
        {
            paybutton.setText("Show Invoice")

            // relativepaid.visibility=View.VISIBLE
           // paybutton.visibility=View.GONE
        }
        btn_Ok.setOnClickListener()
        {
            dialog.dismiss()
            val intent = Intent(mContext, BusServiceRegisterupdate::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)

        }
        dialog.show()
    }
    override fun onActivityResult(
        requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101) {
            when (resultCode) {
                Activity.RESULT_OK -> onCardPaymentResponse(
                    CardPaymentData.getFromIntent(data!!)
                )
                Activity.RESULT_CANCELED ->{
                    Toast.makeText(mContext, "Transaction Failed", Toast.LENGTH_SHORT).show();

                }
            }
        }
    }
    fun onCardPaymentResponse(data: CardPaymentData) {
        when (data.code) {
            CardPaymentData.STATUS_PAYMENT_AUTHORIZED,
            CardPaymentData.STATUS_PAYMENT_CAPTURED -> {
                if (ConstantFunctions.internetCheck(mContext))
                {
                   // Log.e("orderreference",orderReff)
                    paySuccessApi()
                }
                else
                {
                    DialogFunctions.showInternetAlertDialog(mContext)
                }
            }
            CardPaymentData.STATUS_PAYMENT_FAILED -> {
                Toast.makeText(mContext, "Transaction Failed", Toast.LENGTH_SHORT).show();
            }
            CardPaymentData.STATUS_GENERIC_ERROR -> {
                Toast.makeText(mContext, data.reason, Toast.LENGTH_SHORT).show();
            }
            else -> IllegalArgumentException(
                "Unknown payment response (${data.reason})")
        }
    }
}
