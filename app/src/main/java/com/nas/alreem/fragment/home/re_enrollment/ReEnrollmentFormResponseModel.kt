package com.nas.alreem.fragment.home.re_enrollment

import com.google.gson.annotations.SerializedName


data class ReEnrollmentFormResponseModel(
    @SerializedName("status") val status: String,
    @SerializedName("responseArray") val responseArray: SecondResponseArray
) {

    data class SecondResponseArray(
        @SerializedName("settings") val settings: Settings,
        @SerializedName("user") val user: User,
        @SerializedName("students") val students: ArrayList<StudentEnrollList>,
        @SerializedName("current_date") val currentDate: String
    )

    data class Settings(
        @SerializedName("heading") val heading: String,
        @SerializedName("description") val description: String,
        @SerializedName("options") val options: ArrayList<String>,
        @SerializedName("question") val question: String,
        @SerializedName("t_and_c") val tAndC: String,
        @SerializedName("statement_url") val statementUrl: String
    )

    data class User(
        @SerializedName("name") val name: String,
        @SerializedName("email") val email: String
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

