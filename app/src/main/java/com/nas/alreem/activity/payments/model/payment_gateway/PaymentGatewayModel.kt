package com.nas.alreem.activity.payments.model.payment_gateway

import com.google.gson.annotations.SerializedName

class PaymentGatewayModel (
    @SerializedName("status") var status:Int,
    @SerializedName("responseArray") var responseArray:PayGateResModel
)