package com.nas.alreem.activity.staff_directory.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.activity.parent_meetings.adapter.ParentsEveningRoomListAdapter
import com.nas.alreem.activity.parent_meetings.model.PtaTimeSlotList
import com.nas.alreem.activity.staff_directory.model.StaffDeptListModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Date

internal class StaffCategoryAdapter (var context: Context, var staff_cat_list: ArrayList<StaffDeptListModel>) :
    RecyclerView.Adapter<StaffCategoryAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var department: TextView =view.findViewById(R.id.listTxtTitle)

    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_staff_category, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Log.e("ad",staff_cat_list.size.toString())

       holder.department.text=staff_cat_list[position].department_name

    }
    override fun getItemCount(): Int {

        return staff_cat_list.size

    }
}