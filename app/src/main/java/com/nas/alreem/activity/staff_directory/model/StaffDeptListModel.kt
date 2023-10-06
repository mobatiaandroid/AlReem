package com.nas.alreem.activity.staff_directory.model

import com.google.gson.annotations.SerializedName

class StaffDeptListModel (
    @SerializedName("id")var id:Int,
    @SerializedName("department_name")var department_name:String
)