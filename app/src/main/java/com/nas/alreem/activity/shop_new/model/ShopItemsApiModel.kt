package com.nas.alreem.activity.shop_new.model

import com.google.gson.annotations.SerializedName

class ShopItemsApiModel (
    @SerializedName("studentId") var studentId:String,
    @SerializedName("categoryId") var category_id:String,
)