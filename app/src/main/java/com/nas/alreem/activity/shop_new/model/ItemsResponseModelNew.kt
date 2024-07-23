package com.nas.alreem.activity.shop_new.model

import com.google.gson.annotations.SerializedName
import com.nas.alreem.activity.canteen.model.CatItemsListModel_new
import com.nas.alreem.activity.canteen.model.add_orders.CatItemsListModel

class ItemsResponseModelNew (
    @SerializedName("data") var data:ArrayList<CatItemsListModel>
)