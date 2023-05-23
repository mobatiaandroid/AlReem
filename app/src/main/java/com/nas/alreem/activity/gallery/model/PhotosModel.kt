package com.nas.alreem.activity.gallery.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class PhotosModel:Serializable  {
    @SerializedName("id") val id:Int=0
    @SerializedName("image") val image:String=""
    @SerializedName("title") val title:String=""
    @SerializedName("description") val description:String=""

}