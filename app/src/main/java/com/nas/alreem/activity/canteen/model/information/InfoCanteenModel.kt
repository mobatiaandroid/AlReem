package com.nas.alreem.activity.canteen.model.information

import com.google.gson.annotations.SerializedName

class InfoCanteenModel (
    @SerializedName("status") var status:Int,
    @SerializedName("responseArray") var responseArray:InfoResponseModel
)