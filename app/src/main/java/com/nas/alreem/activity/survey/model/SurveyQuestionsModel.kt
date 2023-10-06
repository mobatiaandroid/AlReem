package com.nas.alreem.activity.survey.model

import com.google.gson.annotations.SerializedName

class SurveyQuestionsModel {
    @SerializedName("id")
    var id:Int=0
    @SerializedName("question")
    var question:String=""
    @SerializedName("answer_type")
    var answer_type:Int=0
    @SerializedName("answer") var answer:String=""
    @SerializedName("offered_answers")
    var offered_answers:ArrayList<SurveyOfferedAnswersModel>? =null
}