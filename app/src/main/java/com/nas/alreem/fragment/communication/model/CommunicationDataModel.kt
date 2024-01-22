package com.nas.alreem.fragment.communication.model

import com.google.gson.annotations.SerializedName

class CommunicationDataModel {
    @SerializedName("id") lateinit var id: String
    @SerializedName("submenu") lateinit var submenu: String
    @SerializedName("filename") lateinit var filename: String
    @SerializedName("description") lateinit var description: String
}



