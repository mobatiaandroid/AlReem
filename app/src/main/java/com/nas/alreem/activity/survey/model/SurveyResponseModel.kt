package com.nas.alreem.activity.survey.model

import com.google.gson.annotations.SerializedName

class SurveyResponseModel (
    @SerializedName("status") val status:Int=0,
    @SerializedName("responseArray") val responseArray: SurveyResponseArrayModel? =null
        )