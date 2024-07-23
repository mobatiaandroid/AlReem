package com.nas.alreem.activity.lost_card.model

import com.google.gson.annotations.SerializedName

class ResponseInstrutions (
    @SerializedName("response") val response: String,
    @SerializedName("statuscode") val statuscode: String,
    @SerializedName("instructions") val instructions: String

    )