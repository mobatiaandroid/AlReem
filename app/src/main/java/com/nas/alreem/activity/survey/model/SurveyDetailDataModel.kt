package com.nas.alreem.activity.survey.model

import com.google.gson.annotations.SerializedName

class SurveyDetailDataModel {
    @SerializedName("id")
    var id:Int=0
    @SerializedName("survey_name")
    var survey_name:String=""
    @SerializedName("image")
    var image:String=""
    @SerializedName("title")
    var title:String=""
    @SerializedName("description")
    var description:String=""
    @SerializedName("contact_email") val contact_email:String=""
    @SerializedName("questions")
    var questions:ArrayList<SurveyQuestionsModel>? =null
    @SerializedName("created_at")
    var created_at:String=""
    @SerializedName("updated_at")
    var updated_at:String=""
}