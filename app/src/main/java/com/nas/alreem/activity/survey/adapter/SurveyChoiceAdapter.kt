package com.nas.alreem.activity.survey.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.activity.survey.model.SurveyOfferedAnswersModel
import com.nas.alreem.appcontroller.AppController

class SurveyChoiceAdapter (private var surveyChoiceArrayList: ArrayList<SurveyOfferedAnswersModel>,
                           var context: Context, var answerType:Int) :
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
        var indicateImg:ImageView=view.findViewById(R.id.indicateImg)


    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_survey_choice, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Log.e("ANS_TYPE", answerType.toString())
        if(answerType==1)
        {
            holder.choiseRelative.visibility=View.VISIBLE
            holder.smileyRelative.visibility=View.GONE
            holder.startRelative.visibility=View.GONE
            holder.numberRelative.visibility=View.GONE

            holder.answerTxt.text=surveyChoiceArrayList.get(position).answer
            var isClick = false
            for (i in surveyChoiceArrayList.indices) {
                if (surveyChoiceArrayList.get(i).is_clicked) {
                    isClick = true
                }
            }
            if (isClick) {
                if (surveyChoiceArrayList.get(position).is_clicked) {
                    AppController.answer_id = surveyChoiceArrayList.get(position).id.toString()
                    holder.answerTxt.setBackgroundColor(context.getResources().getColor(R.color.white))
                    holder.choiseRelative.setBackgroundColor(context.getResources().getColor(R.color.white))
                    holder.indicateImg.setImageResource(R.drawable.option_select)
                 //   nextQuestionBtn.setClickable(true)
                } else {
                    AppController.answer_id = ""
                    holder.answerTxt.setBackgroundColor(
                        context.getResources().getColor(R.color.white)
                    )
                    holder.choiseRelative.setBackgroundColor(
                        context.getResources().getColor(R.color.white)
                    )
                    holder.indicateImg.setImageResource(R.drawable.option_initial)
                    //holder.answerTxt.setBackground(mContext.getResources().getDrawable(R.drawable.button_grey_white));
                }
            } else {
                AppController.answer_id = ""
                holder.answerTxt.setBackgroundColor(context.getResources().getColor(R.color.white))
                holder.choiseRelative.setBackgroundColor(
                    context.getResources().getColor(R.color.white)
                )
                holder.indicateImg.setImageResource(R.drawable.option_not_select)
            }

        }
       else if (answerType==2)
        {
            holder.choiseRelative.visibility=View.GONE
            holder.smileyRelative.visibility=View.VISIBLE
            holder.startRelative.visibility=View.GONE
            holder.numberRelative.visibility=View.GONE
            holder.smileyTxt.setText(surveyChoiceArrayList.get(position).answer)
            Log.e("isclicked", surveyChoiceArrayList.get(position).is_clicked.toString())
            if (surveyChoiceArrayList.get(position).is_clicked)
            {
                holder.smileyRelative.setBackgroundColor(context.getResources().getColor(R.color.list_bg))
                holder.smileyTxt.setTextColor(context.getResources().getColor(R.color.white))
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
              //  holder.smileyTxt.text=surveyChoiceArrayList.get(position).answer
                AppController.answer_id = surveyChoiceArrayList.get(position).id.toString()

               // nextQuestionBtn.setClickable(true)
            } else {
                holder.smileyRelative.background = context.getResources().getDrawable(R.drawable.survey_num_bg)
                holder.smileyTxt.setTextColor(context.getResources().getColor(R.color.black))
                AppController.answer_id = ""
                if (surveyChoiceArrayList.get(position).answer.equals("Sad")) {
                    holder.smileyImg.setImageResource(R.drawable.sad_notselected)
                } else if (surveyChoiceArrayList.get(position).answer.equals("Angry")) {
                    holder.smileyImg.setImageResource(R.drawable.angry_notselected)
                } else if (surveyChoiceArrayList.get(position).answer.equals("Happy")) {
                    holder.smileyImg.setImageResource(R.drawable.happy_notselected)
                } else if (surveyChoiceArrayList.get(position).answer.equals("Neutral")) {
                    holder.smileyImg.setImageResource(R.drawable.neutral_notselected)
                } else if (surveyChoiceArrayList.get(position).answer.equals("Excited")) {
                    holder.smileyImg.setImageResource(R.drawable.excited_notselected)
                }
            }
        }

        else if (answerType==3)
        {
            holder.choiseRelative.visibility=View.GONE
            holder.smileyRelative.visibility=View.GONE
            holder.startRelative.visibility=View.VISIBLE
            holder.numberRelative.visibility=View.GONE
            if (surveyChoiceArrayList.get(position).is_clicked0) {
                holder.starImg.setImageResource(R.drawable.star_fill)
                AppController.answer_id = surveyChoiceArrayList.get(position).id.toString()
              //  nextQuestionBtn.setClickable(true)
            } else {
                AppController.answer_id = ""
                holder.smileyImg.setImageResource(R.drawable.star)
            }


        }
        else if (answerType==4)
        {
            holder.choiseRelative.visibility=View.GONE

            holder.smileyRelative.visibility=View.GONE
            holder.startRelative.visibility=View.GONE
            holder.numberRelative.visibility=View.VISIBLE


            holder.numberTxt.text=surveyChoiceArrayList.get(position).answer
            holder.rateType.text=surveyChoiceArrayList.get(position).label
            if (surveyChoiceArrayList.get(position).is_clicked) {
                AppController.answer_id = surveyChoiceArrayList.get(position).id.toString()
                holder.numberTxt.setTextColor(context.getResources().getColor(R.color.white))
                holder.numberTxt.setBackgroundColor(context.getResources().getColor(R.color.list_bg))
                // holder.numberRelative.setBackgroundColor(mContext.getResources().getColor(R.color.list_bg));
              //  nextQuestionBtn.setClickable(true)
            } else {
                AppController.answer_id = ""
                holder.numberTxt.setTextColor(context.getResources().getColor(R.color.black))
                holder.numberTxt.background = context.getResources().getDrawable(R.drawable.survey_num_bg)
                //holder.answerTxt.setBackground(mContext.getResources().getDrawable(R.drawable.button_grey_white));
            }

        }

    }
    override fun getItemCount(): Int {

        return surveyChoiceArrayList.size

    }
}