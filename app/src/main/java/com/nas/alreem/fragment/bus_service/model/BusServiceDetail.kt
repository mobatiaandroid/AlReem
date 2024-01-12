package com.nas.alreem.fragment.bus_service.model

import com.google.gson.annotations.SerializedName

class BusServiceDetail (
    @SerializedName("id") val id: String,
    @SerializedName("requested_time") val requested_time: String,
    @SerializedName("reason") val reason: String,
    @SerializedName("reason_for_rejection") val reason_for_rejection: String,
    @SerializedName("status") val status: Int,
    @SerializedName("parent_name") val parent_name: String,
    @SerializedName("pickup_date") val pickup_date: String

    )