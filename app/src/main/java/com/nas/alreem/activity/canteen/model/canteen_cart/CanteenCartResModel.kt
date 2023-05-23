package com.nas.alreem.activity.canteen.model.canteen_cart

import com.google.gson.annotations.SerializedName

class CanteenCartResModel (
    @SerializedName("delivery_date") val delivery_date: String,
    @SerializedName("total_amount") val total_amount: Int,
    @SerializedName("items") val items: ArrayList<CartItemsListModel>
)