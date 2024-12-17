package com.nas.alreem.activity.bus_service.model

import com.google.gson.annotations.SerializedName

class RegularBusSubmitModel (
    @SerializedName("student_id") var student_id:String,
    @SerializedName("pickup") var pickup:String,
    @SerializedName("drop") var drop:String,
    @SerializedName("unique_id") var unique_id:String,
    @SerializedName("class_name") var class_name:String,
    @SerializedName("parent1_name") var parent1_name:String,
    @SerializedName("parent1_email") var parent1_email:String,
    @SerializedName("parent1_relationship") var parent1_relationship:String,
    @SerializedName("parent1_mobile") var parent1_mobile:String,
    @SerializedName("parent1_additionaltelephone") var parent1_additionaltelephone:String,
    @SerializedName("parent1_country") var parent1_country:String,
    @SerializedName("parent1_address") var parent1_address:String,
    @SerializedName("term") var term:String,
    @SerializedName("type") var type:String,
    @SerializedName("device_type") var device_type:String,
    @SerializedName("device_name") var device_name:String,
    @SerializedName("app_version") var app_version:String

)