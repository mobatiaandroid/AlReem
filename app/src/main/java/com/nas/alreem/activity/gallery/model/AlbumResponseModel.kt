package com.nas.alreem.activity.gallery.model

import com.google.gson.annotations.SerializedName
import com.nas.alreem.fragment.gallery.model.ThumnailResponseArrayModel

class AlbumResponseModel  {
    @SerializedName("status") val status:Int=0
    @SerializedName("responseArray") val responseArray: AlbumResponseArrayModel? =null
}