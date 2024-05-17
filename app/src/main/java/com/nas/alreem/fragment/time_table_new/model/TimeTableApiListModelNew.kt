package com.nas.alreem.fragment.time_table_new.model

import com.google.gson.annotations.SerializedName

class TimeTableApiListModelNew (
    @SerializedName("id") val id: Int,
    @SerializedName("period_id") val period_id: Int,
    @SerializedName("subject_name") val subject_name: String,
    @SerializedName("staff") val staff: String,
    @SerializedName("student_id") val student_id: Int,
    @SerializedName("day") val day: String,
    @SerializedName("sortname") val sortname: String,
    @SerializedName("starttime") val starttime: String,
    @SerializedName("endtime") val endtime: String,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("updated_at") val updated_at: String
)