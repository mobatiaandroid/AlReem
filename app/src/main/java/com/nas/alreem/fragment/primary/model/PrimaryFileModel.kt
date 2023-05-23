package com.nas.alreem.fragment.primary.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class PrimaryFileModel(
    @SerializedName("id") val id: Int,
    @SerializedName("file") val file: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String

): Serializable
