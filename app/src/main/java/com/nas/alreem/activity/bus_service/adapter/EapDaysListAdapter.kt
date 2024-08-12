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
import com.nas.alreem.activity.bus_service.model.StateVj
import com.nas.alreem.constants.PreferenceManager
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody


class EapDaysListAdapter(
    var context: Context,
    private val listState: ArrayList<StateVO>,
    private val days: ArrayList<StateVj>,
    var selecteapdays: TextView
) :
    RecyclerView.Adapter<EapDaysListAdapter.MyViewHolder>() {
    private var isFromView = false
    private val new: ArrayList<String> = ArrayList()
    private val new1: ArrayList<String> = ArrayList()

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
       // var listTxtTitle: TextView = view.findViewById(R.id.daySpinnerList)
         var mTextView: TextView=view. findViewById(R.id.text)
         var mCheckBox: CheckBox=view. findViewById(R.id.checkbox)
    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.spinner_checkbox_dropdown, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.mTextView.text = listState[position].title +"("+days[position].title+")"

        // To check whether checked event fires from getView() or user input
        isFromView = true
        holder.mCheckBox.isChecked = listState[position].isSelected
        isFromView = false

       // holder.mCheckBox.visibility = if (position == 0) View.INVISIBLE else View.VISIBLE
        holder.mCheckBox.tag = position
        holder.mCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            val getPosition = buttonView.tag as Int
            if (!isFromView) {
                listState[getPosition].isSelected = isChecked
                Log.e("isselected", listState[getPosition].isSelected.toString())
            }

            if (isChecked) {
                val selectedText = holder.mTextView.text
                // Handle the selected TextView
                for (i in listState.indices)
                {
                    listState[position].title
                   // Log.e("title", listState[position].title!!)
                }
              //  println("Selected TextView Text: $selectedText")


                new.add( listState[position].title!!)
                new1.add(listState[position].title!!+"("+days[position].title+")")
                val cleanedString = new1.toString().replace("[", "").replace("]", "")
                selecteapdays.text= cleanedString
                Log.e("array", new1.toString())
                PreferenceManager.seteapselecteddates(new,context)

            }
        }
    }
    override fun getItemCount(): Int {
Log.e("size", listState.size.toString())
        return listState.size

    }
}