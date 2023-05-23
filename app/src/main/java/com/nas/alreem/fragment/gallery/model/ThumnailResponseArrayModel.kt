package com.nas.alreem.fragment.gallery.model

import com.google.gson.annotations.SerializedName
import com.nas.alreem.fragment.about_us.model.AboutUsDataModel

class ThumnailResponseArrayModel (
    @SerializedName("albums") var image:ArrayList<ThumnailImageModel>,
    @SerializedName("videos") var video:ArrayList<ThumnailImageModel>
)
