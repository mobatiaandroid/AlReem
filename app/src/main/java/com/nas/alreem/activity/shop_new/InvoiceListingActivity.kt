package com.nas.alreem.activity.shop_new

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.nas.alreem.R
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.activity.payments.adapter.StudentListAdapter
import com.nas.alreem.activity.payments.model.StudentList
import com.nas.alreem.activity.shop.model.MusicPaymentHistoryModel
import com.nas.alreem.activity.shop.model.OrderSummary
import com.nas.alreem.constants.ApiClient
import com.nas.alreem.constants.ApiInterface
import com.nas.alreem.constants.HeaderManager
import com.nas.alreem.constants.PreferenceManager
import com.nas.alreem.recyclermanager.DividerItemDecoration
import com.nas.alreem.recyclermanager.RecyclerItemListener
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InvoiceListingActivity : AppCompatActivity() {
    lateinit var relativeHeader: RelativeLayout
    lateinit var headermanager: HeaderManager
    lateinit var back: ImageView
    lateinit var home: ImageView
    lateinit var mContext: Context
    lateinit var studImg: ImageView
    var stud_img: String? = ""
    var extras: Bundle? = null
    var studentNameStr: String? = ""
    var studentClassStr = ""
    var studentIdStr: String? = ""
    lateinit var studentName: TextView
    var mStudentSpinner: LinearLayout? = null
    var newsLetterModelArrayList: ArrayList<MusicPaymentHistoryModel> =
        ArrayList<MusicPaymentHistoryModel>()
    var studentsModelArrayList: ArrayList<StudentList>? = ArrayList<StudentList>()
    var paymentHistoryList: ArrayList<MusicPaymentHistoryModel> =
        ArrayList<MusicPaymentHistoryModel>()
    lateinit var mNewsLetterListView: RecyclerView
   // var progressBarDialog: ProgressBarDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invoice_listing)
        mContext = this
        initialiseUI()
    }

    private fun initialiseUI() {
        extras = intent.extras
        if (extras != null) {
            studentNameStr = extras!!.getString("studentName")
            studentIdStr = extras!!.getString("studentId")
            studentsModelArrayList = extras!!
                .getSerializable("StudentModelArray") as ArrayList<StudentList>?
            stud_img = extras!!.getString("studentImage")
        }
        relativeHeader = findViewById<View>(R.id.relativeHeader) as RelativeLayout
        mStudentSpinner = findViewById<View>(R.id.studentSpinner) as LinearLayout
        studentName = findViewById<View>(R.id.studentName) as TextView
        studImg = findViewById<View>(R.id.imagicon) as ImageView
        mNewsLetterListView = findViewById<View>(R.id.mListView) as RecyclerView
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        mNewsLetterListView!!.layoutManager = llm
        mNewsLetterListView!!.setHasFixedSize(true)
        mNewsLetterListView!!.addItemDecoration(DividerItemDecoration(resources.getDrawable(R.drawable.list_divider_teal)))
        headermanager = HeaderManager(this@InvoiceListingActivity, "Payment History")
        headermanager.getHeader(relativeHeader, 0)
      //  progressBarDialog = ProgressBarDialog(mContext, R.drawable.spinner)
        back = headermanager.leftButton!!
        home = headermanager.logoButton!!
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

        mStudentSpinner!!.setOnClickListener {
            if (studentsModelArrayList!!.size > 0) {
                showSocialmediaList(studentsModelArrayList)
            } else {

            }
        }
        studentName!!.text = studentNameStr
        if (stud_img != "") {

        } else {
            studImg!!.setImageResource(R.drawable.boy)
        }
        PreferenceManager.setStudentID(mContext, studentIdStr)
        mNewsLetterListView!!.addOnItemTouchListener(
            RecyclerItemListener(mContext, mNewsLetterListView,
                object : RecyclerItemListener.RecyclerTouchListener{
                    override fun onClickItem(v: View?, position: Int) {
                        val intent = Intent(mContext, MusicInvoicePrint::class.java)
                        intent.putExtra("title", "Music Academy Registration")
                        intent.putExtra("key", paymentHistoryList[position].getOrder_summery())
                        intent.putExtra("orderId", paymentHistoryList[position].getId())
                        intent.putExtra("invoice", paymentHistoryList[position].getInvoice())
                        intent.putExtra("amount", paymentHistoryList[position].getAmount())
                        intent.putExtra("paidby", paymentHistoryList[position].getName())
                        intent.putExtra("paidDate", paymentHistoryList[position].getDate_time())
                        intent.putExtra("tr_no", paymentHistoryList[position].getTrn_no())
                        intent.putExtra(
                            "payment_type",
                            paymentHistoryList[position].getPayment_type()
                        )
                        if (paymentHistoryList[position].getBill_no().equals("")) {
                            intent.putExtra("billingCode", "--")
                        } else {
                            intent.putExtra(
                                "billingCode",
                                paymentHistoryList[position].getBill_no()
                            )
                        }
                        mContext!!.startActivity(intent)
                    }

                    override fun onLongClickItem(v: View?, position: Int) {
                        // System.out.println("On Long Click Item interface");
                    }
                })
        )
    }

    private fun getPaymentHistory(studentIdStr: String?) {
        /*val service: ApiInterface = ApiClient.getClient.create(ApiInterface::class.java)
        val paramObject = JsonObject()
        paramObject.addProperty("student_id", studentIdStr)
        val call: Call<PaymentHistoryResponseModel> = service.postMusicPaymentHistory(
            "Bearer " + PreferenceManager.getAccessToken(mContext),
            paramObject
        )
        progressBarDialog.show()
        call.enqueue(object : Callback<PaymentHistoryResponseModel> {
            override fun onResponse(
                call: Call<PaymentHistoryResponseModel>,
                response: Response<PaymentHistoryResponseModel>
            ) {
                progressBarDialog.hide()
                if (response.isSuccessful()) {
                    val apiResponse: PaymentHistoryResponseModel? = response.body()
                    val response_code: String =
                        java.lang.String.valueOf(apiResponse.getResponseCode())
                    if (response_code.equals("200", ignoreCase = true)) {
                        val status_code: String =
                            java.lang.String.valueOf(apiResponse.getResponse().getStatusCode())
                        if (status_code.equals("303", ignoreCase = true)) {
                            if (apiResponse.getResponse().getData().getPayment_history()
                                    .size() > 0
                            ) {
                                paymentHistoryList = ArrayList<MusicPaymentHistoryModel>()
                                for (i in 0 until apiResponse.getResponse().getData()
                                    .getPayment_history().size()) {
                                    val item: MusicPaymentHistoryModel =
                                        response.body().getResponse().getData().getPayment_history()
                                            .get(i)
                                    val gson = Gson()
                                    val eventJson = gson.toJson(item)
                                    try {
                                        val jsonObject = JSONObject(eventJson)
                                        val paymentHistoryModel: MusicPaymentHistoryModel =
                                            addEventsDetails(jsonObject)
                                        paymentHistoryList.add(paymentHistoryModel)
                                        //  Log.e("array", String.valueOf(mCCAmodelArrayList));
                                    } catch (e: JSONException) {
                                        e.printStackTrace()
                                    }
                                }
                                mNewsLetterListView!!.visibility = View.VISIBLE
                                val newsLetterAdapter =
                                    MusicPaymentHistoryRecyclerAdapter(mContext, paymentHistoryList)
                                mNewsLetterListView!!.adapter = newsLetterAdapter
                            } else {
                                newsLetterModelArrayList.clear()
                                mNewsLetterListView!!.visibility = View.GONE
                                val newsLetterAdapter =
                                    MusicPaymentHistoryRecyclerAdapter(mContext, paymentHistoryList)
                                mNewsLetterListView!!.adapter = newsLetterAdapter
                                AppUtils.showDialogAlertDismiss(
                                    mContext as Activity?,
                                    "Alert",
                                    "No data available",
                                    R.drawable.exclamationicon,
                                    R.drawable.round
                                )
                            }
                        } else {
                            mNewsLetterListView!!.visibility = View.GONE
                            AppUtils.showDialogAlertDismiss(
                                mContext as Activity?,
                                "Alert",
                                getString(R.string.common_error),
                                R.drawable.exclamationicon,
                                R.drawable.round
                            )
                        }
                    }
                }
            }

            override fun onFailure(call: Call<PaymentHistoryResponseModel>, t: Throwable) {}
        }
        )*/
    }


    private fun addEventsDetails(obj: JSONObject): MusicPaymentHistoryModel {
        val model = MusicPaymentHistoryModel()
        try {
            if (obj.has("order_reference")) {
                model.setId(obj.optString("order_reference"))
            } else {
                model.setId("")
            }
            if (obj.has("created_on")) {
                model.setDate_time(obj.optString("created_on"))
            } else {
                model.setDate_time("")
            }
            if (obj.has("student_name")) {
                model.setName(obj.optString("student_name"))
            } else {
                model.setName("")
            }
            if (obj.has("order_total")) {
                model.setAmount(obj.optString("order_total"))
            } else {
                model.setAmount("")
            }
            if (obj.has("status")) {
                model.setStatus(obj.optString("status"))
            } else {
                model.setStatus("")
            }
            if (obj.has("keycode")) {
                model.setKeycode(obj.optString("keycode"))
            } else {
                model.setKeycode("")
            }
            if (obj.has("bill_no")) {
                model.setBill_no(obj.optString("bill_no"))
            } else {
                model.setBill_no("")
            }
            if (obj.has("invoice_note")) {
                model.setInvoice(obj.optString("invoice_note"))
            } else {
                model.setInvoice("")
            }
            if (obj.has("trn_no")) {
                model.setTrn_no(obj.optString("trn_no"))
            } else {
                model.setTrn_no("")
            }
            if (obj.has("payment_type")) {
                model.setPayment_type(obj.optString("payment_type"))
            } else {
                model.setPayment_type("")
            }
            if (obj.has("order_summery")) {
                val orderSummaryArray = obj.getJSONArray("order_summery")
                val orderSummaries: ArrayList<OrderSummary> = ArrayList<OrderSummary>()
                for (i in 0 until orderSummaryArray.length()) {
                    val orderSummaryObject = orderSummaryArray.getJSONObject(i)
                    val orderSummary = OrderSummary()
                    orderSummary.setId(orderSummaryObject.optString("id"))
                    orderSummary.setActual_amount(orderSummaryObject.optString("actual_amount"))
                    orderSummary.setTax_amount(orderSummaryObject.optString("tax_amount"))
                    orderSummary.setTotal_amount(orderSummaryObject.optString("total_amount"))
                    orderSummary.setInstrument_name(orderSummaryObject.optString("instrument_name"))
                    orderSummary.setTerm_name(orderSummaryObject.optString("term_name"))
                    orderSummary.setLesson_name(orderSummaryObject.optString("lesson_name"))
                    orderSummaries.add(orderSummary)
                }
                model.setOrder_summery(orderSummaries)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return model
    }


    fun showSocialmediaList(mStudentArray: ArrayList<StudentList>?) {
        val dialog = Dialog(mContext!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_student_media_list)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val dialogDismiss = dialog.findViewById<View>(R.id.btn_dismiss) as Button
        val iconImageView = dialog.findViewById<View>(R.id.iconImageView) as ImageView
        iconImageView.setImageResource(R.drawable.boy)
        val socialMediaList =
            dialog.findViewById<View>(R.id.recycler_view_social_media) as RecyclerView
        //if(mSocialMediaArray.get())
        val sdk = Build.VERSION.SDK_INT
        if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
            dialogDismiss.setBackgroundDrawable(mContext!!.resources.getDrawable(R.drawable.button))
        } else {
            dialogDismiss.background = mContext!!.resources.getDrawable(R.drawable.button)
        }
        socialMediaList.addItemDecoration(DividerItemDecoration(mContext!!.resources.getDrawable(R.drawable.list_divider_teal)))
        socialMediaList.setHasFixedSize(true)
        val llm = LinearLayoutManager(mContext)
        llm.orientation = LinearLayoutManager.VERTICAL
        socialMediaList.layoutManager = llm
        val studentAdapter = StudentListAdapter(mContext, mStudentArray!!)
        socialMediaList.adapter = studentAdapter
        dialogDismiss.setOnClickListener { dialog.dismiss() }
        socialMediaList.addOnItemTouchListener(
            RecyclerItemListener(mContext, socialMediaList,
                object : RecyclerItemListener.RecyclerTouchListener {
                    override fun onClickItem(v: View?, position: Int) {
                        dialog.dismiss()
                        studentName.setText(mStudentArray!![position].name)
                        studentIdStr = mStudentArray!![position].id
                        studentClassStr = mStudentArray[position].studentClass
                        stud_img = mStudentArray[position].photo
                        if (stud_img != "") {

                        } else {
                            studImg!!.setImageResource(R.drawable.boy)
                        }
                        PreferenceManager.setStudentID(mContext, studentIdStr)
                        PreferenceManager.setStudentName(
                            mContext,
                            mStudentArray[position].name
                        )
                        PreferenceManager.setStudentPhoto(
                            mContext,
                            mStudentArray[position].photo
                        )

                            // TODO INVOICE LIST
                            getPaymentHistory(PreferenceManager.getStudentID(mContext))
                            //  getEventsListApi(PreferenceManager.getStudentID(mContext));

                    }

                    override fun onLongClickItem(v: View?, position: Int) {}
                })
        )
        dialog.show()
    }

}