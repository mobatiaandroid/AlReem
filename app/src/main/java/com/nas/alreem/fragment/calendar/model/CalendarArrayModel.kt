package com.nas.alreem.fragment.calendar.model

import com.google.gson.annotations.SerializedName

class CalendarArrayModel (
    @SerializedName("date") val date: String,
    @SerializedName("details") val details: List<CalendarDetailsModel>)