package com.nas.alreem.fragment.reports.model

import com.google.gson.annotations.SerializedName

class ReportListDetailModel(
    @SerializedName("id") val id: Int,
    @SerializedName("reporting_cycle") val report_cycle: String,
    @SerializedName("updated_at") val updated_at: String,
    @SerializedName("file") val file: String,
    @SerializedName("viewreport") val viewreport: String,
    @SerializedName("status") var read_unread_status: String

    )