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
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class CCAfinalReviewAdapter(
    var mContext: Context,
    mCCADetailModelArrayList: ArrayList<CCADetailModel>
) :
    RecyclerView.Adapter<CCAfinalReviewAdapter.MyViewHolder>() {
    var mCCADetailModelArrayList: ArrayList<CCADetailModel>

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var textViewCCAaDateItemChoice1: TextView
        var textViewCCAaDateItemChoice2: TextView
        var textViewCCADay: TextView
        var textViewCCAChoice1: TextView
        var textViewCCAChoice2: TextView
        var locationTxt: TextView
        var location2Txt: TextView
        var description2Txt: TextView
        var descriptionTxt: TextView
        var linearChoice1: LinearLayout
        var linearChoice2: LinearLayout

        init {
            textViewCCAaDateItemChoice1 =
                view.findViewById<View>(R.id.textViewCCAaDateItemChoice1) as TextView
            textViewCCAaDateItemChoice2 =
                view.findViewById<View>(R.id.textViewCCAaDateItemChoice2) as TextView
            textViewCCADay = view.findViewById<View>(R.id.textViewCCADay) as TextView
            textViewCCAChoice1 = view.findViewById<View>(R.id.textViewCCAChoice1) as TextView
            textViewCCAChoice2 = view.findViewById<View>(R.id.textViewCCAChoice2) as TextView
            locationTxt = view.findViewById<View>(R.id.locationTxt) as TextView
            location2Txt = view.findViewById<View>(R.id.location2Txt) as TextView
            description2Txt = view.findViewById<View>(R.id.description2Txt) as TextView
            descriptionTxt = view.findViewById<View>(R.id.descriptionTxt) as TextView
            linearChoice1 = view.findViewById<View>(R.id.linearChoice1) as LinearLayout
            linearChoice2 = view.findViewById<View>(R.id.linearChoice2) as LinearLayout
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
            .inflate(R.layout.adapter_cca_final_review, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Log.e("Location", mCCADetailModelArrayList[position].location.toString())
        Log.e("Location2", mCCADetailModelArrayList[position].location2.toString())
        Log.e("item", mCCADetailModelArrayList[position].choice1.toString())
        Log.e("item2", mCCADetailModelArrayList[position].choice2.toString())
        Log.e("desc", mCCADetailModelArrayList[position].description.toString())
        Log.e("desc", mCCADetailModelArrayList[position].description2.toString())
        if (mCCADetailModelArrayList[position].location
                .equals("0") || mCCADetailModelArrayList[position].location
                .equals("")
        ) {
            holder.locationTxt.visibility = View.GONE
        } else {
            holder.locationTxt.visibility = View.VISIBLE
            holder.locationTxt.text =
                "Location       : " + mCCADetailModelArrayList[position].location
        }
        if (mCCADetailModelArrayList[position].location2
                .equals("0") || mCCADetailModelArrayList[position].location2
                .equals("")
        ) {
            holder.location2Txt.visibility = View.GONE
        } else {
            holder.location2Txt.visibility = View.VISIBLE
            holder.location2Txt.text =
                "Location       : " + mCCADetailModelArrayList[position].location2
        }
        if (mCCADetailModelArrayList[position].description
                .equals("0") || mCCADetailModelArrayList[position].description
                .equals("")
        ) {
            holder.descriptionTxt.visibility = View.GONE
        } else {
            holder.descriptionTxt.visibility = View.VISIBLE
            holder.descriptionTxt.text =
                "Description : " + mCCADetailModelArrayList[position].description
        }
        if (mCCADetailModelArrayList[position].description2
                .equals("0") || mCCADetailModelArrayList[position].description2
                .equals("")
        ) {
            holder.description2Txt.visibility = View.GONE
        } else {
            holder.description2Txt.visibility = View.VISIBLE
            holder.description2Txt.text =
                "Description : " + mCCADetailModelArrayList[position].description2
        }
        holder.textViewCCADay.setText(mCCADetailModelArrayList[position].day)
        if (mCCADetailModelArrayList[position].choice1 == null) {
            holder.linearChoice1.visibility = View.GONE
            holder.textViewCCAChoice1.text = "Choice 1 : Nil"
        } else {
            holder.linearChoice1.visibility = View.VISIBLE
            holder.textViewCCAChoice1.text =
                "Choice 1 : " + mCCADetailModelArrayList[position].choice1
            if (mCCADetailModelArrayList[position].cca_item_start_timechoice1 != null && mCCADetailModelArrayList[position].cca_item_end_timechoice1 != null) {
                holder.textViewCCAaDateItemChoice1.visibility = View.VISIBLE
                holder.textViewCCAaDateItemChoice1.text = "(" + convertTimeToAMPM(
                    mCCADetailModelArrayList[position].cca_item_start_timechoice1
                ) + " - " + convertTimeToAMPM(
                    mCCADetailModelArrayList[position].cca_item_end_timechoice1
                ) + ")"
            } else if (mCCADetailModelArrayList[position].cca_item_start_timechoice1 != null) {
                holder.textViewCCAaDateItemChoice1.visibility = View.VISIBLE
                holder.textViewCCAaDateItemChoice1.text = "(" + convertTimeToAMPM(
                    mCCADetailModelArrayList[position].cca_item_end_timechoice1
                ) + ")"
            } else if (mCCADetailModelArrayList[position].cca_item_end_timechoice1 != null) {
                holder.textViewCCAaDateItemChoice1.visibility = View.VISIBLE
                holder.textViewCCAaDateItemChoice1.text = "(" + convertTimeToAMPM(
                    mCCADetailModelArrayList[position].cca_item_end_timechoice1
                ) + ")"
            } else {
                holder.textViewCCAaDateItemChoice1.visibility = View.GONE
            }
        }
        if (mCCADetailModelArrayList[position].choice2 == null) {
            holder.linearChoice2.visibility = View.GONE
            holder.textViewCCAChoice2.text = "Choice 2 : Nil"
        } else {
            holder.linearChoice2.visibility = View.VISIBLE
            holder.textViewCCAChoice2.text =
                "Choice 2 : " + mCCADetailModelArrayList[position].choice2
            if (mCCADetailModelArrayList[position].cca_item_start_timechoice2 != null && mCCADetailModelArrayList[position].cca_item_end_timechoice2 != null) {
                holder.textViewCCAaDateItemChoice2.visibility = View.VISIBLE
                holder.textViewCCAaDateItemChoice2.text = "(" + convertTimeToAMPM(
                    mCCADetailModelArrayList[position].cca_item_start_timechoice2
                ) + " - " + convertTimeToAMPM(
                    mCCADetailModelArrayList[position].cca_item_end_timechoice2
                ) + ")"
            } else if (mCCADetailModelArrayList[position].cca_item_start_timechoice2 != null) {
                holder.textViewCCAaDateItemChoice2.visibility = View.VISIBLE
                holder.textViewCCAaDateItemChoice2.text = "(" + convertTimeToAMPM(
                    mCCADetailModelArrayList[position].cca_item_start_timechoice2
                ) + ")"
            } else if (mCCADetailModelArrayList[position].cca_item_end_timechoice2 != null) {
                holder.textViewCCAaDateItemChoice2.visibility = View.VISIBLE
                holder.textViewCCAaDateItemChoice2.text = "(" + convertTimeToAMPM(
                    mCCADetailModelArrayList[position].cca_item_end_timechoice2
                ) + ")"
            } else {
                holder.textViewCCAaDateItemChoice2.visibility = View.GONE
            }
        }
    }

    override fun getItemCount(): Int {
        Log.e("size", mCCADetailModelArrayList.size.toString())
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