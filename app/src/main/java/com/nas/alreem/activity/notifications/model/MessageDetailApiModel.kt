package com.nas.alreem.activity.notifications.model

import com.google.gson.annotations.SerializedName

data class MessageDetailApiModel (
    @SerializedName("notification_id") val notification_id: String
)