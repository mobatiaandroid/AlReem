package com.nas.alreem.activity.bus_service.model

import com.google.gson.annotations.SerializedName

class InvoiceSucesssModel (
    @SerializedName("id") var id:Int,
    @SerializedName("student_name") var student_name:String,
    @SerializedName("esis_number") var esis_number:String,
    @SerializedName("academic_year") var academic_year:String,
    @SerializedName("invoice_ref") var invoice_ref:String,
    @SerializedName("total_amount") var total_amount:String,
    @SerializedName("status") var status:String,
    @SerializedName("registration_id") var registration_id:String,
    @SerializedName("class_name") var class_name:String,
    @SerializedName("section") var section:String,
    @SerializedName("type") var type:String,
    @SerializedName("paid_date") var paid_date:String,
    @SerializedName("trm_no") var trm_no:String,
)
