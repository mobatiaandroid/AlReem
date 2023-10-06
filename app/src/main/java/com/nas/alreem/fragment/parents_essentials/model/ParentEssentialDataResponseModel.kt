package com.nas.alreem.fragment.parents_essentials.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ParentEssentialDataResponseModel (
    @SerializedName("banner_image") val banner_image: String,
    @SerializedName("contact_email") val contact_email: String,
    @SerializedName("description") val description: String,

)