package com.nas.alreem.activity.lost_card.model

import com.google.gson.annotations.SerializedName

class LostCardHistoryResponseModel (
    @SerializedName("responsecode") val responsecode: String,
    @SerializedName("response") val response: ResponseHistory
)