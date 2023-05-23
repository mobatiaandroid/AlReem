package com.nas.alreem.activity.canteen.model.order_history

import com.google.gson.annotations.SerializedName

class OrderHistoryApiModel (
    @SerializedName("studentId") var student_id:String,
    @SerializedName("start") var start:String,
    @SerializedName("limit") var limit:String
)