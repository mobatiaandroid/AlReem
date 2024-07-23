package com.nas.alreem.fragment.time_table.model.apimodel

import com.google.gson.annotations.SerializedName

data class FieldApiListModel (
    @SerializedName("period_name") val sortname: String,
    @SerializedName("start_time") val starttime: String,
    @SerializedName("end_time") val endtime: String
)