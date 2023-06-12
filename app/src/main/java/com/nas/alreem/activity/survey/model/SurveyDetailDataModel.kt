package com.nas.alreem.activity.survey.model

import com.google.gson.annotations.SerializedName

class SurveyDetailDataModel {
    @SerializedName("id") val id:Int=0
    @SerializedName("survey_name") val survey_name:String=""
    @SerializedName("image") val image:String=""
    @SerializedName("title") val title:String=""
    @SerializedName("description") val description:String=""
    @SerializedName("contact_email") val contact_email:String=""
    @SerializedName("questions") val questions:ArrayList<SurveyQuestionsModel>? =null
}