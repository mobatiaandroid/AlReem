package com.nas.alreem.activity.parent_meetings

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.activity.parent_meetings.adapter.ParentsEveningRoomListAdapter
import com.nas.alreem.activity.parent_meetings.adapter.TimeslotAdapter
import com.nas.alreem.activity.parent_meetings.model.*
import com.nas.alreem.constants.*
import com.nas.alreem.rest.ApiClient
import kotlinx.android.synthetic.main.activity_parent_meeting_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class ParentMeetingDetailActivity:AppCompatActivity() {
    lateinit var mContext: Context
    lateinit var backRelative: RelativeLayout
    lateinit var heading: TextView
    lateinit var logoClickImgView: ImageView
    lateinit var progressDialogAdd: ProgressBar
    lateinit var date_header: TextView
    var firstVisit:Boolean=true
    var dateString:String=""
    var date_sel:String=""
    var studName:String=""
    var studId:String=""
    var studClass:String=""
    var staff_name:String=""
    var staff_id:String=""
    lateinit var studentName:TextView
    lateinit var studentClass:TextView
    lateinit var staffName:TextView
    lateinit var confirm:TextView
    lateinit var cancel:TextView
    lateinit var vpml_btn:Button
    lateinit var recyclerView: RecyclerView
    lateinit var info_img:ImageView
    lateinit var back: ImageView
    lateinit var home_icon:ImageView
    lateinit var timeSlotList:ArrayList<PtaTimeSlotList>
    lateinit var timeSlotListPost: ArrayList<PtaTimeSlotList>
    var alreadyslotBookedByUser:Boolean=false
    var confirmedslotBookedByUser:Boolean=false
    var confirmed_link:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parent_meeting_detail)

        mContext=this
        initfn()

    }
    private fun initfn(){
        progressDialogAdd=findViewById(R.id.progressDialogAdd)
        heading=findViewById(R.id.heading)
        heading.text=ConstantWords.parentmeetings
        backRelative=findViewById(R.id.backRelative)
        logoClickImgView=findViewById(R.id.logoClickImgView)
        dateString=intent.getStringExtra("date").toString()
        studName=intent.getStringExtra("studentName").toString()
        studId=intent.getStringExtra("studentId").toString()
        studClass=intent.getStringExtra("studentClass").toString()
        staff_name=intent.getStringExtra("staffName").toString()
        staff_id=intent.getStringExtra("staffId").toString()
        studentName=findViewById(R.id.studentNameTV)
        studentClass=findViewById(R.id.studentClassTV)
        staffName=findViewById(R.id.staffTV)
        date_header=findViewById(R.id.dateTextView)
        studentName.text = studName
        studentClass.text = studClass
        staffName.text = staff_name
        val inputFormat: DateFormat = SimpleDateFormat("d/M/yyyy")
        val outputFormat: DateFormat = SimpleDateFormat("dd MMMM yyyy")
        val inputDateStr = dateString
        val date: Date = inputFormat.parse(inputDateStr)
        date_sel = outputFormat.format(date)
        Log.e("dt",date_sel)
        date_header.text = date_sel
        confirm=findViewById(R.id.reviewConfirmTextView)
        cancel=findViewById(R.id.cancelTextView)
        vpml_btn=findViewById(R.id.vpmlBtn)
        recyclerView=findViewById(R.id.recycler_view_alloted_time)
        info_img=findViewById(R.id.infoRoomImg)
        timeSlotList= ArrayList()
        timeSlotListPost= ArrayList()
        timeslotList()

        info_img.setOnClickListener {
            showRoomList()
        }
        confirm.setOnClickListener {
            alertReviewPage()


        }
        cancel.setOnClickListener {
            if (timeSlotListPost.get(0).booking_open.equals("y")) {

                showApiAlert(mContext,"Do you want to cancel this appointment?","Confirm")

            } else {
                DialogFunctions.commonErrorAlertDialog("Alert","Booking and cancellation date is over.",mContext)

            }
        }
        vpml_btn.setOnClickListener {
            if (confirmed_link.equals("")) {
            } else {
                val viewIntent = Intent(
                    "android.intent.action.VIEW",
                    Uri.parse(confirmed_link)
                )
                startActivity(viewIntent)
            }
        }
        backRelative.setOnClickListener(View.OnClickListener {
            finish()
        })
        heading.text= ConstantWords.parentmeetings
        logoClickImgView.setOnClickListener(View.OnClickListener {
            val intent = Intent(mContext, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        })
    }
    private fun showApiAlert(context: Context,message : String,msgHead : String){
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
            postSelectedSlot()
            dialog.dismiss()
        }
        val dialogButtonCancel = dialog.findViewById(R.id.btn_Cancel) as Button
        dialogButtonCancel.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }
    private fun postSelectedSlot(){
        progressDialogAdd.visibility = View.VISIBLE
        val token = PreferenceManager.getaccesstoken(mContext)
        val ptaInsertSuccessBody = PtaInsertApiModel(studId,timeSlotListPost[0].slot_id)
        val call: Call<PtaInsertModel> = ApiClient.getClient.pta_insert(ptaInsertSuccessBody,"Bearer "+token)
        call.enqueue(object : Callback<PtaInsertModel> {
            override fun onFailure(call: Call<PtaInsertModel>, t: Throwable) {
                Log.e("Error", t.localizedMessage)
                progressDialogAdd.visibility = View.GONE
            }
            override fun onResponse(call: Call<PtaInsertModel>, response: Response<PtaInsertModel>) {
                progressDialogAdd.visibility = View.GONE
                //val arraySize :Int = response.body()!!.responseArray.studentList.size
                if (response.body()!!.status==100)
                {

                    DialogFunctions.showDialogAlertSingleBtn(
                        mContext ,
                        "Alert",
                        "Reserved Only – Please review and confirm booking",
                        R.drawable.tick,
                        R.drawable.round
                    )
                    timeslotList()
                } else if (response.body()!!.status == 109) {
                    DialogFunctions.showDialogAlertSingleBtn(
                        mContext,
                        "Alert",
                        "Request cancelled successfully",
                        R.drawable.tick,
                        R.drawable.round
                    )
                    timeslotList()
                } else if (response.body()!!.status == 126) {
                    DialogFunctions.showDialogAlertSingleBtn(
                        mContext ,
                        "Alert",
                        "Slot is already booked by an another user",
                        R.drawable.exclamationicon,
                        R.drawable.round
                    )
//                                    getPtaAllotedDateList();
                } else if (response.body()!!.status == 124) {
                    DialogFunctions.showDialogAlertSingleBtn(
                        mContext ,
                        "Alert",
                        "Timeslot not found",
                        R.drawable.exclamationicon,
                        R.drawable.round
                    )
                } else if (response.body()!!.status == 125) {
                    DialogFunctions.showDialogAlertSingleBtn(
                        mContext,
                        "Alert",
                        getString(R.string.datexpirecontact),
                        R.drawable.exclamationicon,
                        R.drawable.round
                    )
                }
 else
                {

                    DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert), ConstantFunctions.commonErrorString(response.body()!!.status), mContext)
                }


            }

        })
    }
    private fun timeslotList(){
        alreadyslotBookedByUser = false
        timeSlotList= ArrayList()
        timeSlotListPost= ArrayList()
        progressDialogAdd.visibility = View.VISIBLE
        val token = PreferenceManager.getaccesstoken(mContext)
        val inputFormat: DateFormat = SimpleDateFormat("dd MMMM yyyy")
        val outputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        val inputDateStr = date_sel
        val date: Date = inputFormat.parse(inputDateStr)
        val outputDateStr: String = outputFormat.format(date)
        Log.e("dt", outputDateStr)
        val timeslotBody = PtaListApiModel(studId, staff_id, outputDateStr)
        val call: Call<PtaListModel> =
            ApiClient.getClient.pta_list(timeslotBody, "Bearer " + token)
        call.enqueue(object : Callback<PtaListModel> {
            override fun onFailure(call: Call<PtaListModel>, t: Throwable) {
                Log.e("Failed", t.localizedMessage)
                progressDialogAdd.visibility = View.GONE
            }

            override fun onResponse(
                call: Call<PtaListModel>,
                response: Response<PtaListModel>
            ) {
                val responsedata = response.body()
                progressDialogAdd.visibility = View.GONE

                if (responsedata!!.status == 100) {

                    // Log.e("STATUS LOGIN", responsedata.response.statuscode)
                     if (responsedata!!.data.isNotEmpty()) {
                            timeSlotList.addAll(responsedata!!.data)

                            for (i in timeSlotList.indices) {
                                if (timeSlotList[i].status.equals("2")) {

                                    timeSlotListPost.add(timeSlotList[i])
                                    alreadyslotBookedByUser = true

                                     confirm.visibility=View.GONE
                             cancel.visibility=View.GONE
                                }
                            }
                            for (i in timeSlotList.indices) {
                                if (timeSlotList[i].status.equals("3")) {
                                    confirmedslotBookedByUser = true
                                    confirmed_link = timeSlotList[i].vpml
                                     confirm.visibility=View.GONE
                             cancel.visibility=View.GONE
                                }
                            }
                            if (confirmedslotBookedByUser) {
                                if (confirmed_link.equals("")) {
                                    vpmlBtn.visibility=View.GONE
                                } else {
                                    vpmlBtn.visibility=View.VISIBLE
                                }
                                cancel.visibility = View.INVISIBLE
                                confirm.visibility = View.INVISIBLE
                            } else if (alreadyslotBookedByUser) {

                                cancel.visibility = View.VISIBLE
                                confirm.visibility = View.VISIBLE
                            } else {
                                cancel.visibility = View.INVISIBLE
                                confirm.visibility = View.INVISIBLE
                            }
//        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
//        mSwipeRefreshLayout.setRefreshing(false);
                            recyclerView.setHasFixedSize(true)
                            var recyclerViewLayoutManager: RecyclerView.LayoutManager =
                                GridLayoutManager(mContext, 3)
                            val spacing = 5 // 50px


                            recyclerView.layoutManager = recyclerViewLayoutManager

                            //recyclerView.layoutManager=LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false)

                            var timeslot_adapter =
                                TimeslotAdapter(mContext, timeSlotList, confirm, cancel)
                            recyclerView.adapter = timeslot_adapter
                        } else {

                            //CustomStatusDialog();
                            Toast.makeText(mContext, "No Data Available", Toast.LENGTH_SHORT).show()

                        }
                }else if (responsedata!!.status==310){
                    DialogFunctions.commonErrorAlertDialog("Alert","Slot is already booked by an another user.",
                        mContext)
                } else {

                    DialogFunctions.commonErrorAlertDialog(
                        mContext.resources.getString(R.string.alert),
                        ConstantFunctions.commonErrorString(response.body()!!.status),
                        mContext
                    )
                }

                recyclerView.addOnItemClickListener(object: OnItemClickListener {
                    override fun onItemClicked(position: Int, view: View) {

                        if (timeSlotList.get(position).status.equals("1")) {
                            DialogFunctions.commonErrorAlertDialog("Alert","This slot is not available.",mContext)

                        } else if (confirmedslotBookedByUser) {
                            DialogFunctions.commonErrorAlertDialog("Alert","Your time slot is already confirmed.",mContext)
                        } else if (!alreadyslotBookedByUser) {
                            timeSlotListPost=ArrayList()
                            timeSlotListPost.add(timeSlotList[position])
                            if (timeSlotListPost.get(0).booking_open
                                    .equals("y")
                            ) {
                                val inputFormat: DateFormat = SimpleDateFormat("hh:mm:ss")
                                val outputFormat: DateFormat = SimpleDateFormat("hh:mm aa")
                                val inputDateStr = timeSlotListPost[0].start_time
                                val date: Date = inputFormat.parse(inputDateStr)
                                val formt_fromtime= outputFormat.format(date)


                                val inputFormat2: DateFormat = SimpleDateFormat("hh:mm:ss")
                                val outputFormat2: DateFormat = SimpleDateFormat("hh:mm aa")
                                val inputDateStr2 = timeSlotListPost[0].end_time
                                val date2: Date = inputFormat2.parse(inputDateStr2)
                                val formt_totime: String = outputFormat2.format(date2)
                                Log.e("dt",formt_totime)

                                showApiAlert(mContext,"Do you want to reserve your appointment on "+ date_sel +" , "+
                                        formt_fromtime+" - "+formt_totime,"Alert"
                                )

                            } else {
                                DialogFunctions.commonErrorAlertDialog(
                                    mContext.resources.getString(R.string.alert),
                                    "Booking and cancellation date is over",
                                    mContext
                                )
                            }
                        } else {
                            if (timeSlotList.get(position).status.equals("2")
                            ) {
                                DialogFunctions.commonErrorAlertDialog(
                                    mContext.resources.getString(R.string.alert),
                                    "This slot is reserved by you for the Parents' Meeting. Click 'Cancel' option to cancel this appointment",
                                    mContext
                                )

                            } else {
                                DialogFunctions.commonErrorAlertDialog(
                                    mContext.resources.getString(R.string.alert),
                                    "Another Slot is already booked by you. If you want to take appointment on this time, please cancel earlier appointment and try",
                                    mContext
                                )
                            }
                        }
                    }

                })

            }
        })
    }
    private fun showRoomList(){

        val dialog = Dialog(mContext)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_room_slot_list)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val dialogDismiss = dialog.findViewById<View>(R.id.btn_dismiss) as Button
        val iconImageView = dialog.findViewById<View>(R.id.iconImageView) as ImageView
        val socialMediaList =
            dialog.findViewById<View>(R.id.recycler_view_social_media) as RecyclerView
        //if(mSocialMediaArray.get())

        //if(mSocialMediaArray.get())
        val divider = DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL)
        divider.setDrawable(ContextCompat.getDrawable(mContext,R.drawable.list_divider)!!)
        socialMediaList.addItemDecoration(divider)

        socialMediaList.setHasFixedSize(true)
        val llm = LinearLayoutManager(mContext)
        llm.orientation = LinearLayoutManager.VERTICAL
        socialMediaList.layoutManager = llm

        Log.e("tmsie",timeSlotList.size.toString())
        val socialMediaAdapter = ParentsEveningRoomListAdapter(mContext,timeSlotList)
        socialMediaList.adapter = socialMediaAdapter
        dialogDismiss.setOnClickListener { dialog.dismiss() }

        dialog.show()
    }
    private fun alertReviewPage(){
        val dialog = Dialog(mContext)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_review_parentmeeting)
        var btn_maybelater = dialog.findViewById(R.id.btn_Cancel) as Button
        var btn_ok = dialog.findViewById(R.id.btn_Ok) as Button
        btn_maybelater.setOnClickListener()
        {
            dialog.dismiss()
        }
        btn_ok.setOnClickListener {
            val intent = Intent(mContext, ReviewAppointmentsRecyclerViewActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
        dialog.show()
    }
}