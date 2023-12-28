package com.nas.alreem.activity.communication.newesletters.model

import com.google.gson.annotations.SerializedName
import com.nas.alreem.activity.communication.newesletters.NewsLetterModel
import com.nas.alreem.fragment.communication.model.CommunicationDataModel

class NewsletterResponseArrayModel {
    @SerializedName("data") val data:ArrayList<NewsLetterModel>?=null
}