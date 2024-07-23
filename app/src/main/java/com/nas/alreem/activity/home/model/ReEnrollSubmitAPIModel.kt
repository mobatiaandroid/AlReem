package com.nas.alreem.activity.home.model

import com.google.gson.annotations.SerializedName
import com.nas.alreem.fragment.home.re_enrollment.ReEnrollSubmitModel


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
