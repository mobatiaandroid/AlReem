package com.nas.alreem.activity.settings.model

import com.google.gson.annotations.SerializedName
import com.nas.alreem.fragment.primary.model.PrimaryResponseArrayModel

class TermsOfServiceResponseModel
{
    @SerializedName("status") val status:Int=0
    @SerializedName("responseArray") val responseArray: TermsOfServiceResponseArrayModel? =null
}