package com.nas.alreem.fragment.time_table.model.apimodel

import com.google.gson.annotations.SerializedName

data class TimeTableApiListModel (
    @SerializedName("id") val id: Int,
    @SerializedName("period_order") val period_id: Int,
    @SerializedName("subject_name") val subject_name: String,
    @SerializedName("staff") val staff: String,
    @SerializedName("day_name") val day: String,
    @SerializedName("period_name") val sortname: String,
    @SerializedName("start_time") val starttime: String,
    @SerializedName("end_time") val endtime: String,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("updated_at") val updated_at: String,
    @SerializedName("is_break") var is_break: Int
)