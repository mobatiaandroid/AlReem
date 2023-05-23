package com.nas.alreem.activity.primary.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.activity.primary.model.ComingUpDataModell
import com.nas.alreem.fragment.primary.model.PrimaryFileModel

class ComingUpAdapter (private var comingUpArrayList: ArrayList<ComingUpDataModell>, var context: Context) :
    RecyclerView.Adapter<ComingUpAdapter.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var listTxtTitle: TextView = view.findViewById(R.id.listTxtTitle)
    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_primary_data, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.listTxtTitle.text = comingUpArrayList[position].title


    }
    override fun getItemCount(): Int {

        return comingUpArrayList.size

    }
}