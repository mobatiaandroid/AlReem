package com.nas.alreem.activity.lost_card

import android.app.Activity
import android.app.DatePickerDialog
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
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.google.gson.JsonObject
import com.nas.alreem.R
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.activity.payments.adapter.StudentListAdapter
import com.nas.alreem.activity.payments.model.StudentList
import com.nas.alreem.activity.payments.model.StudentListModel
import com.nas.alreem.constants.ApiClient
import com.nas.alreem.constants.ApiInterface
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.DialogFunctions
import com.nas.alreem.constants.PreferenceManager
import com.nas.alreem.recyclermanager.DividerItemDecoration
import com.nas.alreem.recyclermanager.RecyclerItemListener
import okhttp3.ResponseBody
import org.json.JSONObject
import payment.sdk.android.cardpayment.CardPaymentData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar


class LostCardMainActivity : AppCompatActivity() {
   lateinit var mContext: Context
    var tab_type: String? = null
    var relativeHeader: RelativeLayout? = null
    //var headermanager: HeaderManager? = null
    var back: ImageView? = null
    var btn_history: ImageView? = null
    var home: ImageView? = null
    var extras: Bundle? = null
    var nextBtn: ImageView? = null
    var mStudentSpinner: LinearLayout? = null
    var stud_id: String? = null
    var studClass = ""
    var orderId = ""
    var ordered_user_type = ""
    var student_id = ""
    var staff_id = ""
    var parent_id = ""
    lateinit var studentName: String
    lateinit var studentId: String
    lateinit var studentImg: String
    lateinit var studentClass: String
    lateinit var studentNameTxt: TextView
    lateinit var studImg: ImageView

    var addOrderRelative: RelativeLayout? = null
    var orderHistoryRelative: RelativeLayout? = null
    var myOrderRelative: RelativeLayout? = null
    var cartBtn: Button? = null
    var time_exceed = ""
    var cartCount = 0
    var mTitleTextView: TextView? = null
    var instructionTxt: TextView? = null
    var requestForNewCard: Button? = null
    var htmlInstruction: String? = null
    var et_amount: TextView? = null
    var studentList = ArrayList<String>()
    var firstVisit = false
    var PaymentToken = ""
    var OrderRef = ""
    var PayUrl = ""
    var AuthUrl = ""
    var isFrom: String? = null
    var payAmount = ""
    var merchantOrderReference = ""
    var canteen_response = ""
    var Error = ""
    var topup_limit = ""
    var order_id = ""
    var mActivity: Activity? = null
    var fromDate = ""
    var btnhistory: ImageView? = null
    var datePicker: DatePickerDialog? = null
    private val mRootView: View? = null
    private val mTitle: String? = null
    private val mTabId: String? = null
    private val relMain: RelativeLayout? = null
    var studentsModelArrayList : ArrayList<StudentList> = ArrayList<StudentList>()
   // var progressBarDialog: ProgressBarDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lost_card_main)
        mContext = this
        //        mActivity=getActivity();
        firstVisit = true
        initialiseUI()
        if (ConstantFunctions.internetCheck(mContext)) {
//            progressDialog.visibility= View.VISIBLE
            getStudentsListAPI()
        } else {
            DialogFunctions.showInternetAlertDialog(mContext)
        }

    }

    override fun onRestart() {
     //   progressBarDialog.hide()
      //  progressBarDialog.dismiss()
        super.onRestart()
    }

    override fun onResume() {
       // progressBarDialog.hide()
       // progressBarDialog.dismiss()
        super.onResume()
    }

    private fun getStudentsListAPI() {
       // progressBarDialog.show()
        studentsModelArrayList.clear()
        studentList.clear()
        val call: Call<StudentListModel> = ApiClient.getClient.studentList("Bearer "+ PreferenceManager.getaccesstoken(mContext))
        call.enqueue(object : Callback<StudentListModel> {
            override fun onResponse(
                call: Call<StudentListModel?>,
                response: Response<StudentListModel?>
            ) {
                val responsedata = response.body()
                if (responsedata != null) {
                    try {

                        if (response.body()!!.status==100)
                        {
                            studentsModelArrayList=ArrayList()
                            studentsModelArrayList.addAll(response.body()!!.responseArray.studentList)
                            if (PreferenceManager.getStudentID(mContext).equals(""))
                            {
                                studentName=studentsModelArrayList.get(0).name
                                studentImg=studentsModelArrayList.get(0).photo
                                studentId=studentsModelArrayList.get(0).id
                                studentClass=studentsModelArrayList.get(0).section
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
                                else{
                                    studImg.setImageResource(R.drawable.student)
                                }

                            }
                            else{
                                studentName= PreferenceManager.getStudentName(mContext)!!
                                studentImg= PreferenceManager.getStudentPhoto(mContext)!!
                                studentId= PreferenceManager.getStudentID(mContext)!!
                                studentClass= PreferenceManager.getStudentClass(mContext)!!
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
                                else{
                                    studImg.setImageResource(R.drawable.student)
                                }
                            }

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
               // progressBarDialog.hide()

            }
        })



    }



    private fun initialiseUI() {
        extras = intent.extras
        if (extras != null) {
            tab_type = extras!!.getString("tab_type")
        }
        relativeHeader = findViewById<View>(R.id.relativeHeader) as RelativeLayout
      //  progressBarDialog = ProgressBarDialog(mContext, R.drawable.spinner)



        btn_history!!.visibility = View.VISIBLE


        home!!.setOnClickListener {
            val `in` = Intent(
                mContext,
                HomeActivity::class.java
            )
            `in`.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(`in`)
        }

        studentNameTxt = findViewById<TextView>(R.id.studentName)
        studImg = findViewById<ImageView>(R.id.studImg)
        mStudentSpinner = findViewById<View>(R.id.studentSpinner) as LinearLayout
        studImg = findViewById<View>(R.id.imagicon) as ImageView
        instructionTxt = findViewById<View>(R.id.instructionTxt) as TextView
        requestForNewCard = findViewById<View>(R.id.addToWallet) as Button
        et_amount = findViewById<View>(R.id.et_amount) as TextView
        btn_history!!.setOnClickListener {

        }
        fetchInstructionsAPI()



        datePicker = DatePickerDialog(mContext!!)


        val calendar = Calendar.getInstance()
        val day = calendar[Calendar.DAY_OF_MONTH]
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH]
        et_amount!!.setOnClickListener {
            datePicker = DatePickerDialog(
                mContext!!,
                { view, year, month, dayOfMonth -> // adding the selected date in the edittext
                    et_amount!!.text = dayOfMonth.toString() + "/" + (month + 1) + "/" + year
                    fromDate = year.toString() + "-" + (month + 1) + "-" + dayOfMonth
                }, year, month, day
            )

            // set maximum date to be selected as today
            datePicker!!.datePicker.maxDate = calendar.timeInMillis

            // show the dialog
            datePicker!!.show()
        }
        requestForNewCard!!.setOnClickListener {
           /* val intent = Intent(
                mContext,
                LostCardPaymentActivity::class.java
            )*/
            intent.putExtra("fromDate", "2023-10-21")
            intent.putExtra("studentId", stud_id)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            et_amount!!.text = "Lost Card Date"
            startActivity(intent)
        }
       // studentName = findViewById<View>(R.id.studentName) as TextView



        mStudentSpinner!!.setOnClickListener {
            if (studentsModelArrayList.size > 0) {
                showSocialmediaList(studentsModelArrayList)
            } else {

            }
        }
    }

    private fun fetchInstructionsAPI() {



    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("request_code", requestCode.toString())
        Log.d("resultt_code", resultCode.toString())
        if (data == null) {
            Toast.makeText(mContext, "transaction cancelled", Toast.LENGTH_SHORT).show()
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
                                "\"users_id\":\"" + PreferenceManager.getUserCode(mContext) + "\"," +
                                "\"amount\":\"" + payAmount + "\"," +
                                "\"keycode\":\"" + merchantOrderReference + "\"" + "}]}"
                    println("JSON DATA URL$JSONData")
                    CallWalletSubmission(JSONData)
                } else {
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
      //  val version: String = BuildConfig.VERSION_NAME
        //progressBarDialog.show()




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
                        et_amount!!.text = "Lost Card Date"
                        studentName=studentsModelArrayList.get(position).name
                        studentImg=studentsModelArrayList.get(position).photo
                        studentId=studentsModelArrayList.get(position).id
                        studentClass=studentsModelArrayList.get(position).section
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


}