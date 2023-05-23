package com.nas.alreem.activity.gallery.model

import com.google.gson.annotations.SerializedName
import com.nas.alreem.fragment.gallery.model.ThumnailImageModel

class AlbumResponseArrayModel (
    @SerializedName("albums") var albums:ArrayList<AlbumImageModel>
)
