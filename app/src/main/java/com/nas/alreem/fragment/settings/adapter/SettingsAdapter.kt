package com.nas.alreem.fragment.settings.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.constants.PreferenceManager

class SettingsAdapter(private var settingsArrayList: ArrayList<String>, var context: Context) :
    RecyclerView.Adapter<SettingsAdapter.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var listTxtTitle: TextView = view.findViewById(R.id.listTxtTitle)
        var txtUser: TextView = view.findViewById(R.id.txtUser)
    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_settings, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {



            if (!PreferenceManager.getaccesstoken(context).equals(""))
            {

                if (position==7)
                {

                    holder.listTxtTitle.setText(settingsArrayList.get(position).toString())
                    holder.txtUser.visibility=View.VISIBLE
                    holder.txtUser.text="( "+PreferenceManager.getEmailId(context)+" )"
                }
                else{
                    holder.listTxtTitle.text = settingsArrayList.get(position).toString()
                    holder.txtUser.visibility=View.GONE
                }
            }
        else{
               if (position==4)
               {
                   holder.listTxtTitle.text = settingsArrayList.get(position).toString()
                   holder.txtUser.visibility=View.VISIBLE
                   holder.txtUser.text="( Guest )"
               }
                else
                {
                   holder.listTxtTitle.text = settingsArrayList.get(position).toString()
                    holder.txtUser.visibility=View.GONE
                }
            }

    }
    override fun getItemCount(): Int {

        return settingsArrayList.size

    }
}