package com.nas.alreem.activity.lost_card

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
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.google.gson.JsonObject
import com.nas.alreem.R
import com.nas.alreem.activity.payment_history.PaymentWalletHistoryModel
import com.nas.alreem.activity.payments.adapter.StudentListAdapter
import com.nas.alreem.activity.payments.model.StudentList
import com.nas.alreem.constants.PreferenceManager
import com.nas.alreem.recyclermanager.DividerItemDecoration
import com.nas.alreem.recyclermanager.RecyclerItemListener
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LostCardPaymentHistory : AppCompatActivity(){
    var relativeHeader: RelativeLayout? = null

    var back: ImageView? = null
    var home: ImageView? = null
    lateinit var mContext: Context
    lateinit var studImg: ImageView
    var stud_img: String? = ""
    var extras: Bundle? = null
    var studentNameStr: String? = ""
    var studentClassStr = ""
    var studentIdStr: String? = ""
    lateinit var studentName: String
    lateinit var studentId: String
    lateinit var studentImg: String
    lateinit var studentNameTxt: TextView
    lateinit var studentClass: String

    var mStudentSpinner: LinearLayout? = null
    var newsLetterModelArrayList: ArrayList<PaymentWalletHistoryModel> =
        ArrayList<PaymentWalletHistoryModel>()
    var studentsModelArrayList: ArrayList<StudentList> = ArrayList<StudentList>()
    lateinit var mNewsLetterListView: RecyclerView
   // var progressBarDialog: ProgressBarDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_history)
        mContext = this
        initUI()
    }

    private fun initUI() {
        extras = intent.extras
        if (extras != null) {
            studentNameStr = extras!!.getString("studentName")
            studentIdStr = extras!!.getString("studentId")
            studentsModelArrayList = extras!!
                .getSerializable("StudentModelArray") as ArrayList<StudentList>
            stud_img = extras!!.getString("studentImage")
        }
       // progressBarDialog = ProgressBarDialog(mContext, R.drawable.spinner)
        relativeHeader = findViewById<View>(R.id.relativeHeader) as RelativeLayout
        mStudentSpinner = findViewById<View>(R.id.studentSpinner) as LinearLayout
        studentNameTxt = findViewById<View>(R.id.studentName) as TextView
        studImg = findViewById<View>(R.id.imagicon) as ImageView
        mNewsLetterListView = findViewById<View>(R.id.mListView) as RecyclerView
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        mNewsLetterListView!!.layoutManager = llm
        mNewsLetterListView!!.setHasFixedSize(true)
        mNewsLetterListView!!.addItemDecoration(DividerItemDecoration(resources.getDrawable(R.drawable.list_divider_teal)))


        back!!.setOnClickListener { finish() }

            getEventsListApi(studentIdStr)

        mStudentSpinner!!.setOnClickListener {
            if (studentsModelArrayList!!.size > 0) {
                showSocialmediaList(studentsModelArrayList)
            } else {

            }
        }
        studentNameTxt!!.text = studentNameStr
        if (stud_img != "") {
          /*  Picasso.with(mContext).load(AppUtils.replace(stud_img)).placeholder(R.drawable.boy)
                .fit().into(studImg)*/
        } else {
            studImg!!.setImageResource(R.drawable.boy)
        }
        PreferenceManager.setStudentID(mContext, studentIdStr)
        mNewsLetterListView!!.addOnItemTouchListener(
            RecyclerItemListener(mContext, mNewsLetterListView,
                object : RecyclerItemListener.RecyclerTouchListener {
                    override fun onClickItem(v: View?, position: Int) {
                        val intent = Intent(
                            mContext,
                            LostCardPrintPaymentActivity::class.java
                        )
                        intent.putExtra("title", "LOST ID CARD RECEIPT")
                        intent.putExtra("orderId", newsLetterModelArrayList[position].getKeycode())
                        intent.putExtra("invoice", newsLetterModelArrayList[position].getInvoice())
                        intent.putExtra("amount", newsLetterModelArrayList[position].getAmount())
                        intent.putExtra("paidby", newsLetterModelArrayList[position].getName())
                        intent.putExtra(
                            "paidDate",
                            newsLetterModelArrayList[position].getDate_time()
                        )
                        intent.putExtra("tr_no", newsLetterModelArrayList[position].getTrn_no())
                        intent.putExtra(
                            "payment_type",
                            newsLetterModelArrayList[position].getPayment_type()
                        )
                        if (newsLetterModelArrayList[position].getBill_no().equals("")) {
                            intent.putExtra("billingCode", "--")
                        } else {
                            intent.putExtra(
                                "billingCode",
                                newsLetterModelArrayList[position].getBill_no()
                            )
                        }
                        mContext!!.startActivity(intent)
                    }

                    override fun onLongClickItem(v: View?, position: Int) {
                        println("On Long Click Item interface")
                    }
                })
        )
    }

    fun showSocialmediaList(mStudentArray: ArrayList<StudentList>) {
        val dialog = Dialog(mContext!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialogue_student_list)
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
        val studentAdapter = StudentListAdapter(mContext, mStudentArray)
        socialMediaList.adapter = studentAdapter
        dialogDismiss.setOnClickListener { dialog.dismiss() }
        socialMediaList.addOnItemTouchListener(
            RecyclerItemListener(mContext, socialMediaList,
                object : RecyclerItemListener.RecyclerTouchListener {
                    override fun onClickItem(v: View?, position: Int) {
                        dialog.dismiss()
                        studentName=mStudentArray.get(position).name
                        studentImg=mStudentArray.get(position).photo
                        studentId=mStudentArray.get(position).id
                        studentClass=mStudentArray.get(position).section
                        PreferenceManager.setStudentID(mContext,studentId)
                        PreferenceManager.setStudentName(mContext,studentName)
                        PreferenceManager.setStudentPhoto(mContext,studentImg)
                        PreferenceManager.setStudentClass(mContext,studentClass)
                        studentNameTxt.text=studentName
                        if(!studentImg.equals(""))
                        {
                            Glide.with(mContext) //1
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
                    }

                    override fun onLongClickItem(v: View?, position: Int) {
                        println("On Long Click Item interface")
                    }
                })
        )
        dialog.show()
    }

    private fun getEventsListApi(stud_id: String?) {
        /*rogressBarDialog.show()
        val service: APIInterface = APIClient.getRetrofitInstance().create(APIInterface::class.java)
        val paramObject = JsonObject()
        paramObject.addProperty("student_id", stud_id)

        //  paramObject.addProperty("lost_card_date", fromDate);
        val call: Call<ResponseBody> = service.student_lost_card_history(
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
                            val data = secobj.getJSONArray("data")
                            if (data.length() > 0) {
                                newsLetterModelArrayList.clear()
                                for (i in 0 until data.length()) {
                                    val dataObject = data.optJSONObject(i)
                                    newsLetterModelArrayList.add(addEventsDetails(dataObject))
                                }
                                mNewsLetterListView!!.visibility = View.VISIBLE
                                val newsLetterAdapter =
                                    LostCardAdapter(mContext, newsLetterModelArrayList)
                                mNewsLetterListView!!.adapter = newsLetterAdapter
                            } else {
                                newsLetterModelArrayList.clear()
                                mNewsLetterListView!!.visibility = View.GONE
                                val newsLetterAdapter =
                                    LostCardAdapter(mContext, newsLetterModelArrayList)
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
                    } else if (response_code.equals("500", ignoreCase = true)) {
                        AppUtils.showDialogAlertDismiss(
                            mContext as Activity?,
                            "Alert",
                            getString(R.string.common_error),
                            R.drawable.exclamationicon,
                            R.drawable.round
                        )
                    } else if (response_code.equals("400", ignoreCase = true)) {
                        AppUtils.getToken(mContext, object : GetTokenSuccess() {
                            fun tokenrenewed() {}
                        })
                        getEventsListApi(stud_id)
                    } else if (response_code.equals("401", ignoreCase = true)) {
                        AppUtils.getToken(mContext, object : GetTokenSuccess() {
                            fun tokenrenewed() {}
                        })
                        getEventsListApi(stud_id)
                    } else if (response_code.equals("402", ignoreCase = true)) {
                        AppUtils.getToken(mContext, object : GetTokenSuccess() {
                            fun tokenrenewed() {}
                        })
                        getEventsListApi(stud_id)
                    } else {
                        *//*CustomDialog dialog = new CustomDialog(mContext, getResources().getString(R.string.common_error)
								, getResources().getString(R.string.ok));
						dialog.show();*//*
                        AppUtils.showDialogAlertDismiss(
                            mContext as Activity?,
                            "Alert",
                            getString(R.string.common_error),
                            R.drawable.exclamationicon,
                            R.drawable.round
                        )
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

    private fun addEventsDetails(obj: JSONObject): PaymentWalletHistoryModel {
        val model = PaymentWalletHistoryModel()
        try {
            if (obj.has("id")) {
                model.setId(obj.optString("id"))
            } else {
                model.setId("")
            }
            if (obj.has("created_on")) {
                model.setDate_time(obj.optString("created_on"))
            } else {
                model.setDate_time("")
            }
            if (obj.has("firstname")) {
                model.setName(obj.optString("firstname"))
            } else {
                model.setName("")
            }
            if (obj.has("amount")) {
                model.setAmount(obj.optString("amount"))
            } else {
                model.setAmount("")
            }
            if (obj.has("status_type")) {
                val status = obj.optInt("status")
                model.setStatus(status.toString())
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
        } catch (ex: Exception) {
            println("Exception is$ex")
        }
        return model
    }
}

