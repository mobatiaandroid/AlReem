package com.nas.alreem.activity.permission_slip.model

import com.google.gson.annotations.SerializedName

class PermissionResApiModel (

    @SerializedName("permission_slip_id") val permissionSlipId: Int,
    @SerializedName("student_id") val student_id: String,
    @SerializedName("status") val status: String,
    @SerializedName("device_type") val deviceType: String,
    @SerializedName("device_name") val deviceName: String,
    @SerializedName("app_version") val appVersion: String
)