package com.nas.alreem.activity.shop_new.model

import com.google.gson.annotations.SerializedName
import com.nas.alreem.activity.payment_history.PaymentWalletHistoryModel

class PaymentShopWalletHistoryModel (
    @SerializedName("order_reference") var order_reference:String,
    @SerializedName("created_on") var created_on:String,
    @SerializedName("order_total") var order_total:String,
    @SerializedName("student_name") var student_name:String,
    @SerializedName("parent_name") var parent_name:String,
@SerializedName("invoice_note") var invoice_note:String,
@SerializedName("payment_type") var payment_type:String,
@SerializedName("trn_no") var trn_no:String,
    @SerializedName("order_status") var order_status:String,
    @SerializedName("order_summery") val order_summery: ArrayList<ShopModel>



)