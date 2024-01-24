package com.nas.alreem.fragment.student_information.adapter

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
import com.nas.alreem.constants.PreferenceManager
import com.nas.alreem.fragment.student_information.model.StudentInfoDetail

internal class StudentInfoAdapter(
    private var studentInfoList: List<StudentInfoDetail>,
    var mContext: Context,
   var email_icon: ImageView
) :
    RecyclerView.Adapter<StudentInfoAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var nameTxt: TextView = view.findViewById(R.id.nameTxt)
        var valueTxt: TextView = view.findViewById(R.id.valueTxt)
    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_student_info, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movie = studentInfoList[position]
        holder.nameTxt.text = movie.title
        holder.valueTxt.text = movie.value
        Log.e("value",movie.value)
        if(movie.value.equals(""))
        {
            holder.nameTxt.visibility=View.GONE
            holder.valueTxt.visibility=View.GONE
            email_icon.visibility=View.GONE
        }
        else{
            holder.nameTxt.visibility=View.VISIBLE
            holder.valueTxt.visibility=View.VISIBLE
            email_icon.visibility=View.VISIBLE
            if (movie.value.contains(".com"))
            {
                PreferenceManager.setEmail(mContext,movie.value)
            }
        }


    }
    override fun getItemCount(): Int {
        return studentInfoList.size
    }
}