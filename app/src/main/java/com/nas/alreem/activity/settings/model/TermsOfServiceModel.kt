package com.nas.alreem.activity.settings.model

import com.google.gson.annotations.SerializedName
import com.nas.alreem.fragment.primary.model.PrimaryFileModel

class TermsOfServiceModel(
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String
)
