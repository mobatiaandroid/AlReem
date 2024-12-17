package com.nas.alreem.activity.bus_service.model

import com.google.gson.annotations.SerializedName

class InvoiceList (
    @SerializedName("id") var id:Int,
    @SerializedName("type") var type:String,
    @SerializedName("invoice_ref") var invoice_ref:String,
    @SerializedName("total_amount") var total_amount:String,
    @SerializedName("payment_status") var payment_status:String,
    @SerializedName("paid_date") var paid_date:String,
    @SerializedName("invoice_note") var invoice_note:String
    )
