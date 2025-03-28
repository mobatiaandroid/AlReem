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
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.nas.alreem.R
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.activity.lost_card.adapter.LostCardAdapter
import com.nas.alreem.activity.lost_card.model.LostCardHistoryResponseModel
import com.nas.alreem.activity.payment_history.PaymentWalletHistoryModel
import com.nas.alreem.activity.payments.adapter.StudentListAdapter
import com.nas.alreem.activity.payments.model.StudentList
import com.nas.alreem.activity.payments.model.StudentListModel
import com.nas.alreem.constants.ApiClient
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.DialogFunctions
import com.nas.alreem.constants.HeaderManager
import com.nas.alreem.constants.PreferenceManager
import com.nas.alreem.fragment.student_information.model.StudentInfoApiModel
import com.nas.alreem.recyclermanager.DividerItemDecoration
import com.nas.alreem.recyclermanager.RecyclerItemListener
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LostCardPaymentHistory : AppCompatActivity(){
    lateinit var relativeHeader: RelativeLayout
    lateinit var headermanager: HeaderManager

    var back: ImageView? = null
    var home: ImageView? = null
    lateinit var mContext: Context
    lateinit var studImg: ImageView
    var stud_img: String? = ""
    var extras: Bundle? = null
    var studentNameStr: String? = ""
    var studentClassStr = ""
    var studentIdStr: String = ""
    lateinit var studentName: String
    lateinit var studentId: String
    lateinit var studentImg: String
    lateinit var studentNameTxt: TextView
    lateinit var studentClass: String

    var mStudentSpinner: LinearLayout? = null
    var newsLetterModelArrayList: ArrayList<PaymentWalletHistoryModel> =
        ArrayList<PaymentWalletHistoryModel>()
    var studentsModelArrayList : ArrayList<StudentList> = ArrayList<StudentList>()
    lateinit var mNewsLetterListView: RecyclerView
    lateinit var progress: ProgressBar
    lateinit var activity: Activity


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_history)
        mContext = this
        activity=this
        initUI()
        if (ConstantFunctions.internetCheck(mContext))
        {
            getStudentsListAPI()
        }
        else
        {
            DialogFunctions.showInternetAlertDialog(mContext)
        }

    }

    private fun initUI() {
       // studentsModelArrayList = ArrayList()
        extras = intent.extras
        if (extras != null) {
            studentNameStr = extras!!.getString("studentName")
            studentIdStr = extras!!.getString("studentId").toString()
           //studentsModelArrayList = PreferenceManager.getStudentArrayList(mContext)!!
            Log.e("studentsModelArrayList", studentsModelArrayList.toString())
            stud_img = extras!!.getString("studentImage")
        }
        progress = findViewById(R.id.progressDialogAdd)
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

        headermanager = HeaderManager(this@LostCardPaymentHistory, "Payment History")
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

        if (ConstantFunctions.internetCheck(mContext))
        {
            getEventsListApi(studentIdStr)
        }
        else
        {
            DialogFunctions.showInternetAlertDialog(mContext)
        }


        mStudentSpinner!!.setOnClickListener {
            if (studentsModelArrayList!!.size > 0) {
                showSocialmediaList(studentsModelArrayList!!)
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
                        intent.putExtra("order_reference", newsLetterModelArrayList[position].getKeycode())
                        intent.putExtra("invoice_note", newsLetterModelArrayList[position].getInvoice())
                        intent.putExtra("amount", newsLetterModelArrayList[position].getAmount())
                        intent.putExtra("paidby", newsLetterModelArrayList[position].getName())
                        intent.putExtra("created_on", newsLetterModelArrayList[position].date_time)
                        intent.putExtra(
                            "paidDate",
                            newsLetterModelArrayList[position].getDate_time()
                        )
                        intent.putExtra("tr_no", newsLetterModelArrayList[position].getTrn_no())
                        intent.putExtra(
                            "payment_type",
                            newsLetterModelArrayList[position].getPayment_type()
                        )
                        /*if (newsLetterModelArrayList[position].getBill_no().equals("")) {
                            intent.putExtra("billingCode", "--")
                        } else {
                            intent.putExtra(
                                "billingCode",
                                newsLetterModelArrayList[position].getBill_no()
                            )
                        }*/
                        mContext!!.startActivity(intent)
                    }

                    override fun onLongClickItem(v: View?, position: Int) {
                    }
                })
        )
    }
    private fun getStudentsListAPI() {
        progress.visibility = View.VISIBLE
        studentsModelArrayList .clear()
        val call: Call<StudentListModel> = ApiClient(mContext).getClient.studentList("Bearer "+ PreferenceManager.getaccesstoken(mContext))
        call.enqueue(object : Callback<StudentListModel> {
            override fun onResponse(
                call: Call<StudentListModel?>,
                response: Response<StudentListModel?>
            ) {
                progress.visibility = View.GONE

                val responsedata = response.body()
                if (responsedata != null) {
                    try {

                        if (response.body()!!.status==100)
                        {
                            studentsModelArrayList=ArrayList()
                            studentsModelArrayList.addAll(response.body()!!.responseArray.studentList)


                        }
                        else
                        {

                            DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert), ConstantFunctions.commonErrorString(response.body()!!.status), mContext)
                        }


                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<StudentListModel?>, t: Throwable) {
                progress.visibility = View.GONE

            }
        })



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
            dialog.findViewById<View>(R.id.studentListRecycler) as RecyclerView
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
                        if (ConstantFunctions.internetCheck(mContext))
                        {
                            getEventsListApi(studentId)
                        }
                        else
                        {
                            DialogFunctions.showInternetAlertDialog(mContext)
                        }

                    }

                    override fun onLongClickItem(v: View?, position: Int) {
                    }
                })
        )
        dialog.show()
    }

    private fun getEventsListApi(stud_id: String) {
        newsLetterModelArrayList = ArrayList()
        progress.visibility = View.VISIBLE
        val studentbody= StudentInfoApiModel(stud_id)

        val call: Call<LostCardHistoryResponseModel> = ApiClient(mContext).getClient.student_lost_card_history(studentbody,"Bearer "+ PreferenceManager.getaccesstoken(mContext))
        call.enqueue(object : Callback<LostCardHistoryResponseModel> {
            override fun onResponse(
                call: Call<LostCardHistoryResponseModel?>,
                response: Response<LostCardHistoryResponseModel?>
            ) {
                progress.visibility = View.GONE

                val responsedata = response.body()
                if (responsedata != null) {
                    try {
                        if(responsedata.responsecode.equals("200"))
                        {
                            if (responsedata.response.statuscode.equals("303"))
                            {
                                Log.e("size", response.body()!!.response.data.size.toString())
                                if (response.body()!!.response.data.size > 0) {
                                    newsLetterModelArrayList.clear()
                                        newsLetterModelArrayList.addAll(response.body()!!.response.data)
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
                                    DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert),"No Data Found", mContext)


                                }
                        }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<LostCardHistoryResponseModel?>, t: Throwable) {
                progress.visibility = View.GONE



            }
        })


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
        }
        return model
    }
    override fun onResume() {
        super.onResume()
        if (!ConstantFunctions.runMethod.equals("Dev")) {
            if (ConstantFunctions().isDeveloperModeEnabled(mContext)) {
                ConstantFunctions().showDeviceIsDeveloperPopUp(activity)
            }
        }
    }
}

