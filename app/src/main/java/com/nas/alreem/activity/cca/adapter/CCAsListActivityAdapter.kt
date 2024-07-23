package com.nas.alreem.activity.cca.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.activity.cca.model.CCAModel
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class CCAsListActivityAdapter(var mContext: Context, mCCAmodelArrayList: ArrayList<CCAModel>) :
    RecyclerView.Adapter<CCAsListActivityAdapter.MyViewHolder>() {
    var mCCAmodelArrayList: ArrayList<CCAModel>

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var listTxtView: TextView
        var listTxtViewDate: TextView
        var status: TextView
        var statusImageView: ImageView
        var statusLayout: RelativeLayout

        init {
            listTxtView = view.findViewById(R.id.textViewCCAaItem)
            listTxtViewDate = view.findViewById(R.id.textViewCCAaDateItem)
            statusImageView = view.findViewById(R.id.statusImageView)
            statusLayout = view.findViewById(R.id.statusLayout)
            status = view.findViewById(R.id.status)
        }
    }

    init {
        this.mCCAmodelArrayList = mCCAmodelArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_cca_first_list, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.listTxtView.setText(mCCAmodelArrayList[position].title)
        var fromDate: String
        var toDate: String
        holder.listTxtViewDate.text = dateParsingTodd_MMM_yyyy(
            mCCAmodelArrayList[position].from_date
        ) + " to " + dateParsingTodd_MMM_yyyy(
            mCCAmodelArrayList[position].to_date
        )

        if (mCCAmodelArrayList[position].isAttendee.equals("0")) {
            if (mCCAmodelArrayList[position].isSubmissionDateOver.equals("1")) {
                //closed
                holder.statusImageView.setImageResource(R.drawable.closed)
            } else {
                holder.statusImageView.setImageResource(R.drawable.edit) //edit
            }
        } else if (mCCAmodelArrayList[position].isAttendee.equals("1")) {
            //approved
            holder.statusImageView.setImageResource(R.drawable.approve_new)
        } else if (mCCAmodelArrayList[position].isAttendee.equals("2")) {
            //pending
            holder.statusImageView.setImageResource(R.drawable.approve_new)
        }
        if (mCCAmodelArrayList.get(position).status.equals("0")) {
            holder.statusLayout.setVisibility(View.VISIBLE);
            holder.status.setBackgroundResource(R.drawable.rectangle_red);
            holder.status.setText("New");
        } else if (mCCAmodelArrayList.get(position).status.equals("1") ||
            mCCAmodelArrayList.get(position).status.equals("")) {
            holder.statusLayout.setVisibility(View.INVISIBLE);

        } else if (mCCAmodelArrayList.get(position).status.equals("2")) {
            holder.statusLayout.setVisibility(View.VISIBLE);
            holder.status.setBackgroundResource(R.drawable.rectangle_orange);
            holder.status.setText("Updated");
        }
    }

    override fun getItemCount(): Int {
        return mCCAmodelArrayList.size
    }

    companion object {
        fun dateParsingTodd_MMM_yyyy(date: String?): String {
            var strCurrentDate = ""
            var format = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            var newDate: Date? = null
            try {
                newDate = format.parse(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            format = SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH)
            strCurrentDate = format.format(newDate)
            return strCurrentDate
        }
    }
}