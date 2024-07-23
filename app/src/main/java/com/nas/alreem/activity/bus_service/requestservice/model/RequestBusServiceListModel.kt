package com.nas.alreem.activity.bus_service.requestservice.model

import com.google.gson.annotations.SerializedName

class RequestBusServiceListModel (
    @SerializedName("status") val status: Int,
    @SerializedName("responseArray") val responseArray: RequestBusServiceResponseArray
)