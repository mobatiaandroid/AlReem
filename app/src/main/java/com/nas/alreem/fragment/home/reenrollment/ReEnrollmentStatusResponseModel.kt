package com.nas.alreem.fragment.home.re_enrollment

import com.google.gson.annotations.SerializedName
import com.nas.alreem.fragment.home.reenrollment.StudentEnrollList

data class ReEnrollmentStatusResponseModel(
    @SerializedName("status") val status: String,
    @SerializedName("responseArray") val responseArray: ResponseArray
) {
    data class ResponseArray(
        @SerializedName("students") val students: ArrayList<StudentEnrollList>
    )

    data class Student(
        @SerializedName("id") val id: String,
        @SerializedName("unique_id") val uniqueId: String,
        @SerializedName("name") val name: String,
        @SerializedName("class_name") val className: String,
        @SerializedName("section") val section: String,
        @SerializedName("house") val house: String,
        @SerializedName("photo") val photo: String,
        @SerializedName("parent_name") val parentName: String,
        @SerializedName("parent_email") val parentEmail: String,
        @SerializedName("enrollment_status") val enrollmentStatus: String,
        @SerializedName("status") val status: String
    )
}


