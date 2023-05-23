package com.nas.alreem.fragment.about_us.model

import com.google.gson.annotations.SerializedName

class AboutUsDataModel  {
    @SerializedName("id") val id:Int=0
    @SerializedName("name") val name:String=""
    @SerializedName("tab_type") val tab_type:String=""
    @SerializedName("url") val url:String=""
    @SerializedName("title") val title:String=""
    @SerializedName("created_at") val created_at:String=""

}