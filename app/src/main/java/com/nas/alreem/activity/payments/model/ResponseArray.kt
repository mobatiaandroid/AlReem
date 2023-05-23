package com.nas.alreem.activity.payments.model

import com.google.gson.annotations.SerializedName

data class ResponseArray (
    @SerializedName("student_list") val studentList: List<StudentList>

)