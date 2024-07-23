package com.nas.alreem.activity.early_years.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.activity.communication.commingup.model.ComingUpResponseModel
import com.nas.alreem.activity.primary.model.ComingUpDataModell
import com.nas.alreem.constants.ConstantFunctions


class ComingUpAdapterCommon(
    private var comingUpArrayList: ArrayList<ComingUpDataModell>,
    var context: Context
) :
    RecyclerView.Adapter<ComingUpAdapterCommon.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var listTxtTitle: TextView = view.findViewById(R.id.listTxtTitle)
        var listTxtDate: TextView = view.findViewById(R.id.listTxtDate)

        var statusLayout: RelativeLayout =view.findViewById(R.id.statusLayout)
        var status: TextView = view.findViewById(R.id.status)
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_common_list, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.listTxtTitle.text = comingUpArrayList[position].title
        holder.listTxtDate.text = ConstantFunctions().dateParsingTodd_MMM_yyyy(comingUpArrayList[position].date)
        /*if (comingUpArrayList.get(position).read_unread_status.equals("0")) {
            holder.statusLayout.setVisibility(View.VISIBLE)
            holder.status.setBackgroundResource(R.drawable.rectangle_red)
            holder.status.setText("New")
        } else if (comingUpArrayList.get(position).read_unread_status.equals("1") || comingUpArrayList.get(
                position
            ).read_unread_status.equals("")
        ) {
            holder.status.setVisibility(View.INVISIBLE)
        } else if (comingUpArrayList.get(position).read_unread_status.equals("2")) {
            holder.statusLayout.setVisibility(View.VISIBLE)
            holder.status.setBackgroundResource(R.drawable.rectangle_orange)
            holder.status.setText("Updated")
        }*/

    }
    override fun getItemCount(): Int {

        return comingUpArrayList.size

    }
}