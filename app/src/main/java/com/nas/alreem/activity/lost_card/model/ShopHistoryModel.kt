package com.nas.alreem.activity.lost_card.model

import com.google.gson.annotations.SerializedName

class ShopHistoryModel (
    @SerializedName("studentId") val student_id: String,
    @SerializedName("start") val start: String,
    @SerializedName("limit") val limit: String
)