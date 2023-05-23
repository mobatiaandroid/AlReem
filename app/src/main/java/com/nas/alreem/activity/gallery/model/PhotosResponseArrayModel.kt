package com.nas.alreem.activity.gallery.model

import com.google.gson.annotations.SerializedName

class PhotosResponseArrayModel(
    @SerializedName("images") var images:ArrayList<PhotosModel>
)