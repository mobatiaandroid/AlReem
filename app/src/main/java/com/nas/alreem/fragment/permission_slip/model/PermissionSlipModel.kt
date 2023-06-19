package com.nas.alreem.fragment.permission_slip.model

import com.google.gson.annotations.SerializedName

class PermissionSlipModel (
    @SerializedName("status") val status: Int,
    @SerializedName("responseArray") val responseArray: PermissionSlipResponseArray

)