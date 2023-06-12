package com.nas.alreem.activity.survey.model

import com.google.gson.annotations.SerializedName

class SurveyQuestionsModel {
    @SerializedName("id") val id:Int=0
    @SerializedName("question") val question:String=""
    @SerializedName("answer_type") val answer_type:Int=0
    @SerializedName("answer") val answer:String=""
    @SerializedName("offered_answers") val offered_answers:ArrayList<SurveyOfferedAnswersModel>? =null
}