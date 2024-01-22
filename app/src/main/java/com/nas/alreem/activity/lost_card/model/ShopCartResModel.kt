package com.nas.alreem.activity.lost_card.model

import com.google.gson.annotations.SerializedName

class ShopCartResModel (
    @SerializedName("id") var id: Int,
    @SerializedName("item_id") val item_id: Int,
    @SerializedName("delivery_date") val delivery_date: String,
    @SerializedName("quantity") var quantity: Int,
    @SerializedName("price") val price: String,
    @SerializedName("item_name") val item_name: String,
    @SerializedName("item_image") val item_image: String,
    @SerializedName("item_total") val item_total: Int,
    @SerializedName("description") val description: String
)