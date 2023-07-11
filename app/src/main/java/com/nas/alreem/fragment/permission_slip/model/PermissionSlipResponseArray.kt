package com.nas.alreem.fragment.permission_slip.model

import com.google.gson.annotations.SerializedName

class PermissionSlipResponseArray (
    @SerializedName("permission_slips") val request: ArrayList<PermissionSlipListModel>
)