package com.nas.alreem.activity.canteen.model.myorders

import com.google.gson.annotations.SerializedName

class CancelCanteenPreOrderApiModel (
    @SerializedName("studentId") var student_id:String,
    @SerializedName("canteen_preorder_id") var canteen_preorder_id:String
)