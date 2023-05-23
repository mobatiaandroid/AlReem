package com.nas.alreem.fragment.calendar.model

import com.google.gson.annotations.SerializedName
import com.nas.alreem.fragment.calendar.model.CalendarArrayModel

class CalendarDataModel (
    @SerializedName("data") val data: List<CalendarArrayModel>
        )