package com.nas.alreem.fragment.permission_slip.model

import com.google.gson.annotations.SerializedName

class GeneralSlipResponseArray (
    @SerializedName("general_forms") val request: ArrayList<PermissionSlipListModel>

)