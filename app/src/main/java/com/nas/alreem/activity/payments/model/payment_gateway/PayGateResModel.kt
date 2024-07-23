package com.nas.alreem.activity.payments.model.payment_gateway

import ccom.nas.alreem.activity.payments.model.payment_gateway.PayGatewayData
import com.google.gson.annotations.SerializedName

class PayGateResModel (
    @SerializedName("order_reference") var order_reference:String,
    @SerializedName("order_paypage_url") var order_paypage_url:String,
    @SerializedName("authorization") var authorization:String,
    @SerializedName("data") var data: PayGatewayData,
    @SerializedName("order_id") var order_id:Int
)