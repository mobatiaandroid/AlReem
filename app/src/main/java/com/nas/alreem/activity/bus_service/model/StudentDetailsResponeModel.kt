package com.nas.alreem.activity.bus_service.model

import com.google.gson.annotations.SerializedName

class StudentDetailsResponeModel (
    @SerializedName("id") var id:String,
    @SerializedName("unique_id") var unique_id:String,
    @SerializedName("registration_id") var registration_id:String,
    @SerializedName("name") var name:String,
    @SerializedName("class") var classs:String,
    @SerializedName("section") var section:String,
    @SerializedName("enrolmentDate") var enrolmentDate:String,
    @SerializedName("esis_number") var esis_number:String,
    @SerializedName("photo") var photo:String)

