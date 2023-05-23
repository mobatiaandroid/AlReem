package com.nas.alreem.activity.gallery.model

import com.google.gson.annotations.SerializedName

class VideoResponseModel {
    @SerializedName("status") val status:Int=0
    @SerializedName("responseArray") val responseArray: VideoResponseArrayModel? =null
}