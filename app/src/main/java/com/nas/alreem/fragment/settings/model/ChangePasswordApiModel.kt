package com.nas.alreem.fragment.settings.model

import com.google.gson.annotations.SerializedName

class ChangePasswordApiModel(
    @SerializedName("old_password") val old_password: String,
    @SerializedName("password") val password: String,
    @SerializedName("c_password") val c_password: String
)
