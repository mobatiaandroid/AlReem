package com.nas.alreem.activity.parent_engagement.model

import com.google.gson.annotations.SerializedName

class ParentAssociationEventItemsModel (
    @SerializedName("start_time")var start_time:String,
    @SerializedName("from_time")var from_time:String,
    @SerializedName("to_time")var to_time:String,
    @SerializedName("end_time")var end_time:String,
    @SerializedName("eventId")var eventId:String,
    @SerializedName("status")var status:String,
    @SerializedName("userName")var userName:String
)