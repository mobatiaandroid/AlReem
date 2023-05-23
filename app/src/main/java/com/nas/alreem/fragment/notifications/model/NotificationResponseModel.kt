package com.nas.alreem.fragment.notifications.model

import com.google.gson.annotations.SerializedName
import com.nas.alreem.activity.login.model.LoginResponseArrayModel

class NotificationResponseModel {
    @SerializedName("status") val status:Int=0
    @SerializedName("responseArray") val responseArray: NotificationResponseArrayModel? =null
}