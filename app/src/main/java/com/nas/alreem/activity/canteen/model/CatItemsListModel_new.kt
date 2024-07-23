package com.nas.alreem.activity.canteen.model

import com.google.gson.annotations.SerializedName

class CatItemsListModel_new(
    @SerializedName("id") val id: String,
    @SerializedName("bar_code") val bar_code: String,
    @SerializedName("item_name") val item_name: String,
    @SerializedName("description") val description: String,
    @SerializedName("item_image") val item_image: String,
    @SerializedName("initial_price") val initial_price: String,
    @SerializedName("vat_percentage") val vat_percentage: String,
    @SerializedName("vat_amount") val vat_amount: String,
    @SerializedName("price") val price: String,
    @SerializedName("isItemCart") var isItemCart:Boolean,
    @SerializedName("cartId") var cartId:String,
    @SerializedName("quantityCart") var quantityCart:Int,
    @SerializedName("item_already_ordered") var item_already_ordered:Int,
    @SerializedName("student_allergy") var student_allergy:Int,
    @SerializedName("allergy_contents") var allergy_contents:ArrayList<AllergyContentModel>,


    var isAllergic: Boolean = false
)