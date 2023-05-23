package com.nas.alreem.fragment.primary.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.fragment.notifications.adapter.NotificationListAdapter
import com.nas.alreem.fragment.notifications.model.NotificationModel
import com.nas.alreem.fragment.primary.model.PrimaryDataModel

class PrimaryAdapter (private var primaryArrayList: ArrayList<PrimaryDataModel>,var context:Context) :
    RecyclerView.Adapter<PrimaryAdapter.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var listTxtTitle: TextView = view.findViewById(R.id.listTxtTitle)
    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_primary_list, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        if (position==0)
        {
            holder.listTxtTitle.text = context.resources.getString(R.string.comming_up)
        }
        else
        {
            holder.listTxtTitle.text = primaryArrayList[position-1].name
        }


    }
    override fun getItemCount(): Int {

        return primaryArrayList.size+1

    }
}