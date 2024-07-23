package com.nas.alreem.activity.communication.socialmedia.model

import com.google.gson.annotations.SerializedName
import com.nas.alreem.fragment.communication.model.CommunicationDataModel

class SocialMediaResponseArrayModel {
    @SerializedName("banner_image") val banner_image:String?=null
    @SerializedName("data") val data:ArrayList<SocialMediaModel>?=null
}