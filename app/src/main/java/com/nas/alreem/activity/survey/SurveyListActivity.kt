package com.nas.alreem.activity.survey

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.nas.alreem.R
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.activity.login.model.SignUpResponseModel
import com.nas.alreem.activity.survey.adapter.SurveyListAdapter
import com.nas.alreem.activity.survey.adapter.SurveyQuestionPagerAdapter
import com.nas.alreem.activity.survey.model.*
import com.nas.alreem.constants.*
import com.nas.alreem.fragment.payments.model.SendEmailApiModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SurveyListActivity: AppCompatActivity(){
    lateinit var mContext: Context
    lateinit var heading: TextView
    lateinit var logoClickImgView: ImageView
    lateinit var progressDialogAdd: ProgressBar
    lateinit var backRelative: RelativeLayout
    lateinit var mListView: RecyclerView
    lateinit var surveyDetailQuestionsArrayList:ArrayList<SurveyQuestionsModel>
    lateinit var surveyArrayList:ArrayList<SurveyListDataModel>
    lateinit var  mAnswerList:ArrayList<SurveySubmitDataModel>
    lateinit var surveyAnswersArrayList: ArrayList<SurveyOfferedAnswersModel>


    lateinit var survey_name:String
   var survey_ID:Int=0
    lateinit var survey_image:String
    lateinit var survey_title:String
    lateinit var survey_description:String
    lateinit var survey_contact_email:String
    var currentPageSurvey=0
    var survey_satisfation_status = 0
    private var surveySize = 1

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey_list)
        mContext=this
        initUI()
        if (ConstantFunctions.internetCheck(mContext))
        {
            progressDialogAdd.visibility= View.VISIBLE
            callSurveyList()
        }
        else
        {
            DialogFunctions.showInternetAlertDialog(mContext)
        }

    }
    fun initUI()
    {

        mListView=findViewById(R.id.mListView)
        var linearLayoutManager = LinearLayoutManager(mContext)
        mListView.layoutManager = linearLayoutManager
        mListView.itemAnimator = DefaultItemAnimator()
        progressDialogAdd=findViewById(R.id.progressDialogAdd)
        heading=findViewById(R.id.heading)
        logoClickImgView=findViewById(R.id.logoClickImgView)
        backRelative=findViewById(R.id.backRelative)
        heading.text="Survey"
        logoClickImgView.setOnClickListener(View.OnClickListener {
            val intent = Intent(mContext, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        })

        backRelative.setOnClickListener(View.OnClickListener {
            finish()
        })
        mListView.addOnItemClickListener(object : OnItemClickListener {
            @SuppressLint("SimpleDateFormat", "SetTextI18n")
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onItemClicked(position: Int, view: View)
            {

                if (ConstantFunctions.internetCheck(mContext))
                {
                    callSurveyDetailApi(surveyArrayList.get(position).id.toString())
                }
                else
                {
                    DialogFunctions.showInternetAlertDialog(mContext)
                }


            }
        })

    }

    fun callSurveyList()
    {
        progressDialogAdd.visibility= View.VISIBLE
        surveyArrayList= ArrayList()
        val call: Call<SurveyListResponseModel> = ApiClient.getClient.surveyList("Bearer "+ PreferenceManager.getaccesstoken(mContext))
        call.enqueue(object : Callback<SurveyListResponseModel> {
            override fun onFailure(call: Call<SurveyListResponseModel>, t: Throwable) {
                progressDialogAdd.visibility= View.GONE
            }
            override fun onResponse(call: Call<SurveyListResponseModel>, response: Response<SurveyListResponseModel>) {
                val responsedata = response.body()
                progressDialogAdd.visibility= View.GONE
                if (responsedata != null) {
                    try {

                        if (response.body()!!.status==100)
                        {

                            if (response.body()!!.responseArray!!.data.size>0)
                            {
                                surveyArrayList=response.body()!!.responseArray!!.data
                                mListView.adapter = SurveyListAdapter(response.body()!!.responseArray!!.data, mContext)
                            }
                            else{
                                DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert), "No Data Found!", mContext)
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

    fun callSurveyDetailApi(surveyID:String)
    {
        surveyDetailQuestionsArrayList= ArrayList()
        currentPageSurvey=0
        progressDialogAdd.visibility= View.VISIBLE
        var model=SurveyDetailApiModel(surveyID)
        val call: Call<SurveyDetailResponseModel> = ApiClient.getClient.surveyDetail(model,"Bearer "+ PreferenceManager.getaccesstoken(mContext))
        call.enqueue(object : Callback<SurveyDetailResponseModel> {
            override fun onFailure(call: Call<SurveyDetailResponseModel>, t: Throwable) {
                progressDialogAdd.visibility= View.GONE
            }
            override fun onResponse(call: Call<SurveyDetailResponseModel>, response: Response<SurveyDetailResponseModel>) {
                val responsedata = response.body()
                progressDialogAdd.visibility= View.GONE
                if (responsedata != null) {
                    try {

                        if (response.body()!!.status==100)
                        {
                            survey_name=response.body()!!.responseArray!!.data!!.survey_name
                            survey_ID=response.body()!!.responseArray!!.data!!.id
                            survey_image=response.body()!!.responseArray!!.data!!.image
                            survey_title=response.body()!!.responseArray!!.data!!.title
                            survey_description=response.body()!!.responseArray!!.data!!.description
                            survey_contact_email=response.body()!!.responseArray!!.data!!.contact_email
                          //  surveyDetailQuestionsArrayList= response.body()!!.responseArray!!.data!!.questions!!
                            if (response.body()!!.responseArray!!.data!!.questions!!.size > 0) {

                                for ( j in  response.body()!!.responseArray!!.data!!.questions!!.indices)
                                {
                                    val mModel = SurveyQuestionsModel()
                                    mModel.id=(response.body()!!.responseArray!!.data!!.questions!!.get(j).id)
                                    mModel.question=(response.body()!!.responseArray!!.data!!.questions!!.get(j).question)
                                    mModel.answer_type=(response.body()!!.responseArray!!.data!!.questions!!.get(j).answer_type)
                                    mModel.answer=(response.body()!!.responseArray!!.data!!.questions!!.get(j).answer)
                                    surveyAnswersArrayList = ArrayList()
                                    if(response.body()!!.responseArray!!.data!!.questions!!.get(j).offered_answers!!.size>0)
                                    {
                                        for (k in response.body()!!.responseArray!!.data!!.questions!!.get(j).offered_answers!!.indices)
                                        {
                                            val nModel = SurveyOfferedAnswersModel()
                                            nModel.id=(response.body()!!.responseArray!!.data!!.questions!!.get(j).offered_answers!!.get(k).id)
                                            nModel.answer=(response.body()!!.responseArray!!.data!!.questions!!.get(j).offered_answers!!.get(k).answer)
                                            nModel.label=(response.body()!!.responseArray!!.data!!.questions!!.get(j).offered_answers!!.get(k).label)

                                            if (response.body()!!.responseArray!!.data!!.questions!!.get(j).answer.equals(
                                                    response.body()!!.responseArray!!.data!!.questions!!.get(j).offered_answers!!.get(k).id.toString())
                                            ) {
                                                nModel.is_clicked=(true)
                                            } else {
                                                nModel.is_clicked=(false)
                                            }

                                            surveyAnswersArrayList.add(nModel)
                                        }
                                        if (surveyAnswersArrayList.size > 0) {
                                            var isPositionClicked = false
                                            var position = -1
                                            for (m in surveyAnswersArrayList.indices) {
                                                if (surveyAnswersArrayList[m].is_clicked) {
                                                    isPositionClicked = true
                                                    position = m
                                                }
                                            }
                                            if (isPositionClicked) {

                                                if (position == 0) {
                                                    surveyAnswersArrayList[0].is_clicked0=(true)
                                                } else if (position == 1) {
                                                    surveyAnswersArrayList[0].is_clicked0=(true)
                                                    surveyAnswersArrayList[1].is_clicked0=(true)
                                                } else if (position == 2) {
                                                    surveyAnswersArrayList[0].is_clicked0=(true)
                                                    surveyAnswersArrayList[1].is_clicked0=(true)
                                                    surveyAnswersArrayList[2].is_clicked0=(true)
                                                } else if (position == 3) {
                                                    surveyAnswersArrayList[0].is_clicked0=(true)
                                                    surveyAnswersArrayList[1].is_clicked0=(true)
                                                    surveyAnswersArrayList[2].is_clicked0=(true)
                                                    surveyAnswersArrayList[3].is_clicked0=(true)
                                                } else if (position == 4) {
                                                    surveyAnswersArrayList[0].is_clicked0=(true)
                                                    surveyAnswersArrayList[1].is_clicked0=(true)
                                                    surveyAnswersArrayList[2].is_clicked0=(true)
                                                    surveyAnswersArrayList[3].is_clicked0=(true)
                                                    surveyAnswersArrayList[4].is_clicked0=(true)
                                                } else {
                                                    surveyAnswersArrayList[0].is_clicked0=(false)
                                                    surveyAnswersArrayList[1].is_clicked0=(false)
                                                    surveyAnswersArrayList[2].is_clicked0=(false)
                                                    surveyAnswersArrayList[3].is_clicked0=(false)
                                                    surveyAnswersArrayList[4].is_clicked0=(false)
                                                }
                                            }
                                        }
                                    }
                                    mModel.offered_answers=(surveyAnswersArrayList)
                                    surveyDetailQuestionsArrayList.add(mModel)
                                }
                            }

                            showWelcomeSurveyDialog()

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

    fun callSurveySubmitApi(
        survey_ID: Int,
        JSON_STRING: String,
        activity: Any?,
        surveyArrayList: ArrayList<SurveyListDataModel>,
        isThankyou: Boolean,
        status: Int,
        mAnswerList: ArrayList<SurveySubmitDataModel>
    )
    {
        progressDialogAdd.visibility= View.VISIBLE

        surveyDetailQuestionsArrayList= ArrayList()
        currentPageSurvey=0

        val paramObject = JsonObject().apply {
            addProperty("data", JSON_STRING)
            addProperty("survey_id",survey_ID)
            addProperty("survey_satisfaction_status",status)

        }


        var model=SurveySubmitApiModel(survey_ID.toString(), status.toString(),mAnswerList)


         val call: Call<SurveySubmitResponseModel> = ApiClient.getClient.surveysubmit(model,"Bearer "+ PreferenceManager.getaccesstoken(mContext))
         call.enqueue(object : Callback<SurveySubmitResponseModel> {
             override fun onFailure(call: Call<SurveySubmitResponseModel>, t: Throwable) {
                 progressDialogAdd.visibility= View.GONE
             }
             override fun onResponse(call: Call<SurveySubmitResponseModel>, response: Response<SurveySubmitResponseModel>) {
                 val responsedata = response.body()
                 progressDialogAdd.visibility= View.GONE
                 if (responsedata != null) {
                     try {

                         if (response.body()!!.status==100)
                         {

                             showSurveyThankYouDialog(mContext as Activity, isThankyou)
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

    fun showSurveyThankYouDialog(
        activity: Activity?,
      //  surveyArrayList: ArrayList<SurveyModel?>?,
        isThankyou: Boolean
    ) {
        val dialog = Dialog(activity!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_survey_thank_you)
        survey_satisfation_status = 0
        //callSurveySubmitApi(URL_SURVEY_SUBMIT,surveyId,jsonData,getActivity(),surveyArrayList,isThankyou,survey_satisfation_status,dialog);
        val btn_Ok = dialog.findViewById<View>(R.id.btn_Ok) as Button
        btn_Ok.setOnClickListener {
            if (isThankyou) {
                showWelcomeSurveyDialog()
            } else {
            }
            dialog.dismiss()
        }
        val emailImg = dialog.findViewById<View>(R.id.emailImg) as ImageView
        if (survey_contact_email.equals("", ignoreCase = true)) {
            emailImg.visibility = View.GONE
        } else {
            emailImg.visibility = View.VISIBLE
        }
        emailImg.setOnClickListener {
            showSendEmailDialog()
        }
        dialog.show()
    }

    fun showWelcomeSurveyDialog()
    {
        val dialog = Dialog(mContext)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_survey_wlcome)
        var bannerImg = dialog.findViewById(R.id.bannerImg) as ImageView
        var titleTxt = dialog.findViewById(R.id.titleTxt) as TextView
        var descriptionTxt = dialog.findViewById(R.id.descriptionTxt) as TextView
        var skipBtn = dialog.findViewById(R.id.skipBtn) as Button
        var startNowBtn = dialog.findViewById(R.id.startNowBtn) as Button

        titleTxt.text=survey_title
        descriptionTxt.text=survey_description
        if (survey_image.equals(""))
        {
            bannerImg.setImageResource(R.drawable.survey)
        }
        else{
            Glide.with(mContext).load(survey_image).centerCrop().into(bannerImg)
        }

        skipBtn.setOnClickListener()
        {
           showCloseSurveyDialog(dialog)
        }

        startNowBtn.setOnClickListener()
        {
            if(surveyDetailQuestionsArrayList.size>0)
            {
                showQuestionAnswerSurvey()
                dialog.dismiss()
            }
            else{
                DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert), "No Survey Questions Available.", mContext)
                dialog.dismiss()
            }

        }
        dialog.show()
    }


    fun showCloseSurveyDialog(dialogW: Dialog)
    {
        val dialog = Dialog(mContext)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_survey_close)
        var text_dialog = dialog.findViewById(R.id.text_dialog) as TextView
        var btn_Ok = dialog.findViewById(R.id.btn_Ok) as Button
        var btn_Cancel = dialog.findViewById(R.id.btn_Cancel) as Button
        text_dialog.text="Are you sure you want to close this survey."

        btn_Ok.setOnClickListener()
        {
            dialogW.dismiss()
            dialog.dismiss()
        }


        btn_Cancel.setOnClickListener()
        {
            dialog.dismiss()
        }
        dialog.show()
    }


    fun showQuestionAnswerSurvey()
    {
        val dialog = Dialog(mContext)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_question_answer_survey)
        var currentQntTxt = dialog.findViewById(R.id.currentQntTxt) as TextView
        var questionCount = dialog.findViewById(R.id.questionCount) as TextView
        var surveyName = dialog.findViewById(R.id.surveyName) as TextView
        var nxtQnt = dialog.findViewById(R.id.nxtQnt) as TextView
        var closeImg = dialog.findViewById(R.id.closeImg) as ImageView
        var emailImg = dialog.findViewById(R.id.emailImg) as ImageView
        var previousBtn = dialog.findViewById(R.id.previousBtn) as ImageView
        var nextQuestionBtn = dialog.findViewById(R.id.nextQuestionBtn) as ImageView
        var progressBar = dialog.findViewById(R.id.progressBar) as ProgressBar
        var surveyPager = dialog.findViewById(R.id.surveyPager) as ViewPager

        closeImg.setOnClickListener(View.OnClickListener {
            showCloseSurveyDialog(dialog)
        })
        if (surveyDetailQuestionsArrayList.size>9)
        {
            currentQntTxt.text="01"
            questionCount.text="/"+surveyDetailQuestionsArrayList.size.toString()
        }
        else{
            currentQntTxt.text="01"
            questionCount.text="/0"+surveyDetailQuestionsArrayList.size.toString()
        }

        surveyName.text=survey_name

        if (survey_contact_email.equals(""))
        {
            emailImg.visibility=View.GONE
        }
        else{
            emailImg.visibility=View.VISIBLE
        }
        emailImg.setOnClickListener(View.OnClickListener {

            showSendEmailDialog()
        })


        progressBar.max=surveyDetailQuestionsArrayList.size
        progressBar.progressDrawable.setColorFilter(mContext.resources.getColor(R.color.rel_one), PorterDuff.Mode.SRC_IN)

        currentPageSurvey=1
        surveyPager.setCurrentItem(currentPageSurvey-1)
        progressBar.setProgress(currentPageSurvey)
        var adapterPager=SurveyQuestionPagerAdapter(mContext,surveyDetailQuestionsArrayList)
        surveyPager.adapter=adapterPager

        if (currentPageSurvey==surveyDetailQuestionsArrayList.size)
        {
            previousBtn.visibility=View.INVISIBLE
            nextQuestionBtn.visibility=View.INVISIBLE
            nxtQnt.visibility=View.VISIBLE
        }
        else{
            if (currentPageSurvey==1)
            {
                previousBtn.visibility=View.INVISIBLE
                nextQuestionBtn.visibility=View.VISIBLE
                nxtQnt.visibility=View.INVISIBLE
            }
            else{
                previousBtn.visibility=View.INVISIBLE
                nextQuestionBtn.visibility=View.VISIBLE
                nxtQnt.visibility=View.INVISIBLE
            }
        }

        nextQuestionBtn.setOnClickListener(View.OnClickListener {

            if (currentPageSurvey==surveyDetailQuestionsArrayList.size)
            {

            }
            else{
                currentPageSurvey++
                progressBar.setProgress(currentPageSurvey)
                surveyPager.setCurrentItem(currentPageSurvey-1)
                if (currentPageSurvey==surveyDetailQuestionsArrayList.size)
                {
                    nextQuestionBtn.visibility=View.INVISIBLE
                    previousBtn.visibility=View.VISIBLE
                    nxtQnt.visibility=View.VISIBLE
                }
                else{
                    nextQuestionBtn.visibility=View.VISIBLE
                    previousBtn.visibility=View.VISIBLE
                    nxtQnt.visibility=View.INVISIBLE
                }

            }

            if (surveyDetailQuestionsArrayList.size>9)
            {
                if (currentPageSurvey<9)
                {
                    currentQntTxt.text="0"+currentPageSurvey.toString()
                    questionCount.text="/"+surveyDetailQuestionsArrayList.size.toString()
                }
                else{
                    currentQntTxt.text=currentPageSurvey.toString()
                    questionCount.text="/"+surveyDetailQuestionsArrayList.size.toString()
                }
            }
            else{
                if (currentPageSurvey<9)
                {
                    currentQntTxt.text="0"+currentPageSurvey.toString()
                    questionCount.text="/"+"0"+surveyDetailQuestionsArrayList.size.toString()
                }
                else{
                    currentQntTxt.text=currentPageSurvey.toString()
                    questionCount.text="/"+"0"+surveyDetailQuestionsArrayList.size.toString()
                }
            }
        })


        previousBtn.setOnClickListener(View.OnClickListener {

            if (currentPageSurvey==1)
            {
                previousBtn.visibility=View.INVISIBLE
                nxtQnt.visibility=View.INVISIBLE
                if (currentPageSurvey==surveyDetailQuestionsArrayList.size)
                {
                    nxtQnt.visibility=View.VISIBLE
                }
                else{
                    nxtQnt.visibility=View.INVISIBLE
                }
            }
            else{
                currentPageSurvey--
                progressBar.setProgress(currentPageSurvey)
                surveyPager.setCurrentItem(currentPageSurvey-1)
                if (currentPageSurvey==surveyDetailQuestionsArrayList.size)
                {
                    nextQuestionBtn.visibility=View.INVISIBLE
                    previousBtn.visibility=View.VISIBLE
                    nxtQnt.visibility=View.VISIBLE
                }
                else
                {
                    if(currentPageSurvey==1)
                    {
                        nextQuestionBtn.visibility=View.VISIBLE
                        previousBtn.visibility=View.INVISIBLE
                        nxtQnt.visibility=View.INVISIBLE
                    }
                    else
                    {
                        nextQuestionBtn.visibility=View.VISIBLE
                        previousBtn.visibility=View.VISIBLE
                        nxtQnt.visibility=View.INVISIBLE
                    }

                }

            }

            if (surveyDetailQuestionsArrayList.size>9)
            {
                if (currentPageSurvey<9)
                {
                    currentQntTxt.text="0"+currentPageSurvey.toString()
                    questionCount.text="/"+surveyDetailQuestionsArrayList.size.toString()
                }
                else{
                    currentQntTxt.text=currentPageSurvey.toString()
                    questionCount.text="/"+surveyDetailQuestionsArrayList.size.toString()
                }
            }
            else{
                if (currentPageSurvey<9)
                {
                    currentQntTxt.text="0"+currentPageSurvey.toString()
                    questionCount.text="/"+"0"+surveyDetailQuestionsArrayList.size.toString()
                }
                else{
                    currentQntTxt.text=currentPageSurvey.toString()
                    questionCount.text="/"+"0"+surveyDetailQuestionsArrayList.size.toString()
                }

            }
        })

        nxtQnt.setOnClickListener {
            var isFound = false
            var pos = -1
            var emptyvalue = 0
            for (i in surveyDetailQuestionsArrayList.indices) {
                if (surveyDetailQuestionsArrayList.get(i).answer.equals("")) {
                    emptyvalue = emptyvalue + 1
                    if (!isFound) {
                        isFound = true
                        pos = i
                    }
                }
            }
            if (isFound) {
                mAnswerList = ArrayList<SurveySubmitDataModel>()
                for (k in surveyDetailQuestionsArrayList.indices) {
                    val model = SurveySubmitDataModel()
                    model.question_id=(surveyDetailQuestionsArrayList.get(k).id.toString())
                    model.answer_id=(surveyDetailQuestionsArrayList.get(k).answer)
                    mAnswerList.add(model)
                }
                val gson = Gson()
                val PassportArray = ArrayList<String>()
                for (i in mAnswerList.indices) {
                    val nmodel = SurveySubmitDataModel()
                    nmodel.answer_id=(mAnswerList.get(i).answer_id)
                    nmodel.question_id=(mAnswerList.get(i).question_id)
                    val json = gson.toJson(nmodel)
                    PassportArray.add(i, json)
                }
                val JSON_STRING = "" + PassportArray + ""
                if (emptyvalue == surveyDetailQuestionsArrayList.size) {
                    val isEmpty = true
                    showSurveyContinueDialog(
                        mContext,
                        survey_ID,
                        JSON_STRING,
                        surveyArrayList,
                        progressBar,
                        surveyPager,
                        surveyDetailQuestionsArrayList,
                        previousBtn,
                        nextQuestionBtn,
                        nxtQnt,
                        currentQntTxt,
                        questionCount,
                        pos,
                        dialog,
                        isEmpty
                    )
                } else {
                    val isEmpty = false
                    showSurveyContinueDialog(
                        mContext,
                        survey_ID,
                        JSON_STRING,
                        surveyArrayList,
                        progressBar,
                        surveyPager,
                        surveyDetailQuestionsArrayList,
                        previousBtn,
                        nextQuestionBtn,
                        nxtQnt,
                        currentQntTxt,
                        questionCount,
                        pos,
                        dialog,
                        isEmpty
                    )
                }
            } else {
                surveySize = surveySize - 1
                if (surveySize <= 0) {
                    mAnswerList = ArrayList<SurveySubmitDataModel>()
                    for (k in surveyDetailQuestionsArrayList.indices) {
                        val model = SurveySubmitDataModel()
                        model.question_id=(surveyDetailQuestionsArrayList.get(k).id.toString())
                        model.answer_id=(surveyDetailQuestionsArrayList.get(k).answer)
                        mAnswerList.add(model)
                    }
                    val gson = Gson()
                    val PassportArray = java.util.ArrayList<String>()
                    for (i in mAnswerList.indices) {
                        val nmodel = SurveySubmitDataModel()
                        nmodel.answer_id=(mAnswerList.get(i).answer_id)
                        nmodel.question_id=(mAnswerList.get(i).question_id)
                        val json = gson.toJson(nmodel)
                        PassportArray.add(i, json)
                    }
                    val JSON_STRING = "" + PassportArray + ""
                    dialog.dismiss()
                    callSurveySubmitApi(
                        survey_ID,
                        JSON_STRING,
                        mContext,
                        surveyArrayList,
                        false,
                        1,mAnswerList
                    )
                } else {
                    mAnswerList = ArrayList<SurveySubmitDataModel>()
                    for (k in surveyDetailQuestionsArrayList.indices) {
                        val model = SurveySubmitDataModel()
                        model.question_id=(surveyDetailQuestionsArrayList.get(k).id.toString())
                        model.answer_id=(surveyDetailQuestionsArrayList.get(k).answer)
                        mAnswerList.add(model)
                    }
                    val gson = Gson()
                    val PassportArray = java.util.ArrayList<String>()
                    for (i in mAnswerList.indices) {
                        val nmodel = SurveySubmitDataModel()
                        nmodel.answer_id=(mAnswerList.get(i).answer_id)
                        nmodel.question_id=(mAnswerList.get(i).question_id)
                        val json = gson.toJson(nmodel)
                        PassportArray.add(i, json)
                    }
                    val JSON_STRING = "" + PassportArray + ""
                    dialog.dismiss()
                    callSurveySubmitApi(
                        survey_ID,
                        JSON_STRING,
                        mContext,
                        surveyArrayList,
                        true,
                        1,
                    mAnswerList
                    )
                }
            }
        }
        dialog.show()
    }



    private fun showSendEmailDialog()
    {
        val dialog = Dialog(mContext)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_send_email)
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        val btn_submit = dialog.findViewById<Button>(R.id.submitButton)
        val btn_cancel = dialog.findViewById<Button>(R.id.cancelButton)
        val text_dialog = dialog.findViewById<EditText?>(R.id.text_dialog)
        val text_content = dialog.findViewById<EditText>(R.id.text_content)

        btn_cancel.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
        })

        btn_submit.setOnClickListener {
            if (text_dialog.text.toString().trim().equals("")) {
                DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert), resources.getString(R.string.enter_subject), mContext)


            } else {
                if (text_content.text.toString().trim().equals("")) {
                    DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert), resources.getString(R.string.enter_content), mContext)

                } else {
                    // progressDialog.visibility = View.VISIBLE

                    sendEmail(text_dialog.text.toString().trim(), text_content.text.toString().trim(), survey_contact_email, dialog)
                }
            }
        }
        dialog.show()
    }


    fun sendEmail(title: String, message: String,  staffEmail: String, dialog: Dialog)
    {
        progressDialogAdd.visibility= View.VISIBLE

        val sendMailBody = SendEmailApiModel( staffEmail, title, message)
        val call: Call<SignUpResponseModel> = ApiClient.getClient.sendEmailStaff(sendMailBody, "Bearer " + PreferenceManager.getaccesstoken(mContext))
        call.enqueue(object : Callback<SignUpResponseModel> {
            override fun onFailure(call: Call<SignUpResponseModel>, t: Throwable) {
                progressDialogAdd.visibility = View.GONE
            }

            override fun onResponse(call: Call<SignUpResponseModel>, response: Response<SignUpResponseModel>) {
                val responsedata = response.body()
                progressDialogAdd.visibility = View.GONE
                if (responsedata != null) {
                    try {


                        if (response.body()!!.status==100) {
                            dialog.dismiss()
                            showSuccessAlert(
                                mContext,
                                "Email sent Successfully ",
                                "Success",
                                dialog
                            )
                        }else {
                            DialogFunctions.commonErrorAlertDialog(
                                mContext.resources.getString(R.string.alert),
                                ConstantFunctions.commonErrorString(response.body()!!.status),
                                mContext
                            )

                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

        })
    }


    fun showSuccessAlert(context: Context, message: String, msgHead: String, mdialog: Dialog) {
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
        iconImageView.setImageResource(R.drawable.tick)
        btn_Ok.setOnClickListener()
        {
            dialog.dismiss()
            mdialog.dismiss()
        }
        dialog.show()
    }
    fun showSurveyContinueDialog(
        activity: Context,
        surveyID: Int,
        JSON_STRING: String?,
        surveyArrayList: ArrayList<SurveyListDataModel>,
        progressBar: ProgressBar,
        surveyPager: ViewPager,
        surveyQuestionArrayList: ArrayList<SurveyQuestionsModel>,
        previousBtn: ImageView,
        nextQuestionBtn: ImageView,
        nxtQnt: TextView,
        currentQntTxt: TextView,
        questionCount: TextView,
        pos: Int,
        nDialog: Dialog,
        isEmpty: Boolean
    ) {
        val dialog = Dialog(activity!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_continue_layout)
        survey_satisfation_status = 0
        //callSurveySubmitApi(URL_SURVEY_SUBMIT,surveyId,jsonData,getActivity(),surveyArrayList,isThankyou,survey_satisfation_status,dialog);
        val btn_Ok = dialog.findViewById<View>(R.id.btn_Ok) as Button
        val submit = dialog.findViewById<View>(R.id.submit) as Button
        val thoughtsTxt = dialog.findViewById<View>(R.id.thoughtsTxt) as TextView
        if (isEmpty) {
            submit.isClickable = false
            submit.alpha = 0.5f
            thoughtsTxt.text = "Appreciate atleast one feedback from you."
        } else {
            submit.isClickable = true
            submit.alpha = 1.0f
            thoughtsTxt.text =
                "There is an unanswered question on this survey. Would you like to continue?"
        }
        submit.setOnClickListener {
            if (!isEmpty) {
                nDialog.dismiss()
                surveySize = surveySize - 1
                if (surveySize <= 0) {
                    callSurveySubmitApi(
                        surveyID,
                        JSON_STRING!!,
                        activity,
                        surveyArrayList,
                        false,
                        1,
 mAnswerList
                    )
                } else {
                    callSurveySubmitApi(
                        surveyID,
                        JSON_STRING!!,
                        activity,
                        surveyArrayList,
                        true,
                        1,
 mAnswerList
                    )
                }
                dialog.dismiss()
            }
        }
        btn_Ok.setOnClickListener {
            currentPageSurvey = pos + 1
            progressBar.progress = currentPageSurvey
            surveyPager.currentItem = currentPageSurvey - 1
            if (surveyQuestionArrayList.size > 1) {
                if (currentPageSurvey != surveyQuestionArrayList.size) {
                    if (currentPageSurvey == 1) {
                        previousBtn.visibility = View.INVISIBLE
                        nextQuestionBtn.visibility = View.VISIBLE
                        nxtQnt.visibility = View.INVISIBLE
                    } else {
                        if (currentPageSurvey == 1) {
                            previousBtn.visibility = View.INVISIBLE
                            nextQuestionBtn.visibility = View.VISIBLE
                            nxtQnt.visibility = View.INVISIBLE
                        } else {
                            previousBtn.visibility = View.VISIBLE
                            nextQuestionBtn.visibility = View.VISIBLE
                            nxtQnt.visibility = View.INVISIBLE
                        }
                    }
                } else {
                    previousBtn.visibility = View.VISIBLE
                    nextQuestionBtn.visibility = View.INVISIBLE
                    nxtQnt.visibility = View.VISIBLE
                }
            } else {
                if (currentPageSurvey == 1) {
                    previousBtn.visibility = View.INVISIBLE
                    nextQuestionBtn.visibility = View.INVISIBLE
                    nxtQnt.visibility = View.VISIBLE
                }
            }
            if (surveyQuestionArrayList.size > 9) {
                if (currentPageSurvey < 9) {
                    currentQntTxt.text = "0$currentPageSurvey"
                    questionCount.text = "/" + surveyQuestionArrayList.size.toString()
                } else {
                    currentQntTxt.setText(currentPageSurvey.toString())
                    questionCount.text = "/" + surveyQuestionArrayList.size.toString()
                }
            } else {
                if (currentPageSurvey < 9) {
                    currentQntTxt.text = "0$currentPageSurvey"
                    questionCount.text = "/" + "0" + surveyQuestionArrayList.size.toString()
                } else {
                    currentQntTxt.setText(currentPageSurvey.toString())
                    questionCount.text = "/" + "0" + surveyQuestionArrayList.size.toString()
                }
            }
            dialog.dismiss()
        }
        dialog.show()
    }
}