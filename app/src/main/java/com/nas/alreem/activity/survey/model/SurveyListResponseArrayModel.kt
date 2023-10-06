package com.nas.alreem.activity.survey.model

import com.google.gson.annotations.SerializedName
import com.nas.alreem.fragment.about_us.model.AboutUsDataModel
import com.nas.alreem.fragment.about_us.model.AboutUsResponseArrayModel

class SurveyListResponseArrayModel  (
    @SerializedName("data") var data:ArrayList<SurveyListDataModel>
)
