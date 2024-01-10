package com.nas.alreem.fragment.intention.model

import com.google.gson.annotations.SerializedName

class IntentionstatusResponseArray (
    @SerializedName("id") val id: Int,
    @SerializedName("intension_id") val intension_id: Int,
    @SerializedName("parent_id") val parent_id: Int,
    @SerializedName("student_id") val student_id: String,
    @SerializedName("class_id") val class_id: Int,
    @SerializedName("selected_options") val selected_options:String,
    @SerializedName("description") val description: String,
    @SerializedName("status") val status: String
)