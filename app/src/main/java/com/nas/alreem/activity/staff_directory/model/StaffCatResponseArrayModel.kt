package com.nas.alreem.activity.staff_directory.model

import com.google.gson.annotations.SerializedName

class StaffCatResponseArrayModel(
    @SerializedName("departments")var departments:ArrayList<StaffDeptListModel>
)