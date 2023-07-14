package com.nas.alreem.activity.parent_meetings.model

import com.google.gson.annotations.SerializedName

class PtaListModel (
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data_count") val data_count: Int,
    @SerializedName("data") val data: ArrayList<PtaTimeSlotList>

)