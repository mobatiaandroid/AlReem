package com.nas.alreem.fragment.payments.model

import com.google.gson.annotations.SerializedName
import com.nas.alreem.fragment.primary.model.PrimaryResponseArrayModel

class PaymentResponseModel {
    @SerializedName("status") val status:Int=0
    @SerializedName("responseArray") val responseArray: PaymentResponseArrayModel? =null
}