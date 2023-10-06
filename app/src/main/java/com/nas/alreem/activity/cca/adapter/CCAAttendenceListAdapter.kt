package com.nas.alreem.activity.cca.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.activity.cca.model.CCAAttendanceModel
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class CCAAttendenceListAdapter(
    var mContext: Context,
    mSocialMediaModels: ArrayList<CCAAttendanceModel>
) :
    RecyclerView.Adapter<CCAAttendenceListAdapter.MyViewHolder>() {
    var mSocialMediaModels: ArrayList<CCAAttendanceModel>
    var mPosition = 0
    var mChoiceStatus = 0

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var ccaDate: TextView
        var ccaDateStatus: TextView

        init {
            ccaDate = view.findViewById<View>(R.id.ccaDate) as TextView
            ccaDateStatus = view.findViewById<View>(R.id.ccaDateStatus) as TextView
        }
    }

    init {
        this.mSocialMediaModels = mSocialMediaModels
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_cca_review_attendancelist, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.ccaDate.text = dateParsingTodd_MMM_yyyy(
            mSocialMediaModels[position].dateAttend
        )
        if (mSocialMediaModels[position].statusCCA.equals("u")) {
            holder.ccaDateStatus.text = "Upcoming"
            holder.ccaDateStatus.setTextColor(mContext.resources.getColor(R.color.rel_six))
        } else if (mSocialMediaModels[position].statusCCA.equals("p")) {
            holder.ccaDateStatus.text = "Present"
            holder.ccaDateStatus.setTextColor(mContext.resources.getColor(R.color.nas_green))
        } else if (mSocialMediaModels[position].statusCCA.equals("a")) {
            holder.ccaDateStatus.text = "Absent"
            holder.ccaDateStatus.setTextColor(mContext.resources.getColor(R.color.rel_nine))
        }
    }

    override fun getItemCount(): Int {
        return mSocialMediaModels.size
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