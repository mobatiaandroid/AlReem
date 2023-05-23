package com.nas.alreem.fragment.notifications.model

import com.google.gson.annotations.SerializedName

class NotificationResponseArrayModel {

    @SerializedName("notifications") val notifications:ArrayList<NotificationModel>?=null
}