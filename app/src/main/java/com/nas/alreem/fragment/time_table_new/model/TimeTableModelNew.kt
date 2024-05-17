package com.nas.alreem.fragment.time_table_new.model

import com.google.gson.annotations.SerializedName

class TimeTableModelNew (
    @SerializedName("id") val id: String,
    @SerializedName("day") val day: String,
    @SerializedName("period_id") val period_id: String,
    @SerializedName("sortname") val sortname: String,
    @SerializedName("starttime") val starttime: String,
    @SerializedName("endtime") val endtime: String,
    @SerializedName("subject_name") val subject_name: String,
    @SerializedName("student_id") val student_id: String,
    @SerializedName("staff") val staff: String,
    @SerializedName("isBreak") val isBreak: Int
)