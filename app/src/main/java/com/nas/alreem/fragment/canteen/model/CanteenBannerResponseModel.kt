package com.nas.alreem.fragment.canteen.model

import com.google.gson.annotations.SerializedName

class CanteenBannerResponseModel(
    @SerializedName("status") var status:Int,
    @SerializedName("responseArray") var responseArray: CanteenBannerResponseArrayModel
)