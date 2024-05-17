package com.nas.alreem.activity.primary.model

import com.google.gson.annotations.SerializedName
import com.nas.alreem.activity.communication.commingup.model.ComingUpResponseModel
import com.nas.alreem.fragment.primary.model.PrimaryDataModel

class ComingUpResponseArrayModel  {

    @SerializedName("data") val data:ArrayList<ComingUpDataModell>?=null
}