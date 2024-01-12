package com.nas.alreem.fragment.bus_service.model

import com.google.gson.annotations.SerializedName

class RequestBusServiceModelSubmit (
    @SerializedName("student_id") val student_id: String,
    @SerializedName("pickup_date") val pickup_date: String,
    @SerializedName("requested_time") val requested_time: String,
    @SerializedName("pickup_reason") val pickup_reason: String,
    @SerializedName("requested_on") val requested_on: String,
    @SerializedName("device_type") val device_type: Int,
    @SerializedName("device_name") val device_name: String,
    @SerializedName("app_version") val app_version: String
    )