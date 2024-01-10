package com.nas.alreem.fragment.intention.model

import com.google.gson.annotations.SerializedName

class IntentionApiModel (
    @SerializedName("student_id") val student_id: String,
    @SerializedName("start") val start: String,
@SerializedName("limit") val limit: String
)