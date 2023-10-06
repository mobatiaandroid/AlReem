package com.nas.alreem.activity.cca.model

import com.google.gson.annotations.SerializedName
import com.nas.alreem.fragment.cca.model.BannerResponseArrayDataModel

class CCAInfoResponseModel(
    @SerializedName("status") val status:Int,
    @SerializedName("data") val data: ArrayList<CCaInformationList>
)