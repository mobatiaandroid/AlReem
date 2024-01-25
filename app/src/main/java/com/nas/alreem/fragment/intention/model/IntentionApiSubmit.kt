package com.nas.alreem.fragment.intention.model

import com.google.gson.annotations.SerializedName

class IntentionApiSubmit (
    @SerializedName("student_id") val student_id: String,
    @SerializedName("intension_id") val intension_id: String,
    @SerializedName("device_type") val device_type: String,
    @SerializedName("device_name") val device_name: String,
    @SerializedName("app_version") val app_version: String,
    @SerializedName("selected_option") val selected_options: String,
    @SerializedName("selected_option_answer") val selected_option_answer: String




)