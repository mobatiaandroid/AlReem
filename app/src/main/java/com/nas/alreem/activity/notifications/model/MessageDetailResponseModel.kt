package com.nas.alreem.activity.notifications.model

import com.google.gson.annotations.SerializedName

data class MessageDetailResponseModel (
    @SerializedName("notification") val notificationArray:MessageDetailNotificationModel
)