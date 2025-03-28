package com.nas.alreem.activity.cca.model

data class CCAReviewReservedResponseModel(
    val `data`: ArrayList<Data>?,
    val message: String?,
    val status: Int?,

    ) {

        data class Data(
            val choice: Choice?,
            val choice2:Choice2?,
            val day: String?
        ) {
            data class Choice(
                val absentDays: ArrayList<String>?,
                val cca_item_id: Int?,
                val cca_details_id: Int?,
                val cca_item_end_time: String?,
                val cca_item_name: String?,
                val cca_item_start_time: String?,
                val cca_venue: String?,
                val cca_item_description: String?,
                val day: String?,
                val isAttendee: String?,
                val attending_status: String?

            )

            class Choice2(
                val absentDays: ArrayList<String>?,
                val attending_status: String?,
                val cca_details_id: Int?,
                val cca_item_end_time: String?,
                val cca_item_name: String?,
                val cca_venue: String?,
                val cca_item_description: String?,
                val cca_item_start_time: String?,
                val day: String?,
                val isAttendee: String?,
                val presentDays: ArrayList<String>?,
                val upcomingDays: ArrayList<String>?
            )
        }
    }
