package com.nas.alreem.activity.payments.model

import com.google.gson.annotations.SerializedName

class PayCategoryModel (
    @SerializedName("status") var status:Int,
    @SerializedName("responseArray") var responseArray:PayCatListResModel
)