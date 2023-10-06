package com.nas.alreem.activity.cca.model

import com.google.gson.annotations.SerializedName
import com.nas.alreem.fragment.cca.model.BannerResponseArrayDataModel

class CCaInformationList {
    @SerializedName("id") val id:Int=0
    @SerializedName("submenu") val title:String=""
    @SerializedName("file") val url: String=""
    @SerializedName("type") val type: String=""
}