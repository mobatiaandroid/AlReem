package com.nas.alreem.activity.shop

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonObject
import com.nas.alreem.R
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.activity.shop.Adapter.CartDataAdapter
import com.nas.alreem.activity.shop.model.CartInstrumentDataModel
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import payment.sdk.android.PaymentClient
import payment.sdk.android.cardpayment.CardPaymentData
import payment.sdk.android.cardpayment.CardPaymentRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.Arrays


class SummaryActivity : AppCompatActivity() {
   // var headermanager: HeaderManager? = null
    var relativeHeader: RelativeLayout? = null
    var back: ImageView? = null
    var studImg: ImageView? = null
    var merchantOrderReference = ""
    var stud_img = ""
    var home: ImageView? = null
    var tab_type = "Summary"
    var totalValue = 0.0
    var PaymentToken = ""
    lateinit var recyclerView: RecyclerView
    private var adapter: CartDataAdapter? = null
    private var totalAmountTextView: TextView? = null
    var OrderRef = ""
    var PayUrl = ""
    var AuthUrl = ""
    var orderId = ""
    var orderReference = ""
    var activity: Activity = this
    var instrumentDataList: ArrayList<CartInstrumentDataModel> =
        ArrayList<CartInstrumentDataModel>()
    var cartIDs = ArrayList<Int>()
    var totalCost = 0.0
    private var context: Context? = null
   // var progressBarDialog: ProgressBarDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)
        context = this
     //   progressBarDialog = ProgressBarDialog(context, R.drawable.spinner)
        /*if (AppUtils.checkInternet(context)) {
            callCartSummaryAPI(PreferenceManager.getStudentID(context))
            //            callCartSummary(PreferenceManager.getStudentID(context));
        } else {
            AppUtils.showDialogAlertDismiss(
                context as Activity?,
                "Network Error",
                getString(R.string.no_internet),
                R.drawable.nonetworkicon,
                R.drawable.roundred
            )
        }*/
        relativeHeader = findViewById<View>(R.id.relativeHeader) as RelativeLayout
        totalAmountTextView = findViewById<TextView>(R.id.totalAmountTextView)
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.setLayoutManager(LinearLayoutManager(this))
     //   headermanager = HeaderManager(this@SummaryActivity, tab_type)
      //  headermanager.getHeader(relativeHeader, 0)
      //  back = headermanager.getLeftButton()
      /*  headermanager.setButtonLeftSelector(
            R.drawable.back,
            R.drawable.back
        )*/
        back!!.setOnClickListener {
            val intent = Intent(
                context,
                ShopRegisterActivity::class.java
            )
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
       // home = headermanager.getLogoButton()
        home!!.setOnClickListener {
            val `in` = Intent(
                context,
                HomeActivity::class.java
            )
            `in`.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(`in`)
        }

        // Example cart data (replace with your parsed data)
        val checkoutButton = findViewById<Button>(R.id.checkoutButton)
        checkoutButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                paymentTokenAPICall
                //                getPaymentToken();
            }
        })
    }

    override fun onRestart() {
       // progressBarDialog.hide()
      //  progressBarDialog.dismiss()
        super.onRestart()
    }

    override fun onResume() {
       // progressBarDialog.hide()
       // progressBarDialog.dismiss()
        super.onResume()
    }

    private val paymentTokenAPICall: Unit
        private get() {
            /*progressBarDialog.show()
            val service: APIInterface =
                APIClient.getRetrofitInstance().create(APIInterface::class.java)
            val call: Call<FeePayAccessTokenResponseModel> =
                service.accesstokenfeepayment("Bearer " + PreferenceManager.getAccessToken(context))
            call.enqueue(object : Callback<FeePayAccessTokenResponseModel> {
                override fun onResponse(
                    call: Call<FeePayAccessTokenResponseModel>,
                    response: Response<FeePayAccessTokenResponseModel>
                ) {
                    if (response.isSuccessful()) {
                        progressBarDialog.hide()
                        val apiResponse: FeePayAccessTokenResponseModel? = response.body()
                        val response_code: String = apiResponse.getResponsecode()
                        if (response_code == "200") {
                            val statuscode: String = apiResponse.getResponse().getStatuscode()
                            val responseData: ResponseData = apiResponse.getResponse()
                            if (statuscode == "303") {
                                PaymentToken = response.body().getResponse().getAccess_token()
                                val amountDouble = totalCost.toString().toDouble() * 100
                                val amuntInt = amountDouble.toInt()
                                val strDouble = amuntInt.toString()
                                var arrayLength = 0
                                val array = strDouble.toCharArray()
                                arrayLength = array.size
                                var firstNonZeroAt = 0
                                for (i in array.indices) {
                                    if (!array[i].toString().equals("0", ignoreCase = true)) {
                                        firstNonZeroAt = i
                                        break
                                    }
                                }
                                val newArray =
                                    Arrays.copyOfRange(array, firstNonZeroAt, arrayLength)
                                val resultString = String(newArray)
                                println("amount removed zero$resultString")
                                val unixTime = System.currentTimeMillis() / 1000L
                                orderId =
                                    "NASDUBAI_MUSIC_ACADEMY_AND_S_" + PreferenceManager.getStudentID(
                                        context
                                    )
                                callForPayment(strDouble)
                            }
                        } else {

                            //                        progressBarDialog.hide();
                            AppUtils.showDialogAlertDismiss(
                                context as Activity?,
                                "Alert",
                                getString(R.string.common_error),
                                R.drawable.exclamationicon,
                                R.drawable.round
                            )
                        }
                    }
                }

                override fun onFailure(call: Call<FeePayAccessTokenResponseModel>, t: Throwable) {
                    progressBarDialog.hide()
                    AppUtils.showDialogAlertDismiss(
                        context as Activity?,
                        "Alert",
                        context!!.getString(R.string.common_error),
                        R.drawable.exclamationicon,
                        R.drawable.round
                    )
                }
            })*/
        }


    private fun callForPayment(amount: String) {
       /* progressBarDialog.show()
        val tsLong = System.currentTimeMillis() / 1000
        val ts = tsLong.toString()
        val invoice_no = "NASDUBAI-MUSIC"
        merchantOrderReference = invoice_no + ts
        val service: APIInterface = APIClient.getRetrofitInstance().create(APIInterface::class.java)
        val paramObject = JsonObject()
        paramObject.addProperty("access_token", PaymentToken)
        paramObject.addProperty("amount", amount)
        paramObject.addProperty("merchantOrderReference", merchantOrderReference)
        paramObject.addProperty("firstName", PreferenceManager.getStudentName(context))
        paramObject.addProperty("lastName", "")
        paramObject.addProperty("address1", "NAS DUBAI")
        paramObject.addProperty("city", "DUBAI")
        paramObject.addProperty("countryCode", "UAE")
        paramObject.addProperty("emailAddress", PreferenceManager.getUserEmail(context))
        val call: Call<FeePaymentResponseModel> = service.createOrderMusicPayment(
            "Bearer " + PreferenceManager.getAccessToken(context),
            paramObject
        )
        call.enqueue(object : Callback<FeePaymentResponseModel> {
            override fun onResponse(
                call: Call<FeePaymentResponseModel>,
                response: Response<FeePaymentResponseModel>
            ) {
                if (response.isSuccessful()) {
                    progressBarDialog.hide()
                    val apiResponse: FeePaymentResponseModel? = response.body()
                    val response_code: String = apiResponse.getResponsecode()
                    if (response_code == "200") {
                        val statuscode: String = apiResponse.getResponse().getStatuscode()
                        val responseData: ResponseData = apiResponse.getResponse()
                        if (statuscode == "303") {
                            OrderRef = response.body().getResponse().getOrder_reference()
                            PayUrl = response.body().getResponse().getOrder_paypage_url()
                            AuthUrl = response.body().getResponse().getAuthorization()
                            val Code = PayUrl.split("=".toRegex()).dropLastWhile { it.isEmpty() }
                                .toTypedArray()[1]
                            *//* Log.d("PAYMM", OrderRef);
                            Log.d("PAYMM", PayUrl);
                            Log.d("PAYMM", Code);
                            Log.d("PAYMM", AuthUrl);*//*

//                            String AuthURL = "https://api-gateway.sandbox.ngenius-payments.com/transactions/paymentAuthorization";

                            //   CardPaymentRequest request = new CardPaymentRequest(AuthUrl,Code);
                            val request: CardPaymentRequest =
                                Builder().gatewayUrl(AuthUrl).code(Code).build()
                            //   PaymentClient paymentClient = new PaymentClient(mActivity);
                            val paymentClient = PaymentClient(activity, "fdhasfd")
                            paymentClient.launchCardPayment(request, 101)
                        }
                    } else if (response_code.equals("500", ignoreCase = true)) {
                        AppUtils.showDialogAlertDismiss(
                            context as Activity?,
                            "Alert",
                            getString(R.string.common_error),
                            R.drawable.exclamationicon,
                            R.drawable.round
                        )
                    } else if (response_code.equals("400", ignoreCase = true)) {
                        AppUtils.getToken(context, object : GetTokenSuccess() {
                            fun tokenrenewed() {}
                        })
                        callForPayment(amount)
                    } else if (response_code.equals("401", ignoreCase = true)) {
                        AppUtils.getToken(context, object : GetTokenSuccess() {
                            fun tokenrenewed() {}
                        })
                        callForPayment(amount)
                    } else if (response_code.equals("402", ignoreCase = true)) {
                        AppUtils.getToken(context, object : GetTokenSuccess() {
                            fun tokenrenewed() {}
                        })
                        callForPayment(amount)
                    } else {
                        *//*CustomDialog dialog = new CustomDialog(mContext, getResources().getString(R.string.common_error)
								, getResources().getString(R.string.ok));
						dialog.show();*//*
                        progressBarDialog.hide()
                        AppUtils.showDialogAlertDismiss(
                            context as Activity?,
                            "Alert",
                            getString(R.string.common_error),
                            R.drawable.exclamationicon,
                            R.drawable.round
                        )
                    }
                }
            }

            override fun onFailure(call: Call<FeePaymentResponseModel>, t: Throwable) {
                progressBarDialog.hide()
                AppUtils.showDialogAlertDismiss(
                    context as Activity?,
                    "Alert",
                    context!!.getString(R.string.common_error),
                    R.drawable.exclamationicon,
                    R.drawable.round
                )
            }
        })*/
    }

    //    private void callForPayment(String amount) {
    //        Log.e("amount", amount);
    //        Log.e("token", PaymentToken);
    //
    //        Long tsLong = System.currentTimeMillis() / 1000;
    //        String ts = tsLong.toString();
    //        String invoice_no = "NASDUBAI-MUSIC";
    //        merchantOrderReference = invoice_no + ts;
    //        Log.e("MERCHANT ORDER", merchantOrderReference);
    //        VolleyWrapper volleyWrapper = new VolleyWrapper(URLConstants.URL_CREATE_PAYMENT_FEE);
    //        String[] name = {"access_token", "amount", "merchantOrderReference", "firstName", "lastName", "address1", "city", "countryCode", "emailAddress"};
    //        String[] value = {PaymentToken, amount, merchantOrderReference, PreferenceManager.getStudentName(context), "", "NAS DUBAI", "DUBAI", "UAE", PreferenceManager.getUserEmail(context)};
    //        System.out.println("payment Access_token" + PaymentToken);
    //        System.out.println("payment amount" + amount);
    //        System.out.println("payment merchantOrderReference" + merchantOrderReference);
    //        System.out.println("payment firstName" + PreferenceManager.getStudentName(context));
    //        System.out.println("payment emailAddress" + PreferenceManager.getUserEmail(context));
    //        volleyWrapper.getResponsePOST(context, 11, name, value, new VolleyWrapper.ResponseListener() {
    //            @Override
    //            public void responseSuccess(String successResponse) {
    //                System.out.println("The response is" + successResponse);
    //                try {
    //                    JSONObject obj = new JSONObject(successResponse);
    //                    String response_code = obj.getString(JTAG_RESPONSECODE);
    //                    if (response_code.equalsIgnoreCase("200")) {
    //                        JSONObject secobj = obj.getJSONObject(JTAG_RESPONSE);
    //                        String status_code = secobj.getString(JTAG_STATUSCODE);
    //                        if (status_code.equalsIgnoreCase("303")) {
    //
    //                            OrderRef = secobj.getString("order_reference");
    //                            PayUrl = secobj.getString("order_paypage_url");
    //                            AuthUrl = secobj.getString("authorization");
    //                            String Code = PayUrl.split("=")[1];
    //                            Log.d("PAYMM", OrderRef);
    //                            Log.d("PAYMM", PayUrl);
    //                            Log.d("PAYMM", Code);
    //                            Log.d("PAYMM", AuthUrl);
    //
    ////                            String AuthURL = "https://api-gateway.sandbox.ngenius-payments.com/transactions/paymentAuthorization";
    //
    //                            //   CardPaymentRequest request = new CardPaymentRequest(AuthUrl,Code);
    //                            CardPaymentRequest request = new CardPaymentRequest.Builder().gatewayUrl(AuthUrl).code(Code).build();
    //                            //   PaymentClient paymentClient = new PaymentClient(mActivity);
    //                            PaymentClient paymentClient = new PaymentClient(activity, "fdhasfd");
    //                            paymentClient.launchCardPayment(request, 101);
    //
    //                        }
    //                    } else if (response_code.equalsIgnoreCase("500")) {
    //                        AppUtils.showDialogAlertDismiss((Activity) context, "Alert", getString(R.string.common_error), R.drawable.exclamationicon, R.drawable.round);
    //
    //                    } else if (response_code.equalsIgnoreCase("400")) {
    //                        AppUtils.getToken(context, new AppUtils.GetTokenSuccess() {
    //                            @Override
    //                            public void tokenrenewed() {
    //                            }
    //                        });
    //                        callForPayment(amount);
    //
    //                    } else if (response_code.equalsIgnoreCase("401")) {
    //                        AppUtils.getToken(context, new AppUtils.GetTokenSuccess() {
    //                            @Override
    //                            public void tokenrenewed() {
    //                            }
    //                        });
    //                        callForPayment(amount);
    //
    //                    } else if (response_code.equalsIgnoreCase("402")) {
    //                        AppUtils.getToken(context, new AppUtils.GetTokenSuccess() {
    //                            @Override
    //                            public void tokenrenewed() {
    //                            }
    //                        });
    //                        callForPayment(amount);
    //
    //                    } else {
    //                        /*CustomDialog dialog = new CustomDialog(mContext, getResources().getString(R.string.common_error)
    //								, getResources().getString(R.string.ok));
    //						dialog.show();*/
    //                        AppUtils.showDialogAlertDismiss((Activity) context, "Alert", getString(R.string.common_error), R.drawable.exclamationicon, R.drawable.round);
    //
    //                    }
    //                } catch (Exception ex) {
    //                    System.out.println("The Exception in edit profile is" + ex.toString());
    //                }
    //
    //            }
    //
    //            @Override
    //            public void responseFailure(String failureResponse) {
    //                AppUtils.showDialogAlertDismiss((Activity) context, "Alert", getString(R.string.common_error), R.drawable.exclamationicon, R.drawable.round);
    //
    //            }
    //        });
    //    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("request_code", requestCode.toString())
        Log.d("resultt_code", resultCode.toString())
        if (data == null) {
            Toast.makeText(activity, "transaction cancelled", Toast.LENGTH_SHORT).show()
        } else {
            if (requestCode == 101) {

//            Log.d("response",data.getStringExtra("jsonResponse"));
//            String jsonObject=data.getStringExtra("jsonResponse");
//            Log.v("jsonResponse",jsonObject);
                val cardPaymentData = CardPaymentData.getFromIntent(data)
                Log.d("PAYMM", cardPaymentData.code.toString())
                Log.d("PAYMM", cardPaymentData.reason.toString())
                if (cardPaymentData.code == 2) {

//                    callPaymentSuccessSubmit();
                    callPaymentSuccessAPI()

//                Log.d("reason",cardPaymentData.getReason());
                } else {
                    Toast.makeText(context, "Transaction failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun callPaymentSuccessAPI() {
       /* val cartIDString = cartIDs.toString()
        val paramObject = JsonObject()
        paramObject.addProperty("cart_id", cartIDString)
        paramObject.addProperty("order_reference", merchantOrderReference)
        paramObject.addProperty("type", "1")
        paramObject.addProperty("student_id", PreferenceManager.getStudentID(context))
        val service: APIInterface = APIClient.getRetrofitInstance().create(APIInterface::class.java)
        val call: Call<MusicBaseResponseModel> = service.postPaymentSuccess(
            "Bearer " + PreferenceManager.getAccessToken(context),
            paramObject
        )
        progressBarDialog.show()
        call.enqueue(object : Callback<MusicBaseResponseModel?> {
            override fun onResponse(
                call: Call<MusicBaseResponseModel?>,
                response: Response<MusicBaseResponseModel?>
            ) {
                progressBarDialog.hide()
                if (response.body() != null) {
                    val responseCode = response.code().toString()
                    if (response.body().getResponseCode().equalsIgnoreCase("200")) {
                        if (response.body().getResponse().getStatusCode().equalsIgnoreCase("303")) {
                            Toast.makeText(context, "Payment Successful", Toast.LENGTH_SHORT).show()
                            val intent = Intent(
                                context,
                                RegisterMusicActivity::class.java
                            )
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            startActivity(intent)
                        } else {
                            AppUtils.showNotifyAlertDismiss(
                                context as Activity?,
                                "Alert",
                                "Submission Failed",
                                R.drawable.remove,
                                R.drawable.round
                            )
                        }
                    } else if (response.body().getResponseCode()
                            .equalsIgnoreCase(RESPONSE_ACCESSTOKEN_MISSING) ||
                        response.body().getResponseCode()
                            .equalsIgnoreCase(RESPONSE_ACCESSTOKEN_EXPIRED) ||
                        response.body().getResponseCode().equalsIgnoreCase(RESPONSE_INVALID_TOKEN)
                    ) {
                        AppUtils.postInitParam(context, object : GetAccessTokenInterface() {
                            val accessToken: Unit
                                get() {}
                        })
                        callPaymentSuccessAPI()
                    } else if (response.body().getResponseCode().equals(RESPONSE_ERROR)) {
//								CustomStatusDialog(RESPONSE_FAILURE);
                        //Toast.makeText(mContext,"Failure", Toast.LENGTH_SHORT).show();
                        AppUtils.showDialogAlertDismiss(
                            context as Activity?,
                            "Alert",
                            getString(R.string.common_error),
                            R.drawable.exclamationicon,
                            R.drawable.round
                        )
                    } else {
                        AppUtils.showDialogAlertDismiss(
                            context as Activity?,
                            "Alert",
                            getString(R.string.common_error),
                            R.drawable.exclamationicon,
                            R.drawable.round
                        )
                    }
                    //                    studentDataList = new ArrayList<>();
                }
            }

            override fun onFailure(call: Call<MusicBaseResponseModel?>, t: Throwable) {
                progressBarDialog.hide()
                AppUtils.showNotifyAlertDismiss(
                    context as Activity?,
                    "Alert",
                    "Submission Failed",
                    R.drawable.remove,
                    R.drawable.round
                )
            }
        }
        )*/
    }


    private fun calculateTotalCost(instrumentDataList: ArrayList<CartInstrumentDataModel>): Double {
        var totalCost = 0.0
        for (instrumentData in instrumentDataList) {
            for (cartData in instrumentData.getCartData()) {
                totalCost += cartData.getTotalAmount().toDouble()
            }
        }
        return totalCost
    }

    private fun callCartSummaryAPI(stud_id: String) {
       /* val service: APIInterface = APIClient.getRetrofitInstance().create(APIInterface::class.java)
        val paramObject = JsonObject()
        progressBarDialog.show()
        paramObject.addProperty("student_id", stud_id)
        val call: Call<ResponseBody> = service.postMusicCartSummary(
            "Bearer " + PreferenceManager.getAccessToken(context),
            paramObject
        )
        call.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                progressBarDialog.hide()
                if (response.body() != null) {
                    try {
                        val responseString = response.body()!!.string()
                        val rootObject = JSONObject(responseString)
                        val responseCode = rootObject.getString("responsecode")
                        val responseData =
                            rootObject.getJSONObject("response").getJSONObject("data")
                        try {
                            if (responseCode == "200") {
//                                JSONObject responseData = rootObject.getJSONObject("response").getJSONObject("data");
                                val instrumentDataArray =
                                    responseData.getJSONArray("instrument_data")
                                instrumentDataList = ArrayList<CartInstrumentDataModel>()
                                for (i in 0 until instrumentDataArray.length()) {
                                    val instrumentDataObject = instrumentDataArray.getJSONObject(i)
                                    val instrumentId = instrumentDataObject.getInt("instrument_id")
                                    val instrumentName =
                                        instrumentDataObject.getString("instrument_name")
                                    val cartDataArray =
                                        instrumentDataObject.getJSONArray("cart_data")
                                    val cartItemList: ArrayList<CartItemModel> =
                                        ArrayList<CartItemModel>()
                                    for (j in 0 until cartDataArray.length()) {
                                        val cartItemObject = cartDataArray.getJSONObject(j)
                                        val cartItem = CartItemModel()
                                        cartItem.setCartId(cartItemObject.getInt("cart_id"))
                                        cartItem.setStudentId(cartItemObject.getString("student_id"))
                                        cartItem.setInstrumentId(cartItemObject.getString("instrument_id"))
                                        cartItem.setLessonId(cartItemObject.getString("lesson_id"))
                                        cartItem.setLearningLevel(cartItemObject.getString("learning_level"))
                                        cartItem.setLearningLevelStatus(cartItemObject.getString("learning_level_status"))
                                        cartItem.setAmount(cartItemObject.getString("amount"))
                                        cartItem.setCurrency(cartItemObject.getString("currency"))
                                        cartItem.setCurrencySymbol(cartItemObject.getString("currency_symbol"))
                                        cartItem.setTaxType(cartItemObject.getString("tax_type"))
                                        cartItem.setTaxAmount(cartItemObject.getString("tax_amount"))
                                        cartItem.setTaxPercentage(cartItemObject.getString("tax_percentage"))
                                        cartItem.setTotalAmount(cartItemObject.getString("total_amount"))
                                        cartItem.setLessonName(cartItemObject.getString("lesson_name"))
                                        cartItem.setTermID(cartItemObject.getString("term_id"))
                                        cartItem.setTermName(cartItemObject.getString("term_name"))
                                        cartIDs.add(cartItem.getCartId())
                                        cartItemList.add(cartItem)
                                    }
                                    val instrumentData = CartInstrumentDataModel()
                                    instrumentData.setInstrumentId(instrumentId)
                                    instrumentData.setInstrumentName(instrumentName)
                                    instrumentData.setCartData(cartItemList)
                                    instrumentDataList.add(instrumentData)
                                    adapter = CartDataAdapter(instrumentDataList)
                                    recyclerView!!.adapter = adapter
                                    totalCost = calculateTotalCost(instrumentDataList)
                                    totalAmountTextView!!.text =
                                        "AED " + String.format("%.2f", totalCost)
                                }
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                        //                        } else {
//                            AppUtils.showDialogAlertDismiss((Activity) context, "Alert", getString(R.string.common_error), R.drawable.exclamationicon, R.drawable.round);
//                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                progressBarDialog.hide()
               *//* AppUtils.showDialogAlertDismiss(
                    context as Activity?,
                    "Alert",
                    getString(R.string.common_error),
                    R.drawable.exclamationicon,
                    R.drawable.round
                )*//*
            }
        })*/
    }

    private fun callCartSummary(studId: String) {
        /*val volleyWrapper = VolleyWrapper(URL_MUSIC_ACADEMY_SUMMARY)
        val name = arrayOf("access_token", "student_id")
        val value = arrayOf<String>(PreferenceManager.getAccessToken(context), studId)
        volleyWrapper.getResponsePOST(context, 11, name, value, object : ResponseListener() {
            fun responseSuccess(successResponse: String?) {
                if (successResponse != null) {
                    try {
                        val rootObject = JSONObject(successResponse)
                        val responseCode = rootObject.getString("responsecode")
                        if (responseCode == "200") {
                            val responseData =
                                rootObject.getJSONObject("response").getJSONObject("data")
                            val instrumentDataArray = responseData.getJSONArray("instrument_data")
                            instrumentDataList = ArrayList<CartInstrumentDataModel>()
                            for (i in 0 until instrumentDataArray.length()) {
                                val instrumentDataObject = instrumentDataArray.getJSONObject(i)
                                val instrumentId = instrumentDataObject.getInt("instrument_id")
                                val instrumentName =
                                    instrumentDataObject.getString("instrument_name")
                                val cartDataArray = instrumentDataObject.getJSONArray("cart_data")
                                val cartItemList: ArrayList<CartItemModel> =
                                    ArrayList<CartItemModel>()
                                for (j in 0 until cartDataArray.length()) {
                                    val cartItemObject = cartDataArray.getJSONObject(j)
                                    val cartItem = CartItemModel()
                                    cartItem.setCartId(cartItemObject.getInt("cart_id"))
                                    cartItem.setStudentId(cartItemObject.getString("student_id"))
                                    cartItem.setInstrumentId(cartItemObject.getString("instrument_id"))
                                    cartItem.setLessonId(cartItemObject.getString("lesson_id"))
                                    cartItem.setLearningLevel(cartItemObject.getString("learning_level"))
                                    cartItem.setLearningLevelStatus(cartItemObject.getString("learning_level_status"))
                                    cartItem.setAmount(cartItemObject.getString("amount"))
                                    cartItem.setCurrency(cartItemObject.getString("currency"))
                                    cartItem.setCurrencySymbol(cartItemObject.getString("currency_symbol"))
                                    cartItem.setTaxType(cartItemObject.getString("tax_type"))
                                    cartItem.setTaxAmount(cartItemObject.getString("tax_amount"))
                                    cartItem.setTaxPercentage(cartItemObject.getString("tax_percentage"))
                                    cartItem.setTotalAmount(cartItemObject.getString("total_amount"))
                                    cartItem.setLessonName(cartItemObject.getString("lesson_name"))
                                    cartItem.setTermID(cartItemObject.getString("term_id"))
                                    cartItem.setTermName(cartItemObject.getString("term_name"))
                                    cartIDs.add(cartItem.getCartId())
                                    cartItemList.add(cartItem)
                                }
                                val instrumentData = CartInstrumentDataModel()
                                instrumentData.setInstrumentId(instrumentId)
                                instrumentData.setInstrumentName(instrumentName)
                                instrumentData.setCartData(cartItemList)
                                instrumentDataList.add(instrumentData)
                                // Populate instrumentDataList with your parsed data
                                adapter = CartDataAdapter(instrumentDataList)
                                recyclerView!!.adapter = adapter
                                totalCost = calculateTotalCost(instrumentDataList)
                                totalAmountTextView!!.text =
                                    "AED " + String.format("%.2f", totalCost)
                            }

                            // Now you have parsed instrument data with cart items.
                            // You can use instrumentDataList as needed.
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            }

            fun responseFailure(failureResponse: String?) {}
        })*/
    }
}
