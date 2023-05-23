package com.nas.alreem.activity.login.model

import com.google.gson.annotations.SerializedName

class LoginResponseModel {
    @SerializedName("status") val status:Int=0
    @SerializedName("user_code") val user_code:String=""
    @SerializedName("token") val token:String=""
    @SerializedName("responsearray") val responseArray: LoginResponseArrayModel? =null
}
