package com.nas.alreem.fragment.parents_essentials.model

import com.google.gson.annotations.SerializedName
import com.nas.alreem.fragment.primary.model.PrimaryFileModel
import java.io.Serializable

class ParentsEssentialDataModel(
    @SerializedName("name") val name: String,
    @SerializedName("banner_image") val banner_image: String,
    @SerializedName("contact_email") val contact_email: String,
    @SerializedName("description") val description: String,
    @SerializedName("submenu") val submenu: ArrayList<ParentsEssentialSubMenuModel>

):Serializable
