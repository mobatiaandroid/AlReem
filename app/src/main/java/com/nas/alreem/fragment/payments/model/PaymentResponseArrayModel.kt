package com.nas.alreem.fragment.payments.model

import com.google.gson.annotations.SerializedName
import com.nas.alreem.fragment.primary.model.PrimaryDataModel

class PaymentResponseArrayModel (

    @SerializedName("data") var data: PaymentDataModel
)