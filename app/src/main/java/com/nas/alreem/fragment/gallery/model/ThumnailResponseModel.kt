package com.nas.alreem.fragment.gallery.model

import com.google.gson.annotations.SerializedName
import com.nas.alreem.fragment.about_us.model.AboutUsResponseArrayModel

class ThumnailResponseModel {
    @SerializedName("status") val status:Int=0
    @SerializedName("responseArray") val responseArray: ThumnailResponseArrayModel? =null
}