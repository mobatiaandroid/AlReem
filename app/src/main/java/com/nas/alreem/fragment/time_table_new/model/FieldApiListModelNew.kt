package com.nas.alreem.fragment.time_table_new.model

import com.google.gson.annotations.SerializedName

class FieldApiListModelNew (
    @SerializedName("sortname") val sortname: String,
    @SerializedName("starttime") val starttime: String,
    @SerializedName("endtime") val endtime: String
)