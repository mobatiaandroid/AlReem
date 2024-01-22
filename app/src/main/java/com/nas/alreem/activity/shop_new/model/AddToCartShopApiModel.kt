package com.nas.alreem.activity.shop_new.model

import com.google.gson.annotations.SerializedName

class AddToCartShopApiModel (
    @SerializedName("studentId") var studentId:String,
    @SerializedName("item_id") var item_id:String,
    @SerializedName("quantity") var quantity:String,
    @SerializedName("price") var price:String
)