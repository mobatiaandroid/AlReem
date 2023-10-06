package com.nas.alreem.activity.canteen

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
import com.nas.alreem.activity.canteen.adapter.PaymentHistoryAdapter
import com.nas.alreem.activity.canteen.model.wallethistory.CreditHisListModel
import com.nas.alreem.activity.canteen.model.wallethistory.WalletHistoryApiModel
import com.nas.alreem.activity.canteen.model.wallethistory.WalletHistoryModel
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.activity.payments.adapter.StudentListAdapter
import com.nas.alreem.activity.payments.model.StudentList
import com.nas.alreem.activity.payments.model.StudentListModel
import com.nas.alreem.constants.*
import com.nas.alreem.rest.ApiClient

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class PaymentHistory_Activity : AppCompatActivity() {
    lateinit var wallethistory_list: ArrayList<CreditHisListModel>
    lateinit var nContext: Context
    private lateinit var logoClickImg: ImageView
    lateinit var recyclerView: RecyclerView
    lateinit var back: ImageView
    //lateinit var amount: EditText
    //lateinit var addToWallet: Button
   // lateinit var progress: RelativeLayout
    lateinit var studentlist: ArrayList<String>
    //lateinit var studentname: TextView
    lateinit var dropdown: LinearLayout
    lateinit var title: TextView
    lateinit var progress: ProgressBar

    lateinit var studImg: ImageView
    lateinit var studentNameTxt: TextView
    lateinit var studentSpinner: LinearLayout
    lateinit var studentName: String
    lateinit var studentId: String
    lateinit var studentImg: String
    lateinit var studentClass: String
    var studentListArrayList = ArrayList<StudentList>()
    var payAmount = ""
    var merchantOrderReference = ""
    var canteen_response = ""
    var Error = ""
    var topup_limit = ""
    var order_id = ""
    var stud_id: String=""
    var studClass = ""
    var orderId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.canteen_paymenthistory)
        initfn()
        if (ConstantFunctions.internetCheck(nContext)) {
//            progressDialog.visibility= View.VISIBLE
            callStudentListApi()
        } else {
            DialogFunctions.showInternetAlertDialog(nContext)
        }



    }

    private fun initfn() {
        nContext = this
        logoClickImg = findViewById(R.id.logoclick)
        back = findViewById(R.id.back)
        //addToWallet = findViewById(R.id.addToWallet)
       // amount = findViewById(R.id.et_amount)
        studentlist = ArrayList()
        wallethistory_list = ArrayList()
        studentSpinner = findViewById(R.id.studentSpinner)
        studentNameTxt = findViewById(R.id.studentName)!!
        studImg = findViewById(R.id.imagicon)
        recyclerView = findViewById(R.id.history_rec)
        progress = findViewById(R.id.progress)
        title = findViewById(R.id.titleTextView)

        title.text = "Payment History"
        /*val aniRotate: Animation =
            AnimationUtils.loadAnimation(nContext, R.anim.linear_interpolator)
        progress.startAnimation(aniRotate)*/
        back.setOnClickListener {
            finish()
        }
        logoClickImg.setOnClickListener {
            val intent = Intent(nContext, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
        studentSpinner.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                showStudentList(nContext, studentListArrayList)

            }
        })
       // walletHistory()

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
                //progressDialog.visibility=View.VISIBLE
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
                //progressDialog.visibility = View.VISIBLE

                if (ConstantFunctions.internetCheck(nContext)) {
//            progressDialog.visibility= View.VISIBLE
                    walletHistory()
                } else {
                    DialogFunctions.showInternetAlertDialog(nContext)
                }

                //  Toast.makeText(activity, mStudentList.get(position).name, Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
        })
        dialog.show()
    }
    fun callStudentListApi()
    {

        progress.visibility = View.VISIBLE
        val token = PreferenceManager.getaccesstoken(nContext)
        val call: Call<StudentListModel> = ApiClient.getClient.studentList("Bearer "+token)
        call.enqueue(object : Callback<StudentListModel> {
            override fun onFailure(call: Call<StudentListModel>, t: Throwable) {
                Log.e("Error", t.localizedMessage)
                progress.visibility = View.GONE
            }
            override fun onResponse(call: Call<StudentListModel>, response: Response<StudentListModel>) {
                progress.visibility = View.GONE
                //val arraySize :Int = response.body()!!.responseArray.studentList.size
                if (response.body()!!.status==100)
                {
                    studentListArrayList.addAll(response.body()!!.responseArray.studentList)
                    if (PreferenceManager.getStudentID(nContext).equals(""))
                    {
                        Log.e("Empty Img","Empty")
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
                        studentImg= PreferenceManager.getStudentPhoto(nContext)!!
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
                        walletHistory()
                    } else {
                        DialogFunctions.showInternetAlertDialog(nContext)
                    }


                    //callStudentInfoApi()
                }


            }

        })
    }
    private fun walletHistory(){
        progress.visibility=View.VISIBLE

        val token = PreferenceManager.getaccesstoken(nContext)
        val paymentSuccessBody = WalletHistoryApiModel(PreferenceManager.getStudentID(nContext).toString(),"0","50")
        val call: Call<WalletHistoryModel> =
            ApiClient.getClient.get_wallet_history(paymentSuccessBody, "Bearer " + token)
        call.enqueue(object : Callback<WalletHistoryModel> {
            override fun onFailure(call: Call<WalletHistoryModel>, t: Throwable) {
                Log.e("Failed", t.localizedMessage)
                progress.visibility=View.GONE
            }

            override fun onResponse(call: Call<WalletHistoryModel>, response: Response<WalletHistoryModel>) {
                val responsedata = response.body()
                progress.visibility = View.GONE
                Log.e("Response Signup", responsedata.toString())
                if (responsedata != null) {
                    try {

                        if (response.body()!!.status==100) {
                            //mProgressRelLayout.visibility=View.GONE

                            wallethistory_list= ArrayList()
                            wallethistory_list.addAll(response.body()!!.responseArray.credit_history)
                            if (wallethistory_list.size>0)
                            {
                                recyclerView.visibility=View.VISIBLE
                                recyclerView.layoutManager = LinearLayoutManager(nContext)
                                recyclerView.adapter = PaymentHistoryAdapter(wallethistory_list,nContext)
                            }
                            else
                            {
                                recyclerView.visibility=View.GONE
                                showSuccessAlertnew(nContext,"No history available","Alert")
                            }


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
        var text_dialog = dialog.findViewById(R.id.messageTxt) as TextView
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