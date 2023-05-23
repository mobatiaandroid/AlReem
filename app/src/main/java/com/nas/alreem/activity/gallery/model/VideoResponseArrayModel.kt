package com.nas.alreem.activity.gallery.model

import com.google.gson.annotations.SerializedName

class VideoResponseArrayModel (
    @SerializedName("videos") var videos:ArrayList<VideoModel>
)