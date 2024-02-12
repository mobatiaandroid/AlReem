package com.nas.alreem.activity.bus_service.requestservice.model

import com.google.gson.annotations.SerializedName

class RequestServiceArrayModel  (
    @SerializedName("id") val id: String,
    @SerializedName("requested_date") val requested_date: String,
    @SerializedName("class_name") val class_name: String,
    @SerializedName("pickup_point") val pickup_point: String,
    @SerializedName("drop_point") val drop_point: String,
    @SerializedName("status") val status: Int
)