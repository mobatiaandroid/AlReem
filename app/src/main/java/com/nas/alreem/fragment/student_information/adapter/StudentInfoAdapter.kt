package com.nas.alreem.fragment.student_information.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView

import com.nas.alreem.R
import com.nas.alreem.constants.PreferenceManager
import com.nas.alreem.fragment.student_information.model.StudentInfoDetail

internal class StudentInfoAdapter(
    private var studentInfoList: List<StudentInfoDetail>,
    var mContext: Context
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
        if (movie.value.contains(".com"))
        {
            PreferenceManager.setEmail(mContext,movie.value)
        }

    }
    override fun getItemCount(): Int {
        return studentInfoList.size
    }
}