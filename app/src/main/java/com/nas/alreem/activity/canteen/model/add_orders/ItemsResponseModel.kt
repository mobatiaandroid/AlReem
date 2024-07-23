package com.nas.alreem.activity.canteen.model.add_orders

import com.google.gson.annotations.SerializedName
import com.nas.alreem.activity.canteen.model.CatItemsListModel_new

class ItemsResponseModel (
    @SerializedName("data") var data:ArrayList<CatItemsListModel_new>
)