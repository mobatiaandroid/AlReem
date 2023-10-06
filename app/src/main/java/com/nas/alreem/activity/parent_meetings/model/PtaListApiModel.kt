package com.nas.alreem.activity.parent_meetings.model

import com.google.gson.annotations.SerializedName

class PtaListApiModel (
    @SerializedName("student_id") val student_id: String,
    @SerializedName("staff_id") val staff_id: String,
    @SerializedName("date") val date:String

)