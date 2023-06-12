package com.nas.alreem.fragment.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.TypedArray
import android.graphics.Color
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
import com.google.android.material.internal.ContextUtils.getActivity
import com.nas.alreem.BuildConfig
import com.nas.alreem.R
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.activity.login.LoginActivity
import com.nas.alreem.activity.settings.TutorialActivity
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.ConstantWords
import com.nas.alreem.constants.DialogFunctions
import com.nas.alreem.constants.PreferenceManager
import com.nas.alreem.fragment.about_us.AboutUsFragment
import com.nas.alreem.fragment.about_us.model.AboutUsResponseModel
import com.nas.alreem.fragment.calendar.CalendarFragment
import com.nas.alreem.fragment.canteen.CanteenFragment
import com.nas.alreem.fragment.contact_us.ContactUsFragment
import com.nas.alreem.fragment.early_years.EarlyYearsFragment
import com.nas.alreem.fragment.gallery.GalleryFragment
import com.nas.alreem.fragment.home.model.BannerResponseModel
import com.nas.alreem.fragment.notifications.NotificationFragment
import com.nas.alreem.fragment.notifications.adapter.NotificationListAdapter
import com.nas.alreem.fragment.notifications.model.NotificationApiModel
import com.nas.alreem.fragment.notifications.model.NotificationResponseModel
import com.nas.alreem.fragment.parents_essentials.ParentsEssentialFragment
import com.nas.alreem.fragment.payments.PaymentFragment
import com.nas.alreem.fragment.payments.model.PaymentResponseModel
import com.nas.alreem.fragment.primary.PrimaryFragment
import com.nas.alreem.fragment.secondary.SecondaryFragment
import com.nas.alreem.rest.ApiClient
import okhttp3.ResponseBody
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
                Log.e("Failed", t.localizedMessage)
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
                            if(notice.equals(""))
                            {

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
                ConstantWords.TAB_LUNCH_BOX -> {

                    mFragment = CanteenFragment()
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
        dialog.dismiss()
    }


    Handler().postDelayed({

      dialog.dismiss()

    }, NOTICE_TIME_OUT)


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