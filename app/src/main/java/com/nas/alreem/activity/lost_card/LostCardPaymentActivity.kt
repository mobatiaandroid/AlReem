package com.nas.alreem.activity.lost_card

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
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonObject
import com.nas.alreem.BuildConfig
import com.nas.alreem.R
import com.nas.alreem.activity.ProgressBarDialog
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.activity.lost_card.model.StudentLostCardResponseModel
import com.nas.alreem.activity.payment_history.PaymentWalletHistoryModel
import com.nas.alreem.activity.payments.model.StudentList
import com.nas.alreem.activity.payments.model.payment_gateway.PaymentGatewayApiModel
import com.nas.alreem.activity.payments.model.payment_gateway.PaymentGatewayModel
import com.nas.alreem.activity.payments.model.payment_token.PaymentTokenApiModel
import com.nas.alreem.activity.payments.model.payment_token.PaymentTokenModel
import com.nas.alreem.constants.ApiClient
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.DialogFunctions
import com.nas.alreem.constants.HeaderManager
import com.nas.alreem.constants.PreferenceManager
import okhttp3.ResponseBody
import org.json.JSONObject
import payment.sdk.android.PaymentClient
import payment.sdk.android.cardpayment.CardPaymentData
import payment.sdk.android.cardpayment.CardPaymentRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LostCardPaymentActivity : AppCompatActivity() {
    lateinit var relativeHeader: RelativeLayout
    lateinit var headermanager: HeaderManager
    var back: ImageView? = null
    var home: ImageView? = null
    lateinit var mContext: Context
    lateinit var activity: Activity
    var studImg: ImageView? = null
    var stud_img = ""
    var extras: Bundle? = null
    var studentNameStr = ""
    var studentClassStr = ""
    var studentIdStr: String? = ""
    lateinit var studentName: TextView
    var stud_id = ""
    var stud_name:String? = ""
    var fromDate: String? = ""
    var invoice_ref:String=""
    var WalletAmount:Int=0
    var orderReff:String = ""

    var order_id = ""
    var mStudentSpinner: LinearLayout? = null
    var newsLetterModelArrayList: ArrayList<PaymentWalletHistoryModel> =
        ArrayList<PaymentWalletHistoryModel>()
    var studentsModelArrayList: ArrayList<StudentList> = ArrayList<StudentList>()
    var mNewsLetterListView: RecyclerView? = null
    var PaymentToken =
        ""
    var OrderRef:kotlin.String? = ""
    var PayUrl:kotlin.String? = ""
    var AuthUrl:kotlin.String? = ""
    var isFrom: String? = null
    var payAmount = ""
    var merchantOrderReference = ""
    lateinit var progressDialogAdd: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lost_card_payment)
        mContext = this
        initUI()
    }

    private fun initUI() {
        activity=this
        extras = intent.extras
        if (extras != null) {
            studentIdStr = extras!!.getString("studentId")
            fromDate = extras!!.getString("fromDate")
            stud_name = extras!!.getString("studentname")
        }
        progressDialogAdd = findViewById(R.id.progressDialogAdd)


//        getPaymentToken();
        payAmount = PreferenceManager.getLostAmount(mContext)!!
        CallForPaymentToken(studentIdStr)
        //CallForPayment(studentIdStr);
        relativeHeader = findViewById<View>(R.id.relativeHeader) as RelativeLayout
        headermanager = HeaderManager(this@LostCardPaymentActivity, "Lost ID Card")
        headermanager.getHeader(relativeHeader, 0)
        back = headermanager.leftButton
        home = headermanager.logoButton
        home!!.setOnClickListener {
            val `in` = Intent(
                mContext,
                HomeActivity::class.java
            )
            `in`.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(`in`)
        }
        headermanager.setButtonLeftSelector(
            R.drawable.back,
            R.drawable.back
        )
        back!!.setOnClickListener { finish() }
    }




    private fun CallForPaymentToken(studentID: String?) {
        progressDialogAdd.visibility=View.VISIBLE
        val token = PreferenceManager.getaccesstoken(mContext)
        val paymentTokenBody = PaymentTokenApiModel( PreferenceManager.getStudentID(mContext).toString(),"lost_card")
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
                            invoice_ref="NASCANAND"
                            var mechantorderRef=invoice_ref+"-"+ts

                            val amountDouble: Double = WalletAmount.toDouble() * 100
                            val amuntInt = amountDouble.toInt()
                            val strDoubleAmount = amuntInt.toString()
                            //order_id= "BISAD" + id + "S" + studentId
                            var amt:Int=payAmount.toInt() * 100
                            progressDialogAdd.visibility=View.VISIBLE
                            if (ConstantFunctions.internetCheck(mContext)) {
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

    private fun callForPayment(paymentToken: String, amount: String) {
        progressDialogAdd.visibility=View.VISIBLE
        val tsLong = System.currentTimeMillis() / 1000
        val ts = tsLong.toString()
        var mechantorderRef=invoice_ref+"-"+ts
        val token = PreferenceManager.getaccesstoken(mContext)
        val paymentGatewayBody = PaymentGatewayApiModel(amount,PreferenceManager.getEmailId(mContext).toString(),
            mechantorderRef, stud_name!!,"","NAS","","Abu Dhabi",
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("request_code", requestCode.toString())
        Log.d("resultt_code", resultCode.toString())
        if (data == null) {
            Toast.makeText(mContext, "transaction cancelled", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            if (requestCode == 101) {
                val cardPaymentData = CardPaymentData.getFromIntent(data)
                Log.d("PAYMM 101", cardPaymentData.code.toString())
                Log.d("PAYMM 101", cardPaymentData.reason.toString())
                if (cardPaymentData.code == 2) {
                    val JSONData =
                        "{\"details\":[{" + "\"student_id\":\"" + PreferenceManager.getStudentID(
                            mContext
                        ) + "\"," +
                                "\"amount\":\"" + payAmount + "\"," +
                                "\"keycode\":\"" + merchantOrderReference + "\"" + "}]}"
                    println("JSON DATA URL$JSONData")
                    CallWalletSubmission(JSONData)
                } else {
                    finish()
                    Toast.makeText(mContext, "Transaction failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
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
        paramObject.addProperty("student_id", studentIdStr)
        paramObject.addProperty("amount", PreferenceManager.getLostAmount(mContext))
        paramObject.addProperty("keycode", orderReff)
        paramObject.addProperty("network_response", "200")
        paramObject.addProperty("device_type", "2")
        paramObject.addProperty("device_name", devicename)
        paramObject.addProperty("app_version", version)
        val call: Call<StudentLostCardResponseModel> =
            ApiClient.getClient.student_lost_card("Bearer " + PreferenceManager.getaccesstoken(mContext),paramObject)
        call.enqueue(object : Callback<StudentLostCardResponseModel> {
            override fun onFailure(call: Call<StudentLostCardResponseModel>, t: Throwable) {
                progressDialogAdd.visibility = View.GONE
            }

            override fun onResponse(call: Call<StudentLostCardResponseModel>, response: Response<StudentLostCardResponseModel>) {
                val responsedata = response.body()
                progressDialogAdd.visibility = View.GONE
                if (responsedata != null) {
                    try {

if(responsedata.responsecode.equals("200"))
{
    if(responsedata.response.statuscode.equals("303"))
    {
        showDialogAlertDismissOk(
            mContext as Activity?,
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
       /* val service: APIInterface = APIClient.getRetrofitInstance().create(APIInterface::class.java)
        val paramObject = JsonObject()
        paramObject.addProperty("student_id", studentIdStr)
        paramObject.addProperty("order_id", order_id)
        paramObject.addProperty("device_type", "2")
        paramObject.addProperty("device_name", devicename)
        paramObject.addProperty("app_version", version)
        //  paramObject.addProperty("lost_card_date", fromDate);
        val call: Call<ResponseBody> = service.student_lost_card(
            "Bearer " + PreferenceManager.getAccessToken(mContext),
            paramObject
        )
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                progressBarDialog.hide()
                try {
                    val responseString = response.body()!!.string()
                    val obj = JSONObject(responseString)
                    val response_code = obj.getString(JTAG_RESPONSECODE)
                    if (response_code.equals("200", ignoreCase = true)) {
                        val secobj = obj.getJSONObject(JTAG_RESPONSE)
                        val status_code = secobj.getString(JTAG_STATUSCODE)
                        if (status_code.equals("303", ignoreCase = true)) {
                            showDialogAlertDismissOk(
                                mContext as Activity?,
                                "Payment Successful",
                                "Thank You!",
                                R.drawable.tick,
                                R.drawable.round
                            )
                            //finish();
                        }
                    } else if (response_code.equals("500", ignoreCase = true)) {
                        AppUtils.showDialogAlertDismiss(
                            mContext as Activity?,
                            "Alert",
                            getString(R.string.common_error),
                            R.drawable.exclamationicon,
                            R.drawable.round
                        )
                        finish()
                    } else if (response_code.equals("400", ignoreCase = true)) {

                        CallWalletSubmission(data)
                    } else if (response_code.equals("401", ignoreCase = true)) {

                        CallWalletSubmission(data)
                    } else if (response_code.equals("402", ignoreCase = true)) {

                        CallWalletSubmission(data)
                    } else {
                        CustomDialog dialog = new CustomDialog(mContext, getResources().getString(R.string.common_error)
								, getResources().getString(R.string.ok));
						dialog.show();
                        AppUtils.showDialogAlertDismiss(
                            mContext as Activity?,
                            "Alert",
                            getString(R.string.common_error),
                            R.drawable.exclamationicon,
                            R.drawable.round
                        )
                        finish()
                    }
                } catch (ex: Exception) {
                    println("The Exception in edit profile is$ex")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                progressBarDialog.hide()
                AppUtils.showDialogAlertDismiss(
                    mContext as Activity?,
                    "Alert",
                    mContext!!.getString(R.string.common_error),
                    R.drawable.exclamationicon,
                    R.drawable.round
                )
            }
        })*/


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
}