package com.nas.alreem.activity.lost_card.model

import com.google.gson.annotations.SerializedName

class ListModelHistory (
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("id") val id: Int,
    @SerializedName("student_id") val student_id: Int,
    @SerializedName("user_id") val user_id: Int,
    @SerializedName("amount") val amount: Int,
    @SerializedName("keycode") val keycode: String,
    @SerializedName("status") val status: Int,
    @SerializedName("status_type") val status_type: String,
    @SerializedName("created_on") val created_on: String,
    @SerializedName("invoice_note") val invoice_note: String,
    @SerializedName("trn_no") val trn_no: String,
    @SerializedName("payment_type") val payment_type: String,

    )
