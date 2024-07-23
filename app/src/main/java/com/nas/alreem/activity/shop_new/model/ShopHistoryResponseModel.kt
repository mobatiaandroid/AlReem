package com.nas.alreem.activity.shop_new.model

import com.google.gson.annotations.SerializedName
import com.nas.alreem.activity.lost_card.model.ResponseHistory

class ShopHistoryResponseModel (
    @SerializedName("status") val status: Int,
    @SerializedName("responseArray") val response: ResponseShopHistory
)