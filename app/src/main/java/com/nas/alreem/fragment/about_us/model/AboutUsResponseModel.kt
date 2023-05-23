package com.nas.alreem.fragment.about_us.model

import com.google.gson.annotations.SerializedName
import com.nas.alreem.fragment.contact_us.model.ContactUsResponseArrayModel

class AboutUsResponseModel {
    @SerializedName("status") val status:Int=0
    @SerializedName("responseArray") val responseArray: AboutUsResponseArrayModel? =null
}