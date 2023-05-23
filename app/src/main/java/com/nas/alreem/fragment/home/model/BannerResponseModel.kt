package com.nas.alreem.fragment.home.model

import com.google.gson.annotations.SerializedName
import com.nas.alreem.fragment.notifications.model.NotificationResponseArrayModel

class BannerResponseModel {
    @SerializedName("status") val status:Int=0
    @SerializedName("responseArray") val responseArray: BannerResponseArrayModel? =null
}