package com.nas.alreem.fragment.intention.model

import com.google.gson.annotations.SerializedName

class IntentionStatusResponseModel (
    @SerializedName("status") val status: Int,
    @SerializedName("responseArray") val responseArray: IntentionStatusArray
)