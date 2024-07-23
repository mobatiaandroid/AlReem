package com.nas.alreem.activity.shop_new.model

import com.google.gson.annotations.SerializedName

class ShopCartRemoveApiModel (
    @SerializedName("studentId") var studentId:String,
    @SerializedName("shop_cart_id") var shop_cart_id:String
)