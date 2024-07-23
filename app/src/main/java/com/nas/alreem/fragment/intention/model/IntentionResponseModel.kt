package com.nas.alreem.fragment.intention.model

import com.google.gson.annotations.SerializedName
import com.nas.alreem.fragment.student_information.model.StudentInfoResponseArray

class IntentionResponseModel (
    @SerializedName("status") val status: Int,
    @SerializedName("responseArray") val responseArray: IntentionInfoArray
)