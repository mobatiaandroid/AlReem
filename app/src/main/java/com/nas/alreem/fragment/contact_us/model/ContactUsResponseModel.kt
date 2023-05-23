package com.nas.alreem.fragment.contact_us.model

import com.google.gson.annotations.SerializedName
import com.nas.alreem.fragment.primary.model.PrimaryResponseArrayModel

class ContactUsResponseModel {
    @SerializedName("status") val status:Int=0
    @SerializedName("responseArray") val responseArray: ContactUsResponseArrayModel? =null
}