package com.nas.alreem.fragment.communication.model

import com.google.gson.annotations.SerializedName
import com.nas.alreem.activity.primary.model.ComingUpResponseArrayModel

class CommunicationResponseModel {
    @SerializedName("status") val status:Int=0
    @SerializedName("responseArray") val responseArray: CommunicationResponseArrayModel? =null
}