package com.nas.alreem.activity.cca

import androidx.appcompat.app.AppCompatActivity
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
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.nas.alreem.R
import com.nas.alreem.activity.cca.adapter.CCAsListActivityAdapter
import com.nas.alreem.activity.cca.model.*
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.activity.payments.adapter.StudentListAdapter
import com.nas.alreem.activity.payments.model.StudentList
import com.nas.alreem.activity.payments.model.StudentListModel
import com.nas.alreem.appcontroller.AppController
import com.nas.alreem.constants.*
import com.nas.alreem.fragment.cca.CCAFragment
import com.nas.alreem.fragment.home.homeActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class CCA_Activity_New:AppCompatActivity() {
    lateinit var mContext: Context
    lateinit var titleTextView: TextView
    lateinit var back: ImageView
    lateinit var backRelative: RelativeLayout
    lateinit var logoclick: ImageView
    lateinit var progress: ProgressBar
    var studentListArrayList = ArrayList<StudentList>()
    var mCCAmodelArrayList: ArrayList<CCAModel>? = ArrayList()
    var CCADetailModelArrayList: ArrayList<CCADetailModel>? = ArrayList()
    var CCAchoiceModelArrayList: ArrayList<CCAchoiceModel>? = ArrayList()
    var CCAchoiceModelArrayList2: ArrayList<CCAchoiceModel>? = ArrayList()
    lateinit var studentName: TextView
    lateinit var textViewYear: TextView
    lateinit var enterTextView: TextView
    var stud_id = ""
    var stud_class = ""
    var stud_name = ""
    var mStudentSpinner: LinearLayout? = null
    var relativeHeader: RelativeLayout? = null
    // lateinit var student_Name: String
    // lateinit var studentImg: String
    // lateinit var studentClass: String
    lateinit var studImg: ImageView
    var stud_img = ""
    var tab_type = "ECA Options"
    var extras: Bundle? = null
    var recycler_review: RecyclerView? = null
    var recyclerViewLayoutManager: GridLayoutManager? = null
    var mCCAsActivityAdapter: CCAsListActivityAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cca)
        mContext = this
        initilaiseUI()
        AppController.keyy="0"
        logoclick.setOnClickListener {
            val mIntent = Intent(mContext, HomeActivity::class.java)
            mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

            startActivity(mIntent)
        }

        backRelative.setOnClickListener {
            finish()
        }
        mStudentSpinner!!.setOnClickListener { showStudentsList(mContext,studentListArrayList) }
        var internetCheck = ConstantFunctions.internetCheck(mContext)
        if (internetCheck) {
            getStudentList()

        } else {
            DialogFunctions.showInternetAlertDialog(mContext)
        }

    }

    private fun showStudentsList(mContext: Context, mStudentArray: ArrayList<StudentList>) {
        val dialog = Dialog(mContext)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialogue_student_list)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val dialogDismiss = dialog.findViewById<View>(R.id.btn_dismiss) as Button
        val iconImageView = dialog.findViewById<View>(R.id.iconImageView) as ImageView
        iconImageView.setImageResource(R.drawable.boy)
        val socialMediaList =
            dialog.findViewById<View>(R.id.studentListRecycler) as RecyclerView
        //if(mSocialMediaArray.get())
        //if(mSocialMediaArray.get())
        val sdk = Build.VERSION.SDK_INT
        if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
            dialogDismiss.setBackgroundDrawable(mContext.resources.getDrawable(R.drawable.button))
        } else {
            dialogDismiss.background = mContext.resources.getDrawable(R.drawable.button)
        }

        val divider = DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL)
        divider.setDrawable(ContextCompat.getDrawable(mContext,R.drawable.list_divider_teal)!!)
        socialMediaList!!.addItemDecoration(divider)

        socialMediaList.setHasFixedSize(true)
        val llm = LinearLayoutManager(mContext)
        llm.orientation = LinearLayoutManager.VERTICAL
        socialMediaList.layoutManager = llm

        val studentAdapter = StudentListAdapter(mContext, mStudentArray)
        socialMediaList.adapter = studentAdapter
        dialogDismiss.setOnClickListener { dialog.dismiss() }
        socialMediaList.addOnItemClickListener(object :OnItemClickListener{
            override fun onItemClicked(position: Int, view: View) {
                dialog.dismiss()
                studentName!!.setText(mStudentArray!!.get(position).name)
                stud_id = mStudentArray!!.get(position).id.toString()
                stud_name = mStudentArray.get(position).name.toString()
                stud_class = mStudentArray.get(position).studentClass.toString()
                stud_img = mStudentArray.get(position).photo.toString()
                textViewYear!!.text = "Class : " + mStudentArray.get(position).studentClass
                // PreferenceManager.setStudentID(mContext,stud_id)

                if (stud_img != "") {
                    Glide.with(mContext) //1
                        .load(stud_img)
                        .placeholder(R.drawable.student)
                        .error(R.drawable.student)
                        .skipMemoryCache(true) //2
                        .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                        .transform(CircleCrop()) //4
                        .into(studImg!!)
                } else {
                    studImg!!.setImageResource(R.drawable.boy)
                }
                PreferenceManager.setCCAStudentIdPosition(
                    mContext,
                    position.toString() + ""
                )

                var internetCheck = ConstantFunctions.internetCheck(mContext)
                if (internetCheck) {
                    getCCAListAPI(stud_id)

                } else {
                    DialogFunctions.showInternetAlertDialog(mContext)
                }
            }

        })

        dialog.show()
    }

    private fun getStudentList() {
        progress.visibility = View.VISIBLE
        val token = PreferenceManager.getaccesstoken(mContext)
        val call: Call<StudentListModel> = ApiClient.getClient.studentList("Bearer "+token)
        call.enqueue(object : Callback<StudentListModel> {
            override fun onFailure(call: Call<StudentListModel>, t: Throwable) {
                // Log.e("Error", t.localizedMessage)
                progress.visibility = View.GONE
            }
            override fun onResponse(call: Call<StudentListModel>, response: Response<StudentListModel>) {
                progress.visibility = View.GONE
                //val arraySize :Int = response.body()!!.responseArray.studentList.size
                if (response.body()!!.status==100)
                {
                    studentListArrayList.addAll(response.body()!!.responseArray.studentList)
                    if (PreferenceManager.getStudIdForCCA(mContext).equals(""))
                    {
                        //  Log.e("studentname",student_Name)
                        stud_name=studentListArrayList.get(0).name
                        stud_img=studentListArrayList.get(0).photo
                        stud_id=studentListArrayList.get(0).id
                        stud_class=studentListArrayList.get(0).section
                        // Log.e("Student_idss",stud_id)
                        // PreferenceManager.setStudentID(mContext,studentId)
                        //  PreferenceManager.setStudentName(mContext,student_Name)
                        //PreferenceManager.setStudentPhoto(mContext,studentImg)
                        //  PreferenceManager.setStudentClass(mContext,studentClass)
                        studentName.text=stud_name
                        PreferenceManager.setCCAStudentIdPosition(mContext, "0")

                        if(!stud_img.equals(""))
                        {
                            Glide.with(mContext) //1
                                .load(stud_img)
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
                        val studentSelectPosition = Integer.valueOf(
                            PreferenceManager.getCCAStudentIdPosition(mContext)
                        )
                        stud_name= studentListArrayList[studentSelectPosition].name!!
                        stud_img= studentListArrayList[studentSelectPosition].photo!!
                        stud_id=  studentListArrayList!![studentSelectPosition].id.toString()
                        // PreferenceManager.setStudentID(mContext, studentId)
                        // PreferenceManager.setStudIdForCCA(mContext, studentId)
                        //  Log.e("Studentid1",stud_id)
                        stud_class= studentListArrayList[studentSelectPosition].studentClass!!
                        studentName.text=stud_name
                        if(!stud_img.equals(""))
                        {
                            Glide.with(mContext) //1
                                .load(stud_img)
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
                    var internetCheck = ConstantFunctions.internetCheck(mContext)
                    if (internetCheck) {
                        getCCAListAPI(stud_id)

                    } else {
                        DialogFunctions.showInternetAlertDialog(mContext)
                    }

//
                }


            }

        })
    }

    private fun getCCAListAPI(studId: String) {
        // Log.e("studId",studId)
        val body = CCAListRequestModel(studId)
        val token = PreferenceManager.getaccesstoken(mContext)
        val call: Call<CCAListResponseModel> =
            ApiClient.getClient.getCCAList( body,"Bearer $token")
        progress.visibility = View.VISIBLE
        call.enqueue(object : Callback<CCAListResponseModel> {
            override fun onResponse(
                call: Call<CCAListResponseModel>,
                response: Response<CCAListResponseModel>
            ) {
                progress.visibility = View.GONE
                mCCAmodelArrayList = ArrayList()

                if (response.body() != null) {
                    if (response.body()!!.status!!.equals(100)) {

                        if (response.body()!!.data!!.isNotEmpty()) {
                            for (i in response.body()!!.data!!.indices) {
                                enterTextView!!.visibility = View.VISIBLE
                                Log.e("isattentii", response.body()!!.data!![i].isAttendee.toString())
                                mCCAmodelArrayList!!.add(addCCAlist(response.body()!!.data!![i]))
                            }
                            //  Log.e("arraty", mCCAmodelArrayList!!.get(0).isAttendee.toString())
                            //  Log.e("title", mCCAmodelArrayList!!.get(0).title.toString())
                            if (mCCAmodelArrayList!!.size > 0) {
                                mCCAsActivityAdapter = CCAsListActivityAdapter(
                                    this@CCA_Activity_New,
                                    mCCAmodelArrayList!!
                                )
                                recycler_review!!.adapter = mCCAsActivityAdapter
                            }
                        } else {
                            mCCAsActivityAdapter =
                                CCAsListActivityAdapter(this@CCA_Activity_New, mCCAmodelArrayList!!)
                            recycler_review!!.adapter = mCCAsActivityAdapter
                            enterTextView!!.visibility = View.GONE
                            Toast.makeText(
                                this@CCA_Activity_New,
                                "No EAP available",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }else{
                        Toast.makeText(mContext, "Fail", Toast.LENGTH_SHORT).show()
                    }
                }
//                    else if (response_code.equals("500", ignoreCase = true)) {
//                        AppUtils.showDialogAlertDismiss(
//                            mContext as Activity,
//                            "Alert",
//                            getString(R.string.common_error),
//                            R.drawable.exclamationicon,
//                            R.drawable.round
//                        )
//                    } else if (response_code.equals("400", ignoreCase = true)) {
//                        AppUtils.getToken(mContext, object : GetTokenSuccess() {
//                            fun tokenrenewed() {
//                                getCCAListAPI(studentId)
//                            }
//                        })
//                    } else if (response_code.equals("401", ignoreCase = true)) {
//                        AppUtils.getToken(mContext, object : GetTokenSuccess() {
//                            fun tokenrenewed() {
//                                getCCAListAPI(studentId)
//                            }
//                        })
//                    } else if (response_code.equals("402", ignoreCase = true)) {
//                        AppUtils.getToken(mContext, object : GetTokenSuccess() {
//                            fun tokenrenewed() {
//                                getCCAListAPI(studentId)
//                            }
//                        })
//                    }
                else {
                    ConstantFunctions.showDialogueWithOk(mContext,getString(R.string.common_error),"Alert")
                }

            }

            override fun onFailure(call: Call<CCAListResponseModel>, t: Throwable) {
                progress.visibility = View.GONE
                ConstantFunctions.showDialogueWithOk(mContext,getString(R.string.common_error),"Alert")
            }

        })
    }

    private fun addCCAlist(dataObject: CCAListResponseModel.Data): CCAModel {
        val mCCAModel = CCAModel()
        mCCAModel.cca_days_id = dataObject!!.cca_days_id.toString()
        mCCAModel.title = dataObject.title
        mCCAModel.from_date = dataObject.from_date
        mCCAModel.to_date = dataObject.to_date
        mCCAModel.isAttendee = dataObject.isAttendee
        mCCAModel.status = dataObject.status.toString()

        mCCAModel.submission_dateTime = dataObject.submission_dateTime
        mCCAModel.isSubmissionDateOver = dataObject.isSubmissiondateOver
        val jsonCCADetailArray: ArrayList<CCAListResponseModel.Data.Detail>? = dataObject.details
        //   Log.e("choice2adasda", dataObject.details!![0]!!.choice2.toString())

        //  Log.e("choice2adasda", dataObject.details!![0]!!.choice2!![0]!!.attending_status.toString())
        CCADetailModelArrayList = ArrayList<CCADetailModel>()
        if (jsonCCADetailArray!!.isNotEmpty()) {
            for (element in jsonCCADetailArray) {
                val objectCCA = element
                val mCCADetailModel = CCADetailModel()
                mCCADetailModel.day = objectCCA!!.day

                val jsonCCAChoiceArray = objectCCA.choice1
                val jsonCCAChoiceArray2 = objectCCA.choice2
                // Log.e("choice1",objectCCA.choice1.toString())
                // Log.e("choice2 atte", objectCCA.choice1!![0]!!.attending_status.toString())
                // Log.e("choice2 atte", objectCCA.choice2!![0]!!.attending_status.toString())
                //  Log.e("choice2 atte", jsonCCAChoiceArray2!![0]!!.attending_status.toString())
                CCAchoiceModelArrayList = java.util.ArrayList<CCAchoiceModel>()
                if (jsonCCAChoiceArray!!.size > 0) {
                    var k = 0
                    for (j in jsonCCAChoiceArray.indices) {
                        val mCCADetailModelchoice = CCAchoiceModel()
                        //  if (jsonCCAChoiceArray.size != j) {
                        val objectCCAchoice = jsonCCAChoiceArray[j]
                        mCCADetailModelchoice.cca_item_name = objectCCAchoice!!.cca_item_name
                        mCCADetailModelchoice.cca_details_id = objectCCAchoice.cca_details_id.toString()
                        mCCADetailModelchoice.cca_item_id = objectCCAchoice.cca_item_id.toString()
                        mCCADetailModelchoice.isattending = objectCCAchoice.isAttendee
                        mCCADetailModelchoice.cca_item_start_time = objectCCAchoice.cca_item_start_time
                        mCCADetailModelchoice.cca_item_end_time = objectCCAchoice.cca_item_end_time
                        mCCADetailModelchoice.venue = objectCCAchoice.venue
                        mCCADetailModelchoice.description = objectCCAchoice.description
                        mCCADetailModelchoice.slot_remaining_count = objectCCAchoice.slot_remaining_count
                        mCCADetailModelchoice.attending_status = objectCCAchoice.attending_status

                        if (objectCCAchoice.attending_status.equals("1", ignoreCase = true)
                        ) {
                            if (dataObject.isAttendee
                                    .equals("1", ignoreCase = true)
                            ) {
                                mCCADetailModelchoice.status = "1"
                                mCCADetailModel.choice1 = objectCCAchoice.cca_item_name
                                mCCADetailModel.choice1Id = objectCCAchoice.cca_details_id.toString()
                                mCCADetailModel.choiceitem1Id = objectCCAchoice.cca_item_id.toString()
                                Log.e("choice1Id",objectCCAchoice.cca_details_id.toString())
                                Log.e("choice1Id2", mCCADetailModel.choice1Id!!)
                                Log.e("choiceitem1Id",objectCCAchoice.cca_item_id.toString())
                                Log.e("choiceitem1Id2", mCCADetailModel.choiceitem1Id!!)


                            } else {
                                mCCADetailModelchoice.status = "0"
                            }
                            k = k + 1
                        } else {
                            mCCADetailModelchoice.status = "0"
                        }
                        mCCADetailModelchoice.disableCccaiem = false
                        mCCADetailModelchoice.dayChoice = objectCCAchoice.day
                        /* } else {
                             mCCADetailModelchoice.cca_item_name = "None"
                             mCCADetailModelchoice.cca_details_id = "-541"
                             mCCADetailModelchoice.venue = "0"
                             mCCADetailModelchoice.isattending = "0"
                             if (k == 0) {
                                 if (dataObject.isAttendee
                                         .equals("2", ignoreCase = true)
                                 ) {
                                     mCCADetailModelchoice.status = "1"
                                     mCCADetailModel.choice1 = "None"
                                     mCCADetailModel.choice1Id = "-541"
                                 } else {
                                     mCCADetailModelchoice.status = "0"
                                 }
                             } else {
                                 mCCADetailModelchoice.status = "0"
                             }
                             mCCADetailModelchoice.disableCccaiem = false
                             mCCADetailModelchoice.dayChoice = objectCCA.day
                         }*/
                        CCAchoiceModelArrayList!!.add(mCCADetailModelchoice)
                    }
                }
                mCCADetailModel.ccaChoiceModel = CCAchoiceModelArrayList
                CCAchoiceModelArrayList2 = ArrayList<CCAchoiceModel>()
                /* if (jsonCCAChoiceArray2!!.isNotEmpty()) {
                     var k = 0
                     for (j in jsonCCAChoiceArray2.indices) {
                         val mCCADetailModelchoice = CCAchoiceModel()
                       //  if (jsonCCAChoiceArray2.size != j) {
                             val objectCCAchoice = jsonCCAChoiceArray2[j]
                             mCCADetailModelchoice.cca_item_name = objectCCAchoice!!.cca_item_name
                             mCCADetailModelchoice.cca_details_id = objectCCAchoice.cca_details_id.toString()
                             mCCADetailModelchoice.isattending = objectCCAchoice.isAttendee
                             mCCADetailModelchoice.cca_item_start_time = objectCCAchoice.cca_item_start_time
                             mCCADetailModelchoice.cca_item_end_time = objectCCAchoice.cca_item_end_time
                             mCCADetailModelchoice.venue = objectCCAchoice.venue
                             mCCADetailModelchoice.dayChoice = objectCCAchoice.day
                             mCCADetailModelchoice.description = objectCCAchoice.description

                             if (objectCCAchoice.attending_status
                                     .equals("0", ignoreCase = true)
                             ) {
                                 if (dataObject.isAttendee
                                         .equals("2", ignoreCase = true)
                                 ) {
                                     mCCADetailModelchoice.status = "1"
                                     mCCADetailModel.choice2 = objectCCAchoice.cca_item_name
                                     mCCADetailModel.choice2Id = objectCCAchoice.cca_details_id.toString()
                                     //   Log.e("choice2qqq",mCCADetailModel.choice2.toString())
                                 } else {
                                     mCCADetailModelchoice.status = "0"
                                 }
                                 k += 1
                             } else {
                                 mCCADetailModelchoice.status = "0"
                             }
                             mCCADetailModelchoice.disableCccaiem = false
                        *//* } else {
                            mCCADetailModelchoice.cca_item_name = "None"
                            mCCADetailModelchoice.cca_details_id = "-541"
                            mCCADetailModelchoice.isattending = "0"
                            mCCADetailModelchoice.venue = "0"
                            if (k == 0) {
                                if (dataObject.isAttendee
                                        .equals("2", ignoreCase = true)
                                ) {
                                    mCCADetailModelchoice.status = "1"
                                    mCCADetailModel.choice2 = "None"
                                    mCCADetailModel.choice2Id = "-541"
                                } else {
                                    mCCADetailModelchoice.status = "0"
                                }
                            } else {
                                mCCADetailModelchoice.status = "0"
                            }
                            mCCADetailModelchoice.dayChoice = objectCCA.day
                            mCCADetailModelchoice.disableCccaiem = false
                        }*//*
                        CCAchoiceModelArrayList2!!.add(mCCADetailModelchoice)
                    }
                }*/
                mCCADetailModel.ccaChoiceModel2 = CCAchoiceModelArrayList2
                CCADetailModelArrayList!!.add(mCCADetailModel)
            }
        }
        mCCAModel.details = CCADetailModelArrayList
        // Log.e("mCCAModel", mCCAModel.details!!.get(0).choice1.toString())
        return mCCAModel
    }

    private fun initilaiseUI() {
        progress = findViewById(R.id.progress)
        titleTextView = findViewById(R.id.heading)
        back = findViewById(R.id.btn_left)
        backRelative = findViewById(R.id.backRelative)
        logoclick = findViewById(R.id.logoClickImgView)
        extras = intent.extras
        if (extras != null) {
            tab_type = extras!!.getString("tab_type")!!
        }
        relativeHeader = findViewById<View>(R.id.relativeHeader) as RelativeLayout
        recycler_review = findViewById<View>(R.id.recycler_view_cca) as RecyclerView
        recycler_review!!.setHasFixedSize(true)
        recyclerViewLayoutManager = GridLayoutManager(mContext, 1)
        val spacing = 5 // 50px

        val divider = DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL)
        divider.setDrawable(ContextCompat.getDrawable(mContext,R.drawable.list_divider_teal)!!)
        recycler_review!!.addItemDecoration(divider)
        // val itemDecoration = ItemOffsetDecoration(mContext, spacing)
//        recycler_review!!.addItemDecoration(
//            DividerItemDecoration(mContext.resources.getDrawable(R.drawable.list_divider))
//        )
        //      recycler_review!!.addItemDecoration(itemDecoration)
        recycler_review!!.layoutManager = recyclerViewLayoutManager
//        headermanager = HeaderManager(this@CCA_Activity, tab_type)
//        headermanager.getHeader(relativeHeader, 0)
//        back = headermanager.getLeftButton()
//        headermanager.setButtonLeftSelector(
//            R.drawable.back,
//            R.drawable.back
//        )
//        back.setOnClickListener {
//            AppUtils.hideKeyBoard(mContext)
//            finish()
//        }
//        home = headermanager.getLogoButton()
//        home.setOnClickListener(View.OnClickListener {
//            val `in` = Intent(mContext, HomeListAppCompatActivity::class.java)
//            `in`.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//            startActivity(`in`)
//        })
        studImg = findViewById(R.id.imagicon)

        mStudentSpinner = findViewById<View>(R.id.studentSpinner) as LinearLayout
        studentName = findViewById<View>(R.id.studentName) as TextView
        enterTextView = findViewById<View>(R.id.enterTextView) as TextView
        textViewYear = findViewById<View>(R.id.textViewYear) as TextView
        recycler_review!!.addOnItemClickListener(object :OnItemClickListener{
            override fun onItemClicked(position: Int, view: View) {
                if (mCCAmodelArrayList!![position].status.equals("0") || mCCAmodelArrayList!![position].status.equals(
                        "2")
                ) {
                    callStatusChangeApi(
                        mCCAmodelArrayList!![position].cca_days_id,
                        position,
                        mCCAmodelArrayList!![position].status
                    )
                }
                if (mCCAmodelArrayList!![position].isAttendee.equals("0")) {
                    if (mCCAmodelArrayList!![position].isSubmissionDateOver.equals(
                            "0"
                        )
                    )
                    {
                        if (mCCAmodelArrayList!![position].details!!.size > 0) {
                            val intent = Intent(mContext, CCASelectionActivity::class.java)
                            /*intent.putExtra(
                                "CCA_Detail",
                                mCCAmodelArrayList!![position].details
                            )*/
                            intent.putExtra("tab_type", tab_type)
                            PreferenceManager.saveDetailsArrayList(mContext, mCCAmodelArrayList!![position].details)
                            // PreferenceManager.setStudentID(mContext, stud_id)
                            PreferenceManager.setkeyvalue(mContext,"0")
                            PreferenceManager.setStudIdForCCA(mContext, stud_id)
                            PreferenceManager.setStudNameForCCA(mContext, stud_name)
                            PreferenceManager.setStudClassForCCA(mContext, stud_class)
                            PreferenceManager.setCCATitle(
                                mContext,
                                mCCAmodelArrayList!![position].title
                            )
                            PreferenceManager.setCCAItemId(
                                mContext,
                                mCCAmodelArrayList!![position].cca_days_id
                            )
                            startActivity(intent)
                        } else {
                            ConstantFunctions.showDialogueWithOk(mContext,"No Data Available","Alert")
                        }
                    } else {
                        ConstantFunctions.showDialogueWithOk(mContext,"EAP Sign-Up Closed","Alert")

                    }
                } else if (mCCAmodelArrayList!![position].isAttendee.equals("1")) {
                    if(mCCAmodelArrayList!!.get(position).isSubmissionDateOver.equals("0"))
                    {
                        val intent =
                            Intent(mContext, CCAsReviewEditAfterSubmissionActivity::class.java)
                        //  Log.e("cca choice1s", mCCAmodelArrayList!![position].details!!.get(0).choice1!!)
                        //  Log.e("cca choice2s", mCCAmodelArrayList!![position].details!!.get(0).choice2!!)
                        intent.putExtra("tab_type", tab_type)
                        // intent.putExtra("CCA_Detail", mCCAmodelArrayList!![position].details)
                        intent.putExtra("submissiondateover", mCCAmodelArrayList!![position].isSubmissionDateOver)
                        PreferenceManager.saveDetailsArrayList(mContext, mCCAmodelArrayList!![position].details)
                        //  PreferenceManager.setStudentID(mContext, stud_id)
                        //  Log.e("id",stud_id)
                        PreferenceManager.setStudIdForCCA(mContext, stud_id)
                        PreferenceManager.setStudNameForCCA(mContext, stud_name)
                        PreferenceManager.setStudClassForCCA(mContext, stud_class)
                        PreferenceManager.setCCATitle(mContext, mCCAmodelArrayList!![position].title)
                        PreferenceManager.setCCAItemId(mContext, mCCAmodelArrayList!![position].cca_days_id)
                        startActivity(intent)
                    }
                    else{
                        val intent = Intent(mContext, CCAsReviewAfterSubmissionActivity::class.java)
                        intent.putExtra("tab_type", tab_type)
                        //intent.putExtra("CCA_Detail", mCCAmodelArrayList!![position].getDetails())
                        intent.putExtra("submissiondateover", mCCAmodelArrayList!![position].isSubmissionDateOver)
                        PreferenceManager.saveDetailsArrayList(mContext, mCCAmodelArrayList!![position].details)
                        PreferenceManager.setStudIdForCCA(mContext, stud_id)
                        PreferenceManager.setStudNameForCCA(mContext, stud_name)
                        PreferenceManager.setStudClassForCCA(mContext, stud_class)
                        PreferenceManager.setCCATitle(mContext, mCCAmodelArrayList!![position].title)
                        PreferenceManager.setCCAItemId(mContext, mCCAmodelArrayList!![position].cca_days_id)
                        startActivity(intent)
                    }

                } else {
                    val intent =
                        Intent(mContext, CCAsReviewAfterSubmissionActivity::class.java)
                    intent.putExtra("tab_type", tab_type)
                    // PreferenceManager.setStudentID(mContext, stud_id)
                    PreferenceManager.setStudIdForCCA(mContext, stud_id)
                    PreferenceManager.setStudNameForCCA(mContext, stud_name)
                    PreferenceManager.setStudClassForCCA(mContext, stud_class)
                    PreferenceManager.setCCATitle(mContext, mCCAmodelArrayList!![position].title)
                    PreferenceManager.setCCAItemId(mContext, mCCAmodelArrayList!![position].cca_days_id)
                    startActivity(intent)
                }
            }

        })
    }

    private fun callStatusChangeApi(ccaDaysId: String?, eventPosition: Int, status: String?) {


        var model= CCAReadStatusRequestModel(ccaDaysId,"cca")

        val token = PreferenceManager.getaccesstoken(mContext)
        val call: Call<CCASubmitResponseModel> =
            ApiClient.getClient.readstatusupdate( model,"Bearer $token")
        // progressBar.visibility = View.VISIBLE
        call.enqueue(object : Callback<CCASubmitResponseModel> {
            override fun onResponse(
                call: Call<CCASubmitResponseModel>,
                response: Response<CCASubmitResponseModel>
            ) {
                // progressBar.visibility = View.GONE
                if (response.isSuccessful){
                    if (response.body() != null){
                        if (response.body()!!.status!!.equals(100)){

                            if (status.equals("0", ignoreCase = true) || status.equals(
                                    "2",
                                    ignoreCase = true
                                )
                            ) {
                                mCCAmodelArrayList!![eventPosition].status=("1")
                                mCCAsActivityAdapter!!.notifyDataSetChanged()
                            }
                        }
                        else if (response.body()!!.status!!.equals(109))
                        {


                        }
                        else{

                            Toast.makeText(mContext, "Failure", Toast.LENGTH_SHORT).show()
                        }

                    }else{

                        ConstantFunctions.showDialogueWithOk(mContext,getString(R.string.common_error),"Alert")
                    }
                }else{

                    ConstantFunctions.showDialogueWithOk(mContext,getString(R.string.common_error),"Alert")
                }
            }

            override fun onFailure(call: Call<CCASubmitResponseModel>, t: Throwable) {
                // progressBar.visibility = View.GONE
                ConstantFunctions.showDialogueWithOk(mContext,getString(R.string.common_error),"Alert")
            }

        })
    }
}