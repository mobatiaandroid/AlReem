package com.nas.alreem.activity.shop_new.model

import com.google.gson.annotations.SerializedName

class ResponseShopItemHistory (
    @SerializedName("data") val data: ArrayList<ShopItemHistoryModel>

)