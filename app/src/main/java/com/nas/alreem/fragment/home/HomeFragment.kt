package com.nas.alreem.fragment.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.nas.alreem.BuildConfig
import com.nas.alreem.R
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.activity.home.model.ReEnrollSubmitAPIModel
import com.nas.alreem.activity.login.model.SignUpResponseModel
import com.nas.alreem.activity.shop_new.PreOrderActivity_new
import com.nas.alreem.activity.survey.adapter.SurveyQuestionPagerAdapter
import com.nas.alreem.activity.survey.model.*
import com.nas.alreem.constants.ApiClient
import com.nas.alreem.constants.ApiInterface
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.ConstantWords
import com.nas.alreem.constants.DialogFunctions
import com.nas.alreem.constants.PreferenceManager
import com.nas.alreem.constants.WebViewTextActivity
import com.nas.alreem.fragment.about_us.AboutUsFragment
import com.nas.alreem.fragment.absence.AbsenceFragment
import com.nas.alreem.fragment.bus_service.BusServiceFragment
import com.nas.alreem.fragment.calendar.CalendarFragment
import com.nas.alreem.fragment.canteen.CanteenFragment
import com.nas.alreem.fragment.cca.CCAFragment
import com.nas.alreem.fragment.communication.CommunicationFragment
import com.nas.alreem.fragment.contact_us.ContactUsFragment
import com.nas.alreem.fragment.gallery.GalleryFragment
import com.nas.alreem.fragment.home.model.BannerResponseModel
import com.nas.alreem.fragment.home.re_enrollment.EnrollmentHelpResponseModel
import com.nas.alreem.fragment.home.re_enrollment.EnrollmentSaveResponseModel
import com.nas.alreem.fragment.home.re_enrollment.ReEnrollSubmitModel
import com.nas.alreem.fragment.home.re_enrollment.ReEnrollmentFormResponseModel
import com.nas.alreem.fragment.home.re_enrollment.ReEnrollmentFormStudentModel
import com.nas.alreem.fragment.home.re_enrollment.ReEnrollmentStatusResponseModel
import com.nas.alreem.fragment.home.re_enrollment.StudentEnrollList
import com.nas.alreem.fragment.intention.Intentionfragment
import com.nas.alreem.fragment.notifications.NotificationFragment
import com.nas.alreem.fragment.parent_meetings.ParentMeetingsFragment
import com.nas.alreem.fragment.parents_essentials.ParentsEssentialFragment
import com.nas.alreem.fragment.payments.PaymentFragment
import com.nas.alreem.fragment.payments.model.SendEmailApiModel
import com.nas.alreem.fragment.performing_arts.PerformingArtsFragment
import com.nas.alreem.fragment.permission_slip.PermissionSlipFragmentNew
import com.nas.alreem.fragment.reports.ReportsFragment
import com.nas.alreem.fragment.shop.ShopFragment
import com.nas.alreem.fragment.student_information.StudentInformationFragment
import com.nas.alreem.fragment.time_table.TimeTableFragment
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

lateinit var relone: RelativeLayout
lateinit var reltwo: RelativeLayout
lateinit var relthree: RelativeLayout
lateinit var relfour: RelativeLayout
lateinit var relfive: RelativeLayout
lateinit var relsix: RelativeLayout
lateinit var relseven: RelativeLayout
lateinit var releight: RelativeLayout
lateinit var relnine: RelativeLayout
lateinit var reltxtnine: TextView

lateinit var relTxtone: TextView
lateinit var relTxttwo: TextView
lateinit var relTxtfive: TextView
lateinit var relTxtsix: TextView
lateinit var relTxtseven: TextView
lateinit var relTxteight: TextView
lateinit var relTxtthree: TextView
lateinit var relTxtfour: TextView

lateinit var relImgone: ImageView
lateinit var relImgtwo: ImageView
lateinit var relImgthree: ImageView
lateinit var relImgfour: ImageView
lateinit var relImgfive: ImageView
lateinit var relImgsix: ImageView
lateinit var relImgseven: ImageView
lateinit var relImgeight: ImageView
lateinit var relImgnine: ImageView
var versionfromapi: String = ""
var currentversion: String = ""


lateinit var mSectionText: Array<String?>
lateinit var homeActivity: HomeActivity
lateinit var listitems: Array<String>

lateinit var mListImgArrays: TypedArray
lateinit var TouchedView: View
lateinit var pager_rel: RelativeLayout

//lateinit var TAB_ID: String
private var TAB_ID: String = ""
private var CLICKED: String = ""
private var INTENT_TAB_ID: String = ""
private var TABIDfragment: String = ""
private var usertype: String = ""
private var USERDATA: String = ""
private var previousTriggerType: Int = 0

var studentName: String = ""
var studentId: String = ""
var studentImg: String = ""
var studentClass: String = ""
var studntCount: Int = 0


lateinit var pager: ViewPager
var isDraggable: Boolean = false
var bannerarray = ArrayList<String>()
lateinit var mContext: Context
lateinit var current_date: String
var currentPage: Int = 0
private val NOTICE_TIME_OUT: Long = 5000
var currentPageSurvey = 0
var survey_satisfation_status = 0
private var surveyEmail = ""
private val EMAIL_PATTERN =
    "^[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-z0-9])?\\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?$"
private val pattern = "^([a-zA-Z ]*)$"
private var surveySize = 0
var pos = -1
var poss = 0
lateinit var surveyArrayList: ArrayList<SurveyDetailDataModel>
lateinit var surveyQuestionArrayList: ArrayList<SurveyQuestionsModel>
lateinit var surveyAnswersArrayList: ArrayList<SurveyOfferedAnswersModel>
lateinit var mAnswerList: ArrayList<SurveySubmitDataModel>

class HomeFragment : Fragment(), View.OnClickListener {
    lateinit var studentList: ArrayList<StudentEnrollList>
    lateinit var studentEnrollList: ArrayList<StudentEnrollList>
    lateinit var notice: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_home_screen, container, false)
    }

    @SuppressLint("UseRequireInsteadOfGet", "Recycle")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeActivity = activity as HomeActivity
        mContext = requireContext()
        CLICKED = homeActivity.sPosition.toString()
        listitems = resources.getStringArray(R.array.navigation_item_names)
        mListImgArrays = context!!.resources.obtainTypedArray(R.array.navigation_item_icons)
        initializeUI()
        getbannerimages()
        setListeners()
        setdraglisteners()
        getButtonBgAndTextImages()


    }

    fun getbannerimages() {
        val call: Call<BannerResponseModel> = ApiClient.getClient.bannerImages()
        call.enqueue(object : Callback<BannerResponseModel> {
            override fun onFailure(call: Call<BannerResponseModel>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<BannerResponseModel>, response: Response<BannerResponseModel>
            ) {
                val responsedata = response.body()
                if (responsedata != null) {
                    try {

                        if (response.body()!!.status == 100) {
                            bannerarray.addAll(response.body()!!.responseArray!!.banner_images!!)
                            var version =
                                response.body()!!.responseArray!!.android_app_version.toString()
                            var appVersion = BuildConfig.VERSION_NAME.toString()
                            if (!version.equals(appVersion)) {
                                showforceupdate(mContext)
                            }

                            if (bannerarray.size > 0) {
                                pager.adapter = activity?.let { PageView(it, bannerarray) }
                            } else {
                                pager.setBackgroundResource(R.drawable.default_banner)
                            }
                            notice = response.body()!!.responseArray!!.notice
                            val survey: Int = response.body()!!.responseArray!!.survey
                            val lost_student_card_amount: String =
                                response.body()!!.responseArray!!.lost_student_card_amount
                            val enrollmentStatus =
                                response.body()!!.responseArray!!.enrollmentStatus
                            val bus_note= response.body()!!.responseArray!!.bus_note
                            PreferenceManager.setBusnotes(mContext, bus_note)
                            PreferenceManager.setLostAmount(mContext, lost_student_card_amount)
                            PreferenceManager.setSurvey(mContext, survey)
                            if (enrollmentStatus.equals("0") || enrollmentStatus.equals("")) {
                                if (notice.equals("")) {
                                    if (PreferenceManager.getSurvey(mContext) === 1) {
                                        if (PreferenceManager.getIsSurveyHomeVisible(mContext)) {
                                        } else {
                                            if (ConstantFunctions.internetCheck(mContext)) {
                                                callSurveyApi()
                                            } else {
                                                DialogFunctions.showInternetAlertDialog(mContext)
                                            }
                                        }
                                    } else {
                                    }
                                } else {
                                    if (PreferenceManager.getNoticeFirstTime(mContext).equals("")) {
                                        PreferenceManager.setNoticeFirtTime(mContext, "djhdhhf")
                                        showPopUpImage(notice, mContext)
                                    } else {
                                    }
                                }
                            } else {
                                if (PreferenceManager.getIsEnrollmentHomeVisible(mContext)) {

                                } else {
                                    getReEnrollmentStatus()
                                }
                            }


                        } else {

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

    private fun getReEnrollmentStatus() {
        val call: Call<ReEnrollmentStatusResponseModel> = ApiClient.getClient.getenrollstatus(
            "Bearer " + PreferenceManager.getaccesstoken(mContext)
        )

        call.enqueue(object : Callback<ReEnrollmentStatusResponseModel?> {
            override fun onResponse(
                call: Call<ReEnrollmentStatusResponseModel?>,
                response: Response<ReEnrollmentStatusResponseModel?>
            ) {
                if (response.body() != null) {
                    val apiResponse: ReEnrollmentStatusResponseModel? = response.body()
                    val status: String = apiResponse!!.status
                    if (status == "100") {
                        studentList = ArrayList()
                        studentList.addAll(apiResponse.responseArray.students)
                        val responseArrayObject = response.body()!!.responseArray

                        val studentsArray: ArrayList<StudentEnrollList> =
                            responseArrayObject.students

                        var isAtLeastOneStatusEmpty = false

                        for (student in studentsArray) {
                            if ("1" == student.enrollment_status && (student.status == null || student.status.isEmpty())) {
                                isAtLeastOneStatusEmpty = true
                                break
                            }
                        }

                        studentEnrollList = ArrayList()

                        val studentsWithEmptyStatus: ArrayList<StudentEnrollList> =
                            ArrayList<StudentEnrollList>()

                        for (student in studentsArray) {
                            if ("1" == student.enrollment_status && (student.status == null || student.status.isEmpty())) {
                                studentsWithEmptyStatus.add(student)
                            }
                        }
                        studentEnrollList = studentsWithEmptyStatus

                        if (isAtLeastOneStatusEmpty) {
                            showReEnrollmentPopUp()
                        } else {
                            if (notice == "") {
                                if (PreferenceManager.getSurvey(mContext) === 1) {
                                    if (PreferenceManager.getIsSurveyHomeVisible(mContext)) {
                                    } else {
                                        if (ConstantFunctions.internetCheck(mContext)) {
                                            callSurveyApi()
                                        } else {
                                            DialogFunctions.showInternetAlertDialog(mContext)
                                        }
                                    }
                                } else {
                                }
                            } else {
                                if (PreferenceManager.getNoticeFirstTime(mContext).equals("")) {
                                    PreferenceManager.setNoticeFirtTime(mContext, "djhdhhf")
                                    showPopUpImage(notice, mContext)
                                } else {
                                }
                            }
                        }

                    } else {

                    }
                } else {

                }
            }

            override fun onFailure(call: Call<ReEnrollmentStatusResponseModel?>, t: Throwable) {

            }
        })
    }

    private fun showReEnrollmentPopUp() {
        val service: ApiInterface = ApiClient.getClient

        val call: Call<ReEnrollmentFormResponseModel> =
            service.getenrollform("Bearer " + PreferenceManager.getaccesstoken(mContext))
        call.enqueue(object : Callback<ReEnrollmentFormResponseModel?> {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(
                call: Call<ReEnrollmentFormResponseModel?>,
                response: Response<ReEnrollmentFormResponseModel?>
            ) {
                if (response.body() != null) {
                    val apiResponse: ReEnrollmentFormResponseModel? = response.body()
                    val status: String = apiResponse!!.status
                    if (status == "100") {
                        val responseData: ReEnrollmentFormResponseModel.SecondResponseArray =
                            apiResponse.responseArray

                        val settingsObject: ReEnrollmentFormResponseModel.Settings =
                            apiResponse.responseArray.settings
                        val headingString: String = settingsObject.heading
                        val question: String = settingsObject.question
                        val descriptionString: String = settingsObject.description
                        val tAndCString: String = settingsObject.tAndC
                        val optionsArray: ArrayList<String> = settingsObject.options
                        val userObject: ReEnrollmentFormResponseModel.User =
                            apiResponse.responseArray.user
                        val userNameString: String = userObject.name
                        val emailString: String = userObject.email
                        val studentArray: ArrayList<StudentEnrollList> =
                            apiResponse.responseArray.students
                        val studentList: ArrayList<ReEnrollmentFormStudentModel> =
                            ArrayList()
                        val temp = ReEnrollmentFormStudentModel()
                        for (i in studentArray.indices) {
                            val item: StudentEnrollList =
                                apiResponse.responseArray.students[i]
                            val gson = Gson()
                            val eventJson = gson.toJson(item)
                            try {
                                val jsonObject = JSONObject(eventJson)
                                studentList.add(
                                    addStudentReEnrollDetails(
                                        jsonObject
                                    )
                                )
                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }
                        }
                        re_enroll_popup(
                            mContext,
                            headingString,
                            descriptionString,
                            tAndCString,
                            optionsArray,
                            userNameString,
                            emailString,
                            studentEnrollList,
                            question
                        )

                    } else {

                    }
                } else {

                }
            }

            override fun onFailure(call: Call<ReEnrollmentFormResponseModel?>, t: Throwable) {

            }
        })
    }

    @SuppressLint("WrongConstant")
    private fun re_enroll_popup(
        mContext: Context,
        headingString: String,
        descriptionString: String,
        tAndCString: String,
        optionsArray: ArrayList<String>,
        userNameString: String,
        emailString: String,
        studentEnrollList: ArrayList<StudentEnrollList>,  //                                  ArrayList<StudentEnrollList> studentEnrollList,
        question: String
    ) {
        val d = Dialog(mContext)
        d.requestWindowFeature(Window.FEATURE_NO_TITLE)
        d.window!!.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        d.setCancelable(false)
        d.setContentView(R.layout.dialog_re_enrollment_swipe_page)
        val total_count = studentEnrollList.size - 1
        removeStudentsWithEnrollmentStatusZero(studentEnrollList)
        val page_count = intArrayOf(0)
        val check = intArrayOf(0)
        val questionTextView = d.findViewById<TextView>(R.id.questionTxt)
        val scrollView = d.findViewById<ScrollView>(R.id.scrollView)
        val close_img = d.findViewById<ImageView>(R.id.close_img)
        val header = d.findViewById<TextView>(R.id.header)
        val mailIcon = d.findViewById<LinearLayout>(R.id.linear_mail)
        val prev_btn = d.findViewById<LinearLayout>(R.id.prev_linear)
        val nxt_btn = d.findViewById<LinearLayout>(R.id.nxt_linear)
        val sub_btn = d.findViewById<Button>(R.id.sub_btn)
        val terms_and_condtns = d.findViewById<Button>(R.id.terms_conditions)
        val personal_info = d.findViewById<Button>(R.id.personal_info)
        val save = d.findViewById<TextView>(R.id.save)
        val image_view = d.findViewById<ImageView>(R.id.image_view)
        val stud_img = d.findViewById<ImageView>(R.id.stud_img)
        val stud_name = d.findViewById<TextView>(R.id.stud_name)
        val stud_class = d.findViewById<TextView>(R.id.stud_class)
        val date_field = d.findViewById<EditText>(R.id.textField_date)
        val descrcrptn = d.findViewById<TextView>(R.id.descrptn_txt)
        val parent_name = d.findViewById<EditText>(R.id.textField_parentName)
        val parent_email = d.findViewById<EditText>(R.id.textField_parentEmail)
        val spinnerList = d.findViewById<Spinner>(R.id.spinnerlist)
//        val option_txt = d.findViewById<TextView>(R.id.option_txt)
        val clear = d.findViewById<TextView>(R.id.clear)
        val dropdown_btn = d.findViewById<ImageView>(R.id.dropdown_btn)
        val sign_btn = d.findViewById<Button>(R.id.signature_btn)
        val dropdownList: ArrayList<String>
        val calendar = Calendar.getInstance()
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH]
        val day = calendar[Calendar.DAY_OF_MONTH]
        val currentDate = day.toString() + "/" + (month + 1) + "/" + year
        val header_txt = "$year Re-enrolment and NAE Terms & Conditions"
        val reEnrollsave: ArrayList<ReEnrollSubmitModel> = ArrayList<ReEnrollSubmitModel>()
        val reEnrollsubmit: ArrayList<ReEnrollSubmitModel> = ArrayList<ReEnrollSubmitModel>()
        mailIcon.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                val dialog = Dialog(mContext)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setContentView(R.layout.alert_send_email_dialog)
                dialog.window!!.setBackgroundDrawable(
                    ColorDrawable(
                        Color.TRANSPARENT
                    )
                )
                val dialogCancelButton = dialog.findViewById<Button>(R.id.cancelButton)
                val submitButton = dialog.findViewById<Button>(R.id.submitButton)
                val textDialog = dialog.findViewById<EditText>(R.id.text_dialog)
                val textContent = dialog.findViewById<EditText>(R.id.text_content)
                submitButton.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(v: View) {
                        if ((textDialog.text.toString().trim { it <= ' ' } == "")) {
                            ConstantFunctions.showDialogueWithOk(
                                mContext,
                                "Please enter your subject",
                                "Alert"
                            )
                        } else {
                            if ((textContent.text.toString().trim { it <= ' ' } == "")) {
                                ConstantFunctions.showDialogueWithOk(
                                    mContext,
                                    "Please enter your content",
                                    "Alert"
                                )
                            } else {
                                if (ConstantFunctions.internetCheck(mContext)) {
                                    // TODO ReEnrollment Help
                                    sendEmailEnroll(textDialog.text.toString().trim { it <= ' ' },
                                        textContent.text.toString().trim { it <= ' ' }, dialog
                                    )
                                } else {
                                    ConstantFunctions.showDialogueWithOk(
                                        mContext,
                                        "No Network.",
                                        "Alert"
                                    )
                                }
                            }
                        }
                    }
                })
                dialogCancelButton.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(v: View) {
                        dialog.dismiss()
                    }
                })
                dialog.show()
            }
        })
        for (i in studentEnrollList.indices) {
            val m1 = ReEnrollSubmitModel("", "")
            reEnrollsave.add(i, m1)
        }
        if (page_count[0] == total_count) {
            nxt_btn.visibility = View.GONE
            prev_btn.visibility = View.GONE
            sub_btn.visibility = View.VISIBLE
        }
        header.text = headingString
        descrcrptn.text = descriptionString
        val dateFor = currentDate.replace("-", "/")
        date_field.setText(dateFor)
        dropdownList = ArrayList<String>()
        // studDetailList= new ArrayList<>();
        stud_name.text = studentEnrollList.get(0).name
        stud_class.text = studentEnrollList.get(0).section
        val stud_photo = studentEnrollList[0].photo
        val stud_id = studentEnrollList[0].id
        dropdownList.add(0, "Please Select")
        for (i in 1..optionsArray.size) {
            dropdownList.add(i, optionsArray[i - 1])
        }
        val sp_adapter = ArrayAdapter<String>(
            mContext, R.layout.spinner_textview, dropdownList
        )
        spinnerList.adapter = sp_adapter
        sp_adapter.setDropDownViewResource(R.layout.spinner_textview)
        spinnerList.setSelection(0)

        spinnerList.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    val selectedItem: String = parent.getItemAtPosition(position).toString()
                    val optionlistSize: Int = dropdownList.size - 1
                    for (i in 1..optionlistSize) {
                        if ((selectedItem == dropdownList[i])) {
                            reEnrollsave[page_count[0]].status = dropdownList[i]
                            check[0] = 1
                        } else if ((selectedItem == dropdownList[0])) {
                            check[0] = 0
                            reEnrollsave[page_count[0]].status = ""
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        if (stud_photo != "") {
            Glide.with(mContext).load(stud_photo).placeholder(R.drawable.student)
                .error(R.drawable.student).skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE) //                    .transform(new CircleCrop())
                .into(stud_img)
        } else {
            stud_img.setImageResource(R.drawable.student)
        }
        if (userNameString != "") {
            parent_name.setText(userNameString)
        }
        if (emailString != "") {
            parent_email.setText(emailString)
        }
        if (!question.isEmpty()) {
            questionTextView.text = question
        }
//        option_txt.setOnClickListener(object : View.OnClickListener {
//            override fun onClick(v: View) {
//                option_txt.visibility = View.GONE
//                spinnerList.visibility = View.VISIBLE
//            }
//        })
        terms_and_condtns.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                val intent = Intent(mContext, WebViewTextActivity::class.java)
                intent.putExtra("Url", tAndCString)
                startActivity(intent)
            }
        })
        sub_btn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                if (check[0] == 0) {
                    for (i in studentEnrollList.indices) {
                        if (!reEnrollsave[i].status.isEmpty()) {
                            reEnrollsave[i].student_id = studentEnrollList[i].id
                        }
                    }
                    for (i in reEnrollsave.indices) {
                        if (!reEnrollsave[i].student_id
                                .isEmpty() && !reEnrollsave[i].status.isEmpty()
                        ) {
                            if (reEnrollsubmit.size == 0) {
                                val r1 = ReEnrollSubmitModel(
                                    reEnrollsave[i].student_id,
                                    reEnrollsave[i].status
                                )
                                reEnrollsubmit.add(0, r1)
                            } else {
                                for (j in 1..reEnrollsubmit.size) {
                                    if (!reEnrollsave[i].student_id
                                            .equals(reEnrollsubmit[j - 1].student_id)
                                    ) {
                                        val r1 = ReEnrollSubmitModel(
                                            reEnrollsave[i].student_id,
                                            reEnrollsave[i].status
                                        )
                                        reEnrollsubmit.add(j, r1)
                                    } else {
                                    }
                                }
                            }
                        }
                    }
                    if (reEnrollsubmit.size > 0) {
                        showConfirmationPopUp(
                            mContext,
                            "Do you want to Submit?",
                            "",
                            reEnrollsubmit,
                            d
                        )
                    } else {
                        ConstantFunctions.showDialogueWithOk(
                            mContext,
                            "You didn't enter any data of your child. Please Enter data and Submit",
                            "Alert"
                        )
                    }
                } else {
                    for (i in studentEnrollList.indices) {
                        if (!reEnrollsave[i].status.isEmpty()) {
                            reEnrollsave[i].student_id = studentEnrollList[i].id
                        }
                    }
                    for (i in reEnrollsave.indices) {
                        if (!reEnrollsave[i].student_id
                                .isEmpty() && !reEnrollsave[i].status.isEmpty()
                        ) {
                            if (reEnrollsubmit.size == 0) {
                                val r1 = ReEnrollSubmitModel(
                                    reEnrollsave[i].student_id,
                                    reEnrollsave[i].status
                                )
                                reEnrollsubmit.add(0, r1)
                            } else {
                                for (j in 1..reEnrollsubmit.size) {
                                    if (!reEnrollsave[i].student_id
                                            .equals(reEnrollsubmit[j - 1].student_id)
                                    ) {
                                        val r1 = ReEnrollSubmitModel(
                                            reEnrollsave[i].student_id,
                                            reEnrollsave[i].status
                                        )
                                        reEnrollsubmit.add(j, r1)
                                    } else {
                                    }
                                }
                            }
                        }
                    }
                    showConfirmationPopUp(mContext, "Do you want to Submit?", "", reEnrollsubmit, d)
                }
            }
        })
        nxt_btn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                if (check[0] == 0) {
                    page_count[0] = page_count[0] + 1
                    if (page_count[0] == studentEnrollList.size - 1) {
                        nxt_btn.visibility = View.GONE
                        sub_btn.visibility = View.VISIBLE
                        prev_btn.visibility = View.VISIBLE
                    } else {
                        nxt_btn.visibility = View.VISIBLE
                        prev_btn.visibility = View.VISIBLE
                        sub_btn.visibility = View.GONE
                    }
                    if (reEnrollsave[page_count[0]].status.equals("")) {
                        spinnerList.setSelection(0)
                    } else {
                        for (i in dropdownList.indices) {
                            if (reEnrollsave[page_count[0]].status.equals(dropdownList[i])) {
                                spinnerList.setSelection(i)
                            }
                        }
                    }
                    stud_name.text = studentEnrollList.get(page_count.get(0)).name
                    stud_class.text = studentEnrollList.get(page_count.get(0)).section
                    val stud_photo = studentEnrollList[page_count[0]].photo
                    if (stud_photo != "") {
                        Glide.with(mContext).load(stud_photo).placeholder(R.drawable.student)
                            .error(R.drawable.student).skipMemoryCache(true)
                            .diskCacheStrategy(DiskCacheStrategy.NONE) //                                .transform(new CircleCrop())
                            .into(stud_img)
                    }
                } else {
                    page_count[0] = page_count[0] + 1
                    if (page_count[0] == studentEnrollList.size - 1) {
                        nxt_btn.visibility = View.GONE
                        sub_btn.visibility = View.VISIBLE
                        prev_btn.visibility = View.VISIBLE
                    } else {
                        nxt_btn.visibility = View.VISIBLE
                        prev_btn.visibility = View.VISIBLE
                        sub_btn.visibility = View.GONE
                    }
                    if (reEnrollsave[page_count[0]].status.equals("")) {
                        spinnerList.setSelection(0)
                    } else {
                        for (i in dropdownList.indices) {
                            if (reEnrollsave[page_count[0]].status.equals(dropdownList[i])) {
                                spinnerList.setSelection(i)
                            }
                        }
                    }
                    stud_name.text = studentEnrollList.get(page_count.get(0)).name
                    stud_class.text = studentEnrollList.get(page_count.get(0)).section
                    val stud_photo = studentEnrollList[page_count[0]].photo
                    if (stud_photo != "") {
                        Glide.with(mContext).load(stud_photo).placeholder(R.drawable.student)
                            .error(R.drawable.student).skipMemoryCache(true)
                            .diskCacheStrategy(DiskCacheStrategy.NONE) //                                        .transform(new CircleCrop())
                            .into(stud_img)
                    }
                }
                scrollView.post(object : Runnable {
                    override fun run() {
                        // X, Y are scroll positions until where you want to scroll up
                        val X = 0
                        val Y = 0
                        scrollView.scrollTo(X, Y)
                    }
                })
            }
        })
        prev_btn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                if (check[0] == 0) {
                    page_count[0] = page_count[0] - 1
                    if (page_count[0] == 0) {
                        nxt_btn.visibility = View.VISIBLE
                        prev_btn.visibility = View.GONE
                        sub_btn.visibility = View.GONE
                    } else if (page_count[0] < studentEnrollList.size - 1) {
                        if (page_count[0] == studentEnrollList.size - 1) {
                            nxt_btn.visibility = View.GONE
                            sub_btn.visibility = View.VISIBLE
                            prev_btn.visibility = View.VISIBLE
                        } else {
                            nxt_btn.visibility = View.VISIBLE
                            prev_btn.visibility = View.VISIBLE
                            sub_btn.visibility = View.GONE
                        }
                    }
                    if (reEnrollsave[page_count[0]].status.equals("")) {
                        spinnerList.setSelection(0)
                    } else {
                        for (i in dropdownList.indices) {
                            if (reEnrollsave[page_count[0]].status.equals(dropdownList[i])) {
                                spinnerList.setSelection(i)
                            }
                        }
                    }
                    stud_name.text = studentEnrollList.get(page_count.get(0)).name
                    stud_class.text = studentEnrollList.get(page_count.get(0)).section
                    val stud_photo = studentEnrollList[page_count[0]].photo
                    if (stud_photo != "") {
                        Glide.with(mContext).load(stud_photo).placeholder(R.drawable.student)
                            .error(R.drawable.student).skipMemoryCache(true)
                            .diskCacheStrategy(DiskCacheStrategy.NONE) //                                .transform(new CircleCrop())
                            .into(stud_img)
                    }
                } else {
                    page_count[0] = page_count[0] - 1
                    if (page_count.get(0) == 0) {
                        nxt_btn.visibility = View.VISIBLE
                        prev_btn.visibility = View.GONE
                        sub_btn.visibility = View.GONE
                    } else if (page_count.get(0) < studentEnrollList.size - 1) {
                        if (page_count.get(0) == studentEnrollList.size - 1) {
                            nxt_btn.visibility = View.GONE
                            sub_btn.visibility = View.VISIBLE
                            prev_btn.visibility = View.VISIBLE
                        } else {
                            nxt_btn.visibility = View.VISIBLE
                            prev_btn.visibility = View.VISIBLE
                            sub_btn.visibility = View.GONE
                        }
                    }
                    if (reEnrollsave.get(page_count.get(0)).status.equals("")) {
                        spinnerList.setSelection(0)
                    } else {
                        for (i in dropdownList.indices) {
                            if (reEnrollsave.get(page_count.get(0)).status
                                    .equals(dropdownList.get(i))
                            ) {
                                spinnerList.setSelection(i)
                            }
                        }
                    }
                    stud_name.text = studentEnrollList.get(page_count.get(0)).name
                    stud_class.text = studentEnrollList.get(page_count.get(0)).section
                    val stud_photo = studentEnrollList.get(page_count.get(0)).photo
                    if (stud_photo != "") {
                        Glide.with(mContext).load(stud_photo).placeholder(R.drawable.student)
                            .error(R.drawable.student).skipMemoryCache(true)
                            .diskCacheStrategy(DiskCacheStrategy.NONE) //                                    .transform(new CircleCrop())
                            .into(stud_img)
                    }
                }
                scrollView.post(object : Runnable {
                    override fun run() {
                        // X, Y are scroll positions until where you want to scroll up
                        val X = 0
                        val Y = 0
                        scrollView.scrollTo(X, Y)
                    }
                })
            }
        })
        close_img.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                PreferenceManager.setIsEnrollmentHomeVisible(mContext, true)
                if (notice.equals("")) {
                    if (PreferenceManager.getSurvey(mContext) === 1) {
                        if (PreferenceManager.getIsSurveyHomeVisible(mContext)) {
                        } else {
                            if (ConstantFunctions.internetCheck(mContext)) {
                                callSurveyApi()
                            } else {
                                DialogFunctions.showInternetAlertDialog(mContext)
                            }
                        }
                    } else {
                    }
                } else {
                    if (PreferenceManager.getNoticeFirstTime(mContext).equals("")) {
                        PreferenceManager.setNoticeFirtTime(mContext, "djhdhhf")
                        showPopUpImage(notice, mContext)
                    } else {
                    }
                }
                d.dismiss()
            }
        })
        d.show()
    }

    private fun sendEmailEnroll(subject: String, content: String, dialog: Dialog) {
        val paramObject = JsonObject()
        paramObject.addProperty("title", subject.trim().toString())
        paramObject.addProperty("message", content.trim().toString())
        val call: Call<EnrollmentHelpResponseModel> = ApiClient.getClient.getenrollhelp(
            "Bearer " + PreferenceManager.getaccesstoken(mContext),
            paramObject
        )
        call.enqueue(object : Callback<EnrollmentHelpResponseModel> {
            override fun onFailure(call: Call<EnrollmentHelpResponseModel>, t: Throwable) {
                //progressDialog.visibility = View.GONE
            }

            override fun onResponse(
                call: Call<EnrollmentHelpResponseModel>,
                response: Response<EnrollmentHelpResponseModel>
            ) {
                val responsedata = response.body()
                //progressDialog.visibility = View.GONE
                if (responsedata != null) {
                    try {


                        if (response.body()!!.status == 100) {
                            Toast.makeText(context, "Email sent successfully", Toast.LENGTH_SHORT)
                                .show()
                            dialog.dismiss()
                        } else {
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


    //    private void reEnroll(Context mContext) {
    //        int page_count = 0;
    //        //int total_count=studDetailList.size-1;
    //        int check = 0;
    //
    //
    //        final Dialog dialog = new Dialog(mContext);
    //        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    //        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    //        dialog.setCancelable(false);
    //        dialog.setContentView(R.layout.dialogue_re_enrollment_status);
    //
    //        String mTitle;
    //        String mTabId;
    //        View mRootView;
    //        TextView titleTextView;
    //        RelativeLayout relMain;
    //        ImageView closeImage, emailHelpImg;
    //
    //
    //        titleTextView = dialog.findViewById(R.id.titleTextView);
    //        reEnrollRecycler = dialog.findViewById(R.id.enroll_rec);
    //        closeImage = dialog.findViewById(R.id.close_img);
    //        emailHelpImg = dialog.findViewById(R.id.emailHelpImg);
    //
    //        emailHelpImg.setOnClickListener(new View.OnClickListener() {
    //            @Override
    //            public void onClick(View v) {
    //                final Dialog dialog = new Dialog(mContext);
    //                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    //                dialog.setContentView(R.layout.alert_send_email_dialog);
    //                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    //                Button dialogCancelButton = (Button) dialog.findViewById(R.id.cancelButton);
    //                Button submitButton = (Button) dialog.findViewById(R.id.submitButton);
    //                text_dialog = (EditText) dialog.findViewById(R.id.text_dialog);
    //                text_content = (EditText) dialog.findViewById(R.id.text_content);
    //
    //
    //                dialogCancelButton.setOnClickListener(new View.OnClickListener() {
    //
    //                    @Override
    //
    //                    public void onClick(View v) {
    //
    //                        dialog.dismiss();
    //
    //                    }
    //
    //                });
    //
    //                submitButton.setOnClickListener(new View.OnClickListener() {
    //                    @Override
    //                    public void onClick(View v) {
    //                        sendEmailEnroll(URL_SEND_EMAIL_ENROLL);
    //                        dialog.dismiss();
    //                    }
    //                });
    //
    //
    //                dialog.show();
    //
    //
    //            }
    //        });
    //
    //        titleTextView.setText("Re-Enrolment");
    //        studentEnrollList = new ArrayList<>();
    //        callSettingsAPI(mContext);
    //
    //        closeImage.setOnClickListener(new OnClickListener() {
    //            @Override
    //            public void onClick(View v) {
    //                dialog.dismiss();
    //            }
    //        });
    //        reEnrollRecycler.addOnItemTouchListener(new RecyclerItemListener(mContext, reEnrollRecycler,
    //                new RecyclerItemListener.RecyclerTouchListener() {
    //                    @Override
    //                    public void onClickItem(View v, int position) {
    //                        if (studentReEnrollList.get(position).getStatus().equalsIgnoreCase("")) {
    //                            if (studentReEnrollList.get(position).getEnrollment_status().equalsIgnoreCase("1")) {
    //                                callReEnrollAPI(studentReEnrollList, position);
    //                            } else {
    //                                AppUtils.showDialogAlertDismiss(getActivity(), "Alert", "Re-Enrolment is currently not enabled        \n" +
    //
    //                                        "Please wait for further update", R.drawable.exclamationicon, R.drawable.round);
    //                            }
    //                        } else {
    //                            Dialog dialog = new Dialog(mContext);
    //                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    //                            dialog.setContentView(R.layout.alert_re_enroll);
    //                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    //                            TextView name = dialog.findViewById(R.id.nametxt);
    //                            TextView studName = dialog.findViewById(R.id.stud_name);
    //                            TextView department = dialog.findViewById(R.id.mailtxt);
    //                            TextView role = dialog.findViewById(R.id.statustxt);
    //                            ImageView imageView = dialog.findViewById(R.id.iconImageView);
    //                            name.setText(studentReEnrollList.get(position).getParent_name());
    //                            studName.setText(studentReEnrollList.get(position).getName());
    //                            department.setText(studentReEnrollList.get(position).getParent_email());
    //                            role.setText(studentReEnrollList.get(position).getStatus());
    //                            // TODO set Staff Image
    //                            dialog.show();
    //                        }
    //                    }
    //
    //                    @Override
    //                    public void onLongClickItem(View v, int position) {
    //
    //
    //                    }
    //                }));
    //
    //        dialog.show();
    //    }
    //    private void callReEnrollAPI(ArrayList<StudentEnrollList> studentEnrollList, int position) {
    //        progressDialogP.show();
    //        APIInterface service = APIClient.getRetrofitInstance().create(APIInterface.class);
    //        JsonObject paramObject = new JsonObject();
    //
    //        paramObject.addProperty("users_id", PreferenceManager.getUserId(mContext));
    //        Call<EnrollmentFormResponseModel> call = service.getenrollform("Bearer " + PreferenceManager.getAccessToken(mContext), paramObject);
    //        call.enqueue(new Callback<EnrollmentFormResponseModel>() {
    //            @Override
    //            public void onResponse(Call<EnrollmentFormResponseModel> call, Response<EnrollmentFormResponseModel> response) {
    //
    //                if (response.body() != null) {
    //
    //                    EnrollmentFormResponseModel apiResponse = response.body();
    //
    //                    String responseCode = apiResponse.getResponsecode();
    //                    if (responseCode.equals("200")) {
    //                        String statuscode = apiResponse.getResponse().getStatuscode();
    //                        EnrollmentFormResponseModel.ResponseData responseData = apiResponse.getResponse();
    //                        if (statuscode.equals("303")) {
    //                            progressDialogP.hide();
    //                            EnrollmentFormResponseModel.ResponseData.ResponseArray responseArrayObject = responseData.getResponseArray();
    //
    //                            EnrollmentFormResponseModel.ResponseData.ResponseArray.Settings settingsObject = responseArrayObject.getSettings();
    //                            String headingString = settingsObject.getHeading();
    //                            String question = settingsObject.getQuestion();
    //                            String descriptionString = settingsObject.getDescription();
    //                            String tAndCString = settingsObject.getT_and_c();
    //                            ArrayList<String> optionsArray = settingsObject.getOptions();
    //
    //                            EnrollmentFormResponseModel.ResponseData.ResponseArray.User userObject = responseArrayObject.getUser();
    //                            String userNameString = userObject.getFirstname();
    //                            String emailString = userObject.getEmail();
    //
    //                            ArrayList<ReEnrollmentFormStudentModel> studentArray = responseArrayObject.getStudents();
    //                            ArrayList<ReEnrollmentFormStudentModel> studentList = new ArrayList();
    //                            ReEnrollmentFormStudentModel temp = new ReEnrollmentFormStudentModel();
    //                            for (int i = 0; i < studentArray.size(); i++) {
    //                                ReEnrollmentFormStudentModel item = apiResponse.getResponse().getResponseArray().getStudents().get(i);
    //                                Gson gson = new Gson();
    //                                String eventJson = gson.toJson(item);
    //                                try {
    //                                    JSONObject jsonObject = new JSONObject(eventJson);
    //                                    studentList.add(addStudentReEnrollDetails(jsonObject));
    //                                    //  Log.e("array", String.valueOf(mCCAmodelArrayList));
    //                                } catch (JSONException e) {
    //                                    e.printStackTrace();
    //                                }
    //                            }
    //                            showReEnrollmentPopUp(mContext, headingString, descriptionString, tAndCString, optionsArray, userNameString, emailString, studentList, position, studentEnrollList, question);
    //
    //
    //                        }
    //                    } else if (responseCode.equalsIgnoreCase("500")) {
    //                        AppUtils.showDialogAlertDismiss((Activity) mContext, "Alert", "Cannot continue. Please try again later", R.drawable.exclamationicon, R.drawable.round);
    //
    //                    } else if (responseCode.equalsIgnoreCase("400")) {
    //                        AppUtils.getToken(mContext, new AppUtils.GetTokenSuccess() {
    //                            @Override
    //                            public void tokenrenewed() {
    //                            }
    //                        });
    ////						getStudentsListAPI(URLHEAD);
    //
    //                    } else if (responseCode.equalsIgnoreCase("401")) {
    //                        AppUtils.getToken(mContext, new AppUtils.GetTokenSuccess() {
    //                            @Override
    //                            public void tokenrenewed() {
    //                            }
    //                        });
    ////						getStudentsListAPI(URLHEAD);
    //
    //                    } else if (responseCode.equalsIgnoreCase("402")) {
    //                        AppUtils.getToken(mContext, new AppUtils.GetTokenSuccess() {
    //                            @Override
    //                            public void tokenrenewed() {
    //                            }
    //                        });
    ////						getStudentsListAPI(URLHEAD);
    //
    //                    } else {
    //                        progressDialogP.hide();
    //                        AppUtils.showDialogAlertDismiss((Activity) mContext, "Alert", "Cannot continue. Please try again later", R.drawable.exclamationicon, R.drawable.round);
    //
    //                    }
    //                } else {
    //                    AppUtils.showDialogAlertDismiss((Activity) mContext, "Alert", getString(R.string.common_error), R.drawable.exclamationicon, R.drawable.round);
    //                }
    //
    //            }
    //
    //            @Override
    //            public void onFailure(Call<EnrollmentFormResponseModel> call, Throwable t) {
    //                progressDialogP.hide();
    //                AppUtils.showDialogAlertDismiss((Activity) mContext, "Alert", mContext.getString(R.string.common_error), R.drawable.exclamationicon, R.drawable.round);
    //
    //            }
    //        });
    //
    //
    //    }
    fun removeStudentsWithEnrollmentStatusZero(studentEnrollList: ArrayList<StudentEnrollList>) {
        val iterator = studentEnrollList.iterator()
        while (iterator.hasNext()) {
            val student = iterator.next()
            if ("0" == student.enrollment_status) {
                iterator.remove()
            }
        }
    }

    private fun showConfirmationPopUp(
        context: Context,
        message: String,
        msgHead: String,
        reEnrollsubmit: ArrayList<ReEnrollSubmitModel>,
        d: Dialog
    ) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_ok_cancel)
        val iconImageView = dialog.findViewById<ImageView>(R.id.iconImageView)
        val alertHead = dialog.findViewById<TextView>(R.id.alertHead)
        val text_dialog = dialog.findViewById<TextView>(R.id.text_dialog)
        val btn_Ok = dialog.findViewById<Button>(R.id.btn_Ok)
        val btn_Cancel = dialog.findViewById<Button>(R.id.btn_Cancel)
        text_dialog.text = message
        alertHead.text = msgHead
        btn_Ok.setOnClickListener {
            saveReEnrollApi(reEnrollsubmit, dialog, d)
            dialog.dismiss()
        }
        btn_Cancel.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

    private fun showSuccessReenrollAlert(
        context: Context, message: String, msgHead: String, dlg: Dialog, d: Dialog
    ) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_common_error_alert)
        val iconImageView = dialog.findViewById<ImageView>(R.id.iconImageView)
        val alertHead = dialog.findViewById<TextView>(R.id.alertHead)
        val text_dialog = dialog.findViewById<TextView>(R.id.messageTxt)
        val btn_Ok = dialog.findViewById<Button>(R.id.btn_Ok)
        text_dialog.text = message
        alertHead.text = msgHead
        iconImageView.setImageResource(R.drawable.tick)
        btn_Ok.setOnClickListener {
            dialog.dismiss()
            dlg.dismiss()
            d.dismiss()
            if (notice.equals("")) {
                if (PreferenceManager.getSurvey(mContext) === 1) {
                    if (PreferenceManager.getIsSurveyHomeVisible(mContext)) {
                    } else {
                        if (ConstantFunctions.internetCheck(mContext)) {
                            callSurveyApi()
                        } else {
                            DialogFunctions.showInternetAlertDialog(mContext)
                        }
                    }
                } else {
                }
            } else {
                if (PreferenceManager.getNoticeFirstTime(mContext).equals("")) {
                    PreferenceManager.setNoticeFirtTime(mContext, "djhdhhf")
                    showPopUpImage(notice, mContext)
                } else {
                }
            }
        }
        dialog.show()
    }


    private fun saveReEnrollApi(
        reEnrollsubmit: ArrayList<ReEnrollSubmitModel>,
        dialog: Dialog,
        d: Dialog
    ) {
        val service: ApiInterface = ApiClient.getClient

        val save_reenroll = ReEnrollSubmitAPIModel(reEnrollsubmit)
        val call: Call<EnrollmentSaveResponseModel> = service.getenrollsave(
            "Bearer " + PreferenceManager.getaccesstoken(mContext), save_reenroll
        )
        call.enqueue(object : Callback<EnrollmentSaveResponseModel?> {
            override fun onResponse(
                call: Call<EnrollmentSaveResponseModel?>,
                response: Response<EnrollmentSaveResponseModel?>
            ) {
                // progressDialogP.hide()
                if (response.body() != null) {
                    val apiResponse: EnrollmentSaveResponseModel? = response.body()
                    val status: String = apiResponse!!.status
                    if (status == "100") {
                        showSuccessReenrollAlert(
                            mContext, "Success", "", dialog, d
                        )
                    } else {

                    }
                } else {

                }
            }

            override fun onFailure(call: Call<EnrollmentSaveResponseModel?>, t: Throwable) {

            }
        })
    }


    private fun addStudentReEnrollDetails(dataObject: JSONObject): ReEnrollmentFormStudentModel {
        val studentModel = ReEnrollmentFormStudentModel()
        studentModel.id = dataObject.optString("id")
        studentModel.unique_id = dataObject.optString("unique_id")
        studentModel.name = dataObject.optString("name")
        studentModel.class_name = dataObject.optString("class_name")
        studentModel.section = dataObject.optString("section")
        studentModel.house = dataObject.optString("house")
        studentModel.photo = dataObject.optString("photo")
        return studentModel
    }

    private fun setListeners() {
        relone.setOnClickListener(this)
        reltwo.setOnClickListener(this)
        relthree.setOnClickListener(this)
        relfour.setOnClickListener(this)
        relfive.setOnClickListener(this)
        relsix.setOnClickListener(this)
        relseven.setOnClickListener(this)
        releight.setOnClickListener(this)
        relnine.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        if (v == relone) {
            INTENT_TAB_ID = PreferenceManager.getbuttononetabid(mContext).toString()
            CHECKINTENTVALUE(INTENT_TAB_ID)
        }
        if (v == reltwo) {

            INTENT_TAB_ID = PreferenceManager.getbuttontwotabid(mContext).toString()
            CHECKINTENTVALUE(INTENT_TAB_ID)
        }
        if (v == relthree) {

            INTENT_TAB_ID = PreferenceManager.getbuttonthreetabid(mContext).toString()
            CHECKINTENTVALUE(INTENT_TAB_ID)
        }
        if (v == relfour) {

            INTENT_TAB_ID = PreferenceManager.getbuttonfourtabid(mContext).toString()
            CHECKINTENTVALUE(INTENT_TAB_ID)
        }
        if (v == relfive) {

            INTENT_TAB_ID = PreferenceManager.getbuttonfivetabid(mContext).toString()
            CHECKINTENTVALUE(INTENT_TAB_ID)
        }
        if (v == relsix) {

            INTENT_TAB_ID = PreferenceManager.getbuttonsixtabid(mContext).toString()
            CHECKINTENTVALUE(INTENT_TAB_ID)
        }
        if (v == relseven) {

            INTENT_TAB_ID = PreferenceManager.getbuttonseventabid(mContext).toString()
            CHECKINTENTVALUE(INTENT_TAB_ID)
        }
        if (v == releight) {

            INTENT_TAB_ID = PreferenceManager.getbuttoneighttabid(mContext).toString()
            CHECKINTENTVALUE(INTENT_TAB_ID)
        }
        if (v == relnine) {

            INTENT_TAB_ID = PreferenceManager.getbuttonninetabid(mContext).toString()
            CHECKINTENTVALUE(INTENT_TAB_ID)
        }

    }

    private fun getButtonBgAndTextImages() {

        if (PreferenceManager.getbuttononetextimage(mContext)!!.toInt() != 0) {
            relImgone.setImageDrawable(
                mListImgArrays.getDrawable(
                    PreferenceManager.getbuttononetextimage(mContext)!!.toInt()
                )
            )
            var relTwoStr: String? = ""
            relTwoStr =
                listitems[PreferenceManager.getbuttononetextimage(mContext)!!.toInt()].uppercase(
                    Locale.getDefault()
                )
            relTxtone.text = relTwoStr
            relTxtone.setTextColor(ContextCompat.getColor(mContext, R.color.white))
            relone.setBackgroundColor(
                PreferenceManager.getButtonOneGuestBg(mContext)
            )
        }
        if (PreferenceManager.getbuttontwotextimage(mContext)!!.toInt() != 0) {
            relImgtwo.setImageDrawable(
                mListImgArrays.getDrawable(
                    PreferenceManager.getbuttontwotextimage(mContext)!!.toInt()
                )
            )
            var relTwoStr: String? = ""
            relTwoStr =
                listitems[PreferenceManager.getbuttontwotextimage(mContext)!!.toInt()].uppercase(
                    Locale.getDefault()
                )

            relTxttwo.text = relTwoStr
            relTxttwo.setTextColor(ContextCompat.getColor(mContext, R.color.white))
            reltwo.setBackgroundColor(
                PreferenceManager.getButtontwoGuestBg(mContext)
            )
        }
        if (PreferenceManager.getbuttonthreetextimage(mContext)!!.toInt() != 0) {
            relImgthree.setImageDrawable(
                mListImgArrays.getDrawable(
                    PreferenceManager.getbuttonthreetextimage(mContext)!!.toInt()
                )
            )
            var relTwoStr: String? = ""
            relTwoStr =
                listitems[PreferenceManager.getbuttonthreetextimage(mContext)!!.toInt()].uppercase(
                    Locale.getDefault()
                )
            relTxtthree.text = relTwoStr
            relTxtthree.setTextColor(ContextCompat.getColor(mContext, R.color.white))
            relthree.setBackgroundColor(
                PreferenceManager.getButtonthreeGuestBg(mContext)
            )
        }


        if (PreferenceManager.getbuttonfourtextimage(mContext)!!.toInt() != 0) {
            relImgfour.setImageDrawable(
                mListImgArrays.getDrawable(
                    PreferenceManager.getbuttonfourtextimage(mContext)!!.toInt()
                )
            )
            var relTwoStr: String? = ""
            relTwoStr =
                listitems[PreferenceManager.getbuttonfourtextimage(mContext)!!.toInt()].uppercase(
                    Locale.getDefault()
                )
            relTxtfour.text = relTwoStr
            relTxtfour.setTextColor(ContextCompat.getColor(mContext, R.color.white))
            relfour.setBackgroundColor(
                PreferenceManager.getButtonfourGuestBg(mContext)
            )
        }


        if (PreferenceManager.getbuttonfivetextimage(mContext)!!.toInt() != 0) {
            relImgfive.setImageDrawable(
                mListImgArrays.getDrawable(
                    PreferenceManager.getbuttonfivetextimage(mContext)!!.toInt()
                )
            )
            var relTwoStr: String? = ""
            relTwoStr =
                listitems[PreferenceManager.getbuttonfivetextimage(mContext)!!.toInt()].uppercase(
                    Locale.getDefault()
                )
            relTxtfive.text = relTwoStr
            relTxtfive.setTextColor(ContextCompat.getColor(mContext, R.color.white))
            relfive.setBackgroundColor(
                PreferenceManager.getButtonfiveGuestBg(mContext)
            )
        }
        if (PreferenceManager.getbuttonsixtextimage(mContext)!!.toInt() != 0) {
            relImgsix.setImageDrawable(
                mListImgArrays.getDrawable(
                    PreferenceManager.getbuttonsixtextimage(mContext)!!.toInt()
                )
            )
            var relTwoStr: String? = ""
            relTwoStr =
                listitems[PreferenceManager.getbuttonsixtextimage(mContext)!!.toInt()].uppercase(
                    Locale.ROOT
                )
            relTxtsix.text = relTwoStr
            relTxtsix.setTextColor(ContextCompat.getColor(mContext, R.color.white))
            relsix.setBackgroundColor(
                PreferenceManager.getButtonsixGuestBg(mContext)
            )
        }
        if (PreferenceManager.getbuttonseventextimage(mContext)!!.toInt() != 0) {
            relImgseven.setImageDrawable(
                mListImgArrays.getDrawable(
                    PreferenceManager.getbuttonseventextimage(mContext)!!.toInt()
                )
            )
            var relTwoStr: String? = ""
            relTwoStr =
                listitems[PreferenceManager.getbuttonseventextimage(mContext)!!.toInt()].uppercase(
                    Locale.getDefault()
                )
            relTxtseven.text = relTwoStr
            relTxtseven.setTextColor(ContextCompat.getColor(mContext, R.color.white))
            relseven.setBackgroundColor(
                PreferenceManager.getButtonsevenGuestBg(mContext)
            )
        }
        if (PreferenceManager.getbuttoneighttextimage(mContext)!!.toInt() != 0) {
            relImgeight.setImageDrawable(
                mListImgArrays.getDrawable(
                    PreferenceManager.getbuttoneighttextimage(mContext)!!.toInt()
                )
            )
            var relTwoStr: String? = ""
            relTwoStr =
                listitems[PreferenceManager.getbuttoneighttextimage(mContext)!!.toInt()].uppercase(
                    Locale.getDefault()
                )
            relTxteight.text = relTwoStr
            relTxteight.setTextColor(ContextCompat.getColor(mContext, R.color.white))
            releight.setBackgroundColor(
                PreferenceManager.getButtoneightGuestBg(mContext)
            )
        }
        if (PreferenceManager.getbuttonninetextimage(mContext)!!.toInt() != 0) {
            relImgnine.setImageDrawable(
                mListImgArrays.getDrawable(
                    PreferenceManager.getbuttonninetextimage(mContext)!!.toInt()
                )
            )
            var relTwoStr: String? = ""
            relTwoStr =
                listitems[PreferenceManager.getbuttonninetextimage(mContext)!!.toInt()].uppercase(
                    Locale.ROOT
                )
            reltxtnine.text = relTwoStr
            reltxtnine.setTextColor(ContextCompat.getColor(mContext, R.color.white))
            relnine.setBackgroundColor(
                PreferenceManager.getButtonnineGuestBg(mContext)
            )
        }


    }

    private fun setdraglisteners() {
        relone.setOnDragListener(DropListener())
        reltwo.setOnDragListener(DropListener())
        relthree.setOnDragListener(DropListener())
        relfour.setOnDragListener(DropListener())
        relfive.setOnDragListener(DropListener())
        relsix.setOnDragListener(DropListener())
        relseven.setOnDragListener(DropListener())
        releight.setOnDragListener(DropListener())
        relnine.setOnDragListener(DropListener())
    }

    @Suppress("EqualsBetweenInconvertibleTypes")
    class DropListener : View.OnDragListener {
        override fun onDrag(v: View?, event: DragEvent?): Boolean {

            when (event?.action) {
                DragEvent.ACTION_DRAG_STARTED -> Log.d("DRAG", "START")
                DragEvent.ACTION_DRAG_ENTERED -> Log.d("DRAG", "ENTERED")
                DragEvent.ACTION_DRAG_EXITED -> Log.d("DRAG", "EXITED")
                DragEvent.ACTION_DROP -> {
                    val intArray = IntArray(2)
                    v?.getLocationInWindow(intArray)
                    val x = intArray[0]
                    val y = intArray[1]
                    val sPosition = homeActivity.sPosition
                    getButtonViewTouched(x.toFloat().toInt(), y.toFloat().toInt())
                    mSectionText[0] = relTxtone.text.toString().uppercase(Locale.ENGLISH)
                    mSectionText[1] = relTxttwo.text.toString().uppercase(Locale.ENGLISH)
                    mSectionText[2] = relTxtthree.text.toString().uppercase(Locale.ENGLISH)
                    mSectionText[3] = relTxtfour.text.toString().uppercase(Locale.ENGLISH)
                    mSectionText[4] = relTxtfive.text.toString().uppercase(Locale.ENGLISH)
                    mSectionText[5] = relTxtsix.text.toString().uppercase(Locale.ENGLISH)
                    mSectionText[6] = relTxtseven.text.toString().uppercase(Locale.ENGLISH)
                    mSectionText[7] = relTxteight.text.toString().uppercase(Locale.ENGLISH)
                    mSectionText[8] = reltxtnine.text.toString().uppercase(Locale.ENGLISH)

                    for (i in mSectionText.indices) {
                        isDraggable = true
                        if (mSectionText[i].equals(
                                listitems[homeActivity.sPosition], ignoreCase = true
                            )
                        ) {
                            isDraggable = false
                            break
                        }
                    }
                    if (isDraggable) {
                        getButtonDrawablesAndText(TouchedView, homeActivity.sPosition)

                    } else {

                        Toast.makeText(mContext, "Item Already Exists !!!", Toast.LENGTH_SHORT)
                            .show()
                    }

                }

                DragEvent.ACTION_DRAG_ENDED -> Log.d("DRAG", "END")


            }


            return true

        }

        private fun getButtonDrawablesAndText(touchedView: View, sPosition: Int) {
            if (sPosition != 0) {
                if (touchedView == relone) {
                    relImgone.setImageDrawable(mListImgArrays.getDrawable(sPosition))
                    val relstring: String
                    if (listitems[sPosition] == "CCAs") {
                        relstring = "CCAS"
                    } else if (listitems[sPosition] == "Student Information") {
                        relstring = "STUDENT INFORMATION"
                    } else {
                        relstring = listitems[sPosition].uppercase(Locale.getDefault())
                    }
                    relTxtone.text = relstring
                    getTabId(listitems[sPosition])
                    PreferenceManager.setbuttononetabid(mContext, TAB_ID)
                    //setBackgroundColorForView(appController.listitemArrays[sPosition], relone)
                    setBackgroundColorForView(listitems[sPosition], relone)
                    PreferenceManager.setbuttononetextimage(mContext, sPosition.toString())
                } else if (touchedView == reltwo) {
                    relImgtwo.setImageDrawable(mListImgArrays.getDrawable(sPosition))
                    val relstring: String
                    if (listitems[sPosition] == "CCAs") {
                        relstring = "CCAS"
                    } else if (listitems[sPosition] == "Student Information") {
                        relstring = "STUDENT INFORMATION"
                    } else {
                        relstring = listitems[sPosition].uppercase(Locale.getDefault())
                    }
                    relTxttwo.text = relstring
                    getTabId(listitems[sPosition])
                    PreferenceManager.setbuttontwotabid(mContext, TAB_ID)
                    setBackgroundColorForView(listitems[sPosition], reltwo)
                    PreferenceManager.setbuttontwotextimage(mContext, sPosition.toString())
                } else if (touchedView == relthree) {
                    relImgthree.setImageDrawable(mListImgArrays.getDrawable(sPosition))
                    val relstring: String
                    if (listitems[sPosition] == "CCAs") {
                        relstring = "CCAS"
                    } else if (listitems[sPosition] == "Student Information") {
                        relstring = "STUDENT INFORMATION"
                    } else {
                        relstring = listitems[sPosition].uppercase(Locale.getDefault())
                    }
                    relTxtthree.text = relstring
                    getTabId(listitems[sPosition])
                    PreferenceManager.setbuttonthreetabid(mContext, TAB_ID)
                    setBackgroundColorForView(listitems[sPosition], relthree)
                    PreferenceManager.setbuttonthreetextimage(mContext, sPosition.toString())
                } else if (touchedView == relfour) {
                    relImgfour.setImageDrawable(mListImgArrays.getDrawable(sPosition))
                    val relstring: String
                    if (listitems[sPosition] == "CCAs") {
                        relstring = "CCAS"
                    } else if (listitems[sPosition] == "Student Information") {
                        relstring = "STUDENT INFORMATION"
                    } else {
                        relstring = listitems[sPosition].uppercase(Locale.getDefault())
                    }
                    relTxtfour.text = relstring
                    getTabId(listitems[sPosition])
                    PreferenceManager.setbuttonfourtabid(mContext, TAB_ID)
                    setBackgroundColorForView(listitems[sPosition], relfour)
                    PreferenceManager.setbuttonfourtextimage(mContext, sPosition.toString())
                } else if (touchedView == relfive) {
                    relImgfive.setImageDrawable(mListImgArrays.getDrawable(sPosition))
                    val relstring: String
                    if (listitems[sPosition] == "CCAs") {
                        relstring = "CCAS"
                    } else if (listitems[sPosition] == "Student Information") {
                        relstring = "STUDENT INFORMATION"
                    } else {
                        relstring = listitems[sPosition].uppercase(Locale.getDefault())
                    }
                    relTxtfive.text = relstring
                    getTabId(listitems[sPosition])
                    PreferenceManager.setbuttonfivetabid(mContext, TAB_ID)
                    setBackgroundColorForView(listitems[sPosition], relfive)
                    PreferenceManager.setbuttonfivetextimage(mContext, sPosition.toString())
                } else if (touchedView == relsix) {
                    relImgsix.setImageDrawable(mListImgArrays.getDrawable(sPosition))
                    val relstring: String
                    if (listitems[sPosition] == "CCAs") {
                        relstring = "CCAS"
                    } else if (listitems[sPosition] == "Student Information") {
                        relstring = "STUDENT INFORMATION"
                    } else {
                        relstring = listitems[sPosition].uppercase(Locale.getDefault())
                    }
                    relTxtsix.text = relstring
                    getTabId(listitems[sPosition])
                    PreferenceManager.setbuttonsixtabid(mContext, TAB_ID)
                    setBackgroundColorForView(listitems[sPosition], relsix)
                    PreferenceManager.setbuttonsixtextimage(mContext, sPosition.toString())
                } else if (touchedView == relseven) {
                    relImgseven.setImageDrawable(mListImgArrays.getDrawable(sPosition))
                    val relstring: String
                    if (listitems[sPosition] == "CCAs") {
                        relstring = "CCAS"
                    } else if (listitems[sPosition] == "Student Information") {
                        relstring = "STUDENT INFORMATION"
                    } else {
                        relstring = listitems[sPosition].uppercase(Locale.getDefault())
                    }
                    relTxtseven.text = relstring
                    getTabId(listitems[sPosition])
                    PreferenceManager.setbuttonseventabid(mContext, TAB_ID)
                    setBackgroundColorForView(listitems[sPosition], relseven)
                    PreferenceManager.setbuttonseventextimage(mContext, sPosition.toString())
                } else if (touchedView == releight) {
                    relImgeight.setImageDrawable(mListImgArrays.getDrawable(sPosition))
                    val relstring: String
                    if (listitems[sPosition] == "CCAs") {
                        relstring = "CCAS"
                    } else if (listitems[sPosition] == "Student Information") {
                        relstring = "STUDENT INFORMATION"
                    } else {
                        relstring = listitems[sPosition].uppercase(Locale.getDefault())
                    }
                    relTxteight.text = relstring
                    getTabId(listitems[sPosition])
                    PreferenceManager.setbuttoneighttabid(mContext, TAB_ID)
                    setBackgroundColorForView(listitems[sPosition], releight)
                    PreferenceManager.setbuttoneighttextimage(mContext, sPosition.toString())
                } else if (touchedView == relnine) {
                    relImgnine.setImageDrawable(mListImgArrays.getDrawable(sPosition))
                    val relstring: String
                    if (listitems[sPosition] == "CCAs") {
                        relstring = "CCAS"
                    } else if (listitems[sPosition] == "Student Information") {
                        relstring = "STUDENT INFORMATION"
                    } else {
                        relstring = listitems[sPosition].uppercase(Locale.getDefault())
                    }
                    reltxtnine.text = relstring
                    getTabId(listitems[sPosition])
                    PreferenceManager.setbuttonninetabid(mContext, TAB_ID)
                    setBackgroundColorForView(listitems[sPosition], relnine)
                    PreferenceManager.setbuttonninetextimage(mContext, sPosition.toString())
                }

            }
        }

        private fun setBackgroundColorForView(s: String, v: View) {
            if (v == relone) {
                v.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent))
                saveButtonBgColor(v, ContextCompat.getColor(mContext, R.color.transparent))
            } else if (v == reltwo) {
                v.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent))
                saveButtonBgColor(v, ContextCompat.getColor(mContext, R.color.transparent))
            } else if (v == relthree) {
                v.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent))
                saveButtonBgColor(v, ContextCompat.getColor(mContext, R.color.transparent))
            } else if (v == relfour) {
                v.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent))
                saveButtonBgColor(v, ContextCompat.getColor(mContext, R.color.transparent))
            } else if (v == relfive) {
                v.setBackgroundColor(ContextCompat.getColor(mContext, R.color.rel_five))
                saveButtonBgColor(v, ContextCompat.getColor(mContext, R.color.transparent))
            } else if (v == relsix) {
                v.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent))
                saveButtonBgColor(v, ContextCompat.getColor(mContext, R.color.transparent))
            } else if (v == relseven) {
                v.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent))
                saveButtonBgColor(v, ContextCompat.getColor(mContext, R.color.transparent))
            } else if (v == releight) {
                v.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent))
                saveButtonBgColor(v, ContextCompat.getColor(mContext, R.color.transparent))
            } else if (v == relnine) {
                v.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent))
                saveButtonBgColor(v, ContextCompat.getColor(mContext, R.color.transparent))
            }
        }

        private fun saveButtonBgColor(v: View, color: Int) {
            if (v == relone) {
                PreferenceManager.setButtonOneGuestBg(mContext, color)
            } else if (v == reltwo) {
                PreferenceManager.setButtontwoGuestBg(mContext, color)
            } else if (v == relthree) {
                PreferenceManager.setButtonthreeGuestBg(mContext, color)
            } else if (v == relfour) {
                PreferenceManager.setButtonfourGuestBg(mContext, color)
            } else if (v == relfive) {
                PreferenceManager.setButtonfiveGuestBg(mContext, color)
            } else if (v == relsix) {
                PreferenceManager.setButtonsixGuestBg(mContext, color)
            } else if (v == relseven) {
                PreferenceManager.setButtonsevenGuestBg(mContext, color)
            } else if (v == releight) {
                PreferenceManager.setButtoneightGuestBg(mContext, color)
            } else if (v == relnine) {
                PreferenceManager.setButtonnineGuestBg(mContext, color)
            }

        }

        private fun getTabId(textdata: String) {
            when {


                textdata.equals(ConstantWords.calendar, ignoreCase = true) -> {
                    TAB_ID = ConstantWords.TAB_CALENDAR

                }

                textdata.equals(ConstantWords.notification, ignoreCase = true) -> {
                    TAB_ID = ConstantWords.TAB_NOTIFICATIONS
                }

                textdata.equals(ConstantWords.communications, ignoreCase = true) -> {
                    TAB_ID = ConstantWords.TAB_COMMUNICATION
                }

                textdata.equals(ConstantWords.lunchbox, ignoreCase = true) -> {
                    TAB_ID = ConstantWords.TAB_LUNCH_BOX
                }

                textdata.equals(ConstantWords.payments, ignoreCase = true) -> {
                    TAB_ID = ConstantWords.TAB_PAYMENTS
                }

                textdata.equals(ConstantWords.busservice, ignoreCase = true) -> {
                    TAB_ID = ConstantWords.TAB_BUS_SERVICE
                }

                textdata.equals(ConstantWords.intention, ignoreCase = true) -> {
                    TAB_ID = ConstantWords.TAB_INTENTIONS
                }

                textdata.equals(ConstantWords.studentinformation, ignoreCase = true) -> {
                    TAB_ID = ConstantWords.TAB_STUDENT_INFORMATION
                }

                textdata.equals(ConstantWords.timetable, ignoreCase = true) -> {
                    TAB_ID = ConstantWords.TAB_TIMETABLE
                }

                textdata.equals(ConstantWords.performingarts, ignoreCase = true) -> {
                    TAB_ID = ConstantWords.TAB_PERFORMINGARTS
                }

                textdata.equals(ConstantWords.shop, ignoreCase = true) -> {
                    TAB_ID = ConstantWords.TAB_SHOP
                }

                textdata.equals(ConstantWords.gallery, ignoreCase = true) -> {
                    TAB_ID = ConstantWords.TAB_GALLERY
                }

                textdata.equals(ConstantWords.about_us, ignoreCase = true) -> {
                    TAB_ID = ConstantWords.TAB_ABOUT_US
                }

                textdata.equals(ConstantWords.contact_us, ignoreCase = true) -> {
                    TAB_ID = ConstantWords.TAB_CONTACT_US

                }

                textdata.equals(ConstantWords.parentessential, ignoreCase = true) -> {
                    TAB_ID = ConstantWords.TAB_PARENT_ESSENTIAL

                }

                textdata.equals(ConstantWords.absence_earlypickup, ignoreCase = true) -> {
                    TAB_ID = ConstantWords.TAB_ABSENCE
                }

                textdata.equals(ConstantWords.enrichment, ignoreCase = true) -> {
                    TAB_ID = ConstantWords.TAB_ENRICHMENT
                }

                textdata.equals(ConstantWords.parentmeetings, ignoreCase = true) -> {
                    TAB_ID = ConstantWords.TAB_PARENT_MEETINGS
                }

                textdata.equals(ConstantWords.permission_forms, ignoreCase = true) -> {
                    TAB_ID = ConstantWords.TAB_PERMISSION_FORMS
                }

                textdata.equals(ConstantWords.reports, ignoreCase = true) -> {
                    TAB_ID = ConstantWords.TAB_REPORTS
                }
            }

        }

        private fun getButtonViewTouched(centerX: Int, centerY: Int) {
            val array1 = IntArray(2)
            relone.getLocationInWindow(array1)
            val x1: Int = array1[0]
            val x2 = x1 + relone.width
            val y1: Int = array1[1]
            val y2 = y1 + relone.height

            val array2 = IntArray(2)
            reltwo.getLocationInWindow(array2)
            val x3: Int = array2[0]
            val x4 = x3 + reltwo.width
            val y3: Int = array2[1]
            val y4 = y3 + reltwo.height

            val array3 = IntArray(2)
            relthree.getLocationInWindow(array3)
            val x5: Int = array3[0]
            val x6 = x5 + relthree.width
            val y5: Int = array3[1]
            val y6 = y5 + relthree.height

            val array4 = IntArray(2)
            relfour.getLocationInWindow(array4)
            val x7: Int = array4[0]
            val x8 = x7 + relfour.width
            val y7: Int = array4[1]
            val y8 = y7 + relfour.height

            val array5 = IntArray(2)
            relfive.getLocationInWindow(array5)
            val x9: Int = array5[0]
            val x10 = x9 + relfive.width
            val y9: Int = array5[1]
            val y10 = y9 + relfive.height

            val array6 = IntArray(2)
            relsix.getLocationInWindow(array6)
            val x11: Int = array6[0]
            val x12 = x11 + relsix.width
            val y11: Int = array6[1]
            val y12 = y11 + relsix.height

            val array7 = IntArray(2)
            relseven.getLocationInWindow(array7)
            val x13: Int = array7[0]
            val x14 = x13 + relseven.width
            val y13: Int = array7[1]
            val y14 = y13 + relseven.height

            val array8 = IntArray(2)
            releight.getLocationInWindow(array8)
            val x15: Int = array8[0]
            val x16 = x15 + releight.width
            val y15: Int = array8[1]
            val y16 = y15 + releight.height

            val array9 = IntArray(2)
            relnine.getLocationInWindow(array9)
            val x17: Int = array9[0]
            val x18 = x17 + relnine.width
            val y17: Int = array9[1]
            val y18 = y17 + relnine.height

            if (centerX in x1..x2 && y1 <= centerY && centerY <= y2) {
                TouchedView = relone
            } else if (centerX in x3..x4 && y3 <= centerY && centerY <= y4) {
                TouchedView = reltwo
            } else if (centerX in x5..x6 && y5 <= centerY && centerY <= y6) {
                TouchedView = relthree
            } else if (centerX in x7..x8 && y7 <= centerY && centerY <= y8) {
                TouchedView = relfour
            } else if (centerX in x9..x10 && y9 <= centerY && centerY <= y10) {
                TouchedView = relfive
            } else if (centerX in x11..x12 && y11 <= centerY && centerY <= y12) {
                TouchedView = relsix
            } else if (centerX in x13..x14 && y13 <= centerY && centerY <= y14) {
                TouchedView = relseven
            } else if (centerX in x15..x16 && y15 <= centerY && centerY <= y16) {
                TouchedView = releight
            } else if (centerX in x17..x18 && y17 <= centerY && centerY <= y18) {
                TouchedView = relnine
            }

        }

    }

    @SuppressLint("UseRequireInsteadOfGet")
    private fun initializeUI() {
        pager = view!!.findViewById<ViewPager>(R.id.bannerImagePager)
        relone = view!!.findViewById(R.id.relOne) as RelativeLayout
        reltwo = view!!.findViewById(R.id.relTwo) as RelativeLayout
        relthree = view!!.findViewById(R.id.relThree) as RelativeLayout
        relfour = view!!.findViewById(R.id.relFour) as RelativeLayout
        relfive = view!!.findViewById(R.id.relFive) as RelativeLayout
        relsix = view!!.findViewById(R.id.relSix) as RelativeLayout
        relseven = view!!.findViewById(R.id.relSeven) as RelativeLayout
        releight = view!!.findViewById(R.id.relEight) as RelativeLayout
        relnine = view!!.findViewById(R.id.relNine) as RelativeLayout

        relTxtone = view!!.findViewById(R.id.relTxtOne) as TextView
        relTxttwo = view!!.findViewById(R.id.relTxtTwo) as TextView
        relTxtthree = view!!.findViewById(R.id.relTxtThree) as TextView
        relTxtfour = view!!.findViewById(R.id.relTxtFour) as TextView
        relTxtfive = view!!.findViewById(R.id.relTxtFive) as TextView
        relTxtsix = view!!.findViewById(R.id.relTxtSix) as TextView
        relTxtseven = view!!.findViewById(R.id.relTxtSeven) as TextView
        relTxteight = view!!.findViewById(R.id.relTxtEight) as TextView
        reltxtnine = view!!.findViewById(R.id.relTxtNine) as TextView

        relImgone = view!!.findViewById(R.id.relImgOne) as ImageView
        relImgtwo = view!!.findViewById(R.id.relImgTwo) as ImageView
        relImgthree = view!!.findViewById(R.id.relImgThree) as ImageView
        relImgfour = view!!.findViewById(R.id.relImgFour) as ImageView
        relImgfive = view!!.findViewById(R.id.relImgFive) as ImageView
        relImgsix = view!!.findViewById(R.id.relImgSix) as ImageView
        relImgseven = view!!.findViewById(R.id.relImgSeven) as ImageView
        relImgeight = view!!.findViewById(R.id.relImgEight) as ImageView
        relImgnine = view!!.findViewById(R.id.relImgNine) as ImageView
        mSectionText = arrayOfNulls(9)

        pager_rel = view!!.findViewById(R.id.pager_rel)
        updatedata()


        pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int, positionOffset: Float, positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                currentPage = position
            }

        })

    }

    fun updatedata() {
        val handler = Handler()


        val update = Runnable {
            if (currentPage == bannerarray.size) {
                currentPage = 0
                pager.setCurrentItem(
                    currentPage, true
                )
            } else {
                pager.setCurrentItem(currentPage++, true)
            }
        }
        val swipetimer = Timer()

        swipetimer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(update)
            }
        }, 100, 6000)

    }


    private fun CHECKINTENTVALUE(intentTabId: String) {
        TAB_ID = intentTabId
        var mFragment: Fragment? = null
        if (PreferenceManager.getaccesstoken(mContext).equals("")) {
            when (intentTabId) {
                ConstantWords.TAB_NOTIFICATIONS -> {
                    DialogFunctions.commonErrorAlertDialog(
                        mContext.resources.getString(R.string.alert),
                        mContext.resources.getString(R.string.feature_only_for_registered_user),
                        mContext
                    )

                }

                ConstantWords.TAB_CALENDAR -> {
                    DialogFunctions.commonErrorAlertDialog(
                        mContext.resources.getString(R.string.alert),
                        mContext.resources.getString(R.string.feature_only_for_registered_user),
                        mContext
                    )

                }

                ConstantWords.TAB_PAYMENTS -> {
                    DialogFunctions.commonErrorAlertDialog(
                        mContext.resources.getString(R.string.alert),
                        mContext.resources.getString(R.string.feature_only_for_registered_user),
                        mContext
                    )

                }

                ConstantWords.TAB_ABSENCE -> {
                    DialogFunctions.commonErrorAlertDialog(
                        mContext.resources.getString(R.string.alert),
                        mContext.resources.getString(R.string.feature_only_for_registered_user),
                        mContext
                    )

                }

                ConstantWords.TAB_ENRICHMENT -> {
                    DialogFunctions.commonErrorAlertDialog(
                        mContext.resources.getString(R.string.alert),
                        mContext.resources.getString(R.string.feature_only_for_registered_user),
                        mContext
                    )

                }

                ConstantWords.TAB_PARENT_MEETINGS -> {
                    DialogFunctions.commonErrorAlertDialog(
                        mContext.resources.getString(R.string.alert),
                        mContext.resources.getString(R.string.feature_only_for_registered_user),
                        mContext
                    )

                }

                ConstantWords.TAB_PERMISSION_FORMS -> {
                    DialogFunctions.commonErrorAlertDialog(
                        mContext.resources.getString(R.string.alert),
                        mContext.resources.getString(R.string.feature_only_for_registered_user),
                        mContext
                    )

                }

                ConstantWords.TAB_GALLERY -> {
                    DialogFunctions.commonErrorAlertDialog(
                        mContext.resources.getString(R.string.alert),
                        mContext.resources.getString(R.string.feature_only_for_registered_user),
                        mContext
                    )

                }

                ConstantWords.TAB_LUNCH_BOX -> {
                    DialogFunctions.commonErrorAlertDialog(
                        mContext.resources.getString(R.string.alert),
                        mContext.resources.getString(R.string.feature_only_for_registered_user),
                        mContext
                    )

                }

                ConstantWords.TAB_PARENT_ESSENTIAL -> {
                    DialogFunctions.commonErrorAlertDialog(
                        mContext.resources.getString(R.string.alert),
                        mContext.resources.getString(R.string.feature_only_for_registered_user),
                        mContext
                    )

                }

                ConstantWords.TAB_ABOUT_US -> {
                    mFragment = AboutUsFragment()
                    fragmentIntent(mFragment)
                }


                ConstantWords.TAB_CONTACT_US -> {
                    if (ActivityCompat.checkSelfPermission(
                            mContext, Manifest.permission.ACCESS_FINE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                            mContext, Manifest.permission.ACCESS_COARSE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                            mContext, Manifest.permission.CALL_PHONE
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        checkpermission()


                    } else {
                        mFragment = ContactUsFragment()
                        fragmentIntent(mFragment)
                    }
                }

                ConstantWords.TAB_LUNCH_BOX -> {
//                    mFragment = CanteenFragment()
//                    fragmentIntent(mFragment)

                    DialogFunctions.commonErrorAlertDialog(
                        "Coming Soon!", "This Feature will be available shortly", mContext
                    )

                }


            }
        } else {
            when (intentTabId) {

                ConstantWords.TAB_NOTIFICATIONS -> {
                    mFragment = NotificationFragment()
                    fragmentIntent(mFragment)
                }

                ConstantWords.TAB_CALENDAR -> {

                    if (ActivityCompat.checkSelfPermission(
                            mContext, Manifest.permission.READ_CALENDAR
                        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                            mContext, Manifest.permission.WRITE_CALENDAR
                        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                            mContext, Manifest.permission.READ_EXTERNAL_STORAGE
                        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                            mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        checkpermissionCal()

                    } else {
                        mFragment = CalendarFragment()
                        fragmentIntent(mFragment)
                    }

                }

                ConstantWords.TAB_PAYMENTS -> {
                    mFragment = PaymentFragment()
                    fragmentIntent(mFragment)
                }

                ConstantWords.TAB_COMMUNICATION -> {
                    mFragment = CommunicationFragment()
                    fragmentIntent(mFragment)
                }
                ConstantWords.TAB_BUS_SERVICE -> {
                    mFragment = BusServiceFragment()
                    fragmentIntent(mFragment)
                }

                ConstantWords.TAB_REENROLMENT -> {
                    getReEnrollmentStatus()
                }

                ConstantWords.TAB_INTENTIONS -> {
                    mFragment = Intentionfragment()
                    fragmentIntent(mFragment)
                }

                ConstantWords.TAB_STUDENT_INFORMATION -> {
                    mFragment = StudentInformationFragment()
                    fragmentIntent(mFragment)
                }

                ConstantWords.TAB_TIMETABLE -> {
                    mFragment = TimeTableFragment()
                    fragmentIntent(mFragment)
                }

                ConstantWords.TAB_SHOP -> {
                    mFragment = ShopFragment()
                    fragmentIntent(mFragment)
                    //  mFragment = ShopFragment()
                    //  fragmentIntent(mFragment)
                }

                ConstantWords.TAB_PARENT_ESSENTIAL -> {
                    mFragment = ParentsEssentialFragment()
                    fragmentIntent(mFragment)
                }

                ConstantWords.TAB_GALLERY -> {
//                    DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert),mContext.resources.getString(R.string.feature_only_for_registered_user),context)
                    mFragment = GalleryFragment()
                    fragmentIntent(mFragment)
                }

                ConstantWords.TAB_ABOUT_US -> {
                    mFragment = AboutUsFragment()
                    fragmentIntent(mFragment)
                }

                ConstantWords.TAB_PERFORMINGARTS -> {
                    mFragment = PerformingArtsFragment()
                    fragmentIntent(mFragment)
                }

                ConstantWords.TAB_ABSENCE -> {
                    PreferenceManager.setStudentID(mContext, "")
                    mFragment = AbsenceFragment()
                    fragmentIntent(mFragment)
                }

                ConstantWords.TAB_ENRICHMENT -> {
                    mFragment = CCAFragment()
                    fragmentIntent(mFragment)
                }

                ConstantWords.TAB_PARENT_MEETINGS -> {
                    mFragment = ParentMeetingsFragment()
                    fragmentIntent(mFragment)
                }

                ConstantWords.TAB_PERMISSION_FORMS -> {
                    PreferenceManager.setStudentID(mContext, "")
                    mFragment = PermissionSlipFragmentNew()
                    fragmentIntent(mFragment)
                }

                ConstantWords.TAB_LUNCH_BOX -> {

                    mFragment = CanteenFragment()
                    fragmentIntent(mFragment)
//                    DialogFunctions.commonErrorAlertDialog("Coming Soon!","This Feature will be available shortly.",mContext)

                }

                ConstantWords.TAB_REPORTS -> {
                    PreferenceManager.setStudentID(mContext, "")
                    mFragment = ReportsFragment()
                    fragmentIntent(mFragment)
//                    DialogFunctions.commonErrorAlertDialog("Coming Soon!","This Feature will be available shortly.",mContext)

                }

                ConstantWords.TAB_CONTACT_US -> {
                    if (ActivityCompat.checkSelfPermission(
                            mContext, Manifest.permission.ACCESS_FINE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                            mContext, Manifest.permission.ACCESS_COARSE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                            mContext, Manifest.permission.CALL_PHONE
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        checkpermission()


                    } else {
                        mFragment = ContactUsFragment()
                        fragmentIntent(mFragment)
                    }
                }
            }

        }
    }


}

private fun showPopUpImage(notice: String, context: Context) {
    val dialog = Dialog(context)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.setCancelable(false)
    dialog.setContentView(R.layout.dialog_notice)
    var bannerImg = dialog.findViewById(R.id.bannerImg) as ImageView
    var closeImg = dialog.findViewById(R.id.closeImg) as ImageView
    Glide.with(context).load(notice).centerCrop().into(bannerImg)
    closeImg.setOnClickListener {

        if (PreferenceManager.getSurvey(mContext) === 1) {
            if (PreferenceManager.getIsSurveyHomeVisible(mContext)) {

            } else {
                if (ConstantFunctions.internetCheck(mContext)) {
                    callSurveyApi()
                } else {
                    DialogFunctions.showInternetAlertDialog(mContext)
                }
            }
        } else {

        }
        dialog.dismiss()

    }


    Handler().postDelayed({
        if (PreferenceManager.getSurvey(mContext) === 1) {
            if (PreferenceManager.getIsSurveyHomeVisible(mContext)) {

            } else {
                if (ConstantFunctions.internetCheck(mContext)) {
                    callSurveyApi()
                } else {
                    DialogFunctions.showInternetAlertDialog(mContext)
                }
            }
        } else {

        }
        dialog.dismiss()

    }, NOTICE_TIME_OUT)


    dialog.show()
}

fun callSurveyApi() {
    surveyArrayList = ArrayList()
    var model = SurveyApiModel("16")

    val call: Call<SurveyResponseModel> =
        ApiClient.getClient.survey(model, "Bearer " + PreferenceManager.getaccesstoken(mContext))
    call.enqueue(object : Callback<SurveyResponseModel> {
        override fun onFailure(call: Call<SurveyResponseModel>, t: Throwable) {

        }

        override fun onResponse(
            call: Call<SurveyResponseModel>, response: Response<SurveyResponseModel>
        ) {
            val responsedata = response.body()

            if (responsedata != null) {
                try {

                    if (response.body()!!.status == 100) {
                        PreferenceManager.setIsSurveyHomeVisible(mContext, true)

                        if (response.body()!!.responseArray!!.data!!.size > 0) {
                            surveySize = response.body()!!.responseArray!!.data!!.size
                            for (i in response.body()!!.responseArray!!.data!!.indices) {
                                // val dataObject = dataArray.getJSONObject(i)
                                surveyEmail =
                                    response.body()!!.responseArray!!.data!!.get(i).contact_email
                                val model = SurveyDetailDataModel()
                                model.id = (response.body()!!.responseArray!!.data!!.get(i).id)
                                model.survey_name =
                                    (response.body()!!.responseArray!!.data!!.get(i).survey_name)
                                model.image =
                                    (response.body()!!.responseArray!!.data!!.get(i).image)
                                model.title =
                                    (response.body()!!.responseArray!!.data!!.get(i).title)
                                model.description =
                                    (response.body()!!.responseArray!!.data!!.get(i).description)
                                model.created_at =
                                    (response.body()!!.responseArray!!.data!!.get(i).created_at)
                                model.updated_at =
                                    (response.body()!!.responseArray!!.data!!.get(i).updated_at)

                                surveyQuestionArrayList = ArrayList()
                                // val questionsArray = dataObject.getJSONArray("questions")
                                if (response.body()!!.responseArray!!.data!!.get(i).questions!!.size > 0) {
                                    for (j in response.body()!!.responseArray!!.data!!.get(i).questions!!.indices) {
                                        // val questionsObject = questionsArray.getJSONObject(j)
                                        val mModel = SurveyQuestionsModel()
                                        mModel.id =
                                            (response.body()!!.responseArray!!.data!!.get(i).questions!!.get(
                                                j
                                            ).id)
                                        mModel.question =
                                            (response.body()!!.responseArray!!.data!!.get(i).questions!!.get(
                                                j
                                            ).question)
                                        mModel.answer_type =
                                            (response.body()!!.responseArray!!.data!!.get(i).questions!!.get(
                                                j
                                            ).answer_type)
                                        mModel.answer = ("")
                                        surveyAnswersArrayList = ArrayList()

                                        if (response.body()!!.responseArray!!.data!!.get(i).questions!!.get(
                                                j
                                            ).offered_answers!!.size > 0
                                        ) {
                                            for (k in response.body()!!.responseArray!!.data!!.get(i).questions!!.get(
                                                j
                                            ).offered_answers!!.indices) {

                                                val nModel = SurveyOfferedAnswersModel()
                                                nModel.id =
                                                    (response.body()!!.responseArray!!.data!!.get(i).questions!!.get(
                                                        j
                                                    ).offered_answers!!.get(k).id)
                                                nModel.answer =
                                                    (response.body()!!.responseArray!!.data!!.get(i).questions!!.get(
                                                        j
                                                    ).offered_answers!!.get(k).answer)
                                                nModel.label =
                                                    (response.body()!!.responseArray!!.data!!.get(i).questions!!.get(
                                                        j
                                                    ).offered_answers!!.get(k).label)
                                                nModel.is_clicked = (false)
                                                nModel.is_clicked0 = (false)
                                                surveyAnswersArrayList.add(nModel)
                                            }
                                        }
                                        mModel.offered_answers = (surveyAnswersArrayList)
                                        surveyQuestionArrayList.add(mModel)


                                    }
                                }
                                model.questions = (surveyQuestionArrayList)
                                surveyArrayList.add(model)
                            }
                            //showSurvey(getActivity(),surveyArrayList);
                            showSurveyWelcomeDialogue(mContext, surveyArrayList, false)
                        }
                    } else {

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

fun showSurveyWelcomeDialogue(
    activity: Context, surveyArrayList: ArrayList<SurveyDetailDataModel>, isThankyou: Boolean?

) {
//        final Dialog dialog = new Dialog(activity,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
//        dialog.requestWindowFeature(R.style.full_screen_dialog);
    val dialog = Dialog(activity)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.setCancelable(false)
    dialog.setContentView(R.layout.dialog_survey_wlcome)
    val startNowBtn = dialog.findViewById<View>(R.id.startNowBtn) as Button
    val imgClose = dialog.findViewById<View>(R.id.closeImg) as ImageView

    val headingTxt = dialog.findViewById<View>(R.id.titleTxt) as TextView
    val descriptionTxt = dialog.findViewById<View>(R.id.descriptionTxt) as TextView
    //        if (isThankyou)
//		{
//			thankyouTxt.setVisibility(View.VISIBLE);
//			thankyouTxt.setText("Thank you For Submitting your Survey");
//		}
//        else {
//			thankyouTxt.setVisibility(View.GONE);
//		}


    headingTxt.text = surveyArrayList[pos + 1].title
    descriptionTxt.text = surveyArrayList[pos + 1].description
    val bannerImg = dialog.findViewById<View>(R.id.bannerImg) as ImageView
    if (!surveyArrayList[pos + 1].image.equals("")) {
        Glide.with(mContext).load(surveyArrayList[pos + 1].image).centerCrop().into(bannerImg)

    } else {
        bannerImg.setImageResource(R.drawable.survey)
    }
    startNowBtn.setOnClickListener {
        dialog.dismiss()

        if (surveyArrayList.size > 0) {
            pos = pos + 1
            if (pos < surveyArrayList.size) {
                if (surveyArrayList[pos].questions!!.size > 0) {
                    showSurveyQuestionAnswerDialog(
                        activity,
                        surveyArrayList[pos].questions!!,
                        surveyArrayList[pos].survey_name,
                        surveyArrayList[pos].id.toString(),
                        surveyArrayList[pos].contact_email,
                        isThankyou
                    )
                    dialog.dismiss()
                } else {
                    DialogFunctions.commonErrorAlertDialog(
                        mContext.resources.getString(R.string.alert),
                        "No Survey Questions Available.",
                        mContext
                    )
                    dialog.dismiss()
                }

            }
        }
    }
    imgClose.setOnClickListener {
        showCloseSurveyDialog(dialog, isThankyou)
    }
    val skipBtn = dialog.findViewById<View>(R.id.skipBtn) as Button
    skipBtn.setOnClickListener {
        // dialog.dismiss()
        surveySize = surveySize - 1

        if (surveySize <= 0) {
            showCloseSurveyDialog(dialog, false)
        } else {
            showCloseSurveyDialog(dialog, true)
        }
    }
    dialog.show()
}

fun showSurveyQuestionAnswerDialog(
    activity: Context,
    surveyQuestionArrayList: ArrayList<SurveyQuestionsModel>,
    surveyname: String?,
    surveyID: String?,
    contactEmail: String?,
    isThankyou: Boolean?
) {
    val dialog = Dialog(activity)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.setCancelable(false)
    dialog.setContentView(R.layout.dialog_question_answer_survey)
    val surveyPager = dialog.findViewById<View>(R.id.surveyPager) as ViewPager
    val questionCount = dialog.findViewById<View>(R.id.questionCount) as TextView
    val nxtQnt = dialog.findViewById<View>(R.id.nxtQnt) as TextView
    val currentQntTxt = dialog.findViewById<View>(R.id.currentQntTxt) as TextView
    val surveyName = dialog.findViewById<View>(R.id.surveyName) as TextView
    val previousBtn = dialog.findViewById<View>(R.id.previousBtn) as ImageView
    val nextQuestionBtn = dialog.findViewById<View>(R.id.nextQuestionBtn) as ImageView
    val closeImg = dialog.findViewById<View>(R.id.closeImg) as ImageView
    val progressBar = dialog.findViewById<View>(R.id.progressBar) as ProgressBar
    val emailImg = dialog.findViewById<View>(R.id.emailImg) as ImageView

    progressBar.max = surveyQuestionArrayList.size
    progressBar.progressDrawable.setColorFilter(
        mContext.resources.getColor(R.color.rel_one), PorterDuff.Mode.SRC_IN
    )
    closeImg.setOnClickListener {
        surveySize = surveySize - 1
        if (surveySize <= 0) {
            showCloseSurveyDialog(dialog, false)
        } else {
            showCloseSurveyDialog(dialog, true)
        }
        // showCloseSurveyDialog(dialog,true)
    }
    if (surveyQuestionArrayList.size > 9) {
        currentQntTxt.text = "01"
        questionCount.text = "/" + surveyQuestionArrayList.size.toString()
    } else {
        currentQntTxt.text = "01"
        questionCount.text = "/0" + surveyQuestionArrayList.size.toString()
    }
    surveyName.text = surveyname

    if (surveyEmail.equals("")) {
        emailImg.visibility = View.GONE
    } else {
        emailImg.visibility = View.VISIBLE
    }

    emailImg.setOnClickListener {
        showSendEmailDialog()
    }
    //        if (contactEmail.equalsIgnoreCase(""))
//		{
//			emailImg.setVisibility(View.GONE);
//		}
//        else {
//			emailImg.setVisibility(View.GONE);
//		}
    currentPageSurvey = 1
    surveyPager.currentItem = currentPageSurvey - 1
    progressBar.progress = currentPageSurvey
    surveyPager.adapter = SurveyQuestionPagerAdapter(activity, surveyQuestionArrayList)
    if (currentPageSurvey == surveyQuestionArrayList.size) {
        previousBtn.visibility = View.INVISIBLE
        nextQuestionBtn.visibility = View.INVISIBLE
        nxtQnt.visibility = View.VISIBLE
    } else {
        if (currentPageSurvey == 1) {
            previousBtn.visibility = View.INVISIBLE
            nextQuestionBtn.visibility = View.VISIBLE
            nxtQnt.visibility = View.INVISIBLE
        } else {
            previousBtn.visibility = View.INVISIBLE
            nextQuestionBtn.visibility = View.VISIBLE
            nxtQnt.visibility = View.INVISIBLE
        }
    }
    nextQuestionBtn.setOnClickListener {
        if (currentPageSurvey == surveyQuestionArrayList.size) {
        } else {
            currentPageSurvey++
            progressBar.progress = currentPageSurvey
            surveyPager.currentItem = currentPageSurvey - 1
            if (currentPageSurvey == surveyQuestionArrayList.size) {
                nextQuestionBtn.visibility = View.INVISIBLE
                previousBtn.visibility = View.VISIBLE
                nxtQnt.visibility = View.VISIBLE
            } else {
                nextQuestionBtn.visibility = View.VISIBLE
                previousBtn.visibility = View.VISIBLE
                nxtQnt.visibility = View.INVISIBLE
            }
        }
        if (surveyQuestionArrayList.size > 9) {
            if (currentPageSurvey < 9) {
                currentQntTxt.text = "0$currentPageSurvey"
                questionCount.text = "/" + surveyQuestionArrayList.size.toString()
            } else {
                currentQntTxt.text = currentPageSurvey.toString()
                questionCount.text = "/" + surveyQuestionArrayList.size.toString()
            }
        } else {
            if (currentPageSurvey < 9) {
                currentQntTxt.text = "0$currentPageSurvey"
                questionCount.text = "/" + "0" + surveyQuestionArrayList.size.toString()
            } else {
                currentQntTxt.text = currentPageSurvey.toString()
                questionCount.text = "/" + "0" + surveyQuestionArrayList.size.toString()
            }
        }
    }
    previousBtn.setOnClickListener {
        if (currentPageSurvey == 1) {
            previousBtn.visibility = View.INVISIBLE
            nxtQnt.visibility = View.INVISIBLE
            if (currentPageSurvey == surveyQuestionArrayList.size) {
                nxtQnt.visibility = View.VISIBLE
            } else {
                nxtQnt.visibility = View.INVISIBLE
            }
        } else {
            currentPageSurvey--
            progressBar.progress = currentPageSurvey
            surveyPager.currentItem = currentPageSurvey - 1
            if (currentPageSurvey == surveyQuestionArrayList.size) {
                nextQuestionBtn.visibility = View.INVISIBLE
                previousBtn.visibility = View.VISIBLE
                nxtQnt.visibility = View.VISIBLE
            } else {
                if (currentPageSurvey == 1) {
                    previousBtn.visibility = View.INVISIBLE
                    nextQuestionBtn.visibility = View.VISIBLE
                    nxtQnt.visibility = View.INVISIBLE
                } else {
                    nextQuestionBtn.visibility = View.VISIBLE
                    previousBtn.visibility = View.VISIBLE
                    nxtQnt.visibility = View.INVISIBLE
                }
            }
        }
        if (surveyQuestionArrayList.size > 9) {
            if (currentPageSurvey < 9) {
                currentQntTxt.text = "0$currentPageSurvey"
                questionCount.text = "/" + surveyQuestionArrayList.size.toString()
            } else {
                currentQntTxt.text = currentPageSurvey.toString()
                questionCount.text = "/" + surveyQuestionArrayList.size.toString()
            }
        } else {
            if (currentPageSurvey < 9) {
                currentQntTxt.text = "0$currentPageSurvey"
                questionCount.text = "/" + "0" + surveyQuestionArrayList.size.toString()
            } else {
                currentQntTxt.text = currentPageSurvey.toString()
                questionCount.text = "/" + "0" + surveyQuestionArrayList.size.toString()
            }
        }
    }
    nxtQnt.setOnClickListener {
        var isFound = false
        var pos = -1
        var emptyvalue = 0
        for (i in surveyQuestionArrayList.indices) {
            if (surveyQuestionArrayList[i].answer.equals("")) {
                emptyvalue = emptyvalue + 1
                if (!isFound) {
                    isFound = true
                    pos = i
                }
            }
        }
        if (isFound) {
            mAnswerList = ArrayList()
            for (k in surveyQuestionArrayList.indices) {
                val model = SurveySubmitDataModel()
                model.question_id = (surveyQuestionArrayList[k].id.toString())
                model.answer_id = (surveyQuestionArrayList[k].answer)
                mAnswerList.add(model)
            }
            val gson = Gson()
            val PassportArray = ArrayList<String>()
            for (i in mAnswerList.indices) {
                val nmodel = SurveySubmitDataModel()
                nmodel.answer_id = (mAnswerList.get(i).answer_id)
                nmodel.question_id = (mAnswerList.get(i).question_id)
                val json = gson.toJson(nmodel)
                PassportArray.add(i, json)
            }
            val JSON_STRING = "" + PassportArray + ""
            if (emptyvalue == surveyQuestionArrayList.size) {
                val isEmpty = true
                showSurveyContinueDialog(
                    activity,
                    surveyID!!,
                    JSON_STRING,
                    surveyArrayList,
                    progressBar,
                    surveyPager,
                    surveyQuestionArrayList,
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
                    activity,
                    surveyID!!,
                    JSON_STRING,
                    surveyArrayList,
                    progressBar,
                    surveyPager,
                    surveyQuestionArrayList,
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
                mAnswerList = ArrayList()
                for (k in surveyQuestionArrayList.indices) {
                    val model = SurveySubmitDataModel()
                    model.question_id = (surveyQuestionArrayList[k].id.toString())
                    model.answer_id = (surveyQuestionArrayList[k].answer)
                    mAnswerList.add(model)
                }
                val gson = Gson()
                val PassportArray = ArrayList<String>()
                for (i in mAnswerList.indices) {
                    val nmodel = SurveySubmitDataModel()
                    nmodel.answer_id = (mAnswerList.get(i).answer_id)
                    nmodel.question_id = (mAnswerList.get(i).question_id)
                    val json = gson.toJson(nmodel)
                    PassportArray.add(i, json)
                }
                val JSON_STRING = "" + PassportArray + ""
                dialog.dismiss()

                if (ConstantFunctions.internetCheck(mContext)) {
                    callSurveySubmitApi(
                        surveyID!!, JSON_STRING, false, 1, mAnswerList, surveyArrayList
                    )
                } else {
                    DialogFunctions.showInternetAlertDialog(mContext)
                }

            } else {
                mAnswerList = ArrayList()
                for (k in surveyQuestionArrayList.indices) {
                    val model = SurveySubmitDataModel()
                    model.question_id = (surveyQuestionArrayList[k].id.toString())
                    model.answer_id = (surveyQuestionArrayList[k].answer)
                    mAnswerList.add(model)
                }
                val gson = Gson()
                val PassportArray = ArrayList<String>()
                for (i in mAnswerList.indices) {
                    val nmodel = SurveySubmitDataModel()
                    nmodel.answer_id = (mAnswerList.get(i).answer_id)
                    nmodel.question_id = (mAnswerList.get(i).question_id)
                    val json = gson.toJson(nmodel)
                    PassportArray.add(i, json)
                }
                val JSON_STRING = "" + PassportArray + ""
                dialog.dismiss()

                if (ConstantFunctions.internetCheck(mContext)) {
                    callSurveySubmitApi(
                        surveyID!!, JSON_STRING, true, 1, mAnswerList, surveyArrayList
                    )
                } else {
                    DialogFunctions.showInternetAlertDialog(mContext)
                }

            }
        }
    }
    dialog.show()
}

fun callSurveySubmitApi(
    survey_ID: String,
    JSON_STRING: String,
    isThankyou: Boolean,
    status: Int,
    mAnswerList: ArrayList<SurveySubmitDataModel>,
    surveyArrayList: ArrayList<SurveyDetailDataModel>
) {
    //surveyDetailQuestionsArrayList= ArrayList()
    currentPageSurvey = 0

    val paramObject = JsonObject().apply {
        addProperty("data", JSON_STRING)
        addProperty("survey_id", survey_ID)
        addProperty("survey_satisfaction_status", status)

    }

    var model = SurveySubmitApiModel(survey_ID.toString(), status.toString(), mAnswerList)


    val call: Call<SurveySubmitResponseModel> = ApiClient.getClient.surveysubmit(
        model, "Bearer " + PreferenceManager.getaccesstoken(mContext)
    )
    call.enqueue(object : Callback<SurveySubmitResponseModel> {
        override fun onFailure(call: Call<SurveySubmitResponseModel>, t: Throwable) {
            // progressDialogAdd.visibility= View.GONE
        }

        override fun onResponse(
            call: Call<SurveySubmitResponseModel>, response: Response<SurveySubmitResponseModel>
        ) {
            val responsedata = response.body()
            // progressDialogAdd.visibility= View.GONE
            if (responsedata != null) {
                try {

                    if (response.body()!!.status == 100) {

                        showSurveyThankYouDialog(mContext as Activity, isThankyou, surveyArrayList)
                    } else {

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

fun showSurveyThankYouDialog(
    activity: Activity?,
    //  surveyArrayList: ArrayList<SurveyModel?>?,
    isThankyou: Boolean, surveyArrayList: ArrayList<SurveyDetailDataModel>
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
            poss = poss + 1
            showSurveyWelcomeDialogue(mContext, surveyArrayList, false)
        } else {
        }
        dialog.dismiss()
    }
    val emailImg = dialog.findViewById<View>(R.id.emailImg) as ImageView
    if (surveyEmail.equals("", ignoreCase = true)) {
        emailImg.visibility = View.GONE
    } else {
        emailImg.visibility = View.VISIBLE
    }
    emailImg.setOnClickListener {
        showSendEmailDialog()
    }
    dialog.show()
}

private fun showSendEmailDialog() {
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
            DialogFunctions.commonErrorAlertDialog(
                mContext.resources.getString(R.string.alert),
                mContext.resources.getString(R.string.enter_subject),
                mContext
            )


        } else {
            if (text_content.text.toString().trim().equals("")) {
                DialogFunctions.commonErrorAlertDialog(
                    mContext.resources.getString(R.string.alert),
                    mContext.resources.getString(R.string.enter_content),
                    mContext
                )


            } else if (surveyEmail.matches(EMAIL_PATTERN.toRegex())) {
                if (text_dialog.text.toString().trim().matches(pattern.toRegex())) {
                    if (text_content.text.toString().trim().matches(pattern.toRegex())) {

                        if (ConstantFunctions.internetCheck(mContext)) {
                            sendEmail(
                                text_dialog.text.toString().trim(),
                                text_content.text.toString().trim(),
                                surveyEmail,
                                dialog
                            )

                        } else {
                            DialogFunctions.showInternetAlertDialog(mContext)
                        }

                    } else {
                        val toast: Toast = Toast.makeText(
                            mContext,
                            mContext.resources.getString(R.string.enter_valid_contents),
                            Toast.LENGTH_SHORT
                        )
                        toast.show()
                    }
                } else {
                    val toast: Toast = Toast.makeText(
                        mContext, mContext.resources.getString(
                            R.string.enter_valid_subjects
                        ), Toast.LENGTH_SHORT
                    )
                    toast.show()
                }
            } else {
                val toast: Toast = Toast.makeText(
                    mContext, mContext.resources.getString(
                        R.string.enter_valid_email
                    ), Toast.LENGTH_SHORT
                )
                toast.show()
            }
        }


    }
    dialog.show()
}

fun sendEmail(title: String, message: String, staffEmail: String, dialog: Dialog) {
    // progressDialog.visibility = View.VISIBLE
    val sendMailBody = SendEmailApiModel(staffEmail, title, message)
    val call: Call<SignUpResponseModel> = ApiClient.getClient.sendEmailStaff(
        sendMailBody, "Bearer " + PreferenceManager.getaccesstoken(mContext)
    )
    call.enqueue(object : Callback<SignUpResponseModel> {
        override fun onFailure(call: Call<SignUpResponseModel>, t: Throwable) {
            //   progressDialog.visibility = View.GONE
        }

        override fun onResponse(
            call: Call<SignUpResponseModel>, response: Response<SignUpResponseModel>
        ) {
            val responsedata = response.body()
            //   progressDialog.visibility = View.GONE
            if (responsedata != null) {
                try {


                    if (response.body()!!.status == 100) {
                        dialog.dismiss()
                        showSuccessAlert(
                            mContext, "Email sent Successfully ", "Success", dialog
                        )
                    } else {
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

fun showCloseSurveyDialog(dialogW: Dialog, isThankyou: Boolean?) {
    val dialog = Dialog(mContext)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.setCancelable(false)
    dialog.setContentView(R.layout.dialog_survey_close)
    var text_dialog = dialog.findViewById(R.id.text_dialog) as TextView
    var btn_Ok = dialog.findViewById(R.id.btn_Ok) as Button
    var btn_Cancel = dialog.findViewById(R.id.btn_Cancel) as Button
    text_dialog.text = "Are you sure you want to close this survey."

    btn_Ok.setOnClickListener {
        if (isThankyou!!) {
            poss = poss + 1
            showSurveyWelcomeDialogueclose(mContext, surveyArrayList, false)
        } else {
        }
        dialogW.dismiss()
        dialog.dismiss()
    }


    btn_Cancel.setOnClickListener {
        dialog.dismiss()
    }
    dialog.show()
}

fun showSurveyWelcomeDialogueclose(
    mContext: Context, surveyArrayList: ArrayList<SurveyDetailDataModel>, isThankyou: Boolean
) {
    //  final Dialog dialog = new Dialog(activity,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
//        dialog.requestWindowFeature(R.style.full_screen_dialog);
    val dialog = Dialog(mContext)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.setCancelable(false)
    dialog.setContentView(R.layout.dialog_survey_wlcome)
    val startNowBtn = dialog.findViewById<View>(R.id.startNowBtn) as Button
    val imgClose = dialog.findViewById<View>(R.id.closeImg) as ImageView

    val headingTxt = dialog.findViewById<View>(R.id.titleTxt) as TextView
    val descriptionTxt = dialog.findViewById<View>(R.id.descriptionTxt) as TextView
    //        if (isThankyou)
//		{
//			thankyouTxt.setVisibility(View.VISIBLE);
//			thankyouTxt.setText("Thank you For Submitting your Survey");
//		}
//        else {
//			thankyouTxt.setVisibility(View.GONE);
//		}

    headingTxt.text = surveyArrayList[poss].title
    descriptionTxt.text = surveyArrayList[poss].description
    val bannerImg = dialog.findViewById<View>(R.id.bannerImg) as ImageView
    if (!surveyArrayList[poss].image.equals("")) {
        Glide.with(mContext).load(surveyArrayList[poss].image).centerCrop().into(bannerImg)

    } else {
        bannerImg.setImageResource(R.drawable.survey)
    }
    startNowBtn.setOnClickListener {
        dialog.dismiss()

        if (surveyArrayList.size > 0) {
            // poss = poss + 1
            if (poss < surveyArrayList.size) {
                if (surveyArrayList[poss].questions!!.size > 0) {
                    showSurveyQuestionAnswerDialog(
                        mContext,
                        surveyArrayList[poss].questions!!,
                        surveyArrayList[poss].survey_name,
                        surveyArrayList[poss].id.toString(),
                        surveyArrayList[poss].contact_email,
                        isThankyou
                    )
                    dialog.dismiss()
                } else {
                    DialogFunctions.commonErrorAlertDialog(
                        mContext.resources.getString(R.string.alert),
                        "No Survey Questions Available.",
                        mContext
                    )
                    dialog.dismiss()
                }

            }
        }
    }
    imgClose.setOnClickListener {
        showCloseSurveyDialog(dialog, isThankyou)
    }
    val skipBtn = dialog.findViewById<View>(R.id.skipBtn) as Button
    skipBtn.setOnClickListener {
        // dialog.dismiss()
        surveySize = surveySize - 1
        if (surveySize <= 0) {
            showCloseSurveyDialog(dialog, false)
        } else {
            showCloseSurveyDialog(dialog, true)
        }
    }
    dialog.show()
}

fun showSurveyContinueDialog(
    activity: Context,
    surveyID: String,
    JSON_STRING: String?,
    surveyArrayList: ArrayList<SurveyDetailDataModel>,
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
    val dialog = Dialog(activity)
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

                if (ConstantFunctions.internetCheck(mContext)) {
                    callSurveySubmitApi(
                        surveyID, JSON_STRING!!, false, 1, mAnswerList, surveyArrayList
                    )
                } else {
                    DialogFunctions.showInternetAlertDialog(mContext)
                }

            } else {

                if (ConstantFunctions.internetCheck(mContext)) {
                    callSurveySubmitApi(
                        surveyID, JSON_STRING!!, true, 1, mAnswerList, surveyArrayList
                    )
                } else {
                    DialogFunctions.showInternetAlertDialog(mContext)
                }

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
                currentQntTxt.text = currentPageSurvey.toString()
                questionCount.text = "/" + surveyQuestionArrayList.size.toString()
            }
        } else {
            if (currentPageSurvey < 9) {
                currentQntTxt.text = "0$currentPageSurvey"
                questionCount.text = "/" + "0" + surveyQuestionArrayList.size.toString()
            } else {
                currentQntTxt.text = currentPageSurvey.toString()
                questionCount.text = "/" + "0" + surveyQuestionArrayList.size.toString()
            }
        }
        dialog.dismiss()
    }
    dialog.show()
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
    btn_Ok.setOnClickListener {
        dialog.dismiss()
        mdialog.dismiss()
    }
    dialog.show()
}

fun fragmentIntent(mFragment: Fragment?) {
    if (mFragment != null) {

        val fragmentManager = homeActivity.supportFragmentManager
        fragmentManager.beginTransaction().add(R.id.fragment_holder, mFragment, "")
            .addToBackStack("").commitAllowingStateLoss() //commit
        //.commit()
    }
}


private fun checkpermission() {
    if (ContextCompat.checkSelfPermission(
            mContext, Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
            mContext, Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
            mContext, Manifest.permission.CALL_PHONE
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        ActivityCompat.requestPermissions(
            homeActivity, arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.CALL_PHONE
            ), 123
        )
    }
}

fun showforceupdate(mContext: Context) {
    val dialog = Dialog(mContext)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.setCancelable(false)
    dialog.setContentView(R.layout.dialog_updateversion)
    val btnUpdate = dialog.findViewById<View>(R.id.btnUpdate) as Button

    btnUpdate.setOnClickListener {
        dialog.dismiss()
        val appPackageName = mContext.packageName
        try {
            mContext.startActivity(
                Intent(
                    Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName")
                )
            )

        } catch (e: android.content.ActivityNotFoundException) {
            mContext.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                )
            )
        }

    }
    dialog.show()
}

private fun checkpermissionCal() {
    if (ContextCompat.checkSelfPermission(
            mContext, Manifest.permission.READ_CALENDAR
        ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
            mContext, Manifest.permission.WRITE_CALENDAR
        ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
            mContext, Manifest.permission.READ_EXTERNAL_STORAGE
        ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
            mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        ActivityCompat.requestPermissions(
            homeActivity, arrayOf(
                Manifest.permission.READ_CALENDAR,
                Manifest.permission.WRITE_CALENDAR,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ), 123
        )
    }


}