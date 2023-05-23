package com.nas.alreem.fragment.calendar.model

import com.google.gson.annotations.SerializedName

class CalendarDetailsModel {
    @SerializedName("id") var id: Int=0
    @SerializedName("starttime") var starttime: String=""
    @SerializedName("endtime") var endtime: String=""
    @SerializedName("title") var title: String=""
    @SerializedName("isAllday") var isAllday: String=""
    @SerializedName("vpml") var vpml: String=""
    @SerializedName("status") var status: String=""
}

