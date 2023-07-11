package com.nas.alreem.fragment.cca.model

import com.google.gson.annotations.SerializedName

class BannerResponseArrayDataModel {
    @SerializedName("banner_image") val banner_image:String=""
    @SerializedName("description") val description:String=""
    @SerializedName("contact_email") val contact_email:String=""
    @SerializedName("cca_badge") val cca_badge:Int=0
    @SerializedName("cca_edited_badge") val cca_edited_badge:Int=0
}