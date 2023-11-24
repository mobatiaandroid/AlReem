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
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.nas.alreem.BuildConfig
import com.nas.alreem.R
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.activity.login.model.SignUpResponseModel
import com.nas.alreem.activity.survey.adapter.SurveyQuestionPagerAdapter
import com.nas.alreem.activity.survey.model.*
import com.nas.alreem.constants.ApiClient
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.ConstantWords
import com.nas.alreem.constants.DialogFunctions
import com.nas.alreem.constants.PreferenceManager
import com.nas.alreem.fragment.about_us.AboutUsFragment
import com.nas.alreem.fragment.absence.AbsenceFragment
import com.nas.alreem.fragment.calendar.CalendarFragment
import com.nas.alreem.fragment.canteen.CanteenFragment
import com.nas.alreem.fragment.cca.CCAFragment
import com.nas.alreem.fragment.contact_us.ContactUsFragment
import com.nas.alreem.fragment.early_years.EarlyYearsFragment
import com.nas.alreem.fragment.gallery.GalleryFragment
import com.nas.alreem.fragment.home.model.BannerResponseModel
import com.nas.alreem.fragment.notifications.NotificationFragment
import com.nas.alreem.fragment.parent_meetings.ParentMeetingsFragment
import com.nas.alreem.fragment.parents_essentials.ParentsEssentialFragment
import com.nas.alreem.fragment.payments.PaymentFragment
import com.nas.alreem.fragment.payments.model.SendEmailApiModel
import com.nas.alreem.fragment.permission_slip.PermissionSlipFragment
import com.nas.alreem.fragment.primary.PrimaryFragment
import com.nas.alreem.fragment.reports.ReportsFragment
import com.nas.alreem.fragment.secondary.SecondaryFragment
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

var studentName: String=""
var studentId: String=""
var studentImg: String=""
var studentClass: String=""
var studntCount: Int=0


lateinit var pager: ViewPager
var isDraggable: Boolean = false
var bannerarray = ArrayList<String>()
lateinit var mContext: Context
lateinit var current_date:String
var currentPage: Int = 0
private val NOTICE_TIME_OUT:Long = 5000
var currentPageSurvey = 0
var survey_satisfation_status = 0
private var surveyEmail = ""
private var surveySize = 0
var pos = -1
var poss = 0
lateinit var surveyArrayList: ArrayList<SurveyDetailDataModel>
lateinit var surveyQuestionArrayList: ArrayList<SurveyQuestionsModel>
lateinit var surveyAnswersArrayList: ArrayList<SurveyOfferedAnswersModel>
lateinit var mAnswerList: ArrayList<SurveySubmitDataModel>

class HomeFragment : Fragment() , View.OnClickListener{

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_home_screen, container, false)
    }

    @SuppressLint("UseRequireInsteadOfGet", "Recycle")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeActivity =  activity as HomeActivity
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

    fun getbannerimages()
    {
        val call: Call<BannerResponseModel> = ApiClient.getClient.bannerImages()
        call.enqueue(object : Callback<BannerResponseModel> {
            override fun onFailure(call: Call<BannerResponseModel>, t: Throwable) {
            }
            override fun onResponse(call: Call<BannerResponseModel>, response: Response<BannerResponseModel>) {
                val responsedata = response.body()
                if (responsedata != null) {
                    try {

                        if (response.body()!!.status==100)
                        {
                            bannerarray.addAll(response.body()!!.responseArray!!.banner_images!!)
                            var version=response.body()!!.responseArray!!.android_app_version.toString()
                            var appVersion= BuildConfig.VERSION_NAME.toString()
                            if(!version.equals(appVersion))
                            {
                                showforceupdate(mContext)
                            }

                            if (bannerarray.size>0)
                            {
                                pager.adapter = activity?.let { PageView(it, bannerarray) }
                            }
                            else {
                                pager.setBackgroundResource(R.drawable.default_banner)
                            }
                            var notice=response.body()!!.responseArray!!.notice
                            val survey: Int = response.body()!!.responseArray!!.survey
                            //										int survey=0;
                            PreferenceManager.setSurvey(mContext, survey)
                            if(notice.equals(""))
                            {
                                if (PreferenceManager.getSurvey(mContext) === 1) {
                                    if (PreferenceManager.getIsSurveyHomeVisible(mContext))
                                    {
                                    }
                                    else
                                    {
                                        if (ConstantFunctions.internetCheck(mContext))
                                        {
                                            callSurveyApi()
                                        }
                                        else
                                        {
                                            DialogFunctions.showInternetAlertDialog(mContext)
                                        }

                                    }
                                } else
                                {

                                }
                            }
                            else{
                                if (PreferenceManager.getNoticeFirstTime(mContext).equals(""))
                                {
                                    PreferenceManager.setNoticeFirtTime(mContext,"djhdhhf")
                                    showPopUpImage(notice,mContext)
                                }
                                else{

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

        })
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

        if (PreferenceManager
                .getbuttononetextimage(mContext)!!.toInt() != 0
        ) {
            relImgone.setImageDrawable(
                mListImgArrays.getDrawable(
                    PreferenceManager
                        .getbuttononetextimage(mContext)!!.toInt()
                )
            )
            var relTwoStr: String? = ""
            relTwoStr =
                listitems[PreferenceManager
                    .getbuttononetextimage(mContext)!!.toInt()].uppercase(Locale.getDefault())
            relTxtone.text = relTwoStr
            relTxtone.setTextColor(ContextCompat.getColor(mContext, R.color.white))
            relone.setBackgroundColor(
                PreferenceManager.getButtonOneGuestBg(mContext)
            )
        }
        if (PreferenceManager.getbuttontwotextimage(mContext)!!.toInt() != 0
        ) {
            relImgtwo.setImageDrawable(
                mListImgArrays.getDrawable(
                    PreferenceManager
                        .getbuttontwotextimage(mContext)!!.toInt()
                )
            )
            var relTwoStr: String? = ""
            relTwoStr =
                listitems[PreferenceManager
                    .getbuttontwotextimage(mContext)!!.toInt()].uppercase(Locale.getDefault())

            relTxttwo.text = relTwoStr
            relTxttwo.setTextColor(ContextCompat.getColor(mContext, R.color.white))
            reltwo.setBackgroundColor(
                PreferenceManager
                    .getButtontwoGuestBg(mContext)
            )
        }
        if (PreferenceManager
                .getbuttonthreetextimage(mContext)!!.toInt() != 0
        ) {
            relImgthree.setImageDrawable(
                mListImgArrays.getDrawable(
                    PreferenceManager
                        .getbuttonthreetextimage(mContext)!!.toInt()
                )
            )
            var relTwoStr: String? = ""
            relTwoStr =  listitems[PreferenceManager
                .getbuttonthreetextimage(mContext)!!.toInt()].uppercase(Locale.getDefault())
            relTxtthree.text = relTwoStr
            relTxtthree.setTextColor(ContextCompat.getColor(mContext, R.color.white))
            relthree.setBackgroundColor(
                PreferenceManager
                    .getButtonthreeGuestBg(mContext)
            )
        }


        if (PreferenceManager
                .getbuttonfourtextimage(mContext)!!.toInt() != 0
        ) {
            relImgfour.setImageDrawable(
                mListImgArrays.getDrawable(
                    PreferenceManager
                        .getbuttonfourtextimage(mContext)!!.toInt()
                )
            )
            var relTwoStr: String? = ""
            relTwoStr = listitems[PreferenceManager
                .getbuttonfourtextimage(mContext)!!.toInt()].uppercase(Locale.getDefault())
            relTxtfour.text = relTwoStr
            relTxtfour.setTextColor(ContextCompat.getColor(mContext, R.color.white))
            relfour.setBackgroundColor(
                PreferenceManager
                    .getButtonfourGuestBg(mContext)
            )
        }


        if (PreferenceManager
                .getbuttonfivetextimage(mContext)!!.toInt() != 0
        ) {
            relImgfive.setImageDrawable(
                mListImgArrays.getDrawable(
                    PreferenceManager
                        .getbuttonfivetextimage(mContext)!!.toInt()
                )
            )
            var relTwoStr: String? = ""
            relTwoStr = listitems[PreferenceManager
                .getbuttonfivetextimage(mContext)!!.toInt()].uppercase(Locale.getDefault())
            relTxtfive.text = relTwoStr
            relTxtfive.setTextColor(ContextCompat.getColor(mContext, R.color.white))
            relfive.setBackgroundColor(
                PreferenceManager
                    .getButtonfiveGuestBg(mContext)
            )
        }
        if (PreferenceManager.getbuttonsixtextimage(mContext)!!.toInt() != 0) {
            relImgsix.setImageDrawable(
                mListImgArrays.getDrawable(
                    PreferenceManager
                        .getbuttonsixtextimage(mContext)!!.toInt()
                )
            )
            var relTwoStr: String? = ""
            relTwoStr =  listitems[PreferenceManager
                .getbuttonsixtextimage(mContext)!!.toInt()].uppercase(Locale.ROOT)
            relTxtsix.text = relTwoStr
            relTxtsix.setTextColor(ContextCompat.getColor(mContext, R.color.white))
            relsix.setBackgroundColor(
                PreferenceManager
                    .getButtonsixGuestBg(mContext)
            )
        }
        if (PreferenceManager
                .getbuttonseventextimage(mContext)!!.toInt() != 0
        ) {
            relImgseven.setImageDrawable(
                mListImgArrays.getDrawable(
                    PreferenceManager
                        .getbuttonseventextimage(mContext)!!.toInt()
                )
            )
            var relTwoStr: String? = ""
            relTwoStr = listitems[PreferenceManager
                .getbuttonseventextimage(mContext)!!.toInt()].uppercase(Locale.getDefault())
            relTxtseven.text = relTwoStr
            relTxtseven.setTextColor(ContextCompat.getColor(mContext, R.color.white))
            relseven.setBackgroundColor(
                PreferenceManager
                    .getButtonsevenGuestBg(mContext)
            )
        }
        if (PreferenceManager
                .getbuttoneighttextimage(mContext)!!.toInt() != 0
        ) {
            relImgeight.setImageDrawable(
                mListImgArrays.getDrawable(
                    PreferenceManager
                        .getbuttoneighttextimage(mContext)!!.toInt()
                )
            )
            var relTwoStr: String? = ""
            relTwoStr =  listitems[PreferenceManager
                .getbuttoneighttextimage(mContext)!!.toInt()].uppercase(Locale.getDefault())
            relTxteight.text = relTwoStr
            relTxteight.setTextColor(ContextCompat.getColor(mContext, R.color.white))
            releight.setBackgroundColor(
                PreferenceManager
                    .getButtoneightGuestBg(mContext)
            )
        }
        if (PreferenceManager
                .getbuttonninetextimage(mContext)!!.toInt() != 0
        ) {
            relImgnine.setImageDrawable(
                mListImgArrays.getDrawable(
                    PreferenceManager
                        .getbuttonninetextimage(mContext)!!.toInt()
                )
            )
            var relTwoStr: String? = ""
            relTwoStr =   listitems[PreferenceManager
                .getbuttonninetextimage(mContext)!!.toInt()].uppercase(Locale.ROOT)
            reltxtnine.text = relTwoStr
            reltxtnine.setTextColor(ContextCompat.getColor(mContext, R.color.white))
            relnine.setBackgroundColor(
                PreferenceManager
                    .getButtonnineGuestBg(mContext)
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
                                listitems[homeActivity.sPosition],
                                ignoreCase = true
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
                textdata.equals(ConstantWords.lunchbox, ignoreCase = true) -> {
                    TAB_ID = ConstantWords.TAB_LUNCH_BOX
                }
                textdata.equals(ConstantWords.payments, ignoreCase = true) -> {
                    TAB_ID = ConstantWords.TAB_PAYMENTS
                }
                textdata.equals(ConstantWords.early_years, ignoreCase = true) -> {
                    TAB_ID = ConstantWords.TAB_EARLY_YEARS
                }
                textdata.equals(ConstantWords.primary, ignoreCase = true) -> {
                    TAB_ID = ConstantWords.TAB_PRIMARY
                }
                textdata.equals(ConstantWords.secondary, ignoreCase = true) -> {
                    TAB_ID = ConstantWords.TAB_SECONDARY
                }
                textdata.equals(ConstantWords.gallery, ignoreCase = true) -> {
                    TAB_ID = ConstantWords.TAB_GALLERY
                }
                textdata.equals(ConstantWords.about_us, ignoreCase = true) -> {
                    TAB_ID = ConstantWords.TAB_ABOUT_US
                }

                textdata.equals(ConstantWords.contact_us, ignoreCase = true) -> {
                    TAB_ID = ConstantWords.TAB_CONTACT_US

                } textdata.equals(ConstantWords.parentessential, ignoreCase = true) -> {
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

        pager_rel=view!!.findViewById(R.id.pager_rel)
        updatedata()


        pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
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
                    currentPage,
                    true
                )
            } else {
                pager
                    .setCurrentItem(currentPage++, true)
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
                    DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert),mContext.resources.getString(R.string.feature_only_for_registered_user),mContext)

                }
                ConstantWords.TAB_CALENDAR -> {
                    DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert),mContext.resources.getString(R.string.feature_only_for_registered_user),mContext)

                }

                ConstantWords.TAB_PAYMENTS -> {
                    DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert),mContext.resources.getString(R.string.feature_only_for_registered_user),mContext)

                }
                ConstantWords.TAB_ABSENCE -> {
                    DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert),mContext.resources.getString(R.string.feature_only_for_registered_user),mContext)

                }
                ConstantWords.TAB_ENRICHMENT -> {
                    DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert),mContext.resources.getString(R.string.feature_only_for_registered_user),mContext)

                }
                ConstantWords.TAB_PARENT_MEETINGS -> {
                    DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert),mContext.resources.getString(R.string.feature_only_for_registered_user),mContext)

                }
                ConstantWords.TAB_PERMISSION_FORMS -> {
                    DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert),mContext.resources.getString(R.string.feature_only_for_registered_user),mContext)

                }
                ConstantWords.TAB_EARLY_YEARS -> {
                    mFragment = EarlyYearsFragment()
                    fragmentIntent(mFragment)
                }
                ConstantWords.TAB_PRIMARY -> {
                    mFragment = PrimaryFragment()
                    fragmentIntent(mFragment)
                }
                ConstantWords.TAB_SECONDARY -> {
                    mFragment = SecondaryFragment()
                    fragmentIntent(mFragment)
                }
                ConstantWords.TAB_GALLERY -> {
                    DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert),mContext.resources.getString(R.string.feature_only_for_registered_user),mContext)

                }
                ConstantWords.TAB_LUNCH_BOX -> {
                    DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert),mContext.resources.getString(R.string.feature_only_for_registered_user),mContext)

                }
                ConstantWords.TAB_PARENT_ESSENTIAL -> {
                    DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert),mContext.resources.getString(R.string.feature_only_for_registered_user),mContext)

                }
                ConstantWords.TAB_ABOUT_US -> {
                    mFragment = AboutUsFragment()
                    fragmentIntent(mFragment)
                }


                ConstantWords.TAB_CONTACT_US -> {
                    if (ActivityCompat.checkSelfPermission(
                            mContext,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                            mContext,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                            mContext,
                            Manifest.permission.CALL_PHONE
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

                 DialogFunctions.commonErrorAlertDialog("Coming Soon!","This Feature will be available shortly",
                     mContext)

                }



            }
        } else {
            when (intentTabId) {

                ConstantWords.TAB_NOTIFICATIONS -> {
                    mFragment = NotificationFragment()
                    fragmentIntent(mFragment)
                }
                ConstantWords.TAB_CALENDAR -> {

                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_CALENDAR)
                        != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                            mContext, Manifest.permission.WRITE_CALENDAR)
                        != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                            mContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(
                            mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    )
                    {
                        checkpermissionCal()

                    }
                    else
                    {
                        mFragment = CalendarFragment()
                        fragmentIntent(mFragment)
                    }

                }

                ConstantWords.TAB_PAYMENTS -> {
                    mFragment = PaymentFragment()
                    fragmentIntent(mFragment)
                }
                ConstantWords.TAB_EARLY_YEARS -> {
                    mFragment = EarlyYearsFragment()
                    fragmentIntent(mFragment)
                }
                ConstantWords.TAB_PRIMARY -> {
                    mFragment = PrimaryFragment()
                    fragmentIntent(mFragment)
                }
                ConstantWords.TAB_SECONDARY -> {
                    mFragment = SecondaryFragment()
                    fragmentIntent(mFragment)
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
                ConstantWords.TAB_ABSENCE -> {
                    PreferenceManager.setStudentID(mContext,"")
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
                    PreferenceManager.setStudentID(mContext,"")
                    mFragment = PermissionSlipFragment()
                    fragmentIntent(mFragment)
                }
                ConstantWords.TAB_LUNCH_BOX -> {

                    mFragment = CanteenFragment()
                    fragmentIntent(mFragment)
//                    DialogFunctions.commonErrorAlertDialog("Coming Soon!","This Feature will be available shortly.",mContext)

                }
                ConstantWords.TAB_REPORTS -> {
                    PreferenceManager.setStudentID(mContext,"")
                    mFragment = ReportsFragment()
                    fragmentIntent(mFragment)
//                    DialogFunctions.commonErrorAlertDialog("Coming Soon!","This Feature will be available shortly.",mContext)

                }
                ConstantWords.TAB_CONTACT_US -> {
                    if (ActivityCompat.checkSelfPermission(
                            mContext,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                            mContext,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                            mContext,
                            Manifest.permission.CALL_PHONE
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

private fun showPopUpImage(notice:String,context: Context)
{
    val dialog = Dialog(context)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.setCancelable(false)
    dialog.setContentView(R.layout.dialog_notice)
    var bannerImg = dialog.findViewById(R.id.bannerImg) as ImageView
    var closeImg = dialog.findViewById(R.id.closeImg) as ImageView
    Glide.with(context).load(notice).centerCrop().into(bannerImg)
    closeImg.setOnClickListener()
    {

        if (PreferenceManager.getSurvey(mContext) === 1)
        {
            if (PreferenceManager.getIsSurveyHomeVisible(mContext))
            {

            }
            else
            {
                if (ConstantFunctions.internetCheck(mContext))
                {
                    callSurveyApi()
                }
                else
                {
                    DialogFunctions.showInternetAlertDialog(mContext)
                }
            }
        }
        else
        {

        }
        dialog.dismiss()

    }


    Handler().postDelayed({
        if (PreferenceManager.getSurvey(mContext) === 1) {
            if (PreferenceManager.getIsSurveyHomeVisible(mContext))
            {

            }
            else
            {
                if (ConstantFunctions.internetCheck(mContext))
                {
                    callSurveyApi()
                }
                else
                {
                    DialogFunctions.showInternetAlertDialog(mContext)
                }
            }
        }
        else
        {

        }
      dialog.dismiss()

    }, NOTICE_TIME_OUT)


    dialog.show()
}

fun callSurveyApi() {
    surveyArrayList = ArrayList()
    var model= SurveyApiModel("16")

    val call: Call<SurveyResponseModel> = ApiClient.getClient.survey(model,"Bearer "+ PreferenceManager.getaccesstoken(mContext))
    call.enqueue(object : Callback<SurveyResponseModel> {
        override fun onFailure(call: Call<SurveyResponseModel>, t: Throwable) {

        }
        override fun onResponse(call: Call<SurveyResponseModel>, response: Response<SurveyResponseModel>) {
            val responsedata = response.body()

            if (responsedata != null) {
                try {

                    if (response.body()!!.status==100)
                    {
                        PreferenceManager.setIsSurveyHomeVisible(mContext, true)

                        if (response.body()!!.responseArray!!.data!!.size > 0) {
                            surveySize = response.body()!!.responseArray!!.data!!.size
                            for (i in response.body()!!.responseArray!!.data!!.indices) {
                               // val dataObject = dataArray.getJSONObject(i)
                                surveyEmail = response.body()!!.responseArray!!.data!!.get(i).contact_email
                                val model = SurveyDetailDataModel()
                                model.id=(response.body()!!.responseArray!!.data!!.get(i).id)
                                model.survey_name=(response.body()!!.responseArray!!.data!!.get(i).survey_name)
                                model.image=(response.body()!!.responseArray!!.data!!.get(i).image)
                                model.title=(response.body()!!.responseArray!!.data!!.get(i).title)
                                model.description=(response.body()!!.responseArray!!.data!!.get(i).description)
                                model.created_at=(response.body()!!.responseArray!!.data!!.get(i).created_at)
                                model.updated_at=(response.body()!!.responseArray!!.data!!.get(i).updated_at)

                                surveyQuestionArrayList = ArrayList()
                               // val questionsArray = dataObject.getJSONArray("questions")
                                if (response.body()!!.responseArray!!.data!!.get(i).questions!!.size > 0) {
                                    for (j in  response.body()!!.responseArray!!.data!!.get(i).questions!!.indices) {
                                       // val questionsObject = questionsArray.getJSONObject(j)
                                        val mModel = SurveyQuestionsModel()
                                        mModel.id=(response.body()!!.responseArray!!.data!!.get(i).questions!!.get(j).id)
                                        mModel.question=(response.body()!!.responseArray!!.data!!.get(i).questions!!.get(j).question)
                                        mModel.answer_type=(response.body()!!.responseArray!!.data!!.get(i).questions!!.get(j).answer_type)
                                        mModel.answer=("")
                                        surveyAnswersArrayList = ArrayList()

                                        if (response.body()!!.responseArray!!.data!!.get(i).questions!!.get(j).offered_answers!!.size>0) {
                                            for (k in response.body()!!.responseArray!!.data!!.get(i).questions!!.get(j).offered_answers!!.indices) {

                                                val nModel = SurveyOfferedAnswersModel()
                                                nModel.id=(response.body()!!.responseArray!!.data!!.get(i).questions!!.get(j).offered_answers!!.get(k).id)
                                                nModel.answer=(response.body()!!.responseArray!!.data!!.get(i).questions!!.get(j).offered_answers!!.get(k).answer)
                                                nModel.label=(response.body()!!.responseArray!!.data!!.get(i).questions!!.get(j).offered_answers!!.get(k).label)
                                                nModel.is_clicked=(false)
                                                nModel.is_clicked0=(false)
                                                surveyAnswersArrayList.add(nModel)
                                            }
                                        }
                                        mModel.offered_answers=(surveyAnswersArrayList)
                                        surveyQuestionArrayList.add(mModel)


                                    }
                                }
                                model.questions=(surveyQuestionArrayList)
                                surveyArrayList.add(model)
                            }
                            //showSurvey(getActivity(),surveyArrayList);
                            showSurveyWelcomeDialogue(mContext, surveyArrayList, false)
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
fun showSurveyWelcomeDialogue(
    activity: Context,
    surveyArrayList: ArrayList<SurveyDetailDataModel>,
    isThankyou: Boolean?

) {
//        final Dialog dialog = new Dialog(activity,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
//        dialog.requestWindowFeature(R.style.full_screen_dialog);
    val dialog = Dialog(activity!!)
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


    headingTxt.setText(surveyArrayList[pos + 1].title)
    descriptionTxt.setText(surveyArrayList[pos + 1].description)
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
                if(surveyArrayList[pos].questions!!.size>0)
                {
                    showSurveyQuestionAnswerDialog(
                        activity,
                        surveyArrayList[pos].questions!!,
                        surveyArrayList[pos].survey_name,
                        surveyArrayList[pos].id.toString(),
                        surveyArrayList[pos].contact_email,isThankyou
                    )
                    dialog.dismiss()
                }
                else{
                    DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert), "No Survey Questions Available.", mContext)
                    dialog.dismiss()
                }

            }
        }
    }
    imgClose.setOnClickListener {
        showCloseSurveyDialog(dialog,isThankyou)
    }
    val skipBtn = dialog.findViewById<View>(R.id.skipBtn) as Button
    skipBtn.setOnClickListener {
       // dialog.dismiss()
        surveySize = surveySize - 1

        if (surveySize <= 0) {
            showCloseSurveyDialog(dialog,false)
        }
        else
        {
            showCloseSurveyDialog(dialog,true)
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
    val dialog = Dialog(activity!!)
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
        mContext.resources.getColor(R.color.rel_one),
        PorterDuff.Mode.SRC_IN
    )
    closeImg.setOnClickListener {
        surveySize = surveySize - 1
        if (surveySize <= 0) {
            showCloseSurveyDialog(dialog,false)
        }
        else
        {
            showCloseSurveyDialog(dialog,true)
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

    if (surveyEmail.equals(""))
    {
        emailImg.visibility=View.GONE
    }
    else{
        emailImg.visibility=View.VISIBLE
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
                model.question_id=(surveyQuestionArrayList[k].id.toString())
                model.answer_id=(surveyQuestionArrayList[k].answer)
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
                    val model =SurveySubmitDataModel()
                    model.question_id=(surveyQuestionArrayList[k].id.toString())
                    model.answer_id=(surveyQuestionArrayList[k].answer)
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
                dialog.dismiss()

                if (ConstantFunctions.internetCheck(mContext))
                {
                    callSurveySubmitApi(
                        surveyID!!,
                        JSON_STRING,
                        false,
                        1,mAnswerList,surveyArrayList)
                }
                else
                {
                    DialogFunctions.showInternetAlertDialog(mContext)
                }

            } else {
                mAnswerList = ArrayList()
                for (k in surveyQuestionArrayList.indices) {
                    val model = SurveySubmitDataModel()
                    model.question_id=(surveyQuestionArrayList[k].id.toString())
                    model.answer_id=(surveyQuestionArrayList[k].answer)
                    mAnswerList.add(model)
                }
                val gson = Gson()
                val PassportArray = ArrayList<String>()
                for (i in mAnswerList.indices) {
                    val nmodel =SurveySubmitDataModel()
                    nmodel.answer_id=(mAnswerList.get(i).answer_id)
                    nmodel.question_id=(mAnswerList.get(i).question_id)
                    val json = gson.toJson(nmodel)
                    PassportArray.add(i, json)
                }
                val JSON_STRING = "" + PassportArray + ""
                dialog.dismiss()

                if (ConstantFunctions.internetCheck(mContext))
                {
                    callSurveySubmitApi(
                        surveyID!!,
                        JSON_STRING,
                        true,
                        1, mAnswerList, surveyArrayList
                    )
                }
                else
                {
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
)
{
    //surveyDetailQuestionsArrayList= ArrayList()
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
           // progressDialogAdd.visibility= View.GONE
        }
        override fun onResponse(call: Call<SurveySubmitResponseModel>, response: Response<SurveySubmitResponseModel>) {
            val responsedata = response.body()
           // progressDialogAdd.visibility= View.GONE
            if (responsedata != null) {
                try {

                    if (response.body()!!.status==100)
                    {

                        showSurveyThankYouDialog(mContext as Activity, isThankyou,surveyArrayList)
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
    isThankyou: Boolean,
    surveyArrayList: ArrayList<SurveyDetailDataModel>
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
            poss=poss+1
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
            DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert), mContext.resources.getString(R.string.enter_subject), mContext)


        } else {
            if (text_content.text.toString().trim().equals("")) {
                DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert), mContext.resources.getString(R.string.enter_content), mContext)

            } else {
                // progressDialog.visibility = View.VISIBLE

                sendEmail(text_dialog.text.toString().trim(), text_content.text.toString().trim(), surveyEmail, dialog)
            }
        }
    }
    dialog.show()
}

fun sendEmail(title: String, message: String,  staffEmail: String, dialog: Dialog)
{
   // progressDialog.visibility = View.VISIBLE
    val sendMailBody = SendEmailApiModel( staffEmail, title, message)
    val call: Call<SignUpResponseModel> = ApiClient.getClient.sendEmailStaff(sendMailBody, "Bearer " + PreferenceManager.getaccesstoken(mContext))
    call.enqueue(object : Callback<SignUpResponseModel> {
        override fun onFailure(call: Call<SignUpResponseModel>, t: Throwable) {
         //   progressDialog.visibility = View.GONE
        }

        override fun onResponse(call: Call<SignUpResponseModel>, response: Response<SignUpResponseModel>) {
            val responsedata = response.body()
         //   progressDialog.visibility = View.GONE
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
fun showCloseSurveyDialog(dialogW: Dialog, isThankyou: Boolean?)
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
        if (isThankyou!!) {
            poss = poss + 1
            showSurveyWelcomeDialogueclose(mContext, surveyArrayList, false)
        } else {
        }
        dialogW.dismiss()
        dialog.dismiss()
    }


    btn_Cancel.setOnClickListener()
    {
        dialog.dismiss()
    }
    dialog.show()
}

fun showSurveyWelcomeDialogueclose(mContext: Context, surveyArrayList: ArrayList<SurveyDetailDataModel>, isThankyou: Boolean) {
  //  final Dialog dialog = new Dialog(activity,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
//        dialog.requestWindowFeature(R.style.full_screen_dialog);
    val dialog = Dialog(mContext!!)
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

    headingTxt.setText(surveyArrayList[poss ].title)
    descriptionTxt.setText(surveyArrayList[poss ].description)
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
                if(surveyArrayList[poss].questions!!.size>0)
                {
                    showSurveyQuestionAnswerDialog(
                        mContext,
                        surveyArrayList[poss].questions!!,
                        surveyArrayList[poss].survey_name,
                        surveyArrayList[poss].id.toString(),
                        surveyArrayList[poss].contact_email,isThankyou
                    )
                    dialog.dismiss()
                }
                else{
                    DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert), "No Survey Questions Available.", mContext)
                    dialog.dismiss()
                }

            }
        }
    }
    imgClose.setOnClickListener {
        showCloseSurveyDialog(dialog,isThankyou)
    }
    val skipBtn = dialog.findViewById<View>(R.id.skipBtn) as Button
    skipBtn.setOnClickListener {
        // dialog.dismiss()
        surveySize = surveySize - 1
        if (surveySize <= 0) {
            showCloseSurveyDialog(dialog,false)
        }
        else
        {
            showCloseSurveyDialog(dialog,true)
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

                if (ConstantFunctions.internetCheck(mContext))
                {
                    callSurveySubmitApi(
                        surveyID!!,
                        JSON_STRING!!,
                        false,
                        1,
                        mAnswerList,
                        surveyArrayList
                    )
                }
                else
                {
                    DialogFunctions.showInternetAlertDialog(mContext)
                }

            } else {

                if (ConstantFunctions.internetCheck(mContext))
                {
                    callSurveySubmitApi(surveyID!!, JSON_STRING!!, true, 1, mAnswerList, surveyArrayList)
                }
                else
                {
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
fun fragmentIntent(mFragment: Fragment?) {
    if (mFragment != null) {

        val fragmentManager = homeActivity.supportFragmentManager
        fragmentManager.beginTransaction()
            .add(R.id.fragment_holder, mFragment, "")
            .addToBackStack("").commitAllowingStateLoss() //commit
        //.commit()
    }
}


private fun checkpermission() {
    if (ContextCompat.checkSelfPermission(
            mContext,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
            mContext,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
            mContext,
            Manifest.permission.CALL_PHONE
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        ActivityCompat.requestPermissions(
            homeActivity,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.CALL_PHONE
            ),
            123
        )
    }
}

fun showforceupdate(mContext: Context) {
    val dialog = Dialog(mContext)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.setCancelable(false)
    dialog.setContentView(R.layout.dialog_updateversion)
    val btnUpdate =
        dialog.findViewById<View>(R.id.btnUpdate) as Button

    btnUpdate.setOnClickListener {
        dialog.dismiss()
        val appPackageName =
            mContext.packageName
        try {
            mContext.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=$appPackageName")
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
            mContext,
            Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
            mContext,
            Manifest.permission.WRITE_CALENDAR
        ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
            mContext,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
            mContext,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) != PackageManager.PERMISSION_GRANTED
    )
    {
        ActivityCompat.requestPermissions(
            homeActivity,
            arrayOf(
                Manifest.permission.READ_CALENDAR,
                Manifest.permission.WRITE_CALENDAR,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ),
            123
        )
    }




}