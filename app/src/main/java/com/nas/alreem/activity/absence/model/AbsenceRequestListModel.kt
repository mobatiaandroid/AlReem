package com.nas.alreem.activity.absence.model

import com.google.gson.annotations.SerializedName

class AbsenceRequestListModel (
    @SerializedName("id")var id:String,
    @SerializedName("from_date")var from_date:String,
    @SerializedName("to_date")var to_date:String,
    @SerializedName("reason")var reason:String,
    @SerializedName("reason_for_rejection") val reason_for_rejection: String,
    @SerializedName("status") val status: String
)