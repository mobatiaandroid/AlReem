package com.nas.alreem.fragment.parents_essentials.model

import com.google.gson.annotations.SerializedName
import com.nas.alreem.fragment.payments.model.PaymentResponseArrayModel

class ParentsEssentialResponseModel  {
    @SerializedName("status") val status:Int=0
    @SerializedName("responseArray") val responseArray: ParentsEssentialResponseArrayModel? =null
}