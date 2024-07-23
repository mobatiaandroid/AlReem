package com.nas.alreem.fragment.intention.model

import com.google.gson.annotations.SerializedName

class IntentionstatusResponseArray (
    @SerializedName("id") val id: Int,
    @SerializedName("intension_id") val intension_id: Int,
    @SerializedName("parent_id") val parent_id: Int,
    @SerializedName("student_id") val student_id: String,
    @SerializedName("className") val className: String,
    @SerializedName("selected_options") val selected_options:String,
    @SerializedName("selected_option_answer") val selected_option_answer:String,
    @SerializedName("description") val description: String,
    @SerializedName("status") val status: String
)