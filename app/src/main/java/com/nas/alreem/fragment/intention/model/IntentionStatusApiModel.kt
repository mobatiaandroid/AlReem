package com.nas.alreem.fragment.intention.model

import com.google.gson.annotations.SerializedName

class IntentionStatusApiModel (

    @SerializedName("intension_id") val intension_id: Int,
    @SerializedName("start") val start: String,
@SerializedName("limit") val limit: String
)