package com.nas.alreem.activity.survey.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.activity.survey.model.SurveyDetailDataModel
import com.nas.alreem.activity.survey.model.SurveyListDataModel
import com.nas.alreem.activity.survey.model.SurveyOfferedAnswersModel
import kotlinx.android.synthetic.main.date_adapter.view.*

class SurveyChoiceAdapter (private var surveyChoiceArrayList: ArrayList<SurveyOfferedAnswersModel>, var context: Context, var answerType:Int) :
    RecyclerView.Adapter<SurveyChoiceAdapter.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var choiseRelative: RelativeLayout = view.findViewById(R.id.choiseRelative)
        var smileyRelative: RelativeLayout = view.findViewById(R.id.smileyRelative)
        var startRelative: RelativeLayout = view.findViewById(R.id.startRelative)
        var numberRelative: RelativeLayout = view.findViewById(R.id.numberRelative)

        var smileyImg:ImageView=view.findViewById(R.id.smileyImg)
        var smileyTxt:TextView=view.findViewById(R.id.smileyTxt)

        var starImg:ImageView=view.findViewById(R.id.starImg)

        var answerTxt:TextView=view.findViewById(R.id.answerTxt)

        var numberTxt:TextView=view.findViewById(R.id.numberTxt)
        var rateType:TextView=view.findViewById(R.id.rateType)
    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_survey_choice, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if(answerType==1)
        {
            holder.choiseRelative.visibility=View.VISIBLE
            holder.smileyRelative.visibility=View.GONE
            holder.startRelative.visibility=View.GONE
            holder.numberRelative.visibility=View.GONE

            holder.answerTxt.text=surveyChoiceArrayList.get(position).answer
        }
       else if (answerType==2)
        {
            holder.choiseRelative.visibility=View.GONE
            holder.smileyRelative.visibility=View.VISIBLE
            holder.startRelative.visibility=View.GONE
            holder.numberRelative.visibility=View.GONE
            if (surveyChoiceArrayList.get(position).answer.equals("Happy"))
            {
                holder.smileyImg.setImageResource(R.drawable.happy)
            }
            else if (surveyChoiceArrayList.get(position).answer.equals("Sad"))
            {
                holder.smileyImg.setImageResource(R.drawable.sad)
            }
            else if (surveyChoiceArrayList.get(position).answer.equals("Neutral"))
            {
                holder.smileyImg.setImageResource(R.drawable.neutral)
            }
            else if (surveyChoiceArrayList.get(position).answer.equals("Excited"))
            {
                holder.smileyImg.setImageResource(R.drawable.excited)
            }
            else if (surveyChoiceArrayList.get(position).answer.equals("Angry"))
            {
                holder.smileyImg.setImageResource(R.drawable.angry)
            }
            holder.smileyTxt.text=surveyChoiceArrayList.get(position).answer
        }

        else if (answerType==3)
        {
            holder.choiseRelative.visibility=View.GONE
            holder.smileyRelative.visibility=View.GONE
            holder.startRelative.visibility=View.VISIBLE
            holder.numberRelative.visibility=View.GONE

            holder.starImg.setImageResource(R.drawable.star)

        }
        else if (answerType==4)
        {
            holder.choiseRelative.visibility=View.GONE

            holder.smileyRelative.visibility=View.GONE
            holder.startRelative.visibility=View.GONE
            holder.numberRelative.visibility=View.VISIBLE


            holder.numberTxt.text=surveyChoiceArrayList.get(position).answer
            holder.rateType.text=surveyChoiceArrayList.get(position).label

        }

    }
    override fun getItemCount(): Int {

        return surveyChoiceArrayList.size

    }
}