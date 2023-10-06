package com.nas.alreem.activity.survey.model

import com.google.gson.annotations.SerializedName

class SurveySubmitDataModel {
    @SerializedName("answer_id") var answer_id:String=""
    @SerializedName("question_id") var question_id:String=""
}