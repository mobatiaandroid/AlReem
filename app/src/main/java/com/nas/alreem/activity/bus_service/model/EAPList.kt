package com.nas.alreem.activity.bus_service.model

import com.google.gson.annotations.SerializedName

class EAPList (
    @SerializedName("cca_days_id") var cca_days_id:Int,
    @SerializedName("from_date") var from_date:String,
    @SerializedName("to_date") var to_date:String,
    @SerializedName("title") var title:String,
    @SerializedName("date_lists") var date_lists:ArrayList<DateListArray>


    )