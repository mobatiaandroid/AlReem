package com.nas.alreem.activity.absence.model

import com.google.gson.annotations.SerializedName

class RequestPickupApiModel(

    @SerializedName("student_id") val student_id: String,
    @SerializedName("pickup_date") val from_date: String,
    @SerializedName("pickup_time") val time: String,
    @SerializedName("pickup_reason") val reason: String,
    @SerializedName("pickup_by_whom") val pickup_by: String,
    @SerializedName("device_type") val device_type: Int,
    @SerializedName("device_name") val device_name: String,
    @SerializedName("app_version") val app_version: String
)