package com.nas.alreem.activity.communication.information.model

import com.google.gson.annotations.SerializedName

data class InformationResponseModel(
    @SerializedName("status")
    val status: Int,
    @SerializedName("responseArray")
    val responseArray: ResponseArray
) {
    data class ResponseArray(
        @SerializedName("banner_image")
        val bannerImage: String,
        @SerializedName("data")
        val data: ArrayList<CommunicationInfo>
    )

    data class CommunicationInfo(
        @SerializedName("id")
        val id: Int,
        @SerializedName("submenu")
        val submenu: String,
        @SerializedName("filename")
        val filename: String?, // Use nullable type for optional fields
        @SerializedName("description")
        val description: String
    )

}

