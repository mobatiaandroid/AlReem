package com.nas.alreem.fragment.intention.model

import com.google.gson.annotations.SerializedName
import com.nas.alreem.fragment.primary.model.PrimaryDataModel

class IntentionInfoArray (
    @SerializedName("intension") val intension:ArrayList<IntentionInfoResponseArray>?=null
)