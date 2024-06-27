package com.nas.alreem.activity.canteen

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
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.nas.alreem.R
import com.nas.alreem.activity.canteen.model.topup.WalletAmountApiModel
import com.nas.alreem.activity.canteen.model.topup.WalletAmountModel
import com.nas.alreem.activity.canteen.model.wallet.WalletBalanceApiModel
import com.nas.alreem.activity.canteen.model.wallet.WalletBalanceModel
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.activity.payments.adapter.StudentListAdapter
import com.nas.alreem.activity.payments.model.StudentList
import com.nas.alreem.activity.payments.model.StudentListModel
import com.nas.alreem.activity.payments.model.payment_gateway.PaymentGatewayApiModel
import com.nas.alreem.activity.payments.model.payment_gateway.PaymentGatewayModel
import com.nas.alreem.activity.payments.model.payment_token.PaymentTokenApiModel
import com.nas.alreem.activity.payments.model.payment_token.PaymentTokenModel
import com.nas.alreem.constants.*

import payment.sdk.android.PaymentClient
import payment.sdk.android.cardpayment.CardPaymentData
import payment.sdk.android.cardpayment.CardPaymentRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class CanteenPaymentActivity:AppCompatActivity() {
    var nContext: Context = this

    var tab_type: String? = null
    var relativeHeader: RelativeLayout? = null
   // var headermanager: HeaderManager? = null
    var back: ImageView? =
        null
    lateinit var btn_history:ImageView
    var home:android.widget.ImageView? = null
    var extras: Bundle? = null
    private val mTitle: String? = null
    private val mTabId: String? = null
    private lateinit var amount: EditText
    var mTitleTextView: TextView? = null
    lateinit var walletbalance:TextView
   var student_Name: TextView? = null
    private var card_walletbalance: RelativeLayout? = null
    private lateinit var belowViewRelative: RelativeLayout
    private var addToWallet: Button? = null
    private lateinit var paymentRelative: RelativeLayout
   /* var studentsModelArrayList: ArrayList<StudentModel> = ArrayList<StudentModel>()
    private val mAbsenceListViewArray: ArrayList<LeavesModel>? = null*/
   lateinit var studImg: ImageView
    lateinit var studentNameTxt: TextView
    lateinit var studentSpinner: LinearLayout
    lateinit var studentName: String
    lateinit var studentId: String
    lateinit var studentImg: String
    lateinit var studentClass: String
    var studentListArrayList = ArrayList<StudentList>()
    lateinit var studentlist: ArrayList<String>
    lateinit var studentname: TextView
    var WalletAmount:Int=0
    var apiCall:Int=0
    lateinit var dropdown: LinearLayout
    var orderId = ""
    //val JSON_MEDIA_TYPE: MediaType = parse.parse("application/octet-stream")
    var firstVisit = false
    var merchantpassword = "16496a68b8ac0fb9b6fde61274272457"
    var PaymentToken = ""
    var orderRef:String = ""
    var PayUrl:kotlin.String? = ""
    var AuthUrl:kotlin.String? = ""
    var isFrom: String? = null
    var payAmount = ""
    var merchantOrderReference = ""
    var canteen_response = ""
    var Error = ""
    var topup_limit = ""
    var order_id = ""
    var invoice_ref:String=""
    lateinit var activity: Activity
   lateinit var title:TextView
   lateinit var mProgressRelLayout:ProgressBar
   lateinit var WALLET_TOPUP_LIMIT:String
   lateinit var logoClickImg:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.canteen_first_activity)
        nContext = this
        initialiseUI()
        if (ConstantFunctions.internetCheck(nContext)) {
//            progressDialog.visibility= View.VISIBLE
            callStudentListApi()
        } else {
            DialogFunctions.showInternetAlertDialog(nContext)
        }

       // wallet_details()
        studentSpinner.setOnClickListener(){
            showStudentList(nContext,studentListArrayList)
        }
    }
    private fun initialiseUI(){
        activity=this
        paymentRelative = findViewById(R.id.paymentRelative)
        WALLET_TOPUP_LIMIT=intent.getStringExtra("WALLET_TOPUP_LIMIT").toString()
        studentSpinner = findViewById<LinearLayout>(R.id.studentSpinner)
        back=findViewById(R.id.back)
        btn_history=findViewById(R.id.history)
        amount = findViewById(R.id.et_amount)
        addToWallet = findViewById(R.id.addToWallet)
        walletbalance = findViewById(R.id.walletbalance)
        logoClickImg = findViewById(R.id.logoclick)
        card_walletbalance = findViewById(R.id.card_walletbalance)
        belowViewRelative = findViewById(R.id.belowViewRelative)
        paymentRelative.visibility = View.GONE
        belowViewRelative.visibility = View.VISIBLE
        studentNameTxt = findViewById<TextView>(R.id.studentName)
        studImg = findViewById<ImageView>(R.id.imagicon)
        mProgressRelLayout=findViewById(R.id.progressDialog)
        title=findViewById(R.id.titleTextView2)
        title.setText("Payment")

//        val aniRotate: Animation =
//            AnimationUtils.loadAnimation(nContext, R.anim.linear_interpolator)
//        mProgressRelLayout.startAnimation(aniRotate)
        back!!.setOnClickListener(){
            finish()
        }
        logoClickImg.setOnClickListener {
            val intent = Intent(nContext, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
        btn_history.setOnClickListener(){
            val i = Intent(nContext, PaymentHistory_Activity::class.java)
            PreferenceManager.setStudentID(nContext,"")
            nContext.startActivity(i)
        }


        addToWallet!!.setOnClickListener(){

            if (!amount.getText().toString().equals("")) {
                val paymentAmounts = amount.getText().toString()
                payAmount = amount.getText().toString()
                val amountInt = amount.getText().toString().toInt() * 100
                val payment_amount = amountInt.toString()
                if (payment_amount != "") {
                    val paymentAmount = payment_amount.toInt()
                    if (paymentAmount > 0) {
                        if (paymentAmounts.toInt()<=PreferenceManager.getTopUpLimit(nContext)!!.toInt())
                        {
                            var arrayLength = 0
                            val array = payment_amount.toCharArray()
                            arrayLength = array.size
                            var firstNonZeroAt = 0
                            for (i in array.indices) {
                                if (!array[i].toString().equals("0")) {
                                    firstNonZeroAt = i
                                    break
                                }
                            }
                            val newArray = Arrays.copyOfRange(array, firstNonZeroAt, arrayLength)
                            val resultString = String(newArray)
                            println("amount removed zero$resultString")
                            val unixTime = System.currentTimeMillis() / 1000L
                            orderId = "NASAD" + studentId + "C" + unixTime
                            println("Unix Time:::" + unixTime + "Order ID" + orderId)
                            amount.getText().clear()
                            //                        startActivity(intent);
                            order_id = ""
                            merchantOrderReference = ""
                            mProgressRelLayout.visibility=View.VISIBLE
                            if (ConstantFunctions.internetCheck(nContext)) {
                                getpaymenttoken()
                            } else {
                                DialogFunctions.showInternetAlertDialog(nContext)
                            }

                        }
                        else{
                            Toast.makeText(
                                nContext,
                                "Allow only upto " + PreferenceManager.getTopUpLimit(nContext) + "AED on a single transaction",
                                Toast.LENGTH_SHORT
                            ).show()
                        }


                    } else {
                        Toast.makeText(nContext, "Please enter amount", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(nContext, "Please enter amount", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(nContext, "Please enter amount", Toast.LENGTH_SHORT).show()
            }
        }


    }
    fun callStudentListApi()
    {
       // progressDialog.visibility = View.VISIBLE
        val token = PreferenceManager.getaccesstoken(nContext)
        val call: Call<StudentListModel> = ApiClient.getClient.studentList("Bearer "+token)
        call.enqueue(object : Callback<StudentListModel> {
            override fun onFailure(call: Call<StudentListModel>, t: Throwable) {
                //progressDialog.visibility = View.GONE
            }
            override fun onResponse(call: Call<StudentListModel>, response: Response<StudentListModel>) {
                //progressDialog.visibility = View.GONE
                //val arraySize :Int = response.body()!!.responseArray.studentList.size
                if (response.body()!!.status==100)
                {
                    studentListArrayList.addAll(response.body()!!.responseArray.studentList)
                    if (PreferenceManager.getStudentID(nContext).equals(""))
                    {
                        studentName=studentListArrayList.get(0).name
                        studentImg=studentListArrayList.get(0).photo
                        studentId=studentListArrayList.get(0).id
                        studentClass=studentListArrayList.get(0).section
                        PreferenceManager.setStudentID(nContext,studentId)
                        PreferenceManager.setStudentName(nContext,studentName)
                        PreferenceManager.setStudentPhoto(nContext,studentImg)
                        PreferenceManager.setStudentClass(nContext,studentClass)
                        studentNameTxt.text=studentName
                        if(!studentImg.equals(""))
                        {
                            Glide.with(nContext) //1
                                .load(studentImg)
                                .placeholder(R.drawable.student)
                                .error(R.drawable.student)
                                .skipMemoryCache(true) //2
                                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                                .transform(CircleCrop()) //4
                                .into(studImg)
                        }
                        else{
                            studImg.setImageResource(R.drawable.student)
                        }

                    }
                    else{
                        studentName= PreferenceManager.getStudentName(nContext)!!
                        studentImg=PreferenceManager.getStudentPhoto(nContext)!!
                        studentId= PreferenceManager.getStudentID(nContext)!!
                        studentClass= PreferenceManager.getStudentClass(nContext)!!
                        studentNameTxt.text=studentName
                        if(!studentImg.equals(""))
                        {
                            Glide.with(nContext) //1
                                .load(studentImg)
                                .placeholder(R.drawable.student)
                                .error(R.drawable.student)
                                .skipMemoryCache(true) //2
                                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                                .transform(CircleCrop()) //4
                                .into(studImg)
                        }
                        else{
                            studImg.setImageResource(R.drawable.student)
                        }
                    }
                    if (ConstantFunctions.internetCheck(nContext)) {
//            progressDialog.visibility= View.VISIBLE
                        wallet_details()
                    } else {
                        DialogFunctions.showInternetAlertDialog(nContext)
                    }


                    //callStudentInfoApi()
                }


            }

        })
    }
    fun showStudentList(context: Context ,mStudentList : ArrayList<StudentList>)
    {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialogue_student_list)
        var iconImageView = dialog.findViewById(R.id.iconImageView) as ImageView
        var btn_dismiss = dialog.findViewById(R.id.btn_dismiss) as Button
        var studentListRecycler = dialog.findViewById(R.id.studentListRecycler) as RecyclerView
        iconImageView.setImageResource(R.drawable.boy)
        //if(mSocialMediaArray.get())
        val sdk = Build.VERSION.SDK_INT
        if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
            btn_dismiss.setBackgroundDrawable(
                nContext.resources.getDrawable(R.drawable.button_new)
            )
        } else {
            btn_dismiss.background = nContext.resources.getDrawable(R.drawable.button_new)
        }

        studentListRecycler.setHasFixedSize(true)
        val llm = LinearLayoutManager(nContext)
        llm.orientation = LinearLayoutManager.VERTICAL
        studentListRecycler.layoutManager = llm
        if(mStudentList.size>0)
        {
            val studentAdapter = StudentListAdapter(nContext,mStudentList)
            studentListRecycler.adapter = studentAdapter
        }

        btn_dismiss.setOnClickListener()
        {
            dialog.dismiss()
        }
        studentListRecycler.addOnItemClickListener(object: OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                // Your logic
               // progressDialog.visibility=View.VISIBLE
                val aniRotate: Animation =
                    AnimationUtils.loadAnimation(nContext, R.anim.linear_interpolator)
                //progressDialog.startAnimation(aniRotate)

                studentName=studentListArrayList.get(position).name
                studentImg=studentListArrayList.get(position).photo
                studentId=studentListArrayList.get(position).id
                studentClass=studentListArrayList.get(position).section
                PreferenceManager.setStudentID(nContext,studentId)
                PreferenceManager.setStudentName(nContext,studentName)
                PreferenceManager.setStudentPhoto(nContext,studentImg)
                PreferenceManager.setStudentClass(nContext,studentClass)
                studentNameTxt.text=studentName
                //studentInfoArrayList.clear()
                if(!studentImg.equals(""))
                {
                    Glide.with(nContext) //1
                        .load(studentImg)
                        .placeholder(R.drawable.student)
                        .error(R.drawable.student)
                        .skipMemoryCache(true) //2
                        .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                        .transform(CircleCrop()) //4
                        .into(studImg)
                }
                else
                {
                    studImg.setImageResource(R.drawable.student)
                }

                if (ConstantFunctions.internetCheck(nContext)) {
//            progressDialog.visibility= View.VISIBLE
                    wallet_details()
                } else {
                    DialogFunctions.showInternetAlertDialog(nContext)
                }

                //progressDialog.visibility = View.VISIBLE

                //  Toast.makeText(activity, mStudentList.get(position).name, Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
        })
        dialog.show()
    }
    private fun wallet_details(){
mProgressRelLayout.visibility=View.VISIBLE
        val token = PreferenceManager.getaccesstoken(nContext)
        var canteenCart= WalletBalanceApiModel(PreferenceManager.getStudentID(nContext).toString())
        val call: Call<WalletBalanceModel> = ApiClient.getClient.get_wallet_balance(canteenCart,"Bearer "+token)
        call.enqueue(object : Callback<WalletBalanceModel> {
            override fun onFailure(call: Call<WalletBalanceModel>, t: Throwable) {
                mProgressRelLayout.visibility=View.GONE
            }
            override fun onResponse(call: Call<WalletBalanceModel>, response: Response<WalletBalanceModel>) {
                val responsedata = response.body()
                if (responsedata!!.status==100) {
                    mProgressRelLayout.visibility=View.GONE
                    WalletAmount=response!!.body()!!.responseArray.wallet_balance
                    walletbalance.setText(WalletAmount.toString())
                    mProgressRelLayout.visibility=View.GONE
                }else
                {

                    DialogFunctions.commonErrorAlertDialog(nContext.resources.getString(R.string.alert), ConstantFunctions.commonErrorString(response.body()!!.status), nContext)
                }
            }

        })
    }
    private fun getpaymenttoken(){
        mProgressRelLayout.visibility=View.VISIBLE
        val token = PreferenceManager.getaccesstoken(nContext)
        val paymentTokenBody = PaymentTokenApiModel( PreferenceManager.getStudentID(nContext).toString(),"wallet_topup")
        val call: Call<PaymentTokenModel> =
            ApiClient.getClient.payment_token(paymentTokenBody, "Bearer " + token)
        call.enqueue(object : Callback<PaymentTokenModel> {
            override fun onFailure(call: Call<PaymentTokenModel>, t: Throwable) {
                mProgressRelLayout.visibility = View.GONE
            }

            override fun onResponse(call: Call<PaymentTokenModel>, response: Response<PaymentTokenModel>) {
                val responsedata = response.body()
                mProgressRelLayout.visibility = View.GONE
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
                            mProgressRelLayout.visibility=View.VISIBLE
                            if (ConstantFunctions.internetCheck(nContext)) {
                                callForPayment(payment_token,amt.toString())
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
    private fun callForPayment(payment_token:String,amount:String){
        mProgressRelLayout.visibility=View.VISIBLE
        val tsLong = System.currentTimeMillis() / 1000
        val ts = tsLong.toString()
        var mechantorderRef=invoice_ref+"-"+ts
        val token = PreferenceManager.getaccesstoken(nContext)
        val paymentGatewayBody = PaymentGatewayApiModel(amount,PreferenceManager.getEmailId(nContext).toString(),
            mechantorderRef,studentName,"","NAS","","Abu Dhabi",
            payment_token,"wallet_topup")
        val call: Call<PaymentGatewayModel> =
            ApiClient.getClient.payment_gateway(paymentGatewayBody, "Bearer " + token)
        call.enqueue(object : Callback<PaymentGatewayModel> {
            override fun onFailure(call: Call<PaymentGatewayModel>, t: Throwable) {
                mProgressRelLayout.visibility = View.GONE
            }

            override fun onResponse(call: Call<PaymentGatewayModel>, response: Response<PaymentGatewayModel>) {
                val responsedata = response.body()
                mProgressRelLayout.visibility = View.GONE
                if (responsedata != null) {
                    try {

                        if (response.body()!!.status==100) {
                           // mProgressRelLayout.visibility=View.VISIBLE
                            orderRef=responsedata.responseArray.order_reference
                            var orderPageUrl=responsedata.responseArray.order_paypage_url
                            var auth=responsedata.responseArray.authorization
                            val Code: String = orderPageUrl.split("=").toTypedArray().get(1)

                            mProgressRelLayout.visibility = View.GONE
                            val request: CardPaymentRequest = CardPaymentRequest.Builder().gatewayUrl(auth).code(Code).build()

                            val paymentClient = PaymentClient(activity, "fdhasfd")
                            paymentClient.launchCardPayment(request, 101)


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
    override fun onActivityResult(
        requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101) {
            when (resultCode) {
                Activity.RESULT_OK -> onCardPaymentResponse(
                    CardPaymentData.getFromIntent(data!!)
                )
                Activity.RESULT_CANCELED ->{
                    Toast.makeText(nContext, "Transaction Failed", Toast.LENGTH_SHORT).show();

                }
            }
        }
    }
    fun onCardPaymentResponse(data: CardPaymentData) {
        when (data.code) {
            CardPaymentData.STATUS_PAYMENT_AUTHORIZED,
            CardPaymentData.STATUS_PAYMENT_CAPTURED -> {
                if (ConstantFunctions.internetCheck(nContext))
                {
                    paySuccessApi()
                }
                else
                {
                    DialogFunctions.showInternetAlertDialog(nContext)
                }
            }
            CardPaymentData.STATUS_PAYMENT_FAILED -> {
                Toast.makeText(nContext, "Transaction Failed", Toast.LENGTH_SHORT).show();
            }
            CardPaymentData.STATUS_GENERIC_ERROR -> {
                Toast.makeText(nContext, data.reason, Toast.LENGTH_SHORT).show();
            }
            else -> IllegalArgumentException(
                "Unknown payment response (${data.reason})")
        }
    }
    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("request_code", requestCode.toString())
        Log.d("resultt_code", resultCode.toString())
        Log.d("data", data.toString())
        if (data == null) {
            mProgressRelLayout.visibility=View.GONE
            Toast.makeText(nContext, "transaction cancelled", Toast.LENGTH_SHORT).show()
        } else {
            if (requestCode == 101) {
                mProgressRelLayout.visibility=View.GONE
//            Log.d("response",data.getStringExtra("jsonResponse"));
//            String jsonObject=data.getStringExtra("jsonResponse");
//            Log.v("jsonResponse",jsonObject);
                val cardPaymentData = CardPaymentData.getFromIntent(data)
                Log.d("PAYMM", cardPaymentData.code.toString())
                Log.d("PAYMM", cardPaymentData.reason.toString())
                if (cardPaymentData.code == 2) {

                    *//* val tripDetailsAPI = """
                         {
                         "details":[
                         {
                         "amount":"$totalAmount",
                         "order_id":"$order_id",
                         "payment_detail_id":"$id",
                         "users_id":"${PreferenceData().getUserID(context)}",
                         "payment_date":"${Calendar.DATE}",
                         "type":"1",
                         "student_id":"$studentId"
                         }
                         ]
                         }
                         """.trimIndent()*//*

                 *//*   payment_type_print = "Online"
                    payTotalButton.visibility = View.GONE
                    totalLinear.visibility = View.VISIBLE
                    paidImg.visibility = View.VISIBLE
                    mainLinear.visibility = View.VISIBLE
                    printLinear.visibility = View.VISIBLE*//*

                    if (ConstantFunctions.internetCheck(nContext)) {
//            progressDialog.visibility= View.VISIBLE
                        paySuccessApi()
                    } else {
                        DialogFunctions.showInternetAlertDialog(nContext)
                    }

//                Log.d("reason",cardPaymentData.getReason());
                } else {
                    Toast.makeText(nContext, "Transaction failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }*/
    private fun paySuccessApi(){
        mProgressRelLayout.visibility=View.VISIBLE
        var devicename:String= (Build.MANUFACTURER
                + " " + Build.MODEL + " " + Build.VERSION.RELEASE
                + " " + Build.VERSION_CODES::class.java.fields[Build.VERSION.SDK_INT]
            .name)
        val token = PreferenceManager.getaccesstoken(nContext)
        val paymentSuccessBody = WalletAmountApiModel(PreferenceManager.getStudentID(nContext).toString(),orderRef,
        payAmount,"2",devicename,"1.0")
        val call: Call<WalletAmountModel> =
            ApiClient.getClient.wallet_topup(paymentSuccessBody, "Bearer " + token)
        call.enqueue(object : Callback<WalletAmountModel> {
            override fun onFailure(call: Call<WalletAmountModel>, t: Throwable) {

                mProgressRelLayout.visibility=View.INVISIBLE
            }

            override fun onResponse(call: Call<WalletAmountModel>, response: Response<WalletAmountModel>) {
                val responsedata = response.body()
                //progressDialog.visibility = View.GONE
                if (responsedata != null) {
                    try {

                        if (response.body()!!.status==100) {
                            mProgressRelLayout.visibility=View.GONE

                            WalletAmount=response.body()!!.responseArray.wallet_balance
                            walletbalance.setText(WalletAmount.toString())

                            Toast.makeText(nContext, "Transaction successfully completed", Toast.LENGTH_SHORT).show()

                        }
                        else
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