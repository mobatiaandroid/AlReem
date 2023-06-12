package com.nas.alreem.activity.survey.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.nas.alreem.R
import com.nas.alreem.activity.survey.model.SurveyListDataModel
import com.nas.alreem.activity.survey.model.SurveyQuestionsModel
import com.nas.alreem.constants.OnItemClickListener
import com.nas.alreem.constants.addOnItemClickListener
import com.nas.alreem.fragment.home.mContext
import java.util.ArrayList

class SurveyQuestionPagerAdapter: PagerAdapter {
    var context: Context
    var questionArrayList = ArrayList<SurveyQuestionsModel>()
    lateinit var inflater: LayoutInflater


    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as View

    }

    override fun getCount(): Int {
        return questionArrayList.size
    }


    constructor(context: Context, questionArrayList: ArrayList<SurveyQuestionsModel>) {
        this.context = context
        this.questionArrayList = questionArrayList
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        inflater = LayoutInflater.from(context)
        var pageview: View? = inflater.inflate(R.layout.adapter_survey_questions, null)
        val questionTxt = pageview?.findViewById<View>(R.id.questionTxt) as TextView
        val choiceRecycler = pageview?.findViewById<View>(R.id.choiceRecycler) as RecyclerView
        questionTxt.text=questionArrayList.get(position).question

        // answer type : 1 Text Choice
        if (questionArrayList.get(position).answer_type==1)
        {
            var linearLayoutManager = LinearLayoutManager(context)
            choiceRecycler.layoutManager = linearLayoutManager
            choiceRecycler.itemAnimator = DefaultItemAnimator()
        }

        // answer type: 2 Smiley Choice
        if(questionArrayList.get(position).answer_type==2)
        {
            var linearLayoutManager = LinearLayoutManager(context)
            choiceRecycler.layoutManager = linearLayoutManager
            choiceRecycler.itemAnimator = DefaultItemAnimator()
        }
        // answer type : 3 star rating choice
        else if (questionArrayList.get(position).answer_type==3)
        {
            var llm = (LinearLayoutManager(context))
            llm.orientation = LinearLayoutManager.HORIZONTAL
            choiceRecycler.layoutManager = llm
        }

        // answer type: 4 : Number Choice
        else if (questionArrayList.get(position).answer_type==4)
        {
            var llm = (LinearLayoutManager(context))
            llm.orientation = LinearLayoutManager.HORIZONTAL
            choiceRecycler.layoutManager = llm
        }
        if (questionArrayList.get(position).answer.equals(""))
        {
            var cAdapter=SurveyChoiceAdapter(questionArrayList.get(position).offered_answers!!,
                context,questionArrayList.get(position).answer_type)
            choiceRecycler.adapter=cAdapter

        }
        else{
            for (i in 0..questionArrayList.get(position).offered_answers!!.size-1)
            {
                if (questionArrayList.get(position).answer.equals(questionArrayList.get(position).offered_answers!!.get(i).id.toString()))
                {
                    questionArrayList.get(position).offered_answers!!.get(i).is_clicked=true
                }
                else{
                    questionArrayList.get(position).offered_answers!!.get(i).is_clicked=true
                }

            }

            var cAdapter=SurveyChoiceAdapter(questionArrayList.get(position).offered_answers!!,
                context,questionArrayList.get(position).answer_type)
            choiceRecycler.adapter=cAdapter

           choiceRecycler.addOnItemClickListener(object : OnItemClickListener {
                @SuppressLint("SimpleDateFormat", "SetTextI18n")
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onItemClicked(position: Int, view: View)
                {



                }
            })

        }



        (container as ViewPager).addView(pageview, 0)
        return pageview
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }
}