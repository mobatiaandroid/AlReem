package com.nas.alreem.activity.bus_service.requestservice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.activity.bus_service.requestservice.model.RequestServiceArrayModel
import com.nas.alreem.fragment.bus_service.adapter.RequestBusserviceRecyclerAdapter
import com.nas.alreem.fragment.bus_service.model.BusServiceDetail
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date

class RequestServiceListAdapter (private var studentInfoList: List<RequestServiceArrayModel>) :
    RecyclerView.Adapter<RequestServiceListAdapter.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var listDate: TextView = view.findViewById(R.id.listDate)
        var listStatus: TextView =view.findViewById(R.id.listStatus)
    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_absence_leave_recycelr, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val list = studentInfoList[position]

        val fromDate=list.requested_date
        val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        val outputFormat: DateFormat = SimpleDateFormat("dd MMM yyyy")
        val inputDateStr = fromDate
        val date: Date = inputFormat.parse(inputDateStr)
        val outputDateStr: String = outputFormat.format(date)
        holder.listDate.text = outputDateStr

        var status=list.status
        if (status==1){
            holder.listStatus.visibility = View.VISIBLE
            holder.listStatus.text = "PENDING"
            //label pending
        }
        else if (status==2){
            holder.listStatus.visibility = View.VISIBLE
            holder.listStatus.text = "APPROVED"
            //label approved
        }else if (status==3){
            holder.listStatus.visibility = View.VISIBLE
            holder.listStatus.text = "REJECTED"
            //label approved
        }else if (status==4){
            holder.listStatus.visibility = View.VISIBLE
            holder.listStatus.text = "PAYMENT DONE"
            //label approved
        }
        else{
            holder.listStatus.visibility = View.VISIBLE
            holder.listStatus.text = "Payment Pending"
            //label rejected
        }


    }
    override fun getItemCount(): Int {
        return studentInfoList.size
    }
}