package com.nas.alreem.fragment.calendar.model

import com.google.gson.annotations.SerializedName

class CalendarDetailsModel {
    @SerializedName("id") var id: Int=0
    @SerializedName("start_time") var starttime: String=""
    @SerializedName("end_time") var endtime: String=""
    @SerializedName("title") var title: String=""
    @SerializedName("isAllday") var isAllday: String=""
    @SerializedName("vpml") var vpml: String=""
    @SerializedName("status") var status: String=""
}

