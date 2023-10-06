package com.nas.alreem.activity.parent_meetings

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.activity.parent_meetings.adapter.ReviewAdapter
import com.nas.alreem.activity.parent_meetings.model.PtaConfirmApiModel
import com.nas.alreem.activity.parent_meetings.model.PtaConfirmModel
import com.nas.alreem.activity.parent_meetings.model.PtaDatesApiModel
import com.nas.alreem.activity.parent_meetings.model.PtaDatesModel
import com.nas.alreem.activity.parent_meetings.model.PtaReviewListResponseModel
import com.nas.alreem.activity.parent_meetings.model.ReviewListModel
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.ConstantWords
import com.nas.alreem.constants.DialogFunctions
import com.nas.alreem.constants.PreferenceManager
import com.nas.alreem.rest.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date

class ReviewAppointmentsRecyclerViewActivity:AppCompatActivity() {
    lateinit var mContext: Context
    lateinit var backRelative: RelativeLayout
    lateinit var heading: TextView
    lateinit var logoClickImgView: ImageView
    lateinit var progressDialogAdd: ProgressBar
    lateinit var review_rec: RecyclerView
    lateinit var review_list: ArrayList<ReviewListModel>
    lateinit var idList:ArrayList<Int>
    lateinit var home_icon: ImageView
    lateinit var confirm_tv: TextView
    var confimVisibility:Boolean=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parent_meeting_review)

        mContext=this
        initfn()

    }
    private fun initfn(){
        progressDialogAdd=findViewById(R.id.progressDialogAdd)
        heading=findViewById(R.id.heading)
        backRelative=findViewById(R.id.backRelative)
        logoClickImgView=findViewById(R.id.logoClickImgView)
        confirm_tv=findViewById(R.id.confirmTV)
        review_list = ArrayList()
        review_rec = findViewById(R.id.recycler_review)
        idList= ArrayList()

        if (ConstantFunctions.internetCheck(mContext))
        {
            reviewlistcall()
        }
        else
        {
            DialogFunctions.showInternetAlertDialog(mContext)
        }

        backRelative.setOnClickListener(View.OnClickListener {
            finish()
           /* val intent = Intent(mContext, ParentMeetingDetailActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)*/
        })
        heading.text= ConstantWords.parentmeetings
        logoClickImgView.setOnClickListener(View.OnClickListener {
            val intent = Intent(mContext, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        })
        confirm_tv.setOnClickListener {
            showerrorConfirm(mContext,"Do you want to confirm appointment?","Alert")
            //callConfirmPta()
        }
    }
    private fun reviewlistcall(){
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
                        progressDialogAdd.visibility = View.GONE
                        review_rec.layoutManager= LinearLayoutManager(mContext)
                        var review_adapter= ReviewAdapter(mContext,review_list,ReviewAppointmentsRecyclerViewActivity(),
                            progressDialogAdd,review_rec,idList,confirm_tv)
                        review_rec.adapter=review_adapter
                    }else{
                        DialogFunctions.commonErrorAlertDialog("Alert","No Appointments Available.",mContext)
                    }

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
                } else
                {

                    DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert), ConstantFunctions.commonErrorString(response.body()!!.status), mContext)
                }


            }

        })

    }
    private fun showerrorConfirm(context: Context,message : String,msgHead : String)
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
            callConfirmPta()

        }
        btn_Cancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
    private fun callConfirmPta(){
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
                    if (ConstantFunctions.internetCheck(mContext))
                    {
                        reviewlistcall()
                    }
                    else
                    {
                        DialogFunctions.showInternetAlertDialog(mContext)
                    }


                }
                else
                {

                    DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert), ConstantFunctions.commonErrorString(response.body()!!.status), mContext)
                }


            }

        })

    }
}