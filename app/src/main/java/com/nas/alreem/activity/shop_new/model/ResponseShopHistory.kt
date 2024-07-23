package com.nas.alreem.activity.shop_new.model

import com.google.gson.annotations.SerializedName
import com.nas.alreem.activity.payment_history.PaymentWalletHistoryModel

class ResponseShopHistory (
    @SerializedName("order_history") val order_history: ArrayList<PaymentShopWalletHistoryModel>
)