package com.nas.alreem.activity.bus_service.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.activity.bus_service.model.SummeryBusResponseArray


class BusserviceSummeryAdapter(private var communicationList: ArrayList<SummeryBusResponseArray>, var context: Context) :
    RecyclerView.Adapter<BusserviceSummeryAdapter.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var listTxtTitle: TextView = view.findViewById(R.id.listTxtTitle)
        var statusof: TextView = view.findViewById(R.id.status)

    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_primary_list, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.listTxtTitle.text = communicationList[position].title
        if (communicationList[position].status.equals("0"))
        {
            holder.statusof.text ="PENDING"
           // holder.statusof.setTextColor(R.color.white)
            holder.statusof.setTextColor(context.getResources().getColor(R.color.canteen_date_orange))

        }
        else if(communicationList[position].status.equals("1"))
        {
            holder.statusof.text ="PAY"
            holder.statusof.setTextColor(context.getResources().getColor(R.color.split_bg))

           // holder.statusof.setTextColor(R.color.split_bg)

        }
        else if(communicationList[position].status.equals("2"))
        {
            holder.statusof.text ="REJECTED"
            holder.statusof.setTextColor(context.getResources().getColor(R.color.red))


        }
        else if(communicationList[position].status.equals("3"))
        {
            holder.statusof.text ="PAID"
            holder.statusof.setTextColor(context.getResources().getColor(R.color.green))

        }

    }
    override fun getItemCount(): Int {

        return communicationList.size

    }
}