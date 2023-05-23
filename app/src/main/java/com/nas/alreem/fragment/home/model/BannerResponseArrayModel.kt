package com.nas.alreem.fragment.home.model

import com.google.gson.annotations.SerializedName
import com.nas.alreem.fragment.notifications.model.NotificationModel

class BannerResponseArrayModel {

    @SerializedName("android_app_version") val android_app_version:String=""
    @SerializedName("banner_images") val banner_images:ArrayList<String>?=null
}