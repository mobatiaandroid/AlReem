package com.nas.alreem.activity.payments.model

import com.google.gson.annotations.SerializedName

data class StudentListModel (
    @SerializedName("status") val status: Int,
    @SerializedName("responseArray") val responseArray: ResponseArray

)



