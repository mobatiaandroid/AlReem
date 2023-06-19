package com.nas.alreem.activity.survey.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.nas.alreem.R
import com.nas.alreem.activity.survey.model.SurveyQuestionsModel
import com.nas.alreem.appcontroller.AppController
import com.nas.alreem.constants.OnItemClickListener
import com.nas.alreem.constants.addOnItemClickListener
import com.nas.alreem.fragment.home.mContext

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
       /* if (questionArrayList.get(position).answer.equals(""))
        {
            var cAdapter=SurveyChoiceAdapter(questionArrayList.get(position).offered_answers!!,
                context,questionArrayList.get(position).answer_type)
            choiceRecycler.adapter=cAdapter

        }*/
       /* else{
            for (i in 0..questionArrayList.get(position).offered_answers!!.size-1)
            {
                if (questionArrayList.get(position).answer.equals(questionArrayList.get(position).offered_answers!!.get(i).id.toString()))
                {
                    questionArrayList.get(position).offered_answers!!.get(i).is_clicked=true
                }
                else{
                    questionArrayList.get(position).offered_answers!!.get(i).is_clicked=true
                }

            }*/

            var cAdapter=SurveyChoiceAdapter(questionArrayList.get(position).offered_answers!!,
                context,questionArrayList.get(position).answer_type)
            choiceRecycler.adapter=cAdapter

            choiceRecycler.addOnItemClickListener(object : OnItemClickListener {
                @SuppressLint("SimpleDateFormat", "SetTextI18n")
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onItemClicked(pos: Int, view: View)
                {
                    var clickedPosition = -1
                    for (i in questionArrayList.get(position).offered_answers!!.indices) {
                        if (questionArrayList.get(position).offered_answers!!.get(i).is_clicked
                        ) {
                            clickedPosition = i
                        }
                    }
                    if (clickedPosition != -1) {
                        if (clickedPosition == pos) {
                            AppController.answer_id = ""
                            AppController.question_id = questionArrayList.get(position).id.toString()
                            questionArrayList.get(position)!!.answer=""
                            questionArrayList.get(position).offered_answers!!.get(pos).is_clicked=false
                            if (questionArrayList.get(position).offered_answers!!.size === 5
                            ) {
                                questionArrayList.get(position).offered_answers!!.get(0).is_clicked0=false
                                questionArrayList.get(position).offered_answers!!.get(1).is_clicked0=false
                                questionArrayList.get(position).offered_answers!!.get(2).is_clicked0=false
                                questionArrayList.get(position).offered_answers!!.get(3).is_clicked0=false
                                questionArrayList.get(position).offered_answers!!.get(4).is_clicked0=false
                            }
                            if (questionArrayList.get(position).offered_answers!!.size === 4
                            ) {
                                questionArrayList.get(position).offered_answers!!.get(0).is_clicked0=false
                                questionArrayList.get(position).offered_answers!!.get(1).is_clicked0=false
                                questionArrayList.get(position).offered_answers!!.get(2).is_clicked0=false
                                questionArrayList.get(position).offered_answers!!.get(3).is_clicked0=false
                            }
                            if (questionArrayList.get(position).offered_answers!!.size === 3
                            ) {
                                questionArrayList.get(position).offered_answers!!.get(0).is_clicked0=false
                                questionArrayList.get(position).offered_answers!!.get(1).is_clicked0=false
                                questionArrayList.get(position).offered_answers!!.get(2).is_clicked0=false
                            }
                            if (questionArrayList.get(position).offered_answers!!.size === 2
                            ) {
                                questionArrayList.get(position).offered_answers!!.get(0).is_clicked0=false
                                questionArrayList.get(position).offered_answers!!.get(1).is_clicked0=false
                            }
                            if (questionArrayList.get(position).offered_answers!!.size === 1
                            ) {
                                questionArrayList.get(position).offered_answers!!.get(0).is_clicked0=false
                            }

//                                for (int j=0;j< mSurveyArrayList.get(position).getSurveyAnswersrrayList().size();j++)
//                                {
//                                    if (pos)
//                                }
                        } else {
                            AppController.answer_id = questionArrayList.get(position).offered_answers!!.get(pos).id.toString()
                            AppController.question_id = questionArrayList.get(position).id.toString()
                            questionArrayList.get(position).offered_answers!!.get(clickedPosition).is_clicked=false
                            questionArrayList.get(position).offered_answers!!.get(pos).is_clicked=true
                            questionArrayList.get(position).answer=(questionArrayList.get(position).offered_answers!!.get(pos).id.toString())
                            if (questionArrayList.get(position).offered_answers!!.size === 5
                            ) {
                                if (pos == 0) {
                                    questionArrayList.get(position).offered_answers!!.get(0).is_clicked0=true
                                    questionArrayList.get(position).offered_answers!!.get(1).is_clicked0=false
                                    questionArrayList.get(position).offered_answers!!.get(2).is_clicked0=false
                                    questionArrayList.get(position).offered_answers!!.get(3).is_clicked0=false
                                    questionArrayList.get(position).offered_answers!!.get(4).is_clicked0=false
                                } else if (pos == 1) {
                                    questionArrayList.get(position).offered_answers!!.get(0).is_clicked0=true
                                    questionArrayList.get(position).offered_answers!!.get(1).is_clicked0=true
                                    questionArrayList.get(position).offered_answers!!.get(2).is_clicked0=false
                                    questionArrayList.get(position).offered_answers!!.get(3).is_clicked0=false
                                    questionArrayList.get(position).offered_answers!!.get(4).is_clicked0=false
                                } else if (pos == 2) {
                                    questionArrayList.get(position).offered_answers!!.get(0).is_clicked0=true
                                    questionArrayList.get(position).offered_answers!!.get(1).is_clicked0=true
                                    questionArrayList.get(position).offered_answers!!.get(2).is_clicked0=true
                                    questionArrayList.get(position).offered_answers!!.get(3).is_clicked0=false
                                    questionArrayList.get(position).offered_answers!!.get(4).is_clicked0=false
                                } else if (pos == 3) {
                                    questionArrayList.get(position).offered_answers!!.get(0).is_clicked0=true
                                    questionArrayList.get(position).offered_answers!!.get(1).is_clicked0=true
                                    questionArrayList.get(position).offered_answers!!.get(2).is_clicked0=true
                                    questionArrayList.get(position).offered_answers!!.get(3).is_clicked0=true
                                    questionArrayList.get(position).offered_answers!!.get(4).is_clicked0=false
                                } else if (pos == 4) {
                                    questionArrayList.get(position).offered_answers!!.get(0).is_clicked0=true
                                    questionArrayList.get(position).offered_answers!!.get(1).is_clicked0=true
                                    questionArrayList.get(position).offered_answers!!.get(2).is_clicked0=true
                                    questionArrayList.get(position).offered_answers!!.get(3).is_clicked0=true
                                    questionArrayList.get(position).offered_answers!!.get(4).is_clicked0=true
                                }
                            }
                            if (questionArrayList.get(position).offered_answers!!.size === 4) {
                                if (pos == 0) {
                                    questionArrayList.get(position).offered_answers!!.get(0).is_clicked0=true
                                    questionArrayList.get(position).offered_answers!!.get(1).is_clicked0=false
                                    questionArrayList.get(position).offered_answers!!.get(2).is_clicked0=false
                                    questionArrayList.get(position).offered_answers!!.get(3).is_clicked0=false
                                } else if (pos == 1) {
                                    questionArrayList.get(position).offered_answers!!.get(0).is_clicked0=true
                                    questionArrayList.get(position).offered_answers!!.get(1).is_clicked0=true
                                    questionArrayList.get(position).offered_answers!!.get(2).is_clicked0=false
                                    questionArrayList.get(position).offered_answers!!.get(3).is_clicked0=false
                                } else if (pos == 2) {
                                    questionArrayList.get(position).offered_answers!!.get(0).is_clicked0=true
                                    questionArrayList.get(position).offered_answers!!.get(1).is_clicked0=true
                                    questionArrayList.get(position).offered_answers!!.get(2).is_clicked0=true
                                    questionArrayList.get(position).offered_answers!!.get(3).is_clicked0=false
                                } else if (pos == 3) {
                                    questionArrayList.get(position).offered_answers!!.get(0).is_clicked0=true
                                    questionArrayList.get(position).offered_answers!!.get(1).is_clicked0=true
                                    questionArrayList.get(position).offered_answers!!.get(2).is_clicked0=true
                                    questionArrayList.get(position).offered_answers!!.get(3).is_clicked0=true
                                }
                            }
                            if (questionArrayList.get(position).offered_answers!!.size === 3) {
                                if (pos == 0) {
                                    questionArrayList.get(position).offered_answers!!.get(0).is_clicked0=(true)
                                    questionArrayList.get(position).offered_answers!!.get(1).is_clicked0=(false)
                                    questionArrayList.get(position).offered_answers!!.get(2).is_clicked0=(false)
                                } else if (pos == 1) {
                                    questionArrayList.get(position).offered_answers!!.get(0).is_clicked0=(true)
                                    questionArrayList.get(position).offered_answers!!.get(1).is_clicked0=(true)
                                    questionArrayList.get(position).offered_answers!!.get(2).is_clicked0=(false)
                                } else if (pos == 2) {
                                    questionArrayList.get(position).offered_answers!!.get(0).is_clicked0=(true)
                                    questionArrayList.get(position).offered_answers!!.get(1).is_clicked0=(true)
                                    questionArrayList.get(position).offered_answers!!.get(2).is_clicked0=(true)
                                }
                            }
                            if (questionArrayList.get(position).offered_answers!!.size === 2
                            ) {
                                if (pos == 0) {
                                    questionArrayList.get(position).offered_answers!!.get(0).is_clicked0=(true)
                                    questionArrayList.get(position).offered_answers!!.get(1).is_clicked0=(false)
                                } else if (pos == 1) {
                                    questionArrayList.get(position).offered_answers!!.get(0).is_clicked0=(true)
                                    questionArrayList.get(position).offered_answers!!.get(1).is_clicked0=(true)
                                }
                            }
                            if (questionArrayList.get(position).offered_answers!!.size === 1
                            ) {
                                if (pos == 0) {
                                    questionArrayList.get(position).offered_answers!!.get(0).is_clicked0=(true)
                                }
                            }
                        }
                    } else {
                        AppController.answer_id = questionArrayList.get(position).offered_answers!!.get(pos).id.toString()
                        AppController.question_id = questionArrayList.get(position).id.toString()
                        questionArrayList.get(position).offered_answers!!.get(pos).is_clicked=(true)
                        questionArrayList.get(position).answer=(questionArrayList.get(position).offered_answers!!.get(pos).id).toString()
                        if (questionArrayList.get(position).offered_answers!!.size === 5
                        ) {
                            if (pos == 0) {
                                questionArrayList.get(position).offered_answers!!.get(0).is_clicked0=(true)
                                questionArrayList.get(position).offered_answers!!.get(1).is_clicked0=(false)
                                questionArrayList.get(position).offered_answers!!.get(2).is_clicked0=(false)
                                questionArrayList.get(position).offered_answers!!.get(3).is_clicked0=(false)
                                questionArrayList.get(position).offered_answers!!.get(4).is_clicked0=(false)
                            } else if (pos == 1) {
                                questionArrayList.get(position).offered_answers!!.get(0).is_clicked0=(true)
                                questionArrayList.get(position).offered_answers!!.get(1).is_clicked0=(true)
                                questionArrayList.get(position).offered_answers!!.get(2).is_clicked0=(false)
                                questionArrayList.get(position).offered_answers!!.get(3).is_clicked0=(false)
                                questionArrayList.get(position).offered_answers!!.get(4).is_clicked0=(false)
                            } else if (pos == 2) {
                                questionArrayList.get(position).offered_answers!!.get(0).is_clicked0=(true)
                                questionArrayList.get(position).offered_answers!!.get(1).is_clicked0=(true)
                                questionArrayList.get(position).offered_answers!!.get(2).is_clicked0=(true)
                                questionArrayList.get(position).offered_answers!!.get(3).is_clicked0=(false)
                                questionArrayList.get(position).offered_answers!!.get(4).is_clicked0=(false)
                            } else if (pos == 3) {
                                questionArrayList.get(position).offered_answers!!.get(0).is_clicked0=(true)
                                questionArrayList.get(position).offered_answers!!.get(1).is_clicked0=(true)
                                questionArrayList.get(position).offered_answers!!.get(2).is_clicked0=(true)
                                questionArrayList.get(position).offered_answers!!.get(3).is_clicked0=(true)
                                questionArrayList.get(position).offered_answers!!.get(4).is_clicked0=(false)
                            } else if (pos == 4) {
                                questionArrayList.get(position).offered_answers!!.get(0).is_clicked0=(true)
                                questionArrayList.get(position).offered_answers!!.get(1).is_clicked0=(true)
                                questionArrayList.get(position).offered_answers!!.get(2).is_clicked0=(true)
                                questionArrayList.get(position).offered_answers!!.get(3).is_clicked0=(true)
                                questionArrayList.get(position).offered_answers!!.get(4).is_clicked0=(true)
                            }
                        }
                        if (questionArrayList.get(position).offered_answers!!.size === 4
                        ) {
                            if (pos == 0) {
                                questionArrayList.get(position).offered_answers!!.get(0).is_clicked0=(true)
                                questionArrayList.get(position).offered_answers!!.get(1).is_clicked0=(false)
                                questionArrayList.get(position).offered_answers!!.get(2).is_clicked0=(false)
                                questionArrayList.get(position).offered_answers!!.get(3).is_clicked0=(false)
                            } else if (pos == 1) {
                                questionArrayList.get(position).offered_answers!!.get(0).is_clicked0=(true)
                                questionArrayList.get(position).offered_answers!!.get(1).is_clicked0=(true)
                                questionArrayList.get(position).offered_answers!!.get(2).is_clicked0=(false)
                                questionArrayList.get(position).offered_answers!!.get(3).is_clicked0=(false)
                            } else if (pos == 2) {
                                questionArrayList.get(position).offered_answers!!.get(0).is_clicked0=(true)
                                questionArrayList.get(position).offered_answers!!.get(1).is_clicked0=(true)
                                questionArrayList.get(position).offered_answers!!.get(2).is_clicked0=(true)
                                questionArrayList.get(position).offered_answers!!.get(3).is_clicked0=(false)
                            } else if (pos == 3) {
                                questionArrayList.get(position).offered_answers!!.get(0).is_clicked0=(true)
                                questionArrayList.get(position).offered_answers!!.get(1).is_clicked0=(true)
                                questionArrayList.get(position).offered_answers!!.get(2).is_clicked0=(true)
                                questionArrayList.get(position).offered_answers!!.get(3).is_clicked0=(true)
                            }
                        }
                        if (questionArrayList.get(position).offered_answers!!.size === 3
                        ) {
                            if (pos == 0) {
                                questionArrayList.get(position).offered_answers!!.get(0).is_clicked0=(true)
                                questionArrayList.get(position).offered_answers!!.get(1).is_clicked0=(false)
                                questionArrayList.get(position).offered_answers!!.get(2).is_clicked0=(false)
                            } else if (pos == 1) {
                                questionArrayList.get(position).offered_answers!!.get(0).is_clicked0=(true)
                                questionArrayList.get(position).offered_answers!!.get(1).is_clicked0=(true)
                                questionArrayList.get(position).offered_answers!!.get(2).is_clicked0=(false)
                            } else if (pos == 2) {
                                questionArrayList.get(position).offered_answers!!.get(0).is_clicked0=(true)
                                questionArrayList.get(position).offered_answers!!.get(1).is_clicked0=(true)
                                questionArrayList.get(position).offered_answers!!.get(2).is_clicked0=(true)
                            }
                        }
                        if (questionArrayList.get(position).offered_answers!!.size === 2
                        ) {
                            if (pos == 0) {
                                questionArrayList.get(position).offered_answers!!.get(0).is_clicked0=(true)
                                questionArrayList.get(position).offered_answers!!.get(1).is_clicked0=(false)
                            } else if (pos == 1) {
                                questionArrayList.get(position).offered_answers!!.get(0).is_clicked0=(true)
                                questionArrayList.get(position).offered_answers!!.get(1).is_clicked0=(true)
                            }
                        }
                        if (questionArrayList.get(position).offered_answers!!.size === 1
                        ) {
                            if (pos == 0) {
                                questionArrayList.get(position).offered_answers!!.get(0).is_clicked0=(true)
                            }
                        }
                    }
                    if (position == questionArrayList.size - 1) {
                        AppController.answer_id = questionArrayList.get(position).offered_answers!!.get(pos).id.toString()
                        AppController.question_id = questionArrayList.get(position).id.toString()
                    }

                    var cAdapter   = SurveyChoiceAdapter(questionArrayList.get(position).offered_answers!!,
                    context,questionArrayList.get(position).answer_type)
                    choiceRecycler.adapter =   cAdapter

                }


            })

     //   }



        (container as ViewPager).addView(pageview, 0)
        return pageview
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }
}