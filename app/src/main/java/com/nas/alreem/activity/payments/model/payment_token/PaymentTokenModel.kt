package  com.nas.alreem.activity.payments.model.payment_token

import com.google.gson.annotations.SerializedName

class PaymentTokenModel (
    @SerializedName("status") var status:Int,
    @SerializedName("responseArray") var responseArray:PayTokenResModel
)