package com.nas.alreem.activity.parent_meetings.adapter

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.nas.alreem.R
import com.nas.alreem.activity.parent_meetings.ReviewAppointmentsRecyclerViewActivity
import com.nas.alreem.activity.parent_meetings.model.*
import com.nas.alreem.constants.ApiClient
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.DialogFunctions
import com.nas.alreem.constants.PreferenceManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

internal class ReviewAdapter (var mContext: Context, var review_list:ArrayList<ReviewListModel>,
                              var reviewActivity:ReviewAppointmentsRecyclerViewActivity,
                              var progressDialogAdd: ProgressBar, var review_rec: RecyclerView,
                              var idList:ArrayList<Int>, var confirm_tv: TextView
) :
    RecyclerView.Adapter<ReviewAdapter.MyViewHolder>() {
    lateinit var id_list:ArrayList<Int>
    var confimVisibility:Boolean=false
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var student_name: TextView =view.findViewById(R.id.studNameTV)
        var student_class: TextView =view.findViewById(R.id.classNameTV)
        var staff_name: TextView =view.findViewById(R.id.staffNameTV)
        var start_date: TextView =view.findViewById(R.id.reserveDateTimeTextView)
        var end_date: TextView =view.findViewById(R.id.expireDateTimeTextView)
        var student_image: ImageView =view.findViewById(R.id.imgView)
        var cancel_img: ImageView =view.findViewById(R.id.cancelAppointment)
        var confirm_img: ImageView =view.findViewById(R.id.confirmAppointment)
        var confirm_imgview: ImageView =view.findViewById(R.id.confirmationImageview)
        var addtocalendar_img: ImageView =view.findViewById(R.id.addTocalendar)
        var vpml_img: Button =view.findViewById(R.id.vpml)


    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_review_list, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
progressDialogAdd.visibility=View.GONE
        // reviewActivity.fnctiondemo()
        holder.student_name.text = review_list[position].student
        holder.student_class.text = review_list[position].class_name
        holder.staff_name.text = review_list[position].staff
        for (i in review_list.indices){
            if (review_list[i].status==2&&review_list[i].booking_open.equals("y")){
                idList.add(review_list[i].id.toInt())
                // confirm_tv.visibility=View.VISIBLE
                confimVisibility=true

            }else{
                // confimVisibility=false
                // confirm_tv.visibility=View.GONE
            }
        }
        if (confimVisibility==true){
            confirm_tv.visibility=View.VISIBLE
        }else{
            confirm_tv.visibility=View.GONE
        }
        var date_sel=review_list[position].date
        Log.e("dt",date_sel.toString())
        val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        val outputFormat: DateFormat = SimpleDateFormat("dd MMM yyyy")
        val inputDateStr = date_sel
        val date: Date = inputFormat.parse(inputDateStr)
        var date_frmt = outputFormat.format(date)
        Log.e("datefrmt",date_frmt.toString())
        var st_time=review_list[position].start_time
        val inputFormat2: DateFormat = SimpleDateFormat("hh:mm:ss")
        val outputFormat2: DateFormat = SimpleDateFormat("hh:mm aa")
        val inputDateStr2 = st_time
        val date2: Date = inputFormat2.parse(inputDateStr2)
        var st_date_frmt = outputFormat2.format(date2)
        var end_date=review_list[position].end_time
        val inputFormat3: DateFormat = SimpleDateFormat("hh:mm:ss")
        val outputFormat3: DateFormat = SimpleDateFormat("hh:mm aa")
        val inputDateStr3 = end_date
        val date3: Date = inputFormat3.parse(inputDateStr3)
        var end_date_frmt = outputFormat3.format(date3)
        var book_endDate=review_list[position].book_end_date
        val inputFormat4: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val outputFormat4: DateFormat = SimpleDateFormat("dd MMM yyyy hh:mm a")
        val inputDateStr4 = book_endDate
        val date4: Date = inputFormat4.parse(inputDateStr4)
        var bookend_date_frmt = outputFormat4.format(date4)

        var st_date=date_frmt + " "+st_date_frmt+" - "+end_date_frmt
        Log.e("dateline",st_date.toString())
        holder.start_date.text = st_date
        var en_date=bookend_date_frmt
        holder.end_date.text = "Confirm/Cancellation closes at "+ en_date

        var photo=review_list[position].student_photo
        if (review_list[position].student_photo.isEmpty()){
            holder.student_image.setImageResource(R.drawable.student)
        }
        else{
            Glide.with(mContext) //1
                .load(photo)
                .placeholder(R.drawable.student)
                .error(R.drawable.student)
                .skipMemoryCache(true) //2
                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                .transform(CircleCrop()) //4
                .into(holder.student_image)
        }
        if(review_list[position].status==3&&review_list[position].booking_open.equals("y")){
            holder.confirm_img.visibility = View.GONE
            holder.cancel_img.visibility = View.VISIBLE
            holder.addtocalendar_img.visibility = View.VISIBLE
            if(review_list[position].vpml.equals("")){
                holder.vpml_img.visibility = View.GONE
            }
            else{
                holder.vpml_img.visibility = View.VISIBLE
            }
        }else if (review_list[position].status==2&&review_list[position].booking_open.equals("y")){
            holder.confirm_imgview.setBackgroundResource(R.drawable.doubtinparticipatingsmallicon)
            holder.confirm_img.visibility = View.VISIBLE
            holder.confirm_img.setBackgroundResource(R.drawable.confirm)
            holder.cancel_img.visibility = View.VISIBLE
            holder.addtocalendar_img.visibility = View.GONE
            holder.vpml_img.visibility = View.GONE
        }
        else if(review_list[position].status==3&&review_list[position].booking_open.equals("n")){
            holder.confirm_imgview.setBackgroundResource(R.drawable.tick_icon)
            holder.addtocalendar_img.visibility = View.VISIBLE
            holder.confirm_img.visibility = View.GONE
            holder.cancel_img.visibility = View.VISIBLE
            holder.confirm_img.setImageResource(R.drawable.confirm_dis)
            holder.cancel_img.setImageResource(R.drawable.cancel_dis)
            if (review_list[position].vpml.equals("")) {
                holder.vpml_img.visibility = View.GONE
            } else {
                holder.vpml_img.visibility = View.VISIBLE
            }
        }
        else if(review_list[position].status==2&&review_list[position].booking_open.equals("n")){
            holder.confirm_imgview.setBackgroundResource(R.drawable.doubtinparticipatingsmallicon)
            holder.addtocalendar_img.visibility = View.GONE
            holder.confirm_img.visibility = View.VISIBLE
            holder.cancel_img.visibility = View.VISIBLE
            holder.confirm_img.setImageResource(R.drawable.confirm_dis)
            holder.cancel_img.setImageResource(R.drawable.cancel_dis)
            holder.vpml_img.visibility = View.GONE
        }
        holder.confirm_img.setOnClickListener {
            if(review_list[position].booking_open.equals("y")) {
                id_list = ArrayList()
                var id_sel = review_list[position].id.toInt()
                id_list.add(id_sel)
                showerrorConfirm(mContext, "Do you want to confirm appointment?", "Alert", id_list)
            }else{
                DialogFunctions.commonErrorAlertDialog("Alert","Booking and cancellation date is over",mContext)
            }
            //confirmPtaApi(id_list)
        }
        holder.cancel_img.setOnClickListener {
            if (review_list[position].booking_open.equals("y")) {
                id_list = ArrayList()
                var id_sel = review_list[position].id.toInt()
                id_list.add(id_sel)
                showerrorCancel(
                    mContext,
                    "Do you want to cancel appointment?",
                    "Alert",
                    review_list[position].pta_time_slot_id,
                    review_list[position].student_id,
                    review_list[position].staff_id
                )
            }else{
                DialogFunctions.commonErrorAlertDialog("Alert","Booking and cancellation date is over",mContext)

            }
        }
        holder.vpml_img.setOnClickListener {
            if(review_list[position].vpml.equals("")){

            }else {
                val i = Intent(Intent.ACTION_VIEW, Uri.parse(review_list[position].vpml))
                mContext.startActivity(i)
            }
        }
        holder.addtocalendar_img.setOnClickListener {
            var startTime: Long = 0
            var endTime: Long = 0
            var vpmlURL = ""
            try {
//                    Date dateStart = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(mVideosModelArrayList.get(0).getDateAppointment());
                val dateStart = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).parse(
                    review_list[position].date
                        .toString() + " " + review_list[position].start_time
                )
                startTime = dateStart.time
                val dateEnd = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).parse(
                    review_list[position].date
                        .toString() + " " + review_list[position].end_time
                )
                endTime = dateEnd.time
                println("Start time$startTime:::::::::: endTime$endTime")
                vpmlURL = if (review_list[position].vpml.equals("")) {
                    ""
                } else {
                    review_list[position].vpml
                }
            } catch (e: Exception) {
            }

            val cal = Calendar.getInstance()
            val intent = Intent(Intent.ACTION_EDIT)
            intent.type = "vnd.android.cursor.item/event"
            intent.putExtra("beginTime", startTime)
            intent.putExtra("allDay", true)
            intent.putExtra("rrule", "FREQ=YEARLY")
            intent.putExtra("endTime", endTime)
            intent.putExtra("title", "PARENT MEETING")
            intent.putExtra("description", vpmlURL)
           mContext.startActivity(intent)
        }


    }
    override fun getItemCount(): Int {

        return review_list.size

    }

    private fun reviewlistcall() {
        review_list=ArrayList()
        progressDialogAdd.visibility = View.VISIBLE
        val token = PreferenceManager.getaccesstoken(mContext)

        val call: Call<PtaReviewListResponseModel> = ApiClient.getClient.ptaReviewList("Bearer "+token)
        call.enqueue(object : Callback<PtaReviewListResponseModel> {
            override fun onFailure(call: Call<PtaReviewListResponseModel>, t: Throwable) {
                Log.e("Error", t.localizedMessage)
                progressDialogAdd.visibility = View.GONE
            }
            override fun onResponse(call: Call<PtaReviewListResponseModel>, response: Response<PtaReviewListResponseModel>) {
                progressDialogAdd.visibility = View.GONE
                //val arraySize :Int = response.body()!!.responseArray.studentList.size
                if (response.body()!!.status==100)
                {
                    review_list.addAll(response.body()!!.data)
                    if (review_list.size>0){
                        review_rec.layoutManager= LinearLayoutManager(mContext)
                        var review_adapter= ReviewAdapter(mContext,review_list,ReviewAppointmentsRecyclerViewActivity(),
                            progressDialogAdd,review_rec,idList,confirm_tv)
                        review_rec.adapter=review_adapter
                    }else{
                        DialogFunctions.commonErrorAlertDialog("Alert","No Appointments Available.",mContext)
                    }

                    for (i in review_list.indices){
                        if (review_list[i].status.equals("2")&&review_list[i].booking_open.equals("y")){
                            idList.add(review_list[i].id.toInt())
                            // confirm_tv.visibility=View.VISIBLE
                            confimVisibility=true

                        }/*else{
                            confirm_tv.visibility=View.GONE
                        }*/
                    }
                    if (confimVisibility==true){
                        confirm_tv.visibility=View.VISIBLE
                    }else{
                        confirm_tv.visibility=View.GONE
                    }
                } else
                {

                    DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert), ConstantFunctions.commonErrorString(response.body()!!.status), mContext)
                }


            }

        })

    }
    private fun postSelectedSlot(idList: Int,stud_id:String,staff_id: String){
        progressDialogAdd.visibility = View.VISIBLE
        val token = PreferenceManager.getaccesstoken(mContext)
        var idString:String=idList.toString()
        val ptaInsertSuccessBody = PtaInsertApiModel(stud_id,idList,staff_id)
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
                    reviewlistcall()
                } else if (response.body()!!.status== 109) {
                    DialogFunctions.showDialogAlertSingleBtn(
                        mContext,
                        "Success",
                        "Successfully cancelled appointment.",
                        R.drawable.tick,
                        R.drawable.round
                    )
                    reviewlistcall()
                } else if (response.body()!!.status == 126) {
                    DialogFunctions.showDialogAlertSingleBtn(
                        mContext ,
                        "Alert",
                        "Slot is already booked by an another user",
                        R.drawable.exclamationicon,
                        R.drawable.round
                    )
                                   //getPtaAllotedDateList();
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
                        "Alert",mContext.getString(R.string.datexpirecontact),
                        R.drawable.exclamationicon,
                        R.drawable.round
                    )
                }else if (response.body()!!.status == 127) {
                    DialogFunctions.showDialogAlertSingleBtn(
                        mContext,
                        "Success",
                        "Successfully confirmed appointment.",
                        R.drawable.tick,
                        R.drawable.round
                    )
                    reviewlistcall()
                }
                else
                {
                    DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert), ConstantFunctions.commonErrorString(response.body()!!.status), mContext)
                }


            }

        })
    }
    private fun showSuccess(context: Context,message : String,msgHead : String)
    {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_success_alert)
        var iconImageView = dialog.findViewById(R.id.iconImageView) as ImageView
        iconImageView.setImageResource(R.drawable.tick)
        var alertHead = dialog.findViewById(R.id.alertHead) as TextView
        var text_dialog = dialog.findViewById(R.id.text_dialog) as TextView
        var btn_Ok = dialog.findViewById(R.id.btn_Ok) as Button
        text_dialog.text = message
        alertHead.text = msgHead
        btn_Ok.setOnClickListener()
        {
            dialog.dismiss()
            reviewlistcall()
            /* reviewActivity.init()
             reviewActivity.reviewlistcall()*/
            /* val intent = Intent(context, ReviewAppointmentsRecyclerViewActivity::class.java)
             context.startActivity(intent)*/


        }
        dialog.show()
    }

    private fun showerror(context: Context,message : String,msgHead : String)
    {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_common_error_alert)
        var iconImageView = dialog.findViewById(R.id.iconImageView) as ImageView
        iconImageView.setImageResource(R.drawable.questionmark_icon)
        var alertHead = dialog.findViewById(R.id.alertHead) as TextView
        var text_dialog = dialog.findViewById(R.id.text_dialog) as TextView
        var btn_Ok = dialog.findViewById(R.id.btn_Ok) as Button
        text_dialog.text = message
        alertHead.text = msgHead
        btn_Ok.setOnClickListener()
        {
            dialog.dismiss()


        }
        dialog.show()
    }
    private fun showerrorCancel(context: Context,message : String,msgHead : String,id_list:Int,stud_id:String,staff_id:String)
    {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_ok_cancel)
        var iconImageView = dialog.findViewById(R.id.iconImageView) as ImageView
        iconImageView.setImageResource(R.drawable.questionmark_icon)
        var alertHead = dialog.findViewById(R.id.alertHead) as TextView
        var text_dialog = dialog.findViewById(R.id.text_dialog) as TextView
        var btn_Ok = dialog.findViewById(R.id.btn_Ok) as Button
        var btn_Cancel = dialog.findViewById(R.id.btn_Cancel) as Button
        text_dialog.text = message
        alertHead.text = msgHead
        btn_Ok.setOnClickListener()
        {
            dialog.dismiss()
            postSelectedSlot(id_list,stud_id,staff_id)
            //cancelPtaApi(id_list)

        }
        btn_Cancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
    private fun showerrorConfirm(context: Context,message : String,msgHead : String,id_list:ArrayList<Int>)
    {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_ok_cancel)
        var iconImageView = dialog.findViewById(R.id.iconImageView) as ImageView
        iconImageView.setImageResource(R.drawable.questionmark_icon)
        var alertHead = dialog.findViewById(R.id.alertHead) as TextView
        var text_dialog = dialog.findViewById(R.id.text_dialog) as TextView
        var btn_Ok = dialog.findViewById(R.id.btn_Ok) as Button
        var btn_Cancel = dialog.findViewById(R.id.btn_Cancel) as Button
        text_dialog.text = message
        alertHead.text = msgHead
        btn_Ok.setOnClickListener()
        {
            dialog.dismiss()
            //postSelectedSlot(id_list,stud_id)
            confirmPtaApi(id_list)

        }
        btn_Cancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
    private fun confirmPtaApi(idList: ArrayList<Int>){
        progressDialogAdd.visibility = View.VISIBLE
        val token = PreferenceManager.getaccesstoken(mContext)
        var idString:String=idList.toString()
        val ptaconfirmSuccessBody = PtaConfirmApiModel(idString)
        val call: Call<PtaConfirmModel> = ApiClient.getClient.pta_confirm(ptaconfirmSuccessBody,"Bearer "+token)
        call.enqueue(object : Callback<PtaConfirmModel> {
            override fun onFailure(call: Call<PtaConfirmModel>, t: Throwable) {
                Log.e("Error", t.localizedMessage)
                progressDialogAdd.visibility = View.GONE
            }
            override fun onResponse(call: Call<PtaConfirmModel>, response: Response<PtaConfirmModel>) {
                progressDialogAdd.visibility = View.GONE
                //val arraySize :Int = response.body()!!.responseArray.studentList.size
                if (response.body()!!.status==100)
                {
                    DialogFunctions.commonSuccessAlertDialog("Success","Successfully confirmed appointment.",mContext)
                    reviewlistcall()
                } else
                {

                    DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert), ConstantFunctions.commonErrorString(response.body()!!.status), mContext)
                }


            }

        })

    }
}