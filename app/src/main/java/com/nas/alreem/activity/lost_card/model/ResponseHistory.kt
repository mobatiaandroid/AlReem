package com.nas.alreem.activity.lost_card.model

import com.google.gson.annotations.SerializedName
import com.nas.alreem.activity.payment_history.PaymentWalletHistoryModel

class ResponseHistory (
    @SerializedName("response") val response: String,
    @SerializedName("statuscode") val statuscode: String,
    @SerializedName("data") val data: ArrayList<PaymentWalletHistoryModel>
)