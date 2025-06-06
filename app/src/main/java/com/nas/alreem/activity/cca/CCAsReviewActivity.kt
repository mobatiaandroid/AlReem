package com.nas.alreem.activity.cca

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.nas.alreem.R
import com.nas.alreem.activity.cca.adapter.CCAfinalReviewAdapter
import com.nas.alreem.activity.cca.model.*
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.appcontroller.AppController
import com.nas.alreem.constants.ApiClient
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.DialogFunctions
import com.nas.alreem.constants.PreferenceManager

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CCAsReviewActivity : AppCompatActivity() {
    lateinit var mContext: Context
    lateinit var titleTextView: TextView
    lateinit var back: ImageView
    lateinit var backRelative: RelativeLayout
    lateinit var logoclick: ImageView
    lateinit var progressBar: ProgressBar
    var recyclerViewLayoutManager: GridLayoutManager? = null
    var recycler_review: RecyclerView? = null
    //    var headermanager: HeaderManager? = null
    var relativeHeader: RelativeLayout? = null
    var CCADetailModelArrayList: ArrayList<CCADetailModel>? = ArrayList()
    //    var back: ImageView? = null
    var submitBtn: Button? = null
    var home: ImageView? = null
    var tab_type = "ECAs"
    var extras: Bundle? = null
    var mCCADetailModelArrayList: ArrayList<CCADetailModel>? = ArrayList()
    var mCCAItemIdArray: java.util.ArrayList<String>? = null
    var mCCAItemIdArray1: java.util.ArrayList<CCAEditModel>? = null

    var textViewCCAaItem: TextView? = null
    var cca_details = ""
    var cca_detailsId = "["
    var mCCADetailModels: CCADetailModel? = null
    var survey_satisfation_status = 0
    var currentPage = 0
    var currentPageSurvey = 0
    private val surveySize = 0
    var ccaedit = 0
    var keyy=""
    var pos = -1
    //    var surveyArrayList: java.util.ArrayList<SurveyModel>? = null
//    var surveyQuestionArrayList: java.util.ArrayList<SurveyQuestionsModel>? = null
//    var surveyAnswersArrayList: java.util.ArrayList<SurveyAnswersModel>? = null
//    var mAnswerList: java.util.ArrayList<AnswerSubmitModel>? = null
    var text_content: TextView? = null
    var text_dialog: TextView? = null
    private val surveyEmail = ""
    lateinit var activity: Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ccas_review)
        mContext = this
        activity=this
        titleTextView = findViewById(R.id.heading)
        back = findViewById(R.id.btn_left)
        backRelative = findViewById(R.id.backRelative)
        logoclick = findViewById(R.id.logoClickImgView)
        progressBar = findViewById(R.id.progress)
        extras = intent.extras
        logoclick.setOnClickListener {

            if(PreferenceManager.getkeyvalue(mContext).toString().equals("0"))
            {
                showApilogoAlert(mContext,"Leaving this page will cancel all reserved activities for this schedule. Do you want to continue?","Confirm")

            }
            else
            {
                val mIntent = Intent(mContext, HomeActivity::class.java)
                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

                startActivity(mIntent)
            }

        }
        // ccaedit = intent.getIntExtra("ccaedit",0)
        //   keyy=extras!!.getString("keyvalue")!!

        backRelative.setOnClickListener {
            if(PreferenceManager.getkeyvalue(mContext).toString().equals("0"))
            {
                showApiAlert(mContext,"Leaving this page will cancel all reserved activities for this schedule. Do you want to continue?","Confirm")

            }
            else
            {
                finish()
            }

        }
        if (extras != null) {
//            tab_type = extras!!.getString("tab_type").toString()

            CCADetailModelArrayList=
                extras!!.getSerializable("detail_array") as ArrayList<CCADetailModel>?
           // Log.e("size review", CCADetailModelArrayList!!.size.toString())
        }else{
            CCADetailModelArrayList = AppController.CCADetailModelArrayList
            //Log.e("size review", CCADetailModelArrayList!!.size.toString())
        }

        for (j in 0 until CCADetailModelArrayList!!.size)
        {
           // Log.e("ccadetail model size", CCADetailModelArrayList!!.size.toString())
        }

        relativeHeader = findViewById<View>(R.id.relativeHeader) as RelativeLayout
        recycler_review = findViewById<View>(R.id.recycler_view_cca) as RecyclerView
        textViewCCAaItem = findViewById<View>(R.id.textViewCCAaItem) as TextView
        submitBtn = findViewById<View>(R.id.submitBtn) as Button

        recycler_review!!.setHasFixedSize(true)
        recyclerViewLayoutManager = GridLayoutManager(mContext, 1)
        recycler_review!!.layoutManager = recyclerViewLayoutManager
        mCCADetailModelArrayList = ArrayList<CCADetailModel>()
        mCCAItemIdArray = ArrayList<String>()
        mCCAItemIdArray1 = ArrayList<CCAEditModel>()
        if (PreferenceManager.getStudClassForCCA(mContext).equals("")) {
            textViewCCAaItem!!.text = Html.fromHtml(
                PreferenceManager.getCCATitle(mContext)
                    .toString() + "<br/>" + PreferenceManager.getStudNameForCCA(mContext)+ "<br/>Year Group : " + PreferenceManager.getStudClassForCCA(
                    mContext)
            )
        } else {
            textViewCCAaItem!!.text = Html.fromHtml(
                PreferenceManager.getCCATitle(mContext)
                    .toString() + "<br/>" + PreferenceManager.getStudNameForCCA(mContext) + "<br/>Year Group : " + PreferenceManager.getStudClassForCCA(
                    mContext
                )
            )
        }
        for (i in 0 until AppController.weekList.size) {
            for (j in 0 until CCADetailModelArrayList!!.size) {
                if (AppController.weekList[i].weekDay.equals(
                        CCADetailModelArrayList!![j].day,ignoreCase = true
                    )
                ) {
                   // Log.e("ccadetail model size", CCADetailModelArrayList!!.size.toString())
                    val mCCADetailModel = CCADetailModel()
                    mCCADetailModel.day = CCADetailModelArrayList!![j].day
                    mCCADetailModel.choice1 = CCADetailModelArrayList!![j].choice1
                    mCCADetailModel.choice2 = CCADetailModelArrayList!![j].choice2
                    mCCADetailModel.choice1Id = CCADetailModelArrayList!![j].choice1Id
                    mCCADetailModel.choiceitem1Id = CCADetailModelArrayList!![j].choiceitem1Id

                    mCCADetailModel.choice2Id = CCADetailModelArrayList!![j].choice2Id

                    if(CCADetailModelArrayList!![j].location != null){
                        mCCADetailModel.location = CCADetailModelArrayList!![j].location
                    }else{
                        mCCADetailModel.location = ""
                    }
                    if(CCADetailModelArrayList!![j].location2 != null){
                        mCCADetailModel.location2 = CCADetailModelArrayList!![j].location2
                    }else{
                        mCCADetailModel.location2 = ""
                    }
                    if(CCADetailModelArrayList!![j].description != null){
                        mCCADetailModel.description = CCADetailModelArrayList!![j].description
                    }else{
                        mCCADetailModel.description = ""
                    }
                    if(CCADetailModelArrayList!![j].description2 != null){
                        mCCADetailModel.description2 = CCADetailModelArrayList!![j].description2
                    }else{
                        mCCADetailModel.description2 = ""
                    }

                    for (k in 0 until CCADetailModelArrayList!![j].ccaChoiceModel!!.size)
                        if (CCADetailModelArrayList!![j].choice1.equals(
                                CCADetailModelArrayList!![j].ccaChoiceModel!![k].cca_item_name
                            )
                        ) {
                            if (CCADetailModelArrayList!![j].ccaChoiceModel!![k].cca_item_start_time != null
                                && CCADetailModelArrayList!![j].ccaChoiceModel!![k].cca_item_end_time != null
                            ) {
                                mCCADetailModel.cca_item_start_timechoice1 = CCADetailModelArrayList!![j].ccaChoiceModel!![k].cca_item_start_time
                                mCCADetailModel.cca_item_end_timechoice1 = CCADetailModelArrayList!![j].ccaChoiceModel!![k].cca_item_end_time
                                mCCADetailModel.location = CCADetailModelArrayList!![j].ccaChoiceModel!![k].venue
                                mCCADetailModel.description = CCADetailModelArrayList!![j].ccaChoiceModel!![k].description

                                break
                            }
                        }
                    for (k in 0 until CCADetailModelArrayList!![j]
                        .ccaChoiceModel2!!.size) if (CCADetailModelArrayList!![j]
                            .choice2.equals(
                                CCADetailModelArrayList!![j].ccaChoiceModel2!![k].cca_item_name,ignoreCase = true
                            )
                    ) {
                        if (CCADetailModelArrayList!![j].ccaChoiceModel2!![k]
                                .cca_item_start_time != null && CCADetailModelArrayList!![j].ccaChoiceModel2!![k].cca_item_end_time != null
                        ) {
                            mCCADetailModel.cca_item_start_timechoice2 = CCADetailModelArrayList!![j].ccaChoiceModel2!![k].cca_item_start_time
                            mCCADetailModel.cca_item_end_timechoice2 = CCADetailModelArrayList!![j].ccaChoiceModel2!![k].cca_item_end_time
                            mCCADetailModel.location2 = CCADetailModelArrayList!![j].ccaChoiceModel2!![k].venue
                            mCCADetailModel.description2 = CCADetailModelArrayList!![j].ccaChoiceModel2!![k].description


                            break
                        }
                    }
                    mCCADetailModelArrayList!!.add(mCCADetailModel)
                   // Log.e("detaiol",mCCADetailModel.location.toString())
                    break
                }
            }
        }

        val mCCAsActivityAdapter = CCAfinalReviewAdapter(mContext, mCCADetailModelArrayList!!)
        recycler_review!!.adapter = mCCAsActivityAdapter
        for (j in mCCADetailModelArrayList!!.indices) {
           // Log.e("cca", mCCADetailModelArrayList!![j].choice1.toString())
            if (mCCADetailModelArrayList!!.get(j)
                    .choice1 != null && mCCADetailModelArrayList!![j].choice2 != null
            ) {
                if (!mCCADetailModelArrayList!![j].choice1Id.equals("-541") &&
                    !mCCADetailModelArrayList!![j].choice2Id.equals("-541")
                ) {
                    /* Log.e("1",
                         mCCAItemIdArray!!.add(mCCADetailModelArrayList!![j].choice1Id!!).toString()
                     )*/
                    var temp=CCAEditModel("","")
                    temp.cca_day_details_id= mCCADetailModelArrayList!![j].choice1Id!!
                    temp.update_item_id= mCCADetailModelArrayList!![j].choiceitem1Id!!
                    mCCAItemIdArray1!!.add(temp)
                   // Log.e("Array", mCCAItemIdArray1!!.size.toString())
                    mCCAItemIdArray!!.add(mCCADetailModelArrayList!![j].choice1Id!!)
                    mCCAItemIdArray!!.add(mCCADetailModelArrayList!![j].choice2Id!!)
                    //mCCAItemIdArray1!!.add(mCCADetailModelArrayList!![j].choice1Id!!,mCCADetailModelArrayList!![j].choiceitem1Id!!)
                } else if (!mCCADetailModelArrayList!![j].choice1Id.equals("-541")
                ) {
                    /* Log.e("2",
                         mCCAItemIdArray!!.add(mCCADetailModelArrayList!![j].choice1Id!!).toString()
                     )*/
                    var temp=CCAEditModel("","")
                    temp.cca_day_details_id= mCCADetailModelArrayList!![j].choice1Id!!
                    temp.update_item_id= mCCADetailModelArrayList!![j].choiceitem1Id!!
                    mCCAItemIdArray1!!.add(temp)
                  //  Log.e("Array", mCCAItemIdArray1!!.size.toString())

                    mCCAItemIdArray!!.add(mCCADetailModelArrayList!![j].choice1Id!!)
                } else if (!mCCADetailModelArrayList!![j].choice2Id.equals("-541")
                ) {
                    /* Log.e("13",
                         mCCAItemIdArray!!.add(mCCADetailModelArrayList!![j].choice1Id!!).toString()
                     )*/
                    var temp=CCAEditModel("","")
                    temp.cca_day_details_id= mCCADetailModelArrayList!![j].choice1Id!!
                    temp.update_item_id= mCCADetailModelArrayList!![j].choiceitem1Id!!
                    mCCAItemIdArray1!!.add(temp)
                   // Log.e("Array", mCCAItemIdArray1!!.size.toString())

                    mCCAItemIdArray!!.add(mCCADetailModelArrayList!![j].choice2Id!!)
                }
            } else if (mCCADetailModelArrayList!![j].choice1 != null) {
                if (!mCCADetailModelArrayList!![j].choice1Id.equals("-541")) {
                    /* Log.e("14",
                         mCCAItemIdArray!!.add(mCCADetailModelArrayList!![j].choice1Id!!).toString()
                     )*/
                    var temp=CCAEditModel("","")
                    temp.cca_day_details_id= mCCADetailModelArrayList!![j].choice1Id!!
                    temp.update_item_id= mCCADetailModelArrayList!![j].choiceitem1Id!!
                    mCCAItemIdArray1!!.add(temp)
                   // Log.e("Array", mCCAItemIdArray1!!.size.toString())

                    mCCAItemIdArray!!.add(mCCADetailModelArrayList!![j].choice1Id!!)
                }
            } else if (mCCADetailModelArrayList!![j].choice2 != null) {
                if (!mCCADetailModelArrayList!![j].choice2Id.equals("-541")) {
                    /* Log.e("15",
                         mCCAItemIdArray!!.add(mCCADetailModelArrayList!![j].choice1Id!!).toString()
                     )*/
                    var temp=CCAEditModel("","")
                    temp.cca_day_details_id= mCCADetailModelArrayList!![j].choice1Id!!
                    temp.update_item_id= mCCADetailModelArrayList!![j].choiceitem1Id!!
                    mCCAItemIdArray1!!.add(temp)
                  //  Log.e("Array", mCCAItemIdArray1!!.size.toString())

                    mCCAItemIdArray!!.add(mCCADetailModelArrayList!![j].choice2Id!!)
                }
            }
        }

        if (mCCAItemIdArray!!.size == 0) {
            cca_detailsId += "]}"
        }
        for (i in mCCAItemIdArray!!.indices) {
           // Log.e("items", mCCAItemIdArray!![i].toString())
            if (mCCAItemIdArray!!.size - 1 == 0) {
                cca_detailsId += "\"" + mCCAItemIdArray!![i] + "\"]}"
            } else if (i == mCCAItemIdArray!!.size - 1) {
                cca_detailsId += mCCAItemIdArray!![i] + "\"]}"
            } else if (i == 0) {
                cca_detailsId += "\"" + mCCAItemIdArray!![i] + "\",\""
            } else {
                cca_detailsId += mCCAItemIdArray!![i] + "\",\""
            }
        }
        cca_details = "{\"cca_days_id\":\"" + PreferenceManager.getCCAItemId(mContext)
            .toString() + "\",\"student_id\":\"" + PreferenceManager.getStudIdForCCA(mContext)
            .toString() + "\",\"users_id\":\"" + PreferenceManager.getUserCode(mContext)
            .toString() + "\",\"cca_days_details_id\":" + cca_detailsId

     //   Log.e("cca_details",cca_details)
      //  Log.e("cca_detailsId",cca_detailsId)
        val gson = Gson()
        val jsonString = gson.toJson(mCCAItemIdArray1)
       // Log.e("jsonString",jsonString)
        submitBtn!!.setOnClickListener(View.OnClickListener {
            showDialogReviewSubmit(
                mContext as Activity,
                "Confirm",
                "Do you want to confirm this Enrichment?",
                R.drawable.questionmark_icon,
                R.drawable.round,jsonString
            )
        })
    }


    private fun showDialogReviewSubmit(
        activity: Activity,
        msgHead: String,
        msg: String,
        ico: Int,
        bgIcon: Int,
        jsonString: String
    ) {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_ok_cancel)
        val icon = dialog.findViewById<View>(R.id.iconImageView) as ImageView
        icon.setBackgroundResource(bgIcon)
        icon.setImageResource(ico)
        val text = dialog.findViewById<View>(R.id.text_dialog) as TextView
        val textHead = dialog.findViewById<View>(R.id.alertHead) as TextView
        text.text = msg
        textHead.text = msgHead

        val dialogButton = dialog.findViewById<View>(R.id.btn_Ok) as Button
        dialogButton.setOnClickListener {

            var internetCheck = ConstantFunctions.internetCheck(mContext)
            if (internetCheck) {

                if(PreferenceManager.getkeyvalue(mContext).toString().equals("0"))
                {
                    ccaSubmitAPI()
                }
                else
                {
                    ccaeditSubmitApi(jsonString)
                }


            } else {
                DialogFunctions.showInternetAlertDialog(mContext)

            }
            dialog.dismiss()

        }
        val dialogButtonCancel = dialog.findViewById<View>(R.id.btn_Cancel) as Button
        dialogButtonCancel.setOnClickListener { dialog.dismiss() }
        dialog.show()

    }

    private fun ccaeditSubmitApi(jsonString: String) {


        var model= CCAEditRequestModel(PreferenceManager.getStudIdForCCA(mContext).toString(),
            PreferenceManager.getCCAItemId(mContext).toString(),jsonString)
        val token = PreferenceManager.getaccesstoken(mContext)
        val call: Call<CCASubmitResponseModel> =
            ApiClient(mContext).getClient.ccaedit( model,"Bearer $token")
        progressBar.visibility = View.VISIBLE
        call.enqueue(object : Callback<CCASubmitResponseModel> {
            override fun onResponse(
                call: Call<CCASubmitResponseModel>,
                response: Response<CCASubmitResponseModel>
            ) {
                progressBar.visibility = View.GONE
                if (response.isSuccessful){
                    if (response.body() != null){
                        if (response.body()!!.status!!.equals(100)){

//                            val survey: Int = secobj.optInt("survey")
                            showDialogAlert(
                                mContext as Activity,
                                "Success",
                                "Your current Enrichment options are selected and confirmed. If you need to make any changes, Please use the edit option.",
                                R.drawable.tickicon,
                                R.drawable.round,
                            )

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
                progressBar.visibility = View.GONE
                ConstantFunctions.showDialogueWithOk(mContext,getString(R.string.common_error),"Alert")
            }

        })
    }

    private fun ccaSubmitAPI() {
       // Log.e("stud", PreferenceManager.getStudIdForCCA(mContext).toString())
       // Log.e("day",PreferenceManager.getCCAItemId(mContext).toString())
       // Log.e("details",cca_detailsId)
        val ccaDetail: ArrayList<String> = ArrayList()
       // Log.e("arraydata", mCCAItemIdArray.toString())
        for (i in mCCAItemIdArray!!.indices){
//            if ( i != 0) {
            if(!mCCAItemIdArray!![i].equals("-541"))
                ccaDetail.add(mCCAItemIdArray!![i].toString())
//            }

        }
       // Log.e("details1",ccaDetail.toString())

        var model= CCASumbitRequestModel(PreferenceManager.getStudIdForCCA(mContext).toString(),
            PreferenceManager.getCCAItemId(mContext).toString(),ccaDetail.toString()
        )
        val token = PreferenceManager.getaccesstoken(mContext)
        val call: Call<CCASubmitResponseModel> =
            ApiClient(mContext).getClient.ccaSubmit( model,"Bearer $token")
        progressBar.visibility = View.VISIBLE
        call.enqueue(object : Callback<CCASubmitResponseModel> {
            override fun onResponse(
                call: Call<CCASubmitResponseModel>,
                response: Response<CCASubmitResponseModel>
            ) {
                progressBar.visibility = View.GONE
                if (response.isSuccessful){
                    if (response.body() != null){
                        if (response.body()!!.status!!.equals(100)){

//                            val survey: Int = secobj.optInt("survey")
                            showDialogAlert(
                                mContext as Activity,
                                "Success",
                                "Your current Enrichment options are selected and confirmed. If you need to make any changes, Please use the edit option",
                                R.drawable.tickicon,
                                R.drawable.round,
                            )

                        }
                        else if (response.body()!!.status!!.equals(124))
                        {
                            showDialogAlert(
                                mContext as Activity,
                                "Alert",
                                "Sorry , You Must Select Activities or None For All Days",
                                R.drawable.tickicon,
                                R.drawable.round,
                            )

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
                progressBar.visibility = View.GONE
                ConstantFunctions.showDialogueWithOk(mContext,getString(R.string.common_error),"Alert")
            }

        })
    }

    fun showDialogAlert(
        activity: Activity?,
        msgHead: String?,
        msg: String?,
        ico: Int,
        bgIcon: Int,
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
//            if (survey == 1) {
//                callSurveyApi()
//            } else {
            val intent = Intent(mContext, CCA_Activity_New::class.java)
            //  PreferenceManager.setStudIdForCCA(mContext!!, "")

            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
//            }
        }
        dialog.show()
    }
    private fun showApiAlert(
        context: Context,
        message: String,
        msgHead: String,
    ){
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_ok_cancel)
        val icon = dialog.findViewById(R.id.iconImageView) as ImageView
        /* icon.setBackgroundResource(bgIcon)
         icon.setImageResource(ico)*/
        val text = dialog.findViewById(R.id.text_dialog) as TextView
        val textHead = dialog.findViewById(R.id.alertHead) as TextView
        text.text = message
        textHead.text = msgHead
        val dialogButton = dialog.findViewById(R.id.btn_Ok) as Button
        dialogButton.setOnClickListener {
            if (ConstantFunctions.internetCheck(mContext))
            {
                ccacancelAPI()
            }
            else
            {
                DialogFunctions.showInternetAlertDialog(mContext)
            }
            dialog.dismiss()
        }
        val dialogButtonCancel = dialog.findViewById(R.id.btn_Cancel) as Button
        dialogButtonCancel.setOnClickListener {

            dialog.dismiss() }
        dialog.show()
    }

    private fun ccacancelAPI() {

        var model= CCACancelModel(PreferenceManager.getStudIdForCCA(mContext).toString(),
            PreferenceManager.getCCAItemId(mContext).toString()
        )
        val token = PreferenceManager.getaccesstoken(mContext)
        val call: Call<CCASubmitResponseModel> =
            ApiClient(mContext).getClient.ccareservecancel( model,"Bearer $token")
        progressBar.visibility = View.VISIBLE
        call.enqueue(object : Callback<CCASubmitResponseModel> {
            override fun onResponse(
                call: Call<CCASubmitResponseModel>,
                response: Response<CCASubmitResponseModel>
            ) {
                progressBar.visibility = View.GONE
                if (response.isSuccessful){
                    if (response.body() != null){
                        if (response.body()!!.status!!.equals(100)){

//                            val survey: Int = secobj.optInt("survey")
                            val mIntent = Intent(mContext, CCA_Activity_New::class.java)
                            mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            startActivity(mIntent)

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
                progressBar.visibility = View.GONE
                ConstantFunctions.showDialogueWithOk(mContext,getString(R.string.common_error),"Alert")
            }

        })
    }
    override fun onBackPressed() {

        if(PreferenceManager.getkeyvalue(mContext).toString().equals("0"))
        {
            showApiAlert(mContext,"Leaving this page will cancel all reserved activities for this schedule. Do you want to continue?","Confirm")

        }
        else
        {
            finish()
        }

    }



    private fun showApilogoAlert(
        context: Context,
        message: String,
        msgHead: String,
    ){
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_ok_cancel)
        val icon = dialog.findViewById(R.id.iconImageView) as ImageView
        /* icon.setBackgroundResource(bgIcon)
         icon.setImageResource(ico)*/
        val text = dialog.findViewById(R.id.text_dialog) as TextView
        val textHead = dialog.findViewById(R.id.alertHead) as TextView
        text.text = message
        textHead.text = msgHead
        val dialogButton = dialog.findViewById(R.id.btn_Ok) as Button
        dialogButton.setOnClickListener {
            if (ConstantFunctions.internetCheck(mContext))
            {
                ccacancellogoAPI()
            }
            else
            {
                DialogFunctions.showInternetAlertDialog(mContext)
            }
            dialog.dismiss()
        }
        val dialogButtonCancel = dialog.findViewById(R.id.btn_Cancel) as Button
        dialogButtonCancel.setOnClickListener {

            dialog.dismiss() }
        dialog.show()
    }

    private fun ccacancellogoAPI() {

        var model= CCACancelModel(PreferenceManager.getStudIdForCCA(mContext).toString(),
            PreferenceManager.getCCAItemId(mContext).toString()
        )
        val token = PreferenceManager.getaccesstoken(mContext)
        val call: Call<CCASubmitResponseModel> =
            ApiClient(mContext).getClient.ccareservecancel( model,"Bearer $token")
        progressBar.visibility = View.VISIBLE
        call.enqueue(object : Callback<CCASubmitResponseModel> {
            override fun onResponse(
                call: Call<CCASubmitResponseModel>,
                response: Response<CCASubmitResponseModel>
            ) {
                progressBar.visibility = View.GONE
                if (response.isSuccessful){
                    if (response.body() != null){
                        if (response.body()!!.status!!.equals(100)){

//                            val survey: Int = secobj.optInt("survey")
                            val mIntent = Intent(mContext, HomeActivity::class.java)
                            mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            startActivity(mIntent)

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
                progressBar.visibility = View.GONE
                ConstantFunctions.showDialogueWithOk(mContext,getString(R.string.common_error),"Alert")
            }

        })
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