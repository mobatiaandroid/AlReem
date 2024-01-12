package com.nas.alreem.fragment.bus_service.model

import com.google.gson.annotations.SerializedName
import com.nas.alreem.fragment.student_information.model.StudentInfoDetail
import com.nas.alreem.fragment.student_information.model.StudentInfoResponseArray

class BusserviceResponseModel (
    @SerializedName("status") val status: Int,
    @SerializedName("bus_services") val bus_services: List<BusServiceDetail>
)