package com.nas.alreem.activity.survey.model

import com.google.gson.annotations.SerializedName

class SurveySubmitApiModel (
    @SerializedName("survey_id") var id:String="",
    @SerializedName("survey_satisfaction_status") var survey_satisfaction_status:String="",
    @SerializedName("data") var questions:ArrayList<SurveySubmitDataModel>? =null
    )