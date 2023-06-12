package com.nas.alreem.activity.survey.model

import com.google.gson.annotations.SerializedName

class SurveyListResponseModel  {
    @SerializedName("status") val status:Int=0
    @SerializedName("responseArray") val responseArray: SurveyListResponseArrayModel? =null
}