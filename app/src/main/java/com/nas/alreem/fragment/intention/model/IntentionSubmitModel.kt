package com.nas.alreem.fragment.intention.model

import com.google.gson.annotations.SerializedName

class IntentionSubmitModel (
    @SerializedName("student_id") var student_id: String,
    @SerializedName("status") var status: String
)