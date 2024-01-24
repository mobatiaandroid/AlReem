package com.nas.alreem.activity.shop_new.model

import com.google.gson.annotations.SerializedName
import com.nas.alreem.activity.lost_card.model.ResponseStudent

class StudentShopCardResponseModel (
    @SerializedName("responsecode") val responsecode: String,
    @SerializedName("response") val response: ResponseShopStudent
)