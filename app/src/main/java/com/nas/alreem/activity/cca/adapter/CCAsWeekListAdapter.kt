package com.nas.alreem.activity.cca.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.activity.cca.model.WeekListModel
import com.nas.alreem.appcontroller.AppController

class CCAsWeekListAdapter : RecyclerView.Adapter<CCAsWeekListAdapter.MyViewHolder> {
    var mCCAmodelArrayList: ArrayList<String>? = null
    var mWeekListModelArrayList: ArrayList<WeekListModel>
    var mContext: Context
    var weekPosition = 0
    var msgRelative: RelativeLayout? = null

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var listTxtView: TextView
        var selectionCompletedView: View
        var weekSelectedImageView: ImageView
        var linearBg: LinearLayout
        var linearChoice: LinearLayout

        init {
            listTxtView = view.findViewById<View>(R.id.textViewCCAaItem) as TextView
            selectionCompletedView = view.findViewById(R.id.selectionCompletedView) as View
            weekSelectedImageView = view.findViewById<View>(R.id.weekSelectedImageView) as ImageView
            linearBg = view.findViewById<View>(R.id.linearBg) as LinearLayout
            linearChoice = view.findViewById<View>(R.id.linearChoice) as LinearLayout
        }
    }

    constructor(mContext: Context, mCCAmodelArrayList: ArrayList<WeekListModel>) {
        this.mContext = mContext
        mWeekListModelArrayList = mCCAmodelArrayList
    }

    constructor(
        mContext: Context,
        mCCAmodelArrayList: ArrayList<WeekListModel>,
        mWeekPosition: Int,
        msgRelative: RelativeLayout?
    ) {
        this.mContext = mContext
        mWeekListModelArrayList = mCCAmodelArrayList
        weekPosition = mWeekPosition
        this.msgRelative = msgRelative
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_weeklist_cca, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.listTxtView.setText(mWeekListModelArrayList[position].weekDayMMM)
        if (AppController.weekList.get(position).choiceStatus
                .equals("0")
        ) {
            holder.selectionCompletedView.setBackgroundResource(R.drawable.curve_filled_cca_pending)
            holder.linearBg.setBackgroundResource(R.color.white)
            holder.linearChoice.setBackgroundResource(R.color.white)
        } else if (AppController.weekList.get(position).choiceStatus
                .equals("2")
        ) {
            holder.selectionCompletedView.setBackgroundResource(R.drawable.curve_filled_cca_not_available)
            holder.linearBg.setBackgroundResource(R.color.light_grey)
            holder.linearChoice.setBackgroundResource(R.color.light_grey)
        } else {
            holder.selectionCompletedView.setBackgroundResource(R.drawable.curve_filled_cca_completed)
            holder.linearBg.setBackgroundResource(R.color.white)
            holder.linearChoice.setBackgroundResource(R.color.white)
        }
        if (weekPosition == position) {
            holder.weekSelectedImageView.visibility = View.VISIBLE
            if (AppController.weekList.get(position).choiceStatus
                    .equals("0")
            ) {
                msgRelative!!.visibility = View.VISIBLE
            } else if (AppController.weekList.get(position).choiceStatus
                    .equals("2")
            ) {
                msgRelative!!.visibility = View.GONE
            } else {
                msgRelative!!.visibility = View.GONE
            }
        } else {
            holder.weekSelectedImageView.visibility = View.INVISIBLE
        }
    }

    override fun getItemCount(): Int {
        return mWeekListModelArrayList.size
    }
}