package com.nas.alreem.activity.cca

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.activity.cca.model.CCAAttendanceModel
import com.nas.alreem.activity.cca.model.CCADetailModel
import com.nas.alreem.activity.cca.model.CCAReviewAfterSubmissionModel
import com.nas.alreem.constants.PreferenceManager
import org.json.JSONObject
import java.util.*

/*
class CCAsReviewAfterSubmissionNoDeleteActivity : Activity(){
    var recyclerViewLayoutManager: GridLayoutManager? = null
    var recycler_review: RecyclerView? = null
   // var headermanager: HeaderManager? = null
    var relativeHeader: RelativeLayout? = null
    var back: ImageView? = null
    var home: ImageView? = null
    var editCcca: RelativeLayout? = null
    var messageTxt: RelativeLayout? = null
    var msgTxt: TextView? = null
    var tab_type: String? = "CCAs"
    var extras: Bundle? = null
   lateinit var mContext: Context
    var mCCADetailModelArrayList: ArrayList<CCAReviewAfterSubmissionModel>? = null
    var textViewCCAaItem: TextView? = null
    var weekList: ArrayList<String>? = null
    var absentDaysChoice2Array: ArrayList<String>? = null
    var presentDaysChoice2Array: ArrayList<String>? = null
    var upcomingDaysChoice2Array: ArrayList<String>? = null
    var absentDaysChoice1Array: ArrayList<String>? = null
    var presentDaysChoice1Array: ArrayList<String>? = null
    var upcomingDaysChoice1Array: ArrayList<String>? = null
    var datestringChoice1: ArrayList<CCAAttendanceModel>? = null
    var datestringChoice2: ArrayList<CCAAttendanceModel>? = null
    var submissiondateover = "-1"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cca_no_edit_delete)
        mContext = this
        extras = intent.extras
        if (extras != null) {
            tab_type = extras!!.getString("tab_type")
            submissiondateover = extras!!.getString("submissiondateover", "-1")
            CCADetailModelArrayList =
                extras!!.getSerializable("CCA_Detail") as ArrayList<CCADetailModel>?
        }
        weekList = ArrayList()
        weekList!!.add("Sunday")
        weekList!!.add("Monday")
        weekList!!.add("Tuesday")
        weekList!!.add("Wednesday")
        weekList!!.add("Thursday")
        weekList!!.add("Friday")
        weekList!!.add("Saturday")
        absentDaysChoice2Array = ArrayList()
        presentDaysChoice2Array = ArrayList()
        upcomingDaysChoice2Array = ArrayList()
        absentDaysChoice1Array = ArrayList()
        presentDaysChoice1Array = ArrayList()
        upcomingDaysChoice1Array = ArrayList()
        datestringChoice1 = ArrayList<CCAAttendanceModel>()
        datestringChoice2 = ArrayList<CCAAttendanceModel>()
        relativeHeader = findViewById<View>(R.id.relativeHeader) as RelativeLayout
        recycler_review = findViewById<View>(R.id.recycler_view_cca) as RecyclerView
        textViewCCAaItem = findViewById<View>(R.id.textViewCCAaItem) as TextView
        messageTxt = findViewById<View>(R.id.messageTxt) as RelativeLayout
        msgTxt = findViewById<View>(R.id.msgTxt) as TextView
        messageTxt!!.visibility = View.VISIBLE
        editCcca = findViewById<View>(R.id.editCcca) as RelativeLayout
        headermanager = HeaderManager(this@CCAsReviewAfterSubmissionNoDeleteActivity, tab_type)
        headermanager.getHeader(relativeHeader, 0)
        back = headermanager.getLeftButton()
        headermanager.setButtonLeftSelector(
            R.drawable.back,
            R.drawable.back
        )
        back!!.setOnClickListener {
            AppUtils.hideKeyBoard(mContext)
            finish()
        }
        editCcca!!.setOnClickListener {
            val intent = Intent(
                mContext,
                CCASelectionActivity::class.java
            )
            intent.putExtra(
                "CCA_Detail",
                CCADetailModelArrayList
            )
            intent.putExtra("tab_type", tab_type)
            intent.putExtra("ccaedit", 1)
            startActivity(intent)
        }
        home = headermanager.getLogoButton()
        home!!.setOnClickListener {
            val `in` = Intent(
                mContext,
                HomeListAppCompatActivity::class.java
            )
            `in`.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(`in`)
        }
        recycler_review!!.setHasFixedSize(true)
        recyclerViewLayoutManager = GridLayoutManager(mContext, 1)
        recycler_review!!.layoutManager = recyclerViewLayoutManager
        mCCADetailModelArrayList = ArrayList<CCAReviewAfterSubmissionModel>()
        //        textViewCCAaItem.setText(Html.fromHtml(PreferenceManager.getCCATitle(mContext) + "<br/>" + PreferenceManager.getStudNameForCCA(mContext)));
        if (PreferenceManager.getStudClassForCCA(mContext).equals("")) {
            textViewCCAaItem!!.text = Html.fromHtml(
                PreferenceManager.getCCATitle(mContext) + "<br/>" + PreferenceManager.getStudNameForCCA(
                    mContext
                )
            )
        } else {
            textViewCCAaItem!!.text = Html.fromHtml(
                PreferenceManager.getCCATitle(mContext) + "<br/>" + PreferenceManager.getStudNameForCCA(
                    mContext
                ) + "<br/>Year Group : " + PreferenceManager.getStudClassForCCA(mContext)
            )
        }
      //  if (AppUtils.isNetworkConnected(mContext)) {
            ccaReviewListAPI()
      //  } else {
      //      AppUtils.showDialogAlertDismiss(
        //        mContext as Activity?,
        //        "Network Error",
        //        getString(R.string.no_internet),
        //        R.drawable.nonetworkicon,
        //        R.drawable.roundred
        //    )
       // }
    }

    private fun ccaReviewListAPI() {
        val volleyWrapper = VolleyWrapper(URL_CCA_REVIEWS)
        val name = arrayOf("access_token", "student_id", "cca_days_id")
        val value = arrayOf<String>(
            PreferenceManager.getAccessToken(mContext),
            PreferenceManager.getStudIdForCCA(mContext),
            PreferenceManager.getCCAItemId(mContext)
        )
        volleyWrapper.getResponsePOST(mContext, 11, name, value, object : ResponseListener() {
            fun responseSuccess(successResponse: String) {
                println("The response is$successResponse")
                try {
                    val obj = JSONObject(successResponse)
                    val response_code = obj.getString(JTAG_RESPONSECODE)
                    if (response_code.equals("200", ignoreCase = true)) {
                        val secobj = obj.getJSONObject(JTAG_RESPONSE)
                        val status_code = secobj.getString(JTAG_STATUSCODE)
                        if (status_code.equals("303", ignoreCase = true)) {
                            val data = secobj.optJSONArray("data")
                            if (data.length() > 0) {
                                for (j in weekList!!.indices) {
                                    for (i in 0 until data.length()) {
                                        val dataObject = data.getJSONObject(i)
                                        if (dataObject.optString("day")
                                                .equals(weekList!![j], ignoreCase = true)
                                        ) {
                                            mCCADetailModelArrayList!!.add(
                                                addCCAReviewlist(
                                                    dataObject
                                                )
                                            )
                                        }
                                    }
                                }
                                if (mCCADetailModelArrayList!!.size > 0) {
                                    messageTxt!!.visibility = View.VISIBLE
                                    val mCCAsActivityAdapter =
                                        CCAfinalReviewEditAfterSubmissionAdapter(
                                            mContext,
                                            mCCADetailModelArrayList
                                        )
                                    recycler_review!!.adapter = mCCAsActivityAdapter
                                }
                            } else {
                                messageTxt!!.visibility = View.GONE
                                Toast.makeText(
                                    this@CCAsReviewAfterSubmissionNoDeleteActivity,
                                    "No EAP available",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    } else if (response_code.equals("500", ignoreCase = true)) {
                        AppUtils.showDialogAlertDismiss(
                            mContext as Activity?,
                            "Alert",
                            getString(R.string.common_error),
                            R.drawable.exclamationicon,
                            R.drawable.round
                        )
                    } else if (response_code.equals("400", ignoreCase = true)) {
                        AppUtils.getToken(mContext, object : GetTokenSuccess() {
                            fun tokenrenewed() {}
                        })
                        ccaReviewListAPI()
                    } else if (response_code.equals("401", ignoreCase = true)) {
                        AppUtils.getToken(mContext, object : GetTokenSuccess() {
                            fun tokenrenewed() {}
                        })
                        ccaReviewListAPI()
                    } else if (response_code.equals("402", ignoreCase = true)) {
                        AppUtils.getToken(mContext, object : GetTokenSuccess() {
                            fun tokenrenewed() {}
                        })
                        ccaReviewListAPI()
                    } else {
                        AppUtils.showDialogAlertDismiss(
                            mContext as Activity?,
                            "Alert",
                            getString(R.string.common_error),
                            R.drawable.exclamationicon,
                            R.drawable.round
                        )
                    }
                } catch (ex: Exception) {
                }
            }

            fun responseFailure(failureResponse: String?) {
                AppUtils.showDialogAlertDismiss(
                    mContext as Activity?,
                    "Alert",
                    getString(R.string.common_error),
                    R.drawable.exclamationicon,
                    R.drawable.round
                )
            }
        })
    }

    private fun addCCAReviewlist(dataObject: JSONObject): CCAReviewAfterSubmissionModel {
        val mCCAModel = CCAReviewAfterSubmissionModel()
        mCCAModel.setDay(dataObject.optString("day"))
        datestringChoice1 = ArrayList<CCAAttendanceModel>()
        datestringChoice2 = ArrayList<CCAAttendanceModel>()
        if (dataObject.has("choice1")) {
            val choice1 = dataObject.optJSONObject("choice1")
            if (choice1 != null) {
                if (choice1.has("cca_item_name")) {
                    mCCAModel.setChoice1(choice1.optString("cca_item_name"))
                    if (choice1.has("cca_details_venue")) {
                        mCCAModel.setVenue(choice1.optString("cca_details_venue"))
                    } else {
                        mCCAModel.setVenue("")
                    }
                    if (choice1.has("cca_item_description")) {
                        mCCAModel.setCca_item_description(choice1.optString("cca_item_description"))
                    } else {
                        mCCAModel.setCca_item_description("")
                    }
                    mCCAModel.setCca_item_start_time(choice1.optString("cca_item_start_time"))
                    mCCAModel.setCca_item_end_time(choice1.optString("cca_item_end_time"))
                    val absentDaysChoice1 = choice1.optJSONArray("absentDays")
                    absentDaysChoice1Array = ArrayList()
                    if (choice1.has("absentDays")) {
                        for (i in 0 until absentDaysChoice1.length()) {
                            absentDaysChoice1Array!!.add(absentDaysChoice1.optString(i))
                        }
                    }
                    presentDaysChoice1Array = ArrayList()
                    if (choice1.has("presentDays")) {
                        val presentDaysChoice1 = choice1.optJSONArray("presentDays")
                        for (i in 0 until presentDaysChoice1.length()) {
                            presentDaysChoice1Array!!.add(presentDaysChoice1.optString(i))
                        }
                    }
                    upcomingDaysChoice1Array = ArrayList()
                    if (choice1.has("upcomingDays")) {
                        val upcomingDaysChoice1 = choice1.optJSONArray("upcomingDays")
                        for (i in 0 until upcomingDaysChoice1.length()) {
                            upcomingDaysChoice1Array!!.add(upcomingDaysChoice1.optString(i))
                        }
                    }
                } else {
                    mCCAModel.setChoice1("0")
                }
            } else {
                mCCAModel.setChoice1("0")
            }
        } else {
            mCCAModel.setChoice1("-1")
        }
        if (dataObject.has("choice2")) {
            val choice2 = dataObject.optJSONObject("choice2")
            if (choice2 != null) {
                if (choice2.has("cca_item_name")) {
                    mCCAModel.setChoice2(choice2.optString("cca_item_name"))
                    if (choice2.has("cca_details_venue")) {
                        mCCAModel.setVenue2(choice2.optString("cca_details_venue"))
                    } else {
                        mCCAModel.setVenue2("")
                    }
                    if (choice2.has("cca_item_description")) {
                        mCCAModel.setCca_item_description_2(choice2.optString("cca_item_description"))
                    } else {
                        mCCAModel.setCca_item_description_2("")
                    }
                    mCCAModel.setCca_item_start_time(choice2.optString("cca_item_start_time"))
                    mCCAModel.setCca_item_end_time(choice2.optString("cca_item_end_time"))
                    val absentDaysChoice2 = choice2.optJSONArray("absentDays")
                    if (choice2.has("absentDays")) {
                        absentDaysChoice2Array = ArrayList()
                        for (i in 0 until absentDaysChoice2.length()) {
                            absentDaysChoice2Array!!.add(absentDaysChoice2.optString(i))
                        }
                    }
                    presentDaysChoice2Array = ArrayList()
                    val presentDaysChoice2 = choice2.optJSONArray("presentDays")
                    if (choice2.has("presentDays")) {
                        for (i in 0 until presentDaysChoice2.length()) {
                            presentDaysChoice2Array!!.add(presentDaysChoice2.optString(i))
                        }
                    }
                    upcomingDaysChoice2Array = ArrayList()
                    val upcomingDaysChoice2 = choice2.optJSONArray("upcomingDays")
                    if (choice2.has("upcomingDays")) {
                        for (i in 0 until upcomingDaysChoice2.length()) {
                            upcomingDaysChoice2Array!!.add(upcomingDaysChoice2.optString(i))
                        }
                    }
                } else {
                    mCCAModel.setChoice2("0")
                }
            } else {
                mCCAModel.setChoice2("0")
            }
        } else {
            mCCAModel.setChoice2("-1")
        }
        if (absentDaysChoice1Array!!.size > 0) {
            for (i in absentDaysChoice1Array!!.indices) {
                val mCCAAttendanceModel = CCAAttendanceModel()
                mCCAAttendanceModel.setDateAttend(absentDaysChoice1Array!![i])
                mCCAAttendanceModel.setStatusCCA("a")
                datestringChoice1!!.add(mCCAAttendanceModel)
            }
        }
        if (upcomingDaysChoice1Array!!.size > 0) {
            for (i in upcomingDaysChoice1Array!!.indices) {
                val mCCAAttendanceModel = CCAAttendanceModel()
                mCCAAttendanceModel.setDateAttend(upcomingDaysChoice1Array!![i])
                mCCAAttendanceModel.setStatusCCA("u")
                datestringChoice1!!.add(mCCAAttendanceModel)
            }
        }
        if (presentDaysChoice1Array!!.size > 0) {
            for (i in presentDaysChoice1Array!!.indices) {
                val mCCAAttendanceModel = CCAAttendanceModel()
                mCCAAttendanceModel.setDateAttend(presentDaysChoice1Array!![i])
                mCCAAttendanceModel.setStatusCCA("p")
                datestringChoice1!!.add(mCCAAttendanceModel)
            }
        }
        if (absentDaysChoice2Array!!.size > 0) {
            for (i in absentDaysChoice2Array!!.indices) {
                val mCCAAttendanceModel = CCAAttendanceModel()
                mCCAAttendanceModel.setDateAttend(absentDaysChoice2Array!![i])
                mCCAAttendanceModel.setStatusCCA("a")
                datestringChoice2!!.add(mCCAAttendanceModel)
            }
        }
        if (upcomingDaysChoice2Array!!.size > 0) {
            for (i in upcomingDaysChoice2Array!!.indices) {
                val mCCAAttendanceModel = CCAAttendanceModel()
                mCCAAttendanceModel.setDateAttend(upcomingDaysChoice2Array!![i])
                mCCAAttendanceModel.setStatusCCA("u")
                datestringChoice2!!.add(mCCAAttendanceModel)
            }
        }
        if (presentDaysChoice2Array!!.size > 0) {
            for (i in presentDaysChoice2Array!!.indices) {
                val mCCAAttendanceModel = CCAAttendanceModel()
                mCCAAttendanceModel.setDateAttend(presentDaysChoice2Array!![i])
                mCCAAttendanceModel.setStatusCCA("p")
                datestringChoice2!!.add(mCCAAttendanceModel)
            }
        }
        if (datestringChoice1!!.size > 0) {
            Collections.sort(datestringChoice1,
                Comparator<Any?> { s1, s2 ->
                    s1.getDateAttend().compareToIgnoreCase(s2.getDateAttend())
                })
        }
        if (datestringChoice2!!.size > 0) {
            Collections.sort(datestringChoice1,
                Comparator<Any?> { s1, s2 ->
                    s1.getDateAttend().compareToIgnoreCase(s2.getDateAttend())
                })
        }
        mCCAModel.setCalendarDaysChoice1(datestringChoice1)
        mCCAModel.setCalendarDaysChoice2(datestringChoice2)
        return mCCAModel
    }

    companion object {
        var CCADetailModelArrayList: ArrayList<CCADetailModel>? = null
    }
}*/
