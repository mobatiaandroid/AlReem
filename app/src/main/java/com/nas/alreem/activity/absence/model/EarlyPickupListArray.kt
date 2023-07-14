package com.nas.alreem.activity.absence.model

import com.google.gson.annotations.SerializedName

class EarlyPickupListArray (
    @SerializedName("id") val id: Int,
    @SerializedName("pickup_time") val pickup_time: String,
    @SerializedName("reason") val reason: String,
    @SerializedName("pickup_by_whom") val pickup_by_whom: String,
    @SerializedName("reason_for_rejection") val reason_for_rejection: String,
    @SerializedName("status") val status: Int,
    @SerializedName("parent_name") val parent_name: String,
    @SerializedName("pickup_date") val pickup_date: String
)