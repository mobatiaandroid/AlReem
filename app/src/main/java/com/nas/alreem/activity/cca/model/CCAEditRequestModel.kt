package com.nas.alreem.activity.cca.model

import com.google.gson.annotations.SerializedName

class CCAEditRequestModel (
    @SerializedName("student_id") var student_id: String,
    @SerializedName("cca_days_id") var cca_days_id: String,
    @SerializedName("cca_update_data") var cca_update_data: String

)