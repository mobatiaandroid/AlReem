package com.nas.alreem.fragment.contact_us.model

import com.google.gson.annotations.SerializedName
import com.nas.alreem.fragment.primary.model.PrimaryDataModel
import com.nas.alreem.fragment.primary.model.PrimaryResponseArrayModel

class ContactUsResponseArrayModel  {
    @SerializedName("description") val description:String=""
    @SerializedName("latitude") val latitude:String=""
    @SerializedName("longitude") val longitude:String=""
    @SerializedName("contacts") val contacts:ArrayList<ContactUsModel>?=null
}