package com.nas.alreem.fragment.payments.model

import com.google.gson.annotations.SerializedName
import com.nas.alreem.fragment.primary.model.PrimaryFileModel

class PaymentDataModel(
    @SerializedName("id") val id: String,
    @SerializedName("image") val image: String,
    @SerializedName("description") val description: String,
    @SerializedName("contact_email") val contact_email: String

)
