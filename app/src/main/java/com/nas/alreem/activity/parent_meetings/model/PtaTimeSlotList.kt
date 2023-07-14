package com.nas.alreem.activity.parent_meetings.model

import com.google.gson.annotations.SerializedName

class PtaTimeSlotList(
    @SerializedName("parent_evening_id") val parent_evening_id: Int,
    @SerializedName("slot_id") val slot_id: Int,
    @SerializedName("meeting_date") val date: String,
    @SerializedName("slot_start_time") var start_time: String,
    @SerializedName("slot_end_time") val end_time: String,
    @SerializedName("book_end_date") val book_end_date: String,
    @SerializedName("room") val room: String,
    @SerializedName("vpml") val vpml: String,
    @SerializedName("status") val status: String,
    @SerializedName("booking_status") val booking_open: String,
    @SerializedName("isSelected") var isSelected: Boolean


)