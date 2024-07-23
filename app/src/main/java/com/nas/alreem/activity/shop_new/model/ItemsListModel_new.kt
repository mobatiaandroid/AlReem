package com.nas.alreem.activity.shop_new.model

import com.google.gson.annotations.SerializedName
import com.nas.alreem.activity.canteen.model.add_orders.ItemsResponseModel

class ItemsListModel_new (
    @SerializedName("status") var status:Int,
    @SerializedName("responseArray") var responseArray: ItemsResponseModelNew
)