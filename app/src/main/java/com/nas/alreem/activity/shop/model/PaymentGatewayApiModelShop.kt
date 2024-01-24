package com.nas.alreem.activity.shop.model

import com.google.gson.annotations.SerializedName

class PaymentGatewayApiModelShop (
    @SerializedName("amount") var amount:String,
    @SerializedName("emailAddress") var emailAddress:String,
    @SerializedName("merchantOrderReference") var merchantOrderReference:String,
    @SerializedName("firstName") var firstName:String,
    @SerializedName("lastName") var lastName:String,
    @SerializedName("address1") var address1:String,
    @SerializedName("city") var city:String,
    @SerializedName("countryCode") var countryCode:String,
    @SerializedName("access_token") var access_token:String,
    @SerializedName("payment_module") var payment_module:String,
    @SerializedName("shop_cart_id") var shop_cart_id:String,
    @SerializedName("studentId") var studentId:String
)