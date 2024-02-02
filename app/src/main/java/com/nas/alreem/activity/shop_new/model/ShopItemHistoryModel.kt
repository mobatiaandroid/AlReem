package com.nas.alreem.activity.shop_new.model

import com.google.gson.annotations.SerializedName

class ShopItemHistoryModel (
    @SerializedName("id") var id:Int,
    @SerializedName("quantity") var quantity:Int,
    @SerializedName("price") var price:String,
    @SerializedName("item_name") var item_name:String,
    @SerializedName("description") var description:String,
    @SerializedName("order_date") var order_date:String,
    @SerializedName("item_image") val item_image: ArrayList<String>
)