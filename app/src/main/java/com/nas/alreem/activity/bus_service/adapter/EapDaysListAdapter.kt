package com.nas.alreem.activity.bus_service.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.activity.bus_service.model.StateVO
import com.nas.alreem.constants.PreferenceManager
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date

class EapDaysListAdapter(
    private val context: Context,
    private val listState: ArrayList<StateVO>,
    private val selecteapdays: TextView
) : RecyclerView.Adapter<EapDaysListAdapter.MyViewHolder>() {

    private var isFromView = false

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mTextView: TextView = view.findViewById(R.id.text)
        val mCheckBox: CheckBox = view.findViewById(R.id.checkbox)
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.spinner_checkbox_dropdown, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val fromDate = listState[position].title
        val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        val outputFormat: DateFormat = SimpleDateFormat("dd MMM yyyy")
        val inputDateStr = fromDate
        val date: Date = inputFormat.parse(inputDateStr)
        val outputDateStr: String = outputFormat.format(date)
        holder.mTextView.text = "$outputDateStr (${listState[position].dates})"

        // To check whether checked event fires from getView() or user input
        isFromView = true
        holder.mCheckBox.isChecked = listState[position].isSelected
        isFromView = false

        holder.mCheckBox.tag = position
        holder.mCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            val getPosition = buttonView.tag as Int
            if (!isFromView) {
                listState[getPosition].isSelected = isChecked
                Log.e("isselected", listState[getPosition].isSelected.toString())

                // Update the TextView with the cleaned string based on listState
                updateSelectedDays()
                PreferenceManager.seteapselecteddates(getSelectedDates(), context)
                Log.e("getdays", PreferenceManager.geteapselecteddates(context).toString())
            }
        }
    }

    override fun getItemCount(): Int {
        Log.e("size", listState.size.toString())
        return listState.size
    }

    private fun updateSelectedDays() {
        val selectedDays = listState
            .filter { it.isSelected }
            .joinToString(", ") {
                val fromDate = it.title
                val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
                val outputFormat: DateFormat = SimpleDateFormat("dd MMM yyyy")
                val inputDateStr = fromDate
                val date: Date = inputFormat.parse(inputDateStr)
                val outputDateStr: String = outputFormat.format(date)
                "$outputDateStr (${it.dates})"
            }

        if (selectedDays.equals(""))
        {
            selecteapdays.text = "Please Select"
            Log.e("selectedDays", selectedDays)
        }
        else{
            selecteapdays.text = selectedDays
            Log.e("selectedDays", selectedDays)
        }

    }

    private fun getSelectedDates(): ArrayList<String> {
        return listState
            .filter { it.isSelected }
            .map { it.title!! }
            .toCollection(ArrayList())
    }
}
