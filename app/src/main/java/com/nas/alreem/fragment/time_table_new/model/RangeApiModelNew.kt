package com.nas.alreem.fragment.time_table_new.model

import com.google.gson.annotations.SerializedName

class RangeApiModelNew (
    @SerializedName("Monday") val MondayList: List<TimeTableApiListModelNew>,
    @SerializedName("Tuesday") val TuesdayList: List<TimeTableApiListModelNew>,
    @SerializedName("Wednesday") val WednesdayList: List<TimeTableApiListModelNew>,
    @SerializedName("Thursday") val Thursday1List: List<TimeTableApiListModelNew>,
    @SerializedName("Friday") val Friday1List: List<TimeTableApiListModelNew>
)