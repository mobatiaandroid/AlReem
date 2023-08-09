package com.nas.alreem.fragment.about_us.model

import com.google.gson.annotations.SerializedName
import com.nas.alreem.fragment.contact_us.model.ContactUsModel

class AboutUsResponseArrayModel   (
    @SerializedName("banner_image") val banner_image:String="",
    @SerializedName("description") val description:String="",
    @SerializedName("contact_email") val contact_email:String="",
    @SerializedName("website_link") val website_link:String="",
    @SerializedName("data") var data:ArrayList<AboutUsDataModel>
        )


