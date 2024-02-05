package com.nas.alreem.activity.communication.socialmedia.model

import com.google.gson.annotations.SerializedName

class SocialMediaModel {
    @SerializedName("id") var id:Int=0
    @SerializedName("tab_type") var tab_type:String=""
    @SerializedName("url") var url:String=""
    @SerializedName("image") var image:String=""
    @SerializedName("pageid") var pageid:String=""

}