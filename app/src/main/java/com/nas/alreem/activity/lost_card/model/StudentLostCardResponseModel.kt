package com.nas.alreem.activity.lost_card.model

import com.google.gson.annotations.SerializedName

class StudentLostCardResponseModel (
    @SerializedName("responsecode") val responsecode: String,
    @SerializedName("response") val response: ResponseStudent
)