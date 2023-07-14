package com.nas.alreem.activity.parent_meetings.model

import com.google.gson.annotations.SerializedName

class PtaReviewListResponseModel (
    @SerializedName("status")var status:Int,
    @SerializedName("message")var message:String,
    @SerializedName("data")var data:ArrayList<ReviewListModel>
)