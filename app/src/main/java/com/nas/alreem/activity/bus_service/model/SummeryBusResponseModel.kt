package com.nas.alreem.activity.bus_service.model

import com.google.gson.annotations.SerializedName

class SummeryBusResponseModel (
    @SerializedName("status") var status:Int,
    @SerializedName("responseArray") var responseArray:ArrayList<SummeryBusResponseArray>)
