package com.nas.alreem.fragment.reports.model

import com.google.gson.annotations.SerializedName


class ReportResponseArray (

    @SerializedName("survey") val survey: Int,
    //    @SerializedName("data") val data: ReportListDetailModel

    @SerializedName("data") val data: ArrayList<ReportDetailModel>

)