package com.nas.alreem.activity.survey.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.activity.survey.model.SurveyListDataModel
import com.nas.alreem.fragment.about_us.adapter.AboutUsAdapter
import com.nas.alreem.fragment.about_us.model.AboutUsDataModel

class SurveyListAdapter (private var surveyArrayList: ArrayList<SurveyListDataModel>, var context: Context) :
    RecyclerView.Adapter<SurveyListAdapter.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var listTxtTitle: TextView = view.findViewById(R.id.listTxtTitle)
    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_survey_list, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.listTxtTitle.text = surveyArrayList[position].survey_name


    }
    override fun getItemCount(): Int {

        return surveyArrayList.size

    }
}