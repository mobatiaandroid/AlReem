package com.nas.alreem.fragment.calendar.model

import com.google.gson.annotations.SerializedName
import com.nas.alreem.fragment.calendar.model.CalendarDataModel

class CalendarResponseModel (
    @SerializedName("status") val status: Int,
    @SerializedName("responseArray") val responseArray: CalendarDataModel

)