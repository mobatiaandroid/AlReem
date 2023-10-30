package com.nas.alreem.activity.payments

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
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.nas.alreem.R
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.activity.payments.adapter.PayCategoryListAdapter
import com.nas.alreem.activity.payments.adapter.StudentListAdapter
import com.nas.alreem.activity.payments.model.*
import com.nas.alreem.constants.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentCategoryActivity : AppCompatActivity() {
    lateinit var progressDialogAdd: ProgressBar
    lateinit var mContext: Context
    lateinit var studentSpinner: LinearLayout
    var studentListArrayList = ArrayList<StudentList>()
    lateinit var studImg: ImageView
    var studentName: String=""
    var studentId: String=""
    var studentImg: String=""
    var studentClass: String=""
    lateinit var studentNameTxt: TextView
    lateinit var catListRec:RecyclerView
    lateinit var catList:ArrayList<PayCatDataList>
    lateinit var heading: TextView
    lateinit var logoClickImgView: ImageView
    lateinit var backRelative: RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_category)
        init()

    }

    private fun init(){
        mContext=this
        progressDialogAdd = findViewById(R.id.progressDialogAdd)
        catListRec=findViewById(R.id.mListView)
        studentSpinner = findViewById<LinearLayout>(R.id.studentSpinner)
        studImg = findViewById<ImageView>(R.id.imagicon)
        studentNameTxt =findViewById<TextView>(R.id.studentName)
        catListRec=findViewById(R.id.mListView)
        heading=findViewById(R.id.heading)
        backRelative=findViewById(R.id.backRelative)
        logoClickImgView=findViewById(R.id.logoClickImgView)
        heading.text="Payments"
        backRelative.setOnClickListener(View.OnClickListener {
            finish()
        })

        logoClickImgView.setOnClickListener(View.OnClickListener {
            val intent = Intent(mContext, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        })
        studentSpinner.setOnClickListener(){
            showStudentList(mContext,studentListArrayList)
        }
        if (ConstantFunctions.internetCheck(mContext))
        {
            callStudentListApi()
        }
        else
        {
            DialogFunctions.showInternetAlertDialog(mContext)
        }



    }
    fun callStudentListApi()
    {
        progressDialogAdd.visibility=View.VISIBLE
        studentListArrayList= ArrayList()
        val call: Call<StudentListModel> = ApiClient.getClient.studentList("Bearer "+PreferenceManager.getaccesstoken(mContext))
        call.enqueue(object : Callback<StudentListModel> {
            override fun onFailure(call: Call<StudentListModel>, t: Throwable) {
                Log.e("Failed", t.localizedMessage)
                progressDialogAdd.visibility=View.GONE
            }
            override fun onResponse(call: Call<StudentListModel>, response: Response<StudentListModel>) {
                val responsedata = response.body()
                progressDialogAdd.visibility=View.GONE
                if (responsedata != null) {
                    try {

                        if (response.body()!!.status==100)
                        {
                            studentListArrayList=ArrayList()
                            studentListArrayList.addAll(response.body()!!.responseArray.studentList)
                            if (PreferenceManager.getStudentID(mContext).equals(""))
                            {
                                Log.e("Empty Img","Empty")
                                studentName=studentListArrayList.get(0).name
                                studentImg=studentListArrayList.get(0).photo
                                studentId=studentListArrayList.get(0).id
                                studentClass=studentListArrayList.get(0).section
                                PreferenceManager.setStudentID(mContext,studentId)
                                PreferenceManager.setStudentName(mContext,studentName)
                                PreferenceManager.setStudentPhoto(mContext,studentImg)
                                PreferenceManager.setStudentClass(mContext,studentClass)
                                studentNameTxt.text=studentName
                                Log.e("studid(0)", PreferenceManager.getStudentID(mContext).toString())
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
                            catListRec.visibility=View.GONE
                            if (ConstantFunctions.internetCheck(mContext))
                            {
                                callCategoryList()
                            }
                            else
                            {
                                DialogFunctions.showInternetAlertDialog(mContext)
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
                mContext.resources.getDrawable(R.drawable.button_new)
            )
        } else {
            btn_dismiss.background = mContext.resources.getDrawable(R.drawable.button_new)
        }

        studentListRecycler.setHasFixedSize(true)
        val llm = LinearLayoutManager(mContext)
        llm.orientation = LinearLayoutManager.VERTICAL
        studentListRecycler.layoutManager = llm
        if(mStudentList.size>0)
        {
            val studentAdapter = StudentListAdapter(mContext,mStudentList)
            studentListRecycler.adapter = studentAdapter
        }

        btn_dismiss.setOnClickListener()
        {
            dialog.dismiss()
        }
        studentListRecycler.addOnItemClickListener(object: OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                // Your logic
                progressDialogAdd.visibility=View.VISIBLE

                studentName=studentListArrayList.get(position).name
                studentImg=studentListArrayList.get(position).photo
                studentId=studentListArrayList.get(position).id
                studentClass=studentListArrayList.get(position).section
                PreferenceManager.setStudentID(mContext,studentId)
                PreferenceManager.setStudentName(mContext,studentName)
                PreferenceManager.setStudentPhoto(mContext,studentImg)
                PreferenceManager.setStudentClass(mContext,studentClass)
                Log.e("studidclick",PreferenceManager.getStudentID(mContext).toString())
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
                progressDialogAdd.visibility = View.VISIBLE
                catListRec.visibility=View.GONE
                if (ConstantFunctions.internetCheck(mContext))
                {
                    callCategoryList()
                }
                else
                {
                    DialogFunctions.showInternetAlertDialog(mContext)
                }

                dialog.dismiss()
            }
        })
        dialog.show()
    }

    fun callCategoryList()
    {
        progressDialogAdd.visibility = View.VISIBLE
        val paymentCategoriesBody = PaymentCategoriesApiModel( PreferenceManager.getStudentID(mContext).toString())
        val call: Call<PayCategoryModel> = ApiClient.getClient.payment_categories(paymentCategoriesBody, "Bearer " +
                PreferenceManager.getaccesstoken(mContext))
        call.enqueue(object : Callback<PayCategoryModel> {
            override fun onFailure(call: Call<PayCategoryModel>, t: Throwable) {
                Log.e("Failed", t.localizedMessage)
                progressDialogAdd.visibility = View.GONE
            }

            override fun onResponse(call: Call<PayCategoryModel>, response: Response<PayCategoryModel>) {
                val responsedata = response.body()

                catListRec.visibility=View.VISIBLE

                Log.e("Response Signup", responsedata.toString())
                if (responsedata != null) {
                    try {

                        if (response.body()!!.status==100) {
                            catList= ArrayList()
                            var trn_payment=response.body()!!.responseArray.trn_no
                            PreferenceManager.setTrnPayment(mContext,trn_payment)
                            catList=response.body()!!.responseArray.data
                            catListRec.layoutManager=LinearLayoutManager(mContext)
                            if(catList.size>0)
                            {
                                catListRec.visibility=View.VISIBLE
                                progressDialogAdd.visibility = View.GONE
                                var categorytitle_adapter= PayCategoryListAdapter(mContext,catList)
                                catListRec.adapter=categorytitle_adapter
                            }
                            else
                            {
                                catListRec.visibility=View.GONE
                            }


                        }else {
                            catListRec.visibility=View.GONE
                            DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert), ConstantFunctions.commonErrorString(response.body()!!.status), mContext)
                            progressDialogAdd.visibility = View.GONE
                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

        })

    }
    override fun onResume() {
        super.onResume()
        catListRec.visibility=View.GONE
        Log.e("resume","resume")
        if(PreferenceManager.getStudentID(mContext).equals("")){

        }else {
            callStudentListApi()

            //callCategoryList()
            //callStudentListApi()
        }
    }
}