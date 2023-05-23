package com.nas.alreem.fragment.primary.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class PrimaryDataModel(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("file") val file: ArrayList<PrimaryFileModel>

)
