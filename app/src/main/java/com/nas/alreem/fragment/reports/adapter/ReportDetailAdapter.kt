package com.nas.alreem.fragment.reports.adapter

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.constants.PDFViewerActivity
import com.nas.alreem.fragment.reports.model.ReportListDetailModel

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ReportDetailAdapter(private var mContext:Context, private var repoetDetailArray: ArrayList<ReportListDetailModel>) :
    RecyclerView.Adapter<ReportDetailAdapter.MyViewHolder>() {
    lateinit var clickedurl:String

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var report_cycle: TextView = view.findViewById(R.id.termname)
        var relativeclick: LinearLayout = view.findViewById(R.id.clickLinear)

    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_report_cycle, parent, false)
        return MyViewHolder(itemView)
    }


    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val summary = repoetDetailArray[position]
        holder.report_cycle.text = repoetDetailArray[position].report_cycle
        holder.relativeclick.setOnClickListener {

            clickedurl = repoetDetailArray[position].file


          // mContext.startActivity(Intent(mContext, WebviewLoad::class.java).putExtra("Url",repoetDetailArray[position].file))
            mContext.startActivity(Intent(mContext, PDFViewerActivity::class.java).
            putExtra("Url",repoetDetailArray[position].file).
            putExtra("title",repoetDetailArray[position].report_cycle))

        }
    }
    override fun getItemCount(): Int {

        return repoetDetailArray.size

    }
}