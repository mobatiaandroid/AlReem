package com.nas.alreem.activity.staff_directory.model

import com.google.gson.annotations.SerializedName

class StaffDetailResponseArrayModel (
    @SerializedName("department_staff")var department_staffs:ArrayList<DepartmentStaffsModel>
)