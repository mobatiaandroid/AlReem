package com.nas.alreem.activity.absence.model

import com.google.gson.annotations.SerializedName

class AbsenceListModel (
    @SerializedName("status")var status:Int,
    @SerializedName("responseArray")var responseArray:AbsenceListResponseModel
)