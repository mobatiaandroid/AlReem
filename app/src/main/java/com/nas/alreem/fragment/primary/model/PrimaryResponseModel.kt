package com.nas.alreem.fragment.primary.model

import com.google.gson.annotations.SerializedName
import com.nas.alreem.fragment.notifications.model.NotificationResponseArrayModel

class PrimaryResponseModel{
    @SerializedName("status") val status:Int=0
    @SerializedName("responseArray") val responseArray: PrimaryResponseArrayModel? =null
}