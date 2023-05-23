package com.nas.alreem.activity.notifications.model

import com.google.gson.annotations.SerializedName

data class MessageDetailModel (
    @SerializedName("status") val status: Int,
    @SerializedName("responseArray") val responseArray: MessageDetailResponseModel
)