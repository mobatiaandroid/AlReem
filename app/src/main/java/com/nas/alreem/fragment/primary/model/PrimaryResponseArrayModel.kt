package com.nas.alreem.fragment.primary.model

import com.google.gson.annotations.SerializedName
import com.nas.alreem.fragment.notifications.model.NotificationModel

class PrimaryResponseArrayModel {

    @SerializedName("data") val data:ArrayList<PrimaryDataModel>?=null
}