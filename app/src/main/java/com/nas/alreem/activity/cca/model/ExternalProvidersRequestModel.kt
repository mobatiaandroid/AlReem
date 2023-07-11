package com.nas.alreem.activity.cca.model

import com.google.gson.annotations.SerializedName

class ExternalProvidersRequestModel (
    @SerializedName("start") val start: String,
    @SerializedName("limit") val limit: String
)
