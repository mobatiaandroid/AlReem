package com.nas.alreem.activity.shop_new.model

import com.google.gson.annotations.SerializedName

class ShopCartUpdateApiModel (
    @SerializedName("studentId") var studentId:String,
    @SerializedName("quantity") var quantity:String,
    @SerializedName("item_id") var item_id:String,
    @SerializedName("shop_cart_id") var shop_cart_id:String
)