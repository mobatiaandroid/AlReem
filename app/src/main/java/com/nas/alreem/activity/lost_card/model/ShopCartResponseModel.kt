package com.nas.alreem.activity.lost_card.model

import com.google.gson.annotations.SerializedName
import com.nas.alreem.activity.canteen.model.canteen_cart.CanteenCartResModel

class ShopCartResponseModel (
    @SerializedName("total_amount") val total_amount: Int,
    @SerializedName("data") var data:ArrayList<ShopCartResModel>
)