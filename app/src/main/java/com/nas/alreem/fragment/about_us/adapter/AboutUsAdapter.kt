package com.nas.alreem.fragment.about_us.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.fragment.about_us.model.AboutUsDataModel
import com.nas.alreem.fragment.primary.adapter.PrimaryAdapter
import com.nas.alreem.fragment.primary.model.PrimaryDataModel

class AboutUsAdapter (private var aboutUsArrayList: ArrayList<AboutUsDataModel>, var context: Context) :
    RecyclerView.Adapter<AboutUsAdapter.MyViewHolder>() {
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

        holder.listTxtTitle.text = aboutUsArrayList[position].name


    }
    override fun getItemCount(): Int {

        return aboutUsArrayList.size

    }
}