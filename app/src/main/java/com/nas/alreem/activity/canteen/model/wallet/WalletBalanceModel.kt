package com.nas.alreem.activity.canteen.model.wallet

import com.google.gson.annotations.SerializedName

class WalletBalanceModel (
    @SerializedName("status") var status:Int,
    @SerializedName("responseArray") var responseArray:WalletResModel,
)