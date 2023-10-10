package com.nas.alreem.activity.cca.model

import com.google.gson.annotations.SerializedName

class CCAReservetRequestModel (
    @SerializedName("student_id") var student_id: String,
    @SerializedName("cca_days_id") var cca_days_id: String,
    @SerializedName("cca_days_details_id") var cca_days_details_ids: String
)