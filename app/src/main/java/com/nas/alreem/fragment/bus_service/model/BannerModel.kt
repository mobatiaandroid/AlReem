package com.nas.alreem.fragment.bus_service.model

import com.google.gson.annotations.SerializedName
import com.nas.alreem.fragment.intention.model.IntentionInfoArray

class BannerModel (
    @SerializedName("status") val status: Int,
    @SerializedName("responseArray") val responseArray: BannerResponseModel
)