package com.nas.alreem.fragment.intention.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.fragment.intention.model.IntentionListAPIResponseModel


class IntentionAdapter(
    private var primaryArrayList: ArrayList<IntentionListAPIResponseModel.Intention>,
    var context: Context
) :
    RecyclerView.Adapter<IntentionAdapter.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var listTxtTitle: TextView = view.findViewById(R.id.listTxtTitle)
        var arrowImg: ImageView = view.findViewById(R.id.arrowImg)
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_primary_list, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val list = primaryArrayList[position]
            holder.listTxtTitle.text = list.title
        if (primaryArrayList[position].status.equals("")) {

            holder.arrowImg.setImageResource(R.drawable.arrow)
        }
         else {
                holder.arrowImg.setImageResource(R.drawable.approve_new)
            }




    }
    override fun getItemCount(): Int {
        return primaryArrayList.size

    }
}