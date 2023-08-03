package com.nas.alreem.fragment.about_us.model

import com.google.gson.annotations.SerializedName

class AboutUsDataModel  (
    @SerializedName("id") val id:Int=0,
    @SerializedName("tab_type") val tab_type:String="",
@SerializedName("url") val url:String="",
@SerializedName("image") val image:String="",
@SerializedName("banner_image") val banner_image:String="",
@SerializedName("description") val description:String="",
@SerializedName("items") var items:ArrayList<AboutusList>
        )



