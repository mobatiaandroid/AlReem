package com.nas.alreem.fragment.communication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.constants.PreferenceManager

class CommunicationAdapter (private var communicationList: ArrayList<String>, var context: Context) :
    RecyclerView.Adapter<CommunicationAdapter.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var listTxtTitle: TextView = view.findViewById(R.id.listTxtTitle)
        var badge : ImageView = view.findViewById(R.id.statusImg)
    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_primary_list, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.listTxtTitle.text = communicationList[position].toString()
        if (!PreferenceManager.getCommunicationWholeSchoolBadge(context)
                .equals("0") && !PreferenceManager.getCommunicationWholeSchoolEditedBadge(
                context
            ).equals("0")
        ) {
            if (position == 0) {
              holder. badge.setVisibility(
                    View.VISIBLE
                )
              holder. badge.setBackgroundResource(
                    R.drawable.shape_circle_red
                )
            } else {
              holder. badge.setVisibility(
                    View.INVISIBLE
                )
            }
        } else if (PreferenceManager.getCommunicationWholeSchoolBadge(context)
                .equals("0") && !PreferenceManager.getCommunicationWholeSchoolEditedBadge(
                context
            ).equals("0")
        ) {
            if (position == 0) {
              holder.badge.setVisibility(
                    View.VISIBLE
                )
               holder. badge.setBackgroundResource(
                    R.drawable.shape_circle_navy
                )
            } else {
              holder.badge.setVisibility(
                    View.INVISIBLE
                )
            }
        } else if (!PreferenceManager.getCommunicationWholeSchoolBadge(context)
                .equals("0") && PreferenceManager.getCommunicationWholeSchoolEditedBadge(
                context
            ).equals("0")
        ) {
            if (position == 0) {
             holder. badge.setVisibility(
                    View.VISIBLE
                )
              holder. badge.setBackgroundResource(
                    R.drawable.shape_circle_red
                )
            } else {
              holder.badge.setVisibility(
                    View.INVISIBLE
                )
            }
        } else if (PreferenceManager.getCommunicationWholeSchoolBadge(context)
                .equals("0") && PreferenceManager.getCommunicationWholeSchoolEditedBadge(
                context
            ).equals("0")
        ) {
          holder.badge.setVisibility(
                View.GONE
            )
        } else {
           holder.badge.setVisibility(
                View.GONE
            )
        }

    }
    override fun getItemCount(): Int {

        return communicationList.size

    }
}