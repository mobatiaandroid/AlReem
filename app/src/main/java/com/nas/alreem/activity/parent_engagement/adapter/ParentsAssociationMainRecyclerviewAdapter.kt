package com.nas.alreem.activity.parent_engagement.adapter

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.nas.alreem.R
import com.nas.alreem.activity.parent_engagement.ParentsAssociationListActivity
import com.nas.alreem.activity.parent_engagement.model.ParentAssociationEventItemsModel
import com.nas.alreem.activity.parent_engagement.model.ParentAssociationEventResponseModel
import com.nas.alreem.activity.parent_engagement.model.ParentAssociationEventsModel
import com.nas.alreem.activity.parent_engagement.model.VolunteerSubmitResponseModel
import com.nas.alreem.constants.ApiClient
import com.nas.alreem.constants.PreferenceManager
import com.nas.alreem.recyclermanager.ItemOffsetDecoration
import com.nas.alreem.recyclermanager.RecyclerItemListener
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


class ParentsAssociationMainRecyclerviewAdapter :
    RecyclerView.Adapter<ParentsAssociationMainRecyclerviewAdapter.MyViewHolder> {
    private var mContext: Context
    private var mParentAssociationEventsModelArrayList: ArrayList<ParentAssociationEventsModel>
    var photo_id = "-1"
    var startTime = ""
    var startTimeAm = true
    var endTimeAm = true
    var endTime = ""
    var mPosition = 0
    var mainPosition = 0
    var datePosition = 0
    var alreadyslotBookedByUser = false
    var mListViewArrayPost: ArrayList<ParentAssociationEventItemsModel>? = null
    var mListViewArray: ArrayList<ParentAssociationEventsModel>? = null
    var myFormatCalender = "yyyy-MM-dd"
    var sdfcalender: SimpleDateFormat? = null
lateinit var rec: RecyclerView
    // var progressBarDialog: ProgressBarDialog? = null
    private fun postSelectedSlotVolunteer() {
       val paramObject = JsonObject()
       paramObject.addProperty(
           "id",
           mParentAssociationEventsModelArrayList[mainPosition].getEventItemList().get(mPosition)
               .getEventItemStatusList().get(datePosition).getEventId()
       )
       val call: Call<VolunteerSubmitResponseModel> = ApiClient(mContext).getClient.parent_assoc_events_attending_or_not_new("Bearer " + PreferenceManager.getaccesstoken(mContext),
           paramObject)
       call.enqueue(object : Callback<VolunteerSubmitResponseModel> {
           override fun onFailure(call: Call<VolunteerSubmitResponseModel>, t: Throwable) {
           }
           override fun onResponse(call: Call<VolunteerSubmitResponseModel>, response: Response<VolunteerSubmitResponseModel>) {
               val responsedata = response.body()
               if (response.isSuccessful()) {
//                    Log.e("res", response.toString());
                   val apiResponse: VolunteerSubmitResponseModel? = response.body()
                   //                    Log.e("response", String.valueOf(apiResponse));
//                                 System.out.println("response" + apiResponse);
                   val response_code: Int = (apiResponse!!.getResponseCode())
                   if (response_code == 100) {

                       //                                     Log.e("statuscode", statuscode);

                       /*callListApis(mContext,
                           mParentAssociationEventsModelArrayList)*/
                       ParentsAssociationListActivity().callListApis(
                           mContext,
                           mParentAssociationEventsModelArrayList,rec)
                       showDialogAlertSingleBtn(
                           mContext as Activity,
                           "Alert",
                           "Your time slot has been booked successfully.",
                           R.drawable.tick,
                           R.drawable.round
                       )
                   }
                        else if (response_code ==311) {
                       ParentsAssociationListActivity().  callListApis(
                               mContext,
                               mParentAssociationEventsModelArrayList,rec)
                           showDialogAlertSingleBtn(
                               mContext as Activity,
                               "Alert",
                               "Request cancelled successfully.",
                               R.drawable.tick,
                               R.drawable.round
                           )

//                                    getPtaAllotedDateList();
                       } else if (response_code == 136) {
//                                    ParentsAssociationListActivity.callListApis(mContext, mParentAssociationEventsModelArrayList ,new ParentsAssociationListActivity.GetPtaItemList() {
//                                        @Override
//                                        public void getPtaItemData() {
//
//                                        }
//                                    });
                           showDialogAlertSingleBtn(
                               mContext as Activity,
                               "Alert",
                               "Slot is already booked by an another user.",
                               R.drawable.exclamationicon,
                               R.drawable.round
                           )

//                                    getPtaAllotedDateList();

                   }  else {
                      /* AppUtils.showDialogAlertDismiss(
                           mContext as Activity,
                           "Alert",
                           mContext.getString(R.string.common_error),
                           R.drawable.exclamationicon,
                           R.drawable.round
                       )*/
                   }
               }

           }

       })
        /*val service: APIInterface = APIClient.getRetrofitInstance().create(APIInterface::class.java)
        val paramObject = JsonObject()
        paramObject.addProperty(
            "id",
            mParentAssociationEventsModelArrayList[mainPosition].getEventItemList().get(mPosition)
                .getEventItemStatusList().get(datePosition).getEventId()
        )
        val call: Call<VolunteerSubmitResponseModel> = service.postSelectedSlotVolunteer(
            "Bearer " + PreferenceManager.getAccessToken(mContext),
            paramObject
        )
        progressBarDialog.show()
        call.enqueue(object : Callback<VolunteerSubmitResponseModel> {
            override fun onResponse(
                call: Call<VolunteerSubmitResponseModel>,
                response: Response<VolunteerSubmitResponseModel>
            ) {
                progressBarDialog.hide()
                if (response.isSuccessful()) {
//                    Log.e("res", response.toString());
                    val apiResponse: VolunteerSubmitResponseModel? = response.body()
                    //                    Log.e("response", String.valueOf(apiResponse));
//                                 System.out.println("response" + apiResponse);
                    val response_code: String =
                        java.lang.String.valueOf(apiResponse.getResponseCode())
                    if (response_code == "200") {
                        val statuscode: String =
                            java.lang.String.valueOf(response.body().getResponse().getStatusCode())
                        //                                     Log.e("statuscode", statuscode);
                        if (statuscode == "303") {
                            ParentsAssociationListActivity.callListApis(
                                mContext,
                                mParentAssociationEventsModelArrayList,
                                object : GetPtaItemList() {
                                    val ptaItemData: Unit
                                        get() {}
                                })
                            showDialogAlertSingleBtn(
                                mContext as Activity,
                                "Alert",
                                "Your time slot has been booked successfully.",
                                R.drawable.tick,
                                R.drawable.round
                            )
                        } else if (statuscode == STATUS_CANCEL) {
                            ParentsAssociationListActivity.callListApis(
                                mContext,
                                mParentAssociationEventsModelArrayList,
                                object : GetPtaItemList() {
                                    val ptaItemData: Unit
                                        get() {}
                                })
                            showDialogAlertSingleBtn(
                                mContext as Activity,
                                "Alert",
                                "Request cancelled successfully.",
                                R.drawable.tick,
                                R.drawable.round
                            )

//                                    getPtaAllotedDateList();
                        } else if (statuscode == STATUS_BOOKED_BY_USER) {
//                                    ParentsAssociationListActivity.callListApis(mContext, mParentAssociationEventsModelArrayList ,new ParentsAssociationListActivity.GetPtaItemList() {
//                                        @Override
//                                        public void getPtaItemData() {
//
//                                        }
//                                    });
                            showDialogAlertSingleBtn(
                                mContext as Activity,
                                "Alert",
                                "Slot is already booked by an another user.",
                                R.drawable.exclamationicon,
                                R.drawable.round
                            )

//                                    getPtaAllotedDateList();
                        } else {
//										CustomStatusDialog(RESPONSE_FAILURE);
                            Toast.makeText(mContext, "Failure", Toast.LENGTH_SHORT).show()
                        }
                    } else if (response.body().getResponseCode()
                            .equalsIgnoreCase(RESPONSE_ACCESSTOKEN_MISSING) ||
                        response.body().getResponseCode()
                            .equalsIgnoreCase(RESPONSE_ACCESSTOKEN_EXPIRED) ||
                        response.body().getResponseCode().equalsIgnoreCase(RESPONSE_INVALID_TOKEN)
                    ) {
                        AppUtils.postInitParam(mContext, object : GetAccessTokenInterface() {
                            val accessToken: Unit
                                get() {}
                        })
                        postSelectedSlotVolunteer()
                    } else if (response.body().getResponseCode().equals(RESPONSE_ERROR)) {
//								CustomStatusDialog(RESPONSE_FAILURE);
                        //Toast.makeText(mContext,"Failure", Toast.LENGTH_SHORT).show();
                        AppUtils.showDialogAlertDismiss(
                            mContext as Activity,
                            "Alert",
                            mContext.getString(R.string.common_error),
                            R.drawable.exclamationicon,
                            R.drawable.round
                        )
                    } else {
                        AppUtils.showDialogAlertDismiss(
                            mContext as Activity,
                            "Alert",
                            mContext.getString(R.string.common_error),
                            R.drawable.exclamationicon,
                            R.drawable.round
                        )
                    }
                }
            }

            override fun onFailure(call: Call<VolunteerSubmitResponseModel>, t: Throwable) {
                progressBarDialog.hide()
                Toast.makeText(mContext, "Failure", Toast.LENGTH_SHORT).show()
            }
        }
        )*/
    }

    constructor(
        mContext: Context,
        mParentAssociationEventsModelArrayList: ArrayList<ParentAssociationEventsModel>
    ) {
        this.mContext = mContext
        this.mParentAssociationEventsModelArrayList = mParentAssociationEventsModelArrayList
    }

    constructor(
        mContext: Context,
        mParentAssociationEventsModelArrayList: ArrayList<ParentAssociationEventsModel>,
        photo_id: RecyclerView?
    ) {
        this.mContext = mContext
        this.mParentAssociationEventsModelArrayList = mParentAssociationEventsModelArrayList
        this.rec = photo_id!!
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ParentsAssociationMainRecyclerviewAdapter.MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.parents_association_main_adapter, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder:MyViewHolder, position: Int) {
//        holder.phototakenDate.setText(mPhotosModelArrayList.get(position).getMonth() + " " + mPhotosModelArrayList.get(position).getDay() + "," + mPhotosModelArrayList.get(position).getYear());
//        mPosition = position;
//        holder.layout.getBackground().setAlpha(150);
        holder.eventName.setText(mParentAssociationEventsModelArrayList[position].getEventName())
        holder.eventDate.setText(
            ((mParentAssociationEventsModelArrayList[position].getDayOfTheWeek() + " " + mParentAssociationEventsModelArrayList[position].getDay()).toString() + " " + mParentAssociationEventsModelArrayList[position].getMonthString()).toString() + " " +
                    mParentAssociationEventsModelArrayList[position].getYear()
        )

        //holder.eventDate.setText(mParentAssociationEventsModelArrayList.get(position).getDay() + " " + mParentAssociationEventsModelArrayList.get(position).getMonthString() + " " + mParentAssociationEventsModelArrayList.get(position).getYear()+", "+mParentAssociationEventsModelArrayList.get(position).getDayOfTheWeek());
        val llm = LinearLayoutManager(mContext)
        llm.orientation = LinearLayoutManager.HORIZONTAL
        val spacings = 5 // 50px
        val itemDecorations = ItemOffsetDecoration(mContext, spacings)
        holder.mRecyclerViewItemName.addItemDecoration(itemDecorations)
        holder.mRecyclerViewItemName.setLayoutManager(llm)
        if (mParentAssociationEventsModelArrayList[position].getEventItemList().get(
                mParentAssociationEventsModelArrayList[position].getPosition()
            ).getEventItemStatusList().size > 0
        ) {
            holder.mRecyclerViewItem.setVisibility(View.VISIBLE)
            holder.notAvailableTV.setVisibility(View.GONE)
        } else {
            holder.mRecyclerViewItem.setVisibility(View.GONE)
            holder.notAvailableTV.setVisibility(View.VISIBLE)
        }
        //        holder.mRecyclerViewItemName.setAdapter(new ParentsAssociationItemNameRecyclerviewAdapter(mContext, mParentAssociationEventsModelArrayList.get(position).getEventItemList()));
        holder.mRecyclerViewItemName.setAdapter(
            ParentsAssociationItemNameRecyclerviewAdapter(
                mContext,
                mParentAssociationEventsModelArrayList[position].getEventItemList(),
                mParentAssociationEventsModelArrayList[position].getPosition()
            )
        )
        holder.mRecyclerViewItemName.addOnItemTouchListener(
            RecyclerItemListener(mContext, holder.mRecyclerViewItemName,
                object : RecyclerItemListener.RecyclerTouchListener {
                    override fun onClickItem(v: View?, pos: Int) {
                        mPosition = pos
                        mainPosition = position
                        mParentAssociationEventsModelArrayList[position].setPosition(pos)
                        //                        Log.e("Pos","pos1"+""+position+"pos2"+mParentAssociationEventsModelArrayList.get(position).getPosition());
                        holder.mRecyclerViewItemName.setAdapter(
                            ParentsAssociationItemNameRecyclerviewAdapter(
                                mContext,
                                mParentAssociationEventsModelArrayList[position].getEventItemList(),
                                pos
                            )
                        )
                        if (mParentAssociationEventsModelArrayList[position].getEventItemList()
                                .get(pos).getEventItemStatusList().size > 0
                        ) {
                            val mParentsAssociationTimeSlotRecyclerviewAdapter =
                                ParentsAssociationTimeSlotRecyclerviewAdapter(
                                    mContext,
                                    mParentAssociationEventsModelArrayList[position].getEventItemList()
                                        .get(pos).getEventItemStatusList(),
                                    pos
                                )
                            holder.mRecyclerViewItem.setAdapter(
                                mParentsAssociationTimeSlotRecyclerviewAdapter
                            )
                            mParentsAssociationTimeSlotRecyclerviewAdapter.notifyDataSetChanged()
                            holder.mRecyclerViewItem.setVisibility(View.VISIBLE)
                            holder.notAvailableTV.setVisibility(View.GONE)
                            mParentAssociationEventsModelArrayList[position].setPosition(pos)
                            //                            Log.e("Pos", "pos1" + "" + position + "pos2" + mParentAssociationEventsModelArrayList.get(position).getPosition());
                        } else {
                            holder.mRecyclerViewItem.setVisibility(View.GONE)
                            holder.notAvailableTV.setVisibility(View.VISIBLE)
                            //                            showDialogAlertSingleBtn((Activity) mContext, "Alert", "No timeslots available", R.drawable.questionmark_icon, R.drawable.round);
                        }
                    }

                    override fun onLongClickItem(v: View?, pos: Int) {
//                        if ( mParentAssociationEventsModelArrayList.get(mPosition).getEventItemList().get(pos).getEventItemStatusList().size()>0) {
//                            ParentsAssociationTimeSlotRecyclerviewAdapter mParentsAssociationTimeSlotRecyclerviewAdapter = new ParentsAssociationTimeSlotRecyclerviewAdapter(mContext, mParentAssociationEventsModelArrayList.get(mPosition).getEventItemList().get(pos).getEventItemStatusList());
//                            holder.mRecyclerViewItem.setAdapter(mParentsAssociationTimeSlotRecyclerviewAdapter);
//                            mParentsAssociationTimeSlotRecyclerviewAdapter.notifyDataSetChanged();
//                            holder.mRecyclerViewItem.setVisibility(View.VISIBLE);
//
//                        }
//                        else
//                        {
//                            holder.mRecyclerViewItem.setVisibility(View.INVISIBLE);
//                        }
                    }
                })
        )
        holder.mRecyclerViewItem.setHasFixedSize(true)
        val recyclerViewLayoutManager: RecyclerView.LayoutManager = GridLayoutManager(mContext, 3)
        val spacing = 5 // 50px
        val itemDecoration = ItemOffsetDecoration(mContext, spacing)
        holder.mRecyclerViewItem.addItemDecoration(itemDecoration)
        holder.mRecyclerViewItem.setLayoutManager(recyclerViewLayoutManager)
        holder.mRecyclerViewItem.setAdapter(
            ParentsAssociationTimeSlotRecyclerviewAdapter(
                mContext,
                mParentAssociationEventsModelArrayList[position].getEventItemList().get(
                    mParentAssociationEventsModelArrayList[position].getPosition()
                ).getEventItemStatusList(),
                mParentAssociationEventsModelArrayList[position].getPosition()
            )
        )
        //System.out.println("sizes2:"+mParentAssociationEventsModelArrayList.get(position).getEventItemStatusList().size());
//System.out.println("sizes3:" + mParentAssociationEventsModelArrayList.get(position).getEventItemList().get(0).getEventItemStatusList().size());
        holder.mRecyclerViewItem.addOnItemTouchListener(
            RecyclerItemListener(mContext, holder.mRecyclerViewItem,
                object : RecyclerItemListener.RecyclerTouchListener {


                    override fun onClickItem(v: View?, p: Int) {
                        datePosition = p
                        mainPosition = position
                        //                        Log.e("positoin mainPosition", ""+mainPosition);
                        mPosition = v!!.id
                        //                        System.out.println("datePosition=" + datePosition);
                        if (mParentAssociationEventsModelArrayList[mainPosition].getEventItemList()
                                .get(mPosition).getEventItemStatusList().get(p).status
                                .equals("0")
                        ) {
                            val dateOnly: String =
                                (mParentAssociationEventsModelArrayList[mainPosition].getDay() + " " + mParentAssociationEventsModelArrayList[mainPosition].getMonthString()).toString() + " " + mParentAssociationEventsModelArrayList[mainPosition].getYear()
                            val itemName: String =
                                mParentAssociationEventsModelArrayList[mainPosition].getEventItemList()
                                    .get(mPosition).getItemName()
                            val dateTime: String =
                                mParentAssociationEventsModelArrayList[mainPosition].getEventItemList()
                                    .get(mPosition).getEventItemStatusList().get(p)
                                    .from_time + " - " + mParentAssociationEventsModelArrayList[mainPosition].getEventItemList()
                                    .get(mPosition).getEventItemStatusList().get(p).to_time
                            showDialogAlertDoubeBtn(
                                mContext as Activity,
                                "Alert",
                                "Do you want to volunteer for $itemName on $dateOnly at $dateTime?",
                                R.drawable.questionmark_icon,
                                R.drawable.round
                            )
                        } else if (mParentAssociationEventsModelArrayList[mainPosition].getEventItemList()
                                .get(mPosition).getEventItemStatusList().get(p).status
                                .equals("1")
                        ) {
                            showDialogAlertDoubeBtn(
                                mContext as Activity,
                                "Alert",
                                "Do you want to cancel the request?",
                                R.drawable.questionmark_icon,
                                R.drawable.round
                            )
                        } else if (mParentAssociationEventsModelArrayList[mainPosition].getEventItemList()
                                .get(mPosition).getEventItemStatusList().get(p).status
                                .equals("2")
                        ) {
                            showDialogAlertSingleBtn(
                                mContext as Activity,
                                "Alert",
                                "This slot is not available",
                                R.drawable.exclamationicon,
                                R.drawable.round
                            )
                        }
                    }

                    override fun onLongClickItem(v: View?, position: Int) {}
                })
        )
    }

    override fun getItemCount(): Int {
        return mParentAssociationEventsModelArrayList.size
    }


    fun showDialogAlertDoubeBtn(
        activity: Activity?,
        msgHead: String?,
        msg: String?,
        ico: Int,
        bgIcon: Int
    ) {
        val dialog = Dialog((activity)!!)
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
            postSelectedSlotVolunteer()
            //                postSelectedSlot();
            dialog.dismiss()
        }
        val dialogButtonCancel = dialog.findViewById<View>(R.id.btn_Cancel) as Button
        dialogButtonCancel.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var eventName: TextView
        var eventDate: TextView
        var notAvailableTV: TextView
        //var gridClickRelative: LinearLayout
        var layout: LinearLayout
       // var card_view: CardView
        var mRecyclerViewItemName: RecyclerView
        var mRecyclerViewItem: RecyclerView

        init {

//            photoImageView = (ImageView) view.findViewById(R.id.imgView);
            eventName = view.findViewById<View>(R.id.eventName) as TextView
            eventDate = view.findViewById<View>(R.id.eventDate) as TextView
            notAvailableTV = view.findViewById<View>(R.id.notAvailableTV) as TextView
            mRecyclerViewItemName =
                view.findViewById<View>(R.id.mRecyclerViewItemName) as RecyclerView
            mRecyclerViewItem = view.findViewById<View>(R.id.mRecyclerViewItem) as RecyclerView
          //  gridClickRelative = view.findViewById<View>(R.id.gridClickRelative) as LinearLayout
           // card_view = view.findViewById<View>(R.id.card_view) as CardView
            layout = view.findViewById<View>(R.id.layout) as LinearLayout
            mainPosition = adapterPosition
            //            System.out.println("mainPosition=" + getAdapterPosition());
           // progressBarDialog = ProgressBarDialog(mContext, R.drawable.spinner)
        }
    }

    fun showDialogAlertSingleBtn(
        activity: Activity?,
        msgHead: String?,
        msg: String?,
        ico: Int,
        bgIcon: Int
    ) {
        val dialog = Dialog((activity)!!)
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
        dialogButton.setOnClickListener { dialog.dismiss() }
        //		Button dialogButtonCancel = (Button) dialog.findViewById(R.id.btn_Cancel);
//		dialogButtonCancel.setVisibility(View.GONE);
//		dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				dialog.dismiss();
//			}
//		});
        dialog.show()
    }

    fun callListApis(
        context: Context,
        parentAssociationEventsModelsArrayList: ArrayList<ParentAssociationEventsModel>?
    ) {
        myFormatCalender = "yyyy-MM-dd"
        sdfcalender = SimpleDateFormat(myFormatCalender)
        // mRecyclerView = findViewById<View>(R.id.mRecyclerView) as RecyclerView


        mListViewArray = ArrayList<ParentAssociationEventsModel>()
        parentAssociationEventsModelsArrayList
        val paramObject = JsonObject()
        paramObject.addProperty("student_id", PreferenceManager.getStudentID(context))
        val call: Call<ParentAssociationEventResponseModel> = ApiClient(context).getClient.parent_assoc_events(
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
                                                parentAssociationEventsModelsArrayList!![i].getPosition()
                                            )
                                        )
                                    } catch (e: JSONException) {
                                        e.printStackTrace()
                                    }
                                }
                                val mParentsAssociationMainRecyclerviewAdapter = ParentsAssociationMainRecyclerviewAdapter(
                                    context,
                                    mListViewArray!!
                                )
                                rec!!.adapter =
                                    mParentsAssociationMainRecyclerviewAdapter
                                notifyDataSetChanged()
                            } else {
                                //CustomStatusDialog();
                                Toast.makeText(context, "Failure", Toast.LENGTH_SHORT).show()
                            }

                        }   else {
                           /* AppUtils.showDialogAlertDismiss(
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

}
