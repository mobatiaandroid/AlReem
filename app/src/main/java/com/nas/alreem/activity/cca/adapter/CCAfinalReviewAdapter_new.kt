package com.nas.alreem.activity.cca.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.activity.cca.model.CCADetailModel
import com.nas.alreem.activity.cca.model.CCAReviewAfterSubmissionModel
import com.nas.alreem.activity.cca.model.CCAReviewResevedModel
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class CCAfinalReviewAdapter_new(
    var mContext: Context,
    mCCADetailModelArrayList: ArrayList<CCAReviewResevedModel>
) :
    RecyclerView.Adapter<CCAfinalReviewAdapter_new.MyViewHolder>() {
    var mCCADetailModelArrayList: ArrayList<CCAReviewResevedModel>

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var textViewCCAaDateItemChoice1: TextView
        var textViewCCADay: TextView
        var textViewCCAChoice1: TextView
        var locationTxt: TextView
        var descriptionTxt: TextView
        var linearChoice1: LinearLayout

        init {
            textViewCCAaDateItemChoice1 =
                view.findViewById<View>(R.id.textViewCCAaDateItemChoice1) as TextView

            textViewCCADay = view.findViewById<View>(R.id.textViewCCADay) as TextView
            textViewCCAChoice1 = view.findViewById<View>(R.id.textViewCCAChoice1) as TextView

            locationTxt = view.findViewById<View>(R.id.locationTxt) as TextView

            descriptionTxt = view.findViewById<View>(R.id.descriptionTxt) as TextView
            linearChoice1 = view.findViewById<View>(R.id.linearChoice1) as LinearLayout
        }
    }

    //    public CCAfinalReviewAdapter(Context mContext) {
    //        this.mContext = mContext;
    //    }
    //    public CCAfinalReviewAdapter(Context mContext,ArrayList<CCADetailModel>mCCADetailModelArrayList) {
    //        this.mContext = mContext;
    //        this.mCCADetailModelArrayList = mCCADetailModelArrayList;
    //    }
    init {
        this.mCCADetailModelArrayList = mCCADetailModelArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_cca_final_review_new, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (mCCADetailModelArrayList[position].venue != null) {

            if (mCCADetailModelArrayList[position].venue
                    .equals("0") || mCCADetailModelArrayList[position].venue
                    .equals("")
            ) {
                holder.locationTxt.visibility = View.GONE
            } else {
                holder.locationTxt.visibility = View.VISIBLE
                holder.locationTxt.text =
                    "Location      : " + mCCADetailModelArrayList[position].venue
            } }
        else {
            holder.locationTxt.visibility = View.GONE
        }






        if (mCCADetailModelArrayList[position].cca_item_description != null) {

            if (mCCADetailModelArrayList[position].cca_item_description
                    .equals("0") || mCCADetailModelArrayList[position].cca_item_description
                    .equals("")
            ) {
                holder.descriptionTxt.visibility = View.GONE
            } else {
                holder.descriptionTxt.visibility = View.VISIBLE
                holder.descriptionTxt.text =
                    "Description : " + mCCADetailModelArrayList[position].cca_item_description
            }
        }
        else {
            holder.descriptionTxt.visibility = View.GONE
        }





        holder.textViewCCADay.setText(mCCADetailModelArrayList[position].day)
        if (mCCADetailModelArrayList[position].choice1 == null) {
            holder.linearChoice1.visibility = View.GONE
            holder.textViewCCAChoice1.text = "Choice 1 : Nil"
        } else {
            holder.linearChoice1.visibility = View.VISIBLE
            holder.textViewCCAChoice1.text =
                "Choice  : " + mCCADetailModelArrayList[position].choice1
            if (mCCADetailModelArrayList[position].cca_item_start_time != null && mCCADetailModelArrayList[position].cca_item_end_time != null) {
                holder.textViewCCAaDateItemChoice1.visibility = View.VISIBLE
                holder.textViewCCAaDateItemChoice1.text = "(" + convertTimeToAMPM(
                    mCCADetailModelArrayList[position].cca_item_start_time
                ) + " - " + convertTimeToAMPM(
                    mCCADetailModelArrayList[position].cca_item_end_time
                ) + ")"
            } else if (mCCADetailModelArrayList[position].cca_item_start_time != null) {
                holder.textViewCCAaDateItemChoice1.visibility = View.VISIBLE
                holder.textViewCCAaDateItemChoice1.text = "(" + convertTimeToAMPM(
                    mCCADetailModelArrayList[position].cca_item_end_time
                ) + ")"
            } else if (mCCADetailModelArrayList[position].cca_item_end_time != null) {
                holder.textViewCCAaDateItemChoice1.visibility = View.VISIBLE
                holder.textViewCCAaDateItemChoice1.text = "(" + convertTimeToAMPM(
                    mCCADetailModelArrayList[position].cca_item_end_time
                ) + ")"
            } else {
                holder.textViewCCAaDateItemChoice1.visibility = View.GONE
            }
        }

    }

    override fun getItemCount(): Int {
        return mCCADetailModelArrayList.size
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