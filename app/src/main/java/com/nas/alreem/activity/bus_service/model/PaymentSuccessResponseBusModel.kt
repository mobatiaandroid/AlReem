package com.nas.alreem.activity.bus_service.model

import com.google.gson.annotations.SerializedName

class PaymentSuccessResponseBusModel (
    @SerializedName("status") var status:Int,
    @SerializedName("payment_details") var responseArray:InvoiceSucesssModel,
)