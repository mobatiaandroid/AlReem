package com.nas.alreem.activity.bus_service.model

import com.google.gson.annotations.SerializedName

class PaymentSubmitBusApiModel (
    @SerializedName("student_id") var student_id:String,
    @SerializedName("bus_request_id") var bus_request_id:String,
    @SerializedName("order_reference") var order_reference:String,
    @SerializedName("device_type") var device_type:String,
    @SerializedName("device_name") var device_name:String,
    @SerializedName("app_version") var app_version:String


)