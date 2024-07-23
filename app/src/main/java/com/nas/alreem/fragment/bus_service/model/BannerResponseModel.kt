package com.nas.alreem.fragment.bus_service.model

import com.google.gson.annotations.SerializedName

class BannerResponseModel (

    @SerializedName("response") val response: String,
    @SerializedName("statuscode") val statuscode: String,
    @SerializedName("banner_image") val banner_image: String,
    @SerializedName("description") val description: String,
    @SerializedName("contact_email") val contact_email: String,
    @SerializedName("facebookurl") val facebookurl: String )