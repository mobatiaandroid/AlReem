package com.nas.alreem.fragment.communication.model

import com.google.gson.annotations.SerializedName
import com.nas.alreem.activity.communication.socialmedia.model.SocialMediaResponseArrayModel

class SocialMediaResponseModel {
    @SerializedName("status") val status:Int=0
    @SerializedName("responseArray") val responseArray: SocialMediaResponseArrayModel? =null
}