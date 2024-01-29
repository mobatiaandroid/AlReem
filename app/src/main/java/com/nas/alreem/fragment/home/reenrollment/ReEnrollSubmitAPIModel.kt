package com.nas.alreem.fragment.home.reenrollment

import com.google.gson.annotations.SerializedName


class ReEnrollSubmitAPIModel(json: ArrayList<ReEnrollSubmitModel>) {
    @SerializedName("enrollment_data")
    private val json: ArrayList<ReEnrollSubmitModel>

    init {
        this.json = json
    }

    fun getJson(): ArrayList<ReEnrollSubmitModel> {
        return json
    }
}
