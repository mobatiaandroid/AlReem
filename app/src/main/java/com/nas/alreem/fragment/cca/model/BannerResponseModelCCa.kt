package com.nas.alreem.fragment.cca.model

import com.google.gson.annotations.SerializedName
import com.nas.alreem.fragment.home.model.BannerResponseArrayModel

class BannerResponseModelCCa {
    @SerializedName("status") val status:Int=0
    @SerializedName("message") val message:String=""
    @SerializedName("data") val data: BannerResponseArrayDataModel? =null
}