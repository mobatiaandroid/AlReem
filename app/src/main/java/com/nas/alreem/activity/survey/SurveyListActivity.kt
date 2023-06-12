package com.nas.alreem.activity.survey

import android.annotation.SuppressLint
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
import android.view.ViewParent
import android.view.Window
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.nas.alreem.R
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.activity.login.model.SignUpResponseModel
import com.nas.alreem.activity.survey.adapter.SurveyListAdapter
import com.nas.alreem.activity.survey.adapter.SurveyQuestionPagerAdapter
import com.nas.alreem.activity.survey.model.*
import com.nas.alreem.constants.*
import com.nas.alreem.fragment.payments.model.SendEmailApiModel
import com.nas.alreem.rest.ApiClient
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
    lateinit var survey_name:String
    lateinit var survey_image:String
    lateinit var survey_title:String
    lateinit var survey_description:String
    lateinit var survey_contact_email:String
    var currentPageSurvey=0
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

                callSurveyDetailApi(surveyArrayList.get(position).id.toString())

            }
        })

    }

    fun callSurveyList()
    {
        surveyArrayList= ArrayList()
        val call: Call<SurveyListResponseModel> = ApiClient.getClient.surveyList("Bearer "+ PreferenceManager.getaccesstoken(mContext))
        call.enqueue(object : Callback<SurveyListResponseModel> {
            override fun onFailure(call: Call<SurveyListResponseModel>, t: Throwable) {
                Log.e("Failed", t.localizedMessage)
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
        var model=SurveyDetailApiModel(surveyID)
        val call: Call<SurveyDetailResponseModel> = ApiClient.getClient.surveyDetail(model,"Bearer "+ PreferenceManager.getaccesstoken(mContext))
        call.enqueue(object : Callback<SurveyDetailResponseModel> {
            override fun onFailure(call: Call<SurveyDetailResponseModel>, t: Throwable) {
                Log.e("Failed", t.localizedMessage)
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
                            survey_image=response.body()!!.responseArray!!.data!!.image
                            survey_title=response.body()!!.responseArray!!.data!!.title
                            survey_description=response.body()!!.responseArray!!.data!!.description
                            survey_contact_email=response.body()!!.responseArray!!.data!!.contact_email
                            surveyDetailQuestionsArrayList= response.body()!!.responseArray!!.data!!.questions!!

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

        val sendMailBody = SendEmailApiModel( staffEmail, title, message)
        val call: Call<SignUpResponseModel> = ApiClient.getClient.sendEmailStaff(sendMailBody, "Bearer " + PreferenceManager.getaccesstoken(mContext))
        call.enqueue(object : Callback<SignUpResponseModel> {
            override fun onFailure(call: Call<SignUpResponseModel>, t: Throwable) {
                Log.e("Failed", t.localizedMessage)
                //progressDialog.visibility = View.GONE
            }

            override fun onResponse(call: Call<SignUpResponseModel>, response: Response<SignUpResponseModel>) {
                val responsedata = response.body()
                //progressDialog.visibility = View.GONE
                Log.e("Response Signup", responsedata.toString())
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
        var text_dialog = dialog.findViewById(R.id.text_dialog) as TextView
        var btn_Ok = dialog.findViewById(R.id.btn_Ok) as Button
        text_dialog.text = message
        alertHead.text = msgHead
        iconImageView.setImageResource(R.drawable.exclamationicon)
        btn_Ok.setOnClickListener()
        {
            dialog.dismiss()
            mdialog.dismiss()
        }
        dialog.show()
    }

}