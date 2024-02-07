package com.nas.alreem.activity.parent_engagement

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.nas.alreem.R
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.activity.parent_engagement.adapter.ParentsAssociationMainRecyclerviewAdapter
import com.nas.alreem.activity.parent_engagement.model.ParentAssociationEventItemsModel
import com.nas.alreem.activity.parent_engagement.model.ParentAssociationEventResponseModel
import com.nas.alreem.activity.parent_engagement.model.ParentAssociationEventsModel
import com.nas.alreem.constants.ApiClient
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.DialogFunctions
import com.nas.alreem.constants.FullscreenWebViewActivityNoHeader
import com.nas.alreem.constants.HeaderManager
import com.nas.alreem.constants.PreferenceManager
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ParentsAssociationListActivity : AppCompatActivity() {
    var facebookurl = ""
    var tab_type: String? = null
    lateinit var relativeHeader: RelativeLayout
    lateinit var headermanager: HeaderManager
    lateinit var mContext: Context
     var mRecyclerView: RecyclerView? = null
    var extras: Bundle? = null
    var mListViewArray: ArrayList<ParentAssociationEventsModel>? = null
    var myFormatCalender = "yyyy-MM-dd"
    var sdfcalender: SimpleDateFormat? = null
    var PtaItemList: GetPtaItemList? = null
    var mParentAssociationEventsModelsArrayList: ArrayList<ParentAssociationEventsModel>? = null
    lateinit var back: ImageView
    lateinit var fbShare:android.widget.ImageView
    lateinit var infoImg:android.widget.ImageView
    lateinit var home:android.widget.ImageView
   // var progressBarDialog: ProgressBarDialog? = null

    interface GetPtaItemList {
        val ptaItemData: Unit
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.parentsactivityrecyclerview)
        mContext = this

        myFormatCalender = "yyyy-MM-dd"
        sdfcalender = SimpleDateFormat(myFormatCalender)

        initialiseUI()
        //        AppUtils.showDialogAlertDismiss((Activity) mContext, "Alert", "Work In Progress", R.drawable.exclamationicon, R.drawable.round);
        if (PreferenceManager.getIsFirstTimeInPE(mContext)) {
            PreferenceManager.setIsFirstTimeInPE(mContext, false)
           /* val mintent = Intent(mContext, PTAinfoActivity::class.java)
            mintent.putExtra("type", 1)
            startActivity(mintent)*/
        } else {

            if (ConstantFunctions.internetCheck(mContext)) {
                callParentAssociationEventList()
            } else {
                DialogFunctions.showInternetAlertDialog(mContext)
            }

        }
    }


    private fun initialiseUI() {
        extras = intent.extras
        if (extras != null) {
            tab_type = extras!!.getString("tab_type")
        }
        relativeHeader = findViewById<View>(R.id.relativeHeader) as RelativeLayout
        mRecyclerView = findViewById<View>(R.id.mRecyclerView) as RecyclerView
        mRecyclerView!!.setHasFixedSize(true)
       // progressBarDialog = ProgressBarDialog(mContext, R.drawable.spinner)
        headermanager = HeaderManager(this@ParentsAssociationListActivity, tab_type)
        headermanager.getHeader(relativeHeader, 3)
        back = headermanager.leftButton!!
        fbShare = headermanager.rightButton!!
        infoImg = headermanager.infoButton!!
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        mRecyclerView!!.layoutManager = llm
        headermanager.setButtonLeftSelector(
            R.drawable.back,
            R.drawable.back
        )
        back!!.setOnClickListener {

            finish()
        }
        home = headermanager.logoButton!!
        home.setOnClickListener(View.OnClickListener {
            val `in` = Intent(
                mContext,
                HomeActivity::class.java
            )
            `in`.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(`in`)
        })
        fbShare.setOnClickListener(View.OnClickListener {
            val mintent = Intent(
                mContext,
                FullscreenWebViewActivityNoHeader::class.java
            )
            mintent.putExtra("url", facebookurl)
            startActivity(mintent)
        })
        infoImg.setOnClickListener(View.OnClickListener {
          /*  val mintent = Intent(mContext, PTAinfoActivity::class.java)
            startActivity(mintent)*/
        })


//        mRecyclerView.addOnItemTouchListener(new RecyclerItemListener(getApplicationContext(), mRecyclerView,
//                new RecyclerItemListener.RecyclerTouchListener() {
//                    public void onClickItem(View v, int position) {
//
//                    }
//
//                    public void onLongClickItem(View v, int position) {
//                        System.out.println("On Long Click Item interface");
//                    }
//                }));
    }

    private fun callParentAssociationEventList() {
        val paramObject = JsonObject()
        paramObject.addProperty("student_id", PreferenceManager.getStudentID(mContext))
        val call: Call<ParentAssociationEventResponseModel> = ApiClient.getClient.parent_assoc_events("Bearer " + PreferenceManager.getaccesstoken(mContext),paramObject)
        call.enqueue(object : Callback<ParentAssociationEventResponseModel> {
            override fun onFailure(call: Call<ParentAssociationEventResponseModel>, t: Throwable) {
                // progressDialogAdd.visibility=View.GONE
            }

            override fun onResponse(
                call: Call<ParentAssociationEventResponseModel>,
                response: Response<ParentAssociationEventResponseModel>
            ) {
                val responsedata = response.body()
                //  progressDialogAdd.visibility=View.GONE
                if (response.body() != null) {
                    if (response.body()!!.getResponseCode()==100) {
                        val statuscode: String = java.lang.String.valueOf(
                            response.body()!!.getResponse().getStatusCode()
                        )
                        //    Log.e("statuscode", statuscode);

//                            JSONObject respObject = rootObject.getJSONObject(JTAG_RESPONSE);

                            //Log.e("status", response.body().getResponse().getStatusCode());
                            facebookurl = response.body()!!.getResponse().getFacebookUrl()
                            if (facebookurl != "") {
                                headermanager.setButtonRightSelector(
                                    R.drawable.facebookshare,
                                    R.drawable.facebookshare
                                )
                            } else {
                                fbShare.setVisibility(View.GONE)
                            }

//                                JSONArray dataArray = respObject.getJSONArray(JTAG_RESPONSE_DATA_ARRAY);
                                //Log.e("item list size", String.valueOf(response.body().getResponse().getEventDataList().size()));
                                mListViewArray = ArrayList<ParentAssociationEventsModel>()
                                if (response.body()!!.getResponse().getEventDataList()
                                        .size > 0
                                ) {
                                    for (i in 0 until response.body()!!.getResponse()
                                        .getEventDataList().size) {
                                        val item: ParentAssociationEventResponseModel.EventData =
                                            response.body()!!.getResponse().getEventDataList()
                                                .get(i)
                                        //Log.e("item name", item.getEvent());
                                        val gson = Gson()
                                        val eventJson = gson.toJson(item)
                                        //  Log.e("item", eventJson);
                                        try {
                                            val jsonObject = JSONObject(eventJson)
                                            mListViewArray!!.add(
                                                getParentAssociationEventsValues(jsonObject)
                                            )
                                        } catch (e: JSONException) {
                                            e.printStackTrace()
                                        }
                                    }
                                    val mParentsAssociationMainRecyclerviewAdapter =
                                        ParentsAssociationMainRecyclerviewAdapter(
                                            mContext,
                                            mListViewArray!!,mRecyclerView
                                        )
                                    mRecyclerView!!.adapter =
                                        mParentsAssociationMainRecyclerviewAdapter
                                } else {
                                    //CustomStatusDialog();
                                    Toast.makeText(
                                        mContext,
                                        "No Data Available",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }


                    }  else {
                       /* AppUtils.showDialogAlertDismiss(
                            mContext as Activity,
                            "Alert",
                            getString(R.string.common_error),
                            R.drawable.exclamationicon,
                            R.drawable.round
                        )*/
                    }
                } else {
                   /* AppUtils.showDialogAlertDismiss(
                        mContext as Activity,
                        "Alert",
                        getString(R.string.common_error),
                        R.drawable.exclamationicon,
                        R.drawable.round
                    )*/
                }
            }
            })
        }
        /*val service: APIInterface = APIClient.getRetrofitInstance().create(APIInterface::class.java)
        val paramObject = JsonObject()
        paramObject.addProperty("student_id", PreferenceManager.getStudentID(mContext))
        val call: Call<ParentAssociationEventResponseModel> = service.postParentAssociationEvents(
            "Bearer " + PreferenceManager.getAccessToken(mContext), paramObject
        )
        progressBarDialog.show()
        call.enqueue(object : Callback<ParentAssociationEventResponseModel?> {
            override fun onResponse(
                call: Call<ParentAssociationEventResponseModel?>,
                response: Response<ParentAssociationEventResponseModel?>
            ) {
                progressBarDialog.hide()
                if (response.isSuccessful()) {
//                    Log.e("res", response.toString());
                    val apiResponse: ParentAssociationEventResponseModel? = response.body()
                    //                    Log.e("response", String.valueOf(apiResponse));
//                    System.out.println("response" + apiResponse);
                    val response_code: String =
                        java.lang.String.valueOf(apiResponse.getResponseCode())
                    //                    Log.e("errorh", response.body().getResponseCode());
                    if (response.body() != null) {
                        if (response.body().getResponseCode().equals("200")) {
                            val statuscode: String = java.lang.String.valueOf(
                                response.body().getResponse().getStatusCode()
                            )
                            //    Log.e("statuscode", statuscode);
                            if (statuscode == "303") {
//                            JSONObject respObject = rootObject.getJSONObject(JTAG_RESPONSE);
                                val statusCode: String =
                                    response.body().getResponse().getStatusCode()
                                //Log.e("status", response.body().getResponse().getStatusCode());
                                facebookurl = response.body().getResponse().getFacebookUrl()
                                if (facebookurl != "") {
                                    headermanager.setButtonRightSelector(
                                        R.drawable.facebookshare,
                                        R.drawable.facebookshare
                                    )
                                } else {
                                    fbShare.setVisibility(View.GONE)
                                }
                                if (statusCode == "303") {
//                                JSONArray dataArray = respObject.getJSONArray(JTAG_RESPONSE_DATA_ARRAY);
                                    //Log.e("item list size", String.valueOf(response.body().getResponse().getEventDataList().size()));
                                    mListViewArray = ArrayList<ParentAssociationEventsModel>()
                                    if (response.body().getResponse().getEventDataList()
                                            .size() > 0
                                    ) {
                                        for (i in 0 until response.body().getResponse()
                                            .getEventDataList().size()) {
                                            val item: ParentAssociationEventResponseModel.EventData =
                                                response.body().getResponse().getEventDataList()
                                                    .get(i)
                                            //Log.e("item name", item.getEvent());
                                            val gson = Gson()
                                            val eventJson = gson.toJson(item)
                                            //  Log.e("item", eventJson);
                                            try {
                                                val jsonObject = JSONObject(eventJson)
                                                mListViewArray!!.add(
                                                    getParentAssociationEventsValues(jsonObject)
                                                )
                                            } catch (e: JSONException) {
                                                e.printStackTrace()
                                            }
                                        }
                                        val mParentsAssociationMainRecyclerviewAdapter =
                                            ParentsAssociationMainRecyclerviewAdapter(
                                                mContext,
                                                mListViewArray
                                            )
                                        mRecyclerView!!.adapter =
                                            mParentsAssociationMainRecyclerviewAdapter
                                    } else {
                                        //CustomStatusDialog();
                                        Toast.makeText(
                                            mContext,
                                            "No Data Available",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                } else {
//										CustomStatusDialog(RESPONSE_FAILURE);
                                    AppUtils.showDialogAlertDismiss(
                                        mContext as Activity,
                                        "Alert",
                                        getString(R.string.common_error),
                                        R.drawable.exclamationicon,
                                        R.drawable.round
                                    )
                                }
                            }
                        } else if (response.body().getResponseCode()
                                .equalsIgnoreCase(RESPONSE_ACCESSTOKEN_MISSING) ||
                            response.body().getResponseCode()
                                .equalsIgnoreCase(RESPONSE_ACCESSTOKEN_EXPIRED) ||
                            response.body().getResponseCode()
                                .equalsIgnoreCase(RESPONSE_INVALID_TOKEN)
                        ) {
                            AppUtils.postInitParam(mContext, object : GetAccessTokenInterface() {
                                val accessToken: Unit
                                    get() {}
                            })
                            callParentAssociationEventList()
                        } else if (response.body().getResponseCode().equals(RESPONSE_ERROR)) {
//								CustomStatusDialog(RESPONSE_FAILURE);
                            //Toast.makeText(mContext,"Failure", Toast.LENGTH_SHORT).show();
                            AppUtils.showDialogAlertDismiss(
                                mContext as Activity,
                                "Alert",
                                getString(R.string.common_error),
                                R.drawable.exclamationicon,
                                R.drawable.round
                            )
                        } else {
                            AppUtils.showDialogAlertDismiss(
                                mContext as Activity,
                                "Alert",
                                getString(R.string.common_error),
                                R.drawable.exclamationicon,
                                R.drawable.round
                            )
                        }
                    } else {
                        AppUtils.showDialogAlertDismiss(
                            mContext as Activity,
                            "Alert",
                            getString(R.string.common_error),
                            R.drawable.exclamationicon,
                            R.drawable.round
                        )
                    }
                }
            }

            override fun onFailure(call: Call<ParentAssociationEventResponseModel?>, t: Throwable) {
                progressBarDialog.hide()
                AppUtils.showDialogAlertDismiss(
                    mContext as Activity,
                    "Alert",
                    getString(R.string.common_error),
                    R.drawable.exclamationicon,
                    R.drawable.round
                )
            }
        })*/


    @Throws(JSONException::class)
    private fun getParentAssociationEventsValues(mEventJSONdata: JSONObject): ParentAssociationEventsModel {
        val mParentAssociationEventsModel = ParentAssociationEventsModel()
        mParentAssociationEventsModel.setEvenId(mEventJSONdata.optString("even_id"))
        mParentAssociationEventsModel.setEventName(mEventJSONdata.optString("event"))
        mParentAssociationEventsModel.setEventDate(mEventJSONdata.optString("date"))
        mParentAssociationEventsModel.setPosition(0)
        val mDate: String = mParentAssociationEventsModel.getEventDate()
        Log.e("Madate",mDate)
        var mEventDate: Date? = Date()
        try {
            mEventDate = sdfcalender!!.parse(mDate)
            Log.e("mEventDate1", mEventDate.toString())
        } catch (ex: ParseException) {
            // Log.e("Date", "Parsing error");
        }
        val dayOfTheWeek =
            android.text.format.DateFormat.format("EEEE", mEventDate) as String // Thursday
        val day = android.text.format.DateFormat.format("dd", mEventDate) as String // 20
        val monthString =
            android.text.format.DateFormat.format("MMMM", mEventDate) as String // June
        val monthNumber = android.text.format.DateFormat.format("MM", mEventDate) as String // 06
        val year = android.text.format.DateFormat.format("yyyy", mEventDate) as String // 2013
        mParentAssociationEventsModel.setDayOfTheWeek(dayOfTheWeek)
        mParentAssociationEventsModel.setDay(day)
        mParentAssociationEventsModel.setMonthString(monthString)
        mParentAssociationEventsModel.setMonthNumber(monthNumber)
        mParentAssociationEventsModel.setYear(year)
        val mParentAssociationItemsArrayList: ArrayList<ParentAssociationEventsModel> =
            ArrayList<ParentAssociationEventsModel>()
        val itemData = mEventJSONdata.optJSONArray("items")
        for (i in 0 until itemData.length()) {
            val mItemJsonObject = itemData.optJSONObject(i)
            val mItemModel = ParentAssociationEventsModel()
            mItemModel.setItemName(mItemJsonObject.optString("name"))
            //            mItemModel.setItemSelected(false);
//            if (i==0)
//            {
//                mItemModel.setItemSelected(true);
//            }
            val itemDataEvent = mItemJsonObject.optJSONArray("timeslots")
            val mParentAssociationEventItemsArraList: ArrayList<ParentAssociationEventItemsModel> =
                ArrayList<ParentAssociationEventItemsModel>()
            for (j in 0 until itemDataEvent.length()) {
                val mEventItemJsonObject = itemDataEvent.optJSONObject(j)
                val mParentAssociationEventItemsModel = ParentAssociationEventItemsModel()
                mParentAssociationEventItemsModel.userName=(mEventItemJsonObject.optString("user_name"))
                mParentAssociationEventItemsModel.status=(mEventItemJsonObject.optString("status"))
                mParentAssociationEventItemsModel.eventId=(mEventItemJsonObject.optString("id"))
                mParentAssociationEventItemsModel.start_time=(mEventItemJsonObject.optString("start_time"))
                mParentAssociationEventItemsModel.end_time=(mEventItemJsonObject.optString("end_time"))
                val format1: DateFormat = SimpleDateFormat("HH:mm:ss", Locale.ENGLISH)
                try {
                    val dateStart = format1.parse(mEventItemJsonObject.optString("start_time"))
                    val format2 = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
                    val startTime = format2.format(dateStart)
                    mParentAssociationEventItemsModel.setFrom_time(startTime)
                    val dateEndTime = format1.parse(mEventItemJsonObject.optString("end_time"))
                    val endTime = format2.format(dateEndTime)
                    mParentAssociationEventItemsModel.setTo_time(endTime)
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
                mParentAssociationEventItemsArraList.add(mParentAssociationEventItemsModel)
            }
            mItemModel.setEventItemStatusList(mParentAssociationEventItemsArraList)
            mParentAssociationItemsArrayList.add(mItemModel)
        }
        mParentAssociationEventsModel.setEventItemList(mParentAssociationItemsArrayList)
        return mParentAssociationEventsModel
    }

    @Throws(JSONException::class)
    private fun getParentAssociationEventValues(
        mEventJSONdata: JSONObject, postion: Int
    ): ParentAssociationEventsModel {
        val mParentAssociationEventsModel = ParentAssociationEventsModel()
        mParentAssociationEventsModel.setEvenId(mEventJSONdata.optString("even_id"))
        mParentAssociationEventsModel.setEventName(mEventJSONdata.optString("event"))
        mParentAssociationEventsModel.setEventDate(mEventJSONdata.optString("date"))
        mParentAssociationEventsModel.setPosition(postion)
        val mDate: String = mParentAssociationEventsModel.getEventDate()
        Log.e("mDate",mDate)
        var mEventDate: Date? = Date()
        try {
            mEventDate = sdfcalender!!.parse(mDate)
            Log.e("mEventDate2", mEventDate.toString())

        } catch (ex: ParseException) {
            // Log.e("Date", "Parsing error");
        }
        val dayOfTheWeek =
            android.text.format.DateFormat.format("EEEE", mEventDate) as String // Thursday
        val day = android.text.format.DateFormat.format("dd", mEventDate) as String // 20
        val monthString =
            android.text.format.DateFormat.format("MMMM", mEventDate) as String // June
        val monthNumber = android.text.format.DateFormat.format("MM", mEventDate) as String // 06
        val year = android.text.format.DateFormat.format("yyyy", mEventDate) as String // 2013
        mParentAssociationEventsModel.setDayOfTheWeek(dayOfTheWeek)
        mParentAssociationEventsModel.setDay(day)
        mParentAssociationEventsModel.setMonthString(monthString)
        mParentAssociationEventsModel.setMonthNumber(monthNumber)
        mParentAssociationEventsModel.setYear(year)
        val mParentAssociationItemsArrayList: ArrayList<ParentAssociationEventsModel> =
            ArrayList<ParentAssociationEventsModel>()
        val itemData = mEventJSONdata.optJSONArray("items")
        for (i in 0 until itemData.length()) {
            val mItemJsonObject = itemData.optJSONObject(i)
            val mItemModel = ParentAssociationEventsModel()
            mItemModel.setItemName(mItemJsonObject.optString("name"))
            //            mItemModel.setItemSelected(false);
//            if (i==0)
//            {
//                mItemModel.setItemSelected(true);
//            }
            val itemDataEvent = mItemJsonObject.optJSONArray("timeslots")
            val mParentAssociationEventItemsArraList: ArrayList<ParentAssociationEventItemsModel> =
                ArrayList<ParentAssociationEventItemsModel>()
            for (j in 0 until itemDataEvent.length()) {
                val mEventItemJsonObject = itemDataEvent.optJSONObject(j)
                val mParentAssociationEventItemsModel = ParentAssociationEventItemsModel()
                mParentAssociationEventItemsModel.setUserName(mEventItemJsonObject.optString("user_name"))
                mParentAssociationEventItemsModel.setStatus(mEventItemJsonObject.optString("status"))
                mParentAssociationEventItemsModel.setStart_time(mEventItemJsonObject.optString("start_time"))
                mParentAssociationEventItemsModel.setEnd_time(mEventItemJsonObject.optString("end_time"))
                mParentAssociationEventItemsModel.setEventId(mEventItemJsonObject.optString("id"))
                val format1: DateFormat = SimpleDateFormat("HH:mm:ss", Locale.ENGLISH)
                try {
                    val dateStart = format1.parse(mEventItemJsonObject.optString("start_time"))
                    val format2 = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
                    val startTime = format2.format(dateStart)
                    mParentAssociationEventItemsModel.setFrom_time(startTime)
                    val dateEndTime = format1.parse(mEventItemJsonObject.optString("end_time"))
                    val endTime = format2.format(dateEndTime)
                    mParentAssociationEventItemsModel.setTo_time(endTime)
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
                mParentAssociationEventItemsArraList.add(mParentAssociationEventItemsModel)
            }
            mItemModel.setEventItemStatusList(mParentAssociationEventItemsArraList)
            mParentAssociationItemsArrayList.add(mItemModel)
        }
        mParentAssociationEventsModel.setEventItemList(mParentAssociationItemsArrayList)
        return mParentAssociationEventsModel
    }


    fun callListApis(
        context: Context,
        parentAssociationEventsModelsArrayList: ArrayList<ParentAssociationEventsModel>?
    ) {

       // mRecyclerView = findViewById<View>(R.id.mRecyclerView) as RecyclerView
intfn()

        mListViewArray = ArrayList<ParentAssociationEventsModel>()
        mParentAssociationEventsModelsArrayList = parentAssociationEventsModelsArrayList
        val paramObject = JsonObject()
        paramObject.addProperty("student_id", PreferenceManager.getStudentID(context))
        val call: Call<ParentAssociationEventResponseModel> = ApiClient.getClient.parent_assoc_events(
            "Bearer " + PreferenceManager.getaccesstoken(context),
            paramObject
        )
        call.enqueue(object : Callback<ParentAssociationEventResponseModel> {
            override fun onFailure(call: Call<ParentAssociationEventResponseModel>, t: Throwable) {
            }
            override fun onResponse(call: Call<ParentAssociationEventResponseModel>, response: Response<ParentAssociationEventResponseModel>) {
                val responsedata = response.body()
                if (response.isSuccessful()) {
//                    Log.e("res", response.toString());
                    val apiResponse: ParentAssociationEventResponseModel? = response.body()
                    //                    Log.e("response", String.valueOf(apiResponse));
//                    System.out.println("response" + apiResponse);
                    val response_code: String =
                        java.lang.String.valueOf(apiResponse!!.getResponseCode())
                    //                    Log.e("errorh", response.body().getResponseCode());
                    if (response.body() != null) {
                        if (response.body()!!.getResponseCode()==100) {
                            val statuscode: String = java.lang.String.valueOf(
                                response.body()!!.getResponse().getStatusCode()
                            )
                            //    Log.e("statuscode", statuscode);

                                if (apiResponse.getResponse().getEventDataList().size > 0) {
                                    for (i in 0 until apiResponse.getResponse().getEventDataList()
                                        .size) {
                                        val item: ParentAssociationEventResponseModel.EventData =
                                            response.body()!!.getResponse().getEventDataList().get(i)
                                        //Log.e("item name", item.getEvent());
                                        val gson = Gson()
                                        val eventJson = gson.toJson(item)
                                        //  Log.e("item", eventJson);
                                        try {
                                            val jsonObject = JSONObject(eventJson)
                                            Log.e("parent_assoc", jsonObject.toString())
                                            // JSONObject eventJSONdata = dataArray.optJSONObject(i);
                                            mListViewArray!!.add(
                                                getParentAssociationEventValues(
                                                    jsonObject,
                                                    mParentAssociationEventsModelsArrayList!![i].getPosition()
                                                )
                                            )
                                        } catch (e: JSONException) {
                                            e.printStackTrace()
                                        }
                                    }
                                    val mParentsAssociationMainRecyclerviewAdapter = ParentsAssociationMainRecyclerviewAdapter(
                                            context,
                                            mListViewArray!!,mRecyclerView
                                        )
                                    mRecyclerView!!.adapter =
                                        mParentsAssociationMainRecyclerviewAdapter
                                } else {
                                    //CustomStatusDialog();
                                    Toast.makeText(context, "Failure", Toast.LENGTH_SHORT).show()
                                }

                        }   else {
                            /*AppUtils.showDialogAlertDismiss(
                                context as Activity,
                                "Alert",
                                context.getString(R.string.common_error),
                                R.drawable.exclamationicon,
                                R.drawable.round
                            )*/
                        }
                    } else {
                        /*AppUtils.showDialogAlertDismiss(
                            context as Activity,
                            "Alert",
                            context.getString(R.string.common_error),
                            R.drawable.exclamationicon,
                            R.drawable.round
                        )*/
                    }
                }
            }
        })
            }

    private fun intfn() {
        myFormatCalender = "yyyy-MM-dd"
        sdfcalender = SimpleDateFormat(myFormatCalender)

        mRecyclerView = findViewById<View>(R.id.mRecyclerView) as RecyclerView
        mRecyclerView!!.setHasFixedSize(true)
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        mRecyclerView!!.layoutManager = llm
    }
    /*PtaItemList = mPtaItemList
    mListViewArray = ArrayList<ParentAssociationEventsModel>()
    mParentAssociationEventsModelsArrayList = parentAssociationEventsModelsArrayList
    val service: APIInterface = APIClient.getRetrofitInstance().create(APIInterface::class.java)
    val paramObject = JsonObject()
    paramObject.addProperty("student_id", PreferenceManager.getStudentID(context))
    val call: Call<ParentAssociationEventResponseModel> = service.postParentAssociationEvents(
        "Bearer " + PreferenceManager.getAccessToken(context),
        paramObject
    )
    // progressBarDialog.show();
    call.enqueue(object : Callback<ParentAssociationEventResponseModel?> {
        override fun onResponse(
            call: Call<ParentAssociationEventResponseModel?>,
            response: Response<ParentAssociationEventResponseModel?>
        ) {
            //progressBarDialog.hide();
            if (response.isSuccessful()) {
//                    Log.e("res", response.toString());
                val apiResponse: ParentAssociationEventResponseModel? = response.body()
                //                    Log.e("response", String.valueOf(apiResponse));
//                    System.out.println("response" + apiResponse);
                val response_code: String =
                    java.lang.String.valueOf(apiResponse.getResponseCode())
                //                    Log.e("errorh", response.body().getResponseCode());
                if (response.body() != null) {
                    if (response.body().getResponseCode().equals("200")) {
                        val statuscode: String = java.lang.String.valueOf(
                            response.body().getResponse().getStatusCode()
                        )
                        //    Log.e("statuscode", statuscode);
                        if (statuscode == STATUS_SUCCESS) {
                            if (apiResponse.getResponse().getEventDataList().size() > 0) {
                                for (i in 0 until apiResponse.getResponse().getEventDataList()
                                    .size()) {
                                    val item: ParentAssociationEventResponseModel.EventData =
                                        response.body().getResponse().getEventDataList().get(i)
                                    //Log.e("item name", item.getEvent());
                                    val gson = Gson()
                                    val eventJson = gson.toJson(item)
                                    //  Log.e("item", eventJson);
                                    try {
                                        val jsonObject = JSONObject(eventJson)
                                        // JSONObject eventJSONdata = dataArray.optJSONObject(i);
                                        mListViewArray!!.add(
                                            getParentAssociationEventValues(
                                                jsonObject,
                                                mParentAssociationEventsModelsArrayList!![i].getPosition()
                                            )
                                        )
                                    } catch (e: JSONException) {
                                        e.printStackTrace()
                                    }
                                }
                                val mParentsAssociationMainRecyclerviewAdapter =
                                    ParentsAssociationMainRecyclerviewAdapter(
                                        context,
                                        mListViewArray
                                    )
                                mRecyclerView!!.adapter =
                                    mParentsAssociationMainRecyclerviewAdapter
                            } else {
                                //CustomStatusDialog();
                                Toast.makeText(context, "Failure", Toast.LENGTH_SHORT).show()
                            }
                        } else {
//										CustomStatusDialog(RESPONSE_FAILURE);
                            Toast.makeText(context, "Failure", Toast.LENGTH_SHORT).show()
                        }
                    } else if (response.body().getResponseCode()
                            .equalsIgnoreCase(RESPONSE_ACCESSTOKEN_MISSING) ||
                        response.body().getResponseCode()
                            .equalsIgnoreCase(RESPONSE_ACCESSTOKEN_EXPIRED) ||
                        response.body().getResponseCode()
                            .equalsIgnoreCase(RESPONSE_INVALID_TOKEN)
                    ) {
                        AppUtils.postInitParam(context, object : GetAccessTokenInterface() {
                            val accessToken: Unit
                                get() {}
                        })
                        // callParentAssociationEventList();
                    } else if (response.body().getResponseCode().equals(RESPONSE_ERROR)) {
//								CustomStatusDialog(RESPONSE_FAILURE);
                        //Toast.makeText(mContext,"Failure", Toast.LENGTH_SHORT).show();
                        AppUtils.showDialogAlertDismiss(
                            context as Activity,
                            "Alert",
                            context.getString(R.string.common_error),
                            R.drawable.exclamationicon,
                            R.drawable.round
                        )
                    } else {
                        AppUtils.showDialogAlertDismiss(
                            context as Activity,
                            "Alert",
                            context.getString(R.string.common_error),
                            R.drawable.exclamationicon,
                            R.drawable.round
                        )
                    }
                } else {
                    AppUtils.showDialogAlertDismiss(
                        context as Activity,
                        "Alert",
                        context.getString(R.string.common_error),
                        R.drawable.exclamationicon,
                        R.drawable.round
                    )
                }
            }
        }

        override fun onFailure(call: Call<ParentAssociationEventResponseModel?>, t: Throwable) {
            //  progressBarDialog.hide();
            AppUtils.showDialogAlertDismiss(
                context as Activity,
                "Alert",
                context.getString(R.string.common_error),
                R.drawable.exclamationicon,
                R.drawable.round
            )
        }
    })*/





    override fun onRestart() {
        super.onRestart()
        if (ConstantFunctions.internetCheck(mContext)) {
            callParentAssociationEventList()
        } else {
            DialogFunctions.showInternetAlertDialog(mContext)
        }

    }
}