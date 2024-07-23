package com.nas.alreem.fragment.home.model

import com.google.gson.annotations.SerializedName


data class BadgeResponseModel(
    @SerializedName("status") val status: Int,
    @SerializedName("responseArray") val responseArray: ResponseArray
){
    data class ResponseArray(
        @SerializedName("badge_counts") val badgeCounts: BadgeCounts
    )

    data class BadgeCounts(
        @SerializedName("response") val response: String,
        @SerializedName("statuscode") val statusCode: String,
        @SerializedName("calendar_badge") val calendarBadge: String,
        @SerializedName("notification_badge") val notificationBadge: String,
        @SerializedName("reports_badge") val reportsBadge: String,
        @SerializedName("cca_badge") val ccaBadge: String,
        @SerializedName("calendar_edited_badge") val calendarEditedBadge: String,
        @SerializedName("notification_edited_badge") val notificationEditedBadge: String,
        @SerializedName("reports_edited_badge") val reportsEditedBadge: String,
        @SerializedName("cca_edited_badge") val ccaEditedBadge: String,
        @SerializedName("whole_school_coming_ups_badge") val wholeSchoolComingUpsBadge: String,
        @SerializedName("whole_school_coming_ups_edited_badge") val wholeSchoolComingUpsEditedBadge: String,
        @SerializedName("paymentitem_badge") val paymentItemBadge: String,
        @SerializedName("paymentitem_edit_badge") val paymentItemEditBadge: String,
        @SerializedName("guidance_calendar_badge") val guidanceCalendarBadge: String,
        @SerializedName("guidance_calendar_edited_badge") val guidanceCalendarEditedBadge: String
    )

}

