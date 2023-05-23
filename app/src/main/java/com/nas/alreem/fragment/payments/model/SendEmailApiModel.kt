package com.nas.alreem.fragment.payments.model

import com.google.gson.annotations.SerializedName

class SendEmailApiModel(
    @SerializedName("staff_email") val staff_email: String,
    @SerializedName("title") val title: String,
    @SerializedName("message") val message: String
)
