package com.nas.alreem.fragment.intention.model

import com.google.gson.annotations.SerializedName

class IntentionInfoResponseArray (
    @SerializedName("intension_id") val intension_id: Int,
    @SerializedName("class") val classs: String,
    @SerializedName("student_name") val student: String,
    @SerializedName("title") val title: String,
    @SerializedName("question") val question: String,
    @SerializedName("options") val options:ArrayList<String>,
    @SerializedName("description") val description: String,
    @SerializedName("status") val status: String )