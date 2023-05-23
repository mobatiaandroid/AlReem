package com.nas.alreem.fragment.calendar.model

import com.google.gson.annotations.SerializedName
import com.nas.alreem.fragment.calendar.model.TermCalendarDataModel

class TermCalendarResponseModel (
    @SerializedName("status") val status: Int,
    @SerializedName("banner_image") val banner_image: String,
    @SerializedName("lists") val lists: List<TermCalendarListModel>

)