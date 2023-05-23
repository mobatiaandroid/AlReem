package com.nas.alreem.activity.canteen.model.wallethistory

import com.google.gson.annotations.SerializedName

class WalletHistoryModel (
    @SerializedName("status") var status:Int,
    @SerializedName("responseArray") var responseArray:WalletHisResModel
)