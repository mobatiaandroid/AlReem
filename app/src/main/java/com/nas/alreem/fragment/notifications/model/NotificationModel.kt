package com.nas.alreem.fragment.notifications.model

import com.google.gson.annotations.SerializedName

class NotificationModel  (
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("alert_type") val alert_type: String,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("read_unread_status") var read_unread_status: String
)
