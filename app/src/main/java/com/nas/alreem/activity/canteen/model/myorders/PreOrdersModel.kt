package com.nas.alreem.activity.canteen.model.myorders

import com.google.gson.annotations.SerializedName

class PreOrdersModel (
    @SerializedName("status") var status:Int,
    @SerializedName("responseArray") var responseArray:PreOrdersResponseModel
)