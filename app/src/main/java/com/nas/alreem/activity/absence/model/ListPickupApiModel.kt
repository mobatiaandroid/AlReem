package com.nas.alreem.activity.absence.model

import com.google.gson.annotations.SerializedName

class ListPickupApiModel (

    @SerializedName("student_id") val student_id: String,
    @SerializedName("start") val start: String,
    @SerializedName("limit") val limit: String

)