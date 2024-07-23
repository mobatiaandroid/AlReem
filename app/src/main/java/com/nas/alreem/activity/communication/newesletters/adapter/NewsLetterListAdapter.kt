package com.nas.alreem.activity.communication.newesletters.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.fragment.communication.adapter.CommunicationAdapter
import com.nas.alreem.fragment.communication.model.CommunicationDataModel

class NewsLetterListAdapter (private var communicationList: ArrayList<CommunicationDataModel>, var context: Context) :
    RecyclerView.Adapter<NewsLetterListAdapter.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var listTxtTitle: TextView = view.findViewById(R.id.listTxtTitle)
    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_news_letter, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.listTxtTitle.text = communicationList[position].submenu

    }
    override fun getItemCount(): Int {

        return communicationList.size

    }
}