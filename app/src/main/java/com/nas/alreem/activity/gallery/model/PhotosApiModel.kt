package com.nas.alreem.activity.gallery.model

import com.google.gson.annotations.SerializedName

class PhotosApiModel (
    @SerializedName("page_from") val page_from: String,
    @SerializedName("album_id") val album_id: String,
    @SerializedName("scroll_to") val scroll_to: String
)