package com.nas.alreem.activity.communication.newesletters.model

import com.google.gson.annotations.SerializedName
import com.nas.alreem.fragment.communication.model.CommunicationResponseArrayModel

class NewsletterResponseModel {
    @SerializedName("status") val status:Int=0
    @SerializedName("responseArray") val responseArray: NewsletterResponseArrayModel? =null
}