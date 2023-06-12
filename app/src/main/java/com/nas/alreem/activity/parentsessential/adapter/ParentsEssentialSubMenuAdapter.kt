package com.nas.alreem.activity.parentsessential.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.activity.primary.adapter.ComingUpAdapter
import com.nas.alreem.activity.primary.model.ComingUpDataModell
import com.nas.alreem.fragment.parents_essentials.model.ParentsEssentialSubMenuModel

class ParentsEssentialSubMenuAdapter(private var comingUpArrayList: ArrayList<ParentsEssentialSubMenuModel>, var context: Context) :
    RecyclerView.Adapter<ParentsEssentialSubMenuAdapter.MyViewHolder>() {
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

        holder.listTxtTitle.text = comingUpArrayList[position].submenu


    }
    override fun getItemCount(): Int {

        return comingUpArrayList.size

    }
}