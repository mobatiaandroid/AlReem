package com.nas.alreem.activity.gallery.model

import com.google.gson.annotations.SerializedName

class AlbumApiModel (
    @SerializedName("page_from") val page_from: String,
    @SerializedName("scroll_to") val scroll_to: String
)
