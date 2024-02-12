package com.nas.alreem.activity.bus_service.requestservice.model

import com.google.gson.annotations.SerializedName

class RequestBusServiceResponseArray (
    @SerializedName("bus_service_request") val bus_service_request: ArrayList<RequestServiceArrayModel>
)