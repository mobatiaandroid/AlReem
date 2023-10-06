package com.nas.alreem.activity.parent_meetings.model

import com.google.gson.annotations.SerializedName

class PtaInsertApiModel (
    @SerializedName("student_id")var student_id:String,
    @SerializedName("pta_time_slot_id")var pta_time_slot_id:Int,
    @SerializedName("staff_id")var staff_id:String
)