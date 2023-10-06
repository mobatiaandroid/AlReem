package com.nas.alreem.fragment.parents_essentials.model

import com.google.gson.annotations.SerializedName
import com.nas.alreem.fragment.primary.model.PrimaryDataModel

class ParentsEssentialResponseArrayModel(

    @SerializedName("data") val data: ArrayList<ParentsEssentialDataModel>,
    @SerializedName("parentEssentialData") val parentEssentialData: ParentEssentialDataResponseModel

)