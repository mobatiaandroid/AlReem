package com.nas.alreem.activity.communication.commingup.model

import com.google.gson.annotations.SerializedName


data class ComingUpResponseModel(
    @SerializedName("status")
    val status: Int,
    @SerializedName("responseArray")
    val responseArray: ResponseArray
) {
    data class ResponseArray(
        @SerializedName("data")
        val data: ArrayList<ComingUpItem>
    )

    data class ComingUpItem(
        @SerializedName("id")
        val id: Int,
        @SerializedName("title")
        val title: String,
        @SerializedName("description")
        val description: String,
        @SerializedName("date")
        val date: String,
        @SerializedName("image")
        val image: String,
        @SerializedName("status")
        var status: String,
        @SerializedName("read_unread_status")
    var read_unread_status: String
    )
}

