package com.nas.alreem.fragment.time_table.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.fragment.time_table.model.apimodel.TimeTableApiListModel

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
internal class TimeTableSingleWeekSelectionAdapter (private var mContetx:Context, private var calendarArrayList: List<TimeTableApiListModel>) :
    RecyclerView.Adapter<TimeTableSingleWeekSelectionAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var llread: RelativeLayout = view.findViewById(R.id.llread)
        var llreadbreak: RelativeLayout = view.findViewById(R.id.llreadbreak)
        var starLinear: LinearLayout = view.findViewById(R.id.starLinear)
        var relSub: LinearLayout = view.findViewById(R.id.relSub)
        var card_view: CardView = view.findViewById(R.id.card_view)
        var breakTxt: TextView = view.findViewById(R.id.breakTxt)
        var subjectTxt: TextView = view.findViewById(R.id.subjectTxt)
        var tutorNameTxt: TextView = view.findViewById(R.id.tutorNameTxt)
        var subjectNameTxt: TextView = view.findViewById(R.id.subjectNameTxt)
        var timeAPTxt: TextView = view.findViewById(R.id.timeAPTxt)
        var timeTxt: TextView = view.findViewById(R.id.timeTxt)
        var breakTxtt: TextView = view.findViewById(R.id.breakTxtt)

    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_single_week_selection, parent, false)
        return MyViewHolder(itemView)
    }
    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val summary = calendarArrayList[position]

        if(calendarArrayList[position].is_break.equals(1))
        {
            holder.breakTxtt.visibility = View.VISIBLE
            holder.tutorNameTxt.visibility = View.GONE
            holder.subjectTxt.visibility = View.GONE
            holder.subjectNameTxt.visibility = View.GONE
            holder.timeTxt.setTextColor(mContetx.getResources().getColor(R.color.white))
            holder.relSub.setBackgroundColor(mContetx.getResources().getColor(R.color.present))
            val time = calendarArrayList[position].starttime


            if(calendarArrayList[position].sortname.equals("Break"))
            {
                holder.breakTxtt.text = calendarArrayList[position].sortname
            }
            else
            {
                holder.breakTxtt.text = calendarArrayList[position].sortname
            }

            try {
                val sdf = SimpleDateFormat("HH:mm")
                val dateObj: Date = sdf.parse(time)
                holder.timeTxt.text = SimpleDateFormat("hh:mm a").format(dateObj)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

        }
        else {
            holder.llread.visibility = View.VISIBLE
            holder.timeAPTxt.visibility = View.GONE
            holder.llreadbreak.visibility = View.GONE
            holder.starLinear.visibility = View.GONE
            holder.tutorNameTxt.text = calendarArrayList[position].staff
            holder.subjectTxt.text = calendarArrayList[position].sortname
            holder.subjectNameTxt.text = calendarArrayList[position].subject_name

            val time = calendarArrayList[position].starttime

            try {
                val sdf = SimpleDateFormat("HH:mm")
                val dateObj: Date = sdf.parse(time)
                holder.timeTxt.text = SimpleDateFormat("hh:mm a").format(dateObj)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
        }


    }
    override fun getItemCount(): Int {

        return calendarArrayList.size

    }
}