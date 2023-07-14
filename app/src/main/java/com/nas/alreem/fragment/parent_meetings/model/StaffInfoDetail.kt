package com.nas.alreem.fragment.parent_meetings.model

import com.google.gson.annotations.SerializedName

class StaffInfoDetail (
    @SerializedName("name")var name:String,
    @SerializedName("image_url")var staff_photo:String,
    @SerializedName("id")var id:Int
)