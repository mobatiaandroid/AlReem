package com.nas.alreem.fragment.time_table_new.model

import com.google.gson.annotations.SerializedName

class RangeModelNew (
    @SerializedName("timeTableDayModel") val timeTableDayModel: List<DayModelNew>,
    @SerializedName("timeTableDayThursdayModel") val timeTableDayThursdayModel: List<DayModelNew>,
    @SerializedName("timeTableModel") val timeTableModel: List<TimeTableModelNew>,
    @SerializedName("periodModel") val periodModel: List<PeriodModelNew>
)