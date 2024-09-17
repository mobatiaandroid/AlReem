package com.nas.alreem.activity.cca.adapter

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
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.activity.cca.model.CCADetailModel
import com.nas.alreem.activity.cca.model.CCAchoiceModel
import com.nas.alreem.activity.cca.model.WeekListModel
import com.nas.alreem.appcontroller.AppController
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.DialogFunctions
import com.nas.alreem.constants.PreferenceManager
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class CCAsChoiceListActivityAdapter :
    RecyclerView.Adapter<CCAsChoiceListActivityAdapter.MyViewHolder> {
    //    ArrayList<String> mSocialMediaModels;
    var mCCAmodelArrayList: ArrayList<CCAchoiceModel>
    var mContext: Context
    var dayPosition = 0
    var choicePosition = 0
    var ccaDetailpos = 0
    var msgRelative: RelativeLayout? = null
    var weekList: ArrayList<WeekListModel>? = null
    var ccaDetailModelArrayList: ArrayList<CCADetailModel>? = null
    var submitBtn: Button? = null
    var nextBtn: Button? = null
    var filled: Boolean? = null
    var recyclerWeek: RecyclerView? = null

    constructor(
        mContext: Context,
        mCCAmodelArrayList: ArrayList<CCAchoiceModel>,
        mdayPosition: Int,
        mWeekList: ArrayList<WeekListModel>?,
        mChoicePosition: Int,
        recyclerWeek: RecyclerView?,
        ccaDetailModelArrayList: ArrayList<CCADetailModel>?,
        submitBtn: Button?,
        nextBtn: Button?,
        filled: Boolean?,
        ccaDetailpos: Int,
        msgRelative: RelativeLayout?
    ) {
        this.mContext = mContext
        this.mCCAmodelArrayList = mCCAmodelArrayList
        dayPosition = mdayPosition
        weekList = mWeekList
        choicePosition = mChoicePosition
        this.recyclerWeek = recyclerWeek
        this.ccaDetailModelArrayList = ccaDetailModelArrayList
        this.submitBtn = submitBtn
        this.nextBtn = nextBtn
        this.filled = filled
        this.ccaDetailpos = ccaDetailpos
        this.msgRelative = msgRelative

    }

    constructor(mContext: Context, mCCAmodelArrayList: ArrayList<CCAchoiceModel>) {
        this.mContext = mContext
        this.mCCAmodelArrayList = mCCAmodelArrayList
    }

    constructor(
        mContext: Context,
        mCCAmodelArrayList: ArrayList<CCAchoiceModel>,
        mdayPosition: Int,
        mWeekList: ArrayList<WeekListModel>?
    ) {
        this.mContext = mContext
        this.mCCAmodelArrayList = mCCAmodelArrayList
        dayPosition = mdayPosition
        weekList = mWeekList
    }

    constructor(
        mContext: Context,
        mCCAmodelArrayList: ArrayList<CCAchoiceModel>,
        mdayPosition: Int,
        mWeekList: ArrayList<WeekListModel>?,
        mChoicePosition: Int,
        recyclerWeek: RecyclerView?
    ) {
        this.mContext = mContext
        this.mCCAmodelArrayList = mCCAmodelArrayList
        dayPosition = mdayPosition
        weekList = mWeekList
        choicePosition = mChoicePosition
        this.recyclerWeek = recyclerWeek

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_ccalist_activity_new, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.confirmationImageview.visibility = View.VISIBLE



        if (mCCAmodelArrayList[position].venue != null) {
            if (mCCAmodelArrayList[position].venue
                    .equals("0") || mCCAmodelArrayList[position].venue
                    .equals("")
            ) {
                holder.textViewCCAVenue.visibility = View.GONE
            } else {
                holder.textViewCCAVenue.text =
                    java.lang.String.format("Location    : %s", mCCAmodelArrayList[position].venue)
                holder.textViewCCAVenue.visibility = View.VISIBLE
            }
        } else {
            holder.textViewCCAVenue.visibility = View.GONE
        }

//
//        System.out.println("DESC TEST"+mCCAmodelArrayList.get(position).getDescription());
//
        if (mCCAmodelArrayList[position].description != null) {
            if (mCCAmodelArrayList[position].description
                    .equals("0") || mCCAmodelArrayList[position].description
                    .equals("")
            ) {
                holder.descriptionRel.visibility = View.GONE
            } else {
                holder.descriptionRel.visibility = View.VISIBLE
                holder.descriptionTxt.text =
                    java.lang.String.format("Description : %s",
                        mCCAmodelArrayList[position].description
                    )
                if (mCCAmodelArrayList[position].description!!.length > 22) {
                    // holder.readMoreTxt.visibility = View.VISIBLE
                } else {
                    holder.readMoreTxt.visibility = View.GONE
                }
                holder.readMoreTxt.setOnClickListener {
                    ConstantFunctions.Companion.showDialogueWithOk(
                        mContext,
                        mCCAmodelArrayList[position].description!!,
                        "Description"
                    )
                }
            }
        } else {
            holder.descriptionRel.visibility = View.GONE
        }
        //        //   Log.e("DESC ADA",mCCAmodelArrayList.get(position).getDescription());
//
//        Integer count=holder.descriptionTxt.getLineCount();
//        Log.e("LINE COUNT", String.valueOf(count));
//
//
        // if (choicePosition == 0) {


        if (mCCAmodelArrayList[position].slot_remaining_count==0) {

            if (mCCAmodelArrayList[position].attending_status.equals("1"))
            {

                if(mCCAmodelArrayList[position].status.equals("1"))
                {
                    AppController.keyy="1"
                    holder.confirmationImageview.setBackgroundResource(R.drawable.participatingsmallicon_new)
                    holder.listTxtView.setTextColor(mContext.resources.getColor(R.color.black))
                    AppController.weekList.get(dayPosition).choiceStatus=("1")
                    ccaDetailModelArrayList!![ccaDetailpos].choice1=(mCCAmodelArrayList[position].cca_item_name)
                    ccaDetailModelArrayList!![ccaDetailpos].choice1Id=(mCCAmodelArrayList[position].cca_details_id)
                    ccaDetailModelArrayList!![ccaDetailpos].choiceitem1Id=(mCCAmodelArrayList[position].cca_item_id)
                }
                else{
                    holder.confirmationImageview.setBackgroundResource(R.drawable.close_icon_with_white)
                    holder.listTxtView.setTextColor(mContext.resources.getColor(R.color.black))
                }
            }
            else{
                // AppController.keyy="0"

                holder.listTxtView.alpha = 0.5f
                holder.confirmationImageview.setImageResource(R.drawable.disablecrossicon)
                holder.listTxtView.setTextColor(mContext.resources.getColor(R.color.grey))
            }

        }
        else{

            if(mCCAmodelArrayList[position].status.equals("1"))
            {
                AppController.keyy="1"
                holder.confirmationImageview.setBackgroundResource(R.drawable.participatingsmallicon_new)
                holder.listTxtView.setTextColor(mContext.resources.getColor(R.color.black))
                AppController.weekList.get(dayPosition).choiceStatus=("1")
                ccaDetailModelArrayList!![ccaDetailpos].choice1=(mCCAmodelArrayList[position].cca_item_name)
                ccaDetailModelArrayList!![ccaDetailpos].choice1Id=(mCCAmodelArrayList[position].cca_details_id)
                ccaDetailModelArrayList!![ccaDetailpos].choiceitem1Id=(mCCAmodelArrayList[position].cca_item_id)
            }
            else{
                if (mCCAmodelArrayList[position].slot_remaining_count!!<0)
                {
                    holder.listTxtView.alpha = 0.5f
                    holder.confirmationImageview.setImageResource(R.drawable.disablecrossicon)
                    holder.listTxtView.setTextColor(mContext.resources.getColor(R.color.grey))
                }
                else
                {
                    holder.confirmationImageview.setBackgroundResource(R.drawable.close_icon_with_white)
                    holder.listTxtView.setTextColor(mContext.resources.getColor(R.color.black))
                }

            }


        }

        //showApiAlert(mContext,"Do you want to reserve this appointment?","Confirm",mCCAmodelArrayList[position].cca_details_id)


        /*} else {
            System.out.println(
                "disable2::" + mCCAmodelArrayList[position].disableCccaiem.toString() + " @ " + position
            )
            System.out.println(
                "disable2::" + mCCAmodelArrayList[position].disableCccaiem.toString() + " @dayPosition: " + dayPosition
            )
            if (mCCAmodelArrayList[position].disableCccaiem!!) {
                holder.confirmationImageview.setBackgroundResource(R.drawable.disablecrossicon)
                holder.listTxtView.setTextColor(mContext.resources.getColor(R.color.grey))
            } else if (mCCAmodelArrayList[position].status.equals("0")) {
                holder.confirmationImageview.setBackgroundResource(R.drawable.close_icon_with_white)
                holder.listTxtView.setTextColor(mContext.resources.getColor(R.color.black))
            } else {
                holder.confirmationImageview.setBackgroundResource(R.drawable.participatingsmallicon_new)
                AppController.weekList.get(dayPosition).choiceStatus1=("1")
                ccaDetailModelArrayList!![ccaDetailpos].choice2=(mCCAmodelArrayList[position].cca_item_name)
                ccaDetailModelArrayList!![ccaDetailpos].choice2Id=(mCCAmodelArrayList[position].cca_details_id)



                val mCCAsWeekListAdapter =
                    CCAsWeekListAdapter(mContext, AppController.weekList, dayPosition, msgRelative)
                mCCAsWeekListAdapter.notifyDataSetChanged()
                recyclerWeek!!.adapter = mCCAsWeekListAdapter
            }
        }*/
        for (j in 0 until AppController.weekList.size) {
            if (AppController.weekList.get(j).choiceStatus
                    .equals("0") || AppController.weekList.get(j).choiceStatus1
                    .equals("0")
            ) {
                filled = false
                break
            } else {
                filled = true
            }
            if (!filled!!) {
                break
            }
        }
        if (filled!!) {
            submitBtn!!.background.alpha = 255
            submitBtn!!.visibility = View.VISIBLE
            nextBtn!!.background.alpha = 255
            nextBtn!!.visibility = View.GONE
        } else {
            submitBtn!!.background.alpha = 150
            submitBtn!!.visibility = View.INVISIBLE
            nextBtn!!.background.alpha = 255
            nextBtn!!.visibility = View.VISIBLE
        }

        holder.listTxtView.setText(mCCAmodelArrayList[position].cca_item_name)
        if (mCCAmodelArrayList[position].cca_item_start_time != null && mCCAmodelArrayList[position].cca_item_end_time != null) {
            holder.textViewCCAaDateItem.visibility = View.VISIBLE
            holder.textViewCCAaDateItem.text = "(" + convertTimeToAMPM(
                mCCAmodelArrayList[position].cca_item_start_time
            ) + " - " + convertTimeToAMPM(
                mCCAmodelArrayList[position].cca_item_end_time
            ) + ")"
        } else if (mCCAmodelArrayList[position].cca_item_start_time != null) {
            holder.textViewCCAaDateItem.visibility = View.VISIBLE
            holder.textViewCCAaDateItem.text = "(" + convertTimeToAMPM(
                mCCAmodelArrayList[position].cca_item_start_time
            ) + ")"
        } else if (mCCAmodelArrayList[position].cca_item_end_time != null) {
            holder.textViewCCAaDateItem.visibility = View.VISIBLE
            holder.textViewCCAaDateItem.text = "(" + convertTimeToAMPM(
                mCCAmodelArrayList[position].cca_item_end_time
            ) + ")"
        } else {
            holder.textViewCCAaDateItem.visibility = View.GONE
        }

    }
    private fun showApiAlert(
        context: Context,
        message: String,
        msgHead: String,
        ccaDetailsId: String?
    ){
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
            if (ConstantFunctions.internetCheck(mContext))
            {
                // ccaSubmitAPI(ccaDetailsId)
            }
            else
            {
                DialogFunctions.showInternetAlertDialog(mContext)
            }
            dialog.dismiss()
        }
        val dialogButtonCancel = dialog.findViewById(R.id.btn_Cancel) as Button
        dialogButtonCancel.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

    private fun ccaSubmitAPI(ccaDetailsId: String?) {

        /* var model= CCAReservetRequestModel(
             PreferenceManager.getStudIdForCCA(mContext).toString(),
             PreferenceManager.getCCAItemId(mContext).toString(), ccaDetailsId!!
         )
         val token = PreferenceManager.getaccesstoken(mContext)
         val call: Call<CCASubmitResponseModel> =
             ApiClient.getClient.ccareserve( model,"Bearer $token")
        // progressBar.visibility = View.VISIBLE
         call.enqueue(object : Callback<CCASubmitResponseModel> {
             override fun onResponse(
                 call: Call<CCASubmitResponseModel>,
                 response: Response<CCASubmitResponseModel>
             ) {
               //  progressBar.visibility = View.GONE
                 if (response.isSuccessful){
                     if (response.body() != null){
                         if (response.body()!!.status!!.equals(100)){

 //                            val survey: Int = secobj.optInt("survey")


                         }
                         else if (response.body()!!.status!!.equals(109))
                         {


                         }
                         else{

                             Toast.makeText(mContext, "Failure", Toast.LENGTH_SHORT).show()
                         }

                     }else{

                        // ConstantFunctions.showDialogueWithOk(mContext,getString(R.string.common_error),"Alert")
                     }
                 }else{

                    // ConstantFunctions.showDialogueWithOk(mContext,getString(R.string.common_error),"Alert")
                 }
             }

             override fun onFailure(call: Call<CCASubmitResponseModel>, t: Throwable) {
                 //progressBar.visibility = View.GONE
                // ConstantFunctions.showDialogueWithOk(mContext,getString(R.string.common_error),"Alert")
             }

         })*/
    }
    override fun getItemCount(): Int {
        return mCCAmodelArrayList.size
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // var cancel:TextView
        var listTxtView: TextView
        var textViewCCAaDateItem: TextView
        var confirmationImageview: ImageView
        var textViewCCAVenue: TextView
        var descriptionTxt: TextView
        var readMoreTxt: TextView
        var descriptionRel: RelativeLayout

        init {
            // cancel=view.findViewById<View>(R.id.cancel) as TextView
            textViewCCAaDateItem = view.findViewById<View>(R.id.textViewCCAaDateItem) as TextView
            listTxtView = view.findViewById<View>(R.id.textViewCCAaItem) as TextView
            textViewCCAVenue = view.findViewById<View>(R.id.textViewCCAVenue) as TextView
            descriptionTxt = view.findViewById<View>(R.id.descriptionTxt) as TextView
            readMoreTxt = view.findViewById<View>(R.id.readMoreTxt) as TextView
            confirmationImageview = view.findViewById<View>(R.id.confirmationImageview) as ImageView
            descriptionRel = view.findViewById<View>(R.id.descriptionRel) as RelativeLayout
        }
    }

    companion object {
        fun convertTimeToAMPM(date: String?): String {
            var strCurrentDate = ""
            var format = SimpleDateFormat("HH:mm:ss", Locale.ENGLISH)
            var newDate: Date? = null
            try {
                newDate = format.parse(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            format = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
            strCurrentDate = format.format(newDate)
            return strCurrentDate
        }
    }
}