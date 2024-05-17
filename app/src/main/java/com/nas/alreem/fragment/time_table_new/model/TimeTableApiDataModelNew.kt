package com.nas.alreem.fragment.time_table_new.model

import com.google.gson.annotations.SerializedName

class TimeTableApiDataModelNew (
    @SerializedName("status") val status: Int,
    @SerializedName("responseArray") val responseArray: TimeTableResponseArrayNew
)