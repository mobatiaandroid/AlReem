package com.nas.alreem.activity.survey.model

import com.google.gson.annotations.SerializedName

class SurveyOfferedAnswersModel {
    @SerializedName("id") val id:Int=0
    @SerializedName("answer") val answer:String=""
    @SerializedName("label") val label:String=""
    @SerializedName("isCicked") var is_clicked:Boolean=false
}