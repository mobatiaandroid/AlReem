package com.nas.alreem.activity.staff_directory.model

import com.google.gson.annotations.SerializedName

class ListStaffDetailModel (
    @SerializedName("status")var status:Int,
    @SerializedName("responseArray")var responseArray:StaffDetailResponseArrayModel
)