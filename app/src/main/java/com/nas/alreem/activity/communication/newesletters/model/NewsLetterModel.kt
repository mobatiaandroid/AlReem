package com.nas.alreem.activity.communication.newesletters.model

import com.google.gson.annotations.SerializedName

class NewsLetterModel {
    @SerializedName("id") lateinit var id: String
    @SerializedName("name") lateinit var name: String
}