package com.nas.alreem.activity.primary.adapter

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

class ComingUpAdapter(
    private var comingUpArrayList: ArrayList<ComingUpResponseModel.ComingUpItem>,
    var context: Context
) :
    RecyclerView.Adapter<ComingUpAdapter.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var listTxtTitle: TextView = view.findViewById(R.id.listTxtTitle)
         var statusLayout: RelativeLayout =view.findViewById(R.id.statusLayout)
        var status: TextView = view.findViewById(R.id.status)
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_primary_data, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.listTxtTitle.text = comingUpArrayList[position].title
        if (comingUpArrayList.get(position).status.equals("0")) {
            holder.statusLayout.setVisibility(View.VISIBLE)
            holder.status.setBackgroundResource(R.drawable.rectangle_red)
            holder.status.setText("New")
        } else if (comingUpArrayList.get(position).status.equals("1") || comingUpArrayList.get(
                position
            ).status.equals("")
        ) {
            holder.status.setVisibility(View.INVISIBLE)
        } else if (comingUpArrayList.get(position).status.equals("2")) {
            holder.statusLayout.setVisibility(View.VISIBLE)
            holder.status.setBackgroundResource(R.drawable.rectangle_orange)
            holder.status.setText("Updated")
        }

    }
    override fun getItemCount(): Int {

        return comingUpArrayList.size

    }
}