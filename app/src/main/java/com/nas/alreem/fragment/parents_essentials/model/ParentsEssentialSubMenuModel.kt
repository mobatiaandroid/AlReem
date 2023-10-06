package com.nas.alreem.fragment.parents_essentials.model

import com.google.gson.annotations.SerializedName
import com.nas.alreem.fragment.primary.model.PrimaryFileModel
import java.io.Serializable

class ParentsEssentialSubMenuModel (
    @SerializedName("submenu") val submenu: String,
    @SerializedName("filename") val filename: String

):Serializable
