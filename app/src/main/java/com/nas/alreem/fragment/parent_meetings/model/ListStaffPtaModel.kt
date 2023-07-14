package com.nas.alreem.fragment.parent_meetings.model

import com.google.gson.annotations.SerializedName

class ListStaffPtaModel(
    @SerializedName("status")var status:Int,
    @SerializedName("message")var message:String,
    @SerializedName("data")var data:ArrayList<StaffInfoDetail>
)