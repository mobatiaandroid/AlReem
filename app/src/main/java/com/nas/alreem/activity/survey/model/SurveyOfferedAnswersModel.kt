package com.nas.alreem.activity.survey.model

import com.google.gson.annotations.SerializedName

class SurveyOfferedAnswersModel {
    @SerializedName("id")
    var id:Int=0
    @SerializedName("answer")
    var answer:String=""
    @SerializedName("label")
    var label:String=""
    @SerializedName("isCicked")
    var is_clicked:Boolean=false
    @SerializedName("isCicked0")
    var is_clicked0:Boolean=false
}