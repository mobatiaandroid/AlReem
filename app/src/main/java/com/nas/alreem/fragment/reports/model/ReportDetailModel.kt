package com.nas.alreem.fragment.reports.model

import com.google.gson.annotations.SerializedName

class ReportDetailModel (
    @SerializedName("academic_year") val academic_year: String,
    @SerializedName("data") val data: ArrayList<ReportListDetailModel>

)


