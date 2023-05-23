package com.nas.alreem.activity.primary.model

import com.google.gson.annotations.SerializedName
import com.nas.alreem.fragment.primary.model.PrimaryResponseArrayModel

class ComingUpResponseModel {
    @SerializedName("status") val status:Int=0
    @SerializedName("responseArray") val responseArray: ComingUpResponseArrayModel? =null
}