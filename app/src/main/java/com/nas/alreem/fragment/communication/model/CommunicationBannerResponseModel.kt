package com.nas.alreem.fragment.communication.model

import com.google.gson.annotations.SerializedName

data class CommunicationBannerResponseModel(
    @SerializedName("status")
    val status: String,
    @SerializedName("responseArray")
    val responseArray: ResponseArray
) {
    data class ResponseArray(
        @SerializedName("response")
        val response: String,
        @SerializedName("statuscode")
        val statusCode: String,
        @SerializedName("banner_image")
        val bannerImage: String,
        @SerializedName("description")
        val description: String,
        @SerializedName("contact_email")
        val contactEmail: String,
        @SerializedName("whole_school_coming_up_badge")
        val wholeSchoolComingUpBadge: String,
        @SerializedName("whole_school_coming_up_edited_badge")
        val wholeSchoolComingUpEditedBadge: String
    )
}


