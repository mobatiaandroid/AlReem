package com.nas.alreem.fragment.time_table_new.model

import com.google.gson.annotations.SerializedName

class TimeTableResponseArrayNew (
    @SerializedName("range") val range: RangeApiModelNew,
    @SerializedName("field") val field1List: List<FieldApiListModelNew>,
    @SerializedName("pdf_timetable") val pdf_timetable:String = "",
    @SerializedName("Timetable") val timeTableList: List<TimeTableApiListModelNew>
)