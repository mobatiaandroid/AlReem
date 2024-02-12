package com.nas.alreem.activity.bus_service.requestservice.model

import com.google.gson.annotations.SerializedName

class RequestServiceApiModel (
    @SerializedName("student_id") val student_id: String,
    @SerializedName("requested_date") val requested_date: String,
    @SerializedName("pickup_point") val pickup_point: String,
    @SerializedName("drop_point") val drop_point: String
)