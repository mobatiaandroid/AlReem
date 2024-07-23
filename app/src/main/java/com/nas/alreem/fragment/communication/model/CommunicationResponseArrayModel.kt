package com.nas.alreem.fragment.communication.model

import com.google.gson.annotations.SerializedName
import com.nas.alreem.fragment.primary.model.PrimaryDataModel

class CommunicationResponseArrayModel {
    @SerializedName("banner_image") val banner_image:String?=null
    @SerializedName("data") val data:ArrayList<CommunicationDataModel>?=null
}