package com.nas.alreem.activity.payments.model

import com.google.gson.annotations.SerializedName

class InfoResponseModel (
    @SerializedName("information") var information:ArrayList<InfoListModel>
)