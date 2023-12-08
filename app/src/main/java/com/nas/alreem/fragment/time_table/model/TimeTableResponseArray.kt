package com.nas.alreem.fragment.time_table.model.apimodel

import com.google.gson.annotations.SerializedName


class TimeTableResponseArray (
    @SerializedName("range") val range: RangeApiModel,
    @SerializedName("sort_fields") val field1List: List<FieldApiListModel>,
    @SerializedName("pdf_timetable") val pdf_timetable:String = "",
    @SerializedName("all") val timeTableList: List<TimeTableApiListModel>
)