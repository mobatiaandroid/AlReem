package com.nas.alreem.fragment.home.model

import com.google.gson.annotations.SerializedName

class BannerResponseArrayModel {

    @SerializedName("android_app_version")
    val android_app_version: String = ""
    @SerializedName("notice")
    val notice: BannerResponseNoticeModel = BannerResponseNoticeModel()
    @SerializedName("banner_images")
    val banner_images: ArrayList<String>? = null
    @SerializedName("survey") val survey:Int=0
    @SerializedName("lost_student_card_amount")
    val lost_student_card_amount: String = ""
    @SerializedName("enrollment_status")
    val enrollmentStatus: String = ""
    @SerializedName("bus_note")
    val bus_note: String = ""

}