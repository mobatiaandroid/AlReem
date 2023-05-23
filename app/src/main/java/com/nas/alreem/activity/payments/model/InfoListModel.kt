package com.nas.alreem.activity.payments.model

import com.google.gson.annotations.SerializedName

class InfoListModel (
    @SerializedName("id") var id:String,
    @SerializedName("title") var title:String,
    @SerializedName("file") var file:String
)