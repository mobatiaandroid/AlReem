package com.nas.alreem.activity.staff_directory.model

import com.google.gson.annotations.SerializedName

class DepartmentStaffsModel (
    @SerializedName("id")var id:Int,
    @SerializedName("name")var name:String,
    @SerializedName("email")var email:String,
    @SerializedName("staff_photo")var staff_photo:String,
    @SerializedName("staff_bio")var staff_bio:String,
    @SerializedName("staff_department_id")var staff_department_id:String,
    @SerializedName("department_name")var department_name:String
)