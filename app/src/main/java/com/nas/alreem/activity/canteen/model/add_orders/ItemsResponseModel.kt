package com.nas.alreem.activity.canteen.model.add_orders

import com.google.gson.annotations.SerializedName

class ItemsResponseModel (
    @SerializedName("data") var data:ArrayList<CatItemsListModel>
)