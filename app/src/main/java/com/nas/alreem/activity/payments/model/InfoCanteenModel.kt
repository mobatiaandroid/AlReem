package com.nas.alreem.activity.payments.model

import com.google.gson.annotations.SerializedName

class InfoCanteenModel (
    @SerializedName("status") var status:Int,
    @SerializedName("responseArray") var responseArray:InfoResponseModel
)