package  com.nas.alreem.activity.payments.model.payment_token

import com.google.gson.annotations.SerializedName

class PaymentTokenApiModel (
    @SerializedName("studentId") var studentId:String,
    @SerializedName("payment_module") var payment_module:String
)