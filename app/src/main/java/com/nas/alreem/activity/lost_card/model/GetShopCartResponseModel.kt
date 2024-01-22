package com.nas.alreem.activity.lost_card.model

import com.google.gson.annotations.SerializedName
import com.nas.alreem.activity.canteen.model.canteen_cart.CanteenCartResponseModel

class GetShopCartResponseModel (
    @SerializedName("status") var status:Int,
    @SerializedName("responseArray") var responseArray: ShopCartResponseModel
)