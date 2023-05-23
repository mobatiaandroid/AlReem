package com.nas.alreem.fragment.notifications.model

import com.google.gson.annotations.SerializedName

class NotificationApiModel  (
    @SerializedName("start") val start: Int,
    @SerializedName("limit") val limit: Int
)
