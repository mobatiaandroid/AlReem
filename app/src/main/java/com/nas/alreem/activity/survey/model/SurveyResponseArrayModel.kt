package com.nas.alreem.activity.survey.model

import com.google.gson.annotations.SerializedName

class SurveyResponseArrayModel (
        @SerializedName("data") val data:ArrayList<SurveyDetailDataModel>? =null
        )