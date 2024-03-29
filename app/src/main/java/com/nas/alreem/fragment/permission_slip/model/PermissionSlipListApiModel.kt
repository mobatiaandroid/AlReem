package com.nas.alreem.fragment.permission_slip.model

import com.google.gson.annotations.SerializedName

class PermissionSlipListApiModel (
    @SerializedName("start") val start: String,
    @SerializedName("limit") val limit: String,
    @SerializedName("student_id") val student_id: String
        )