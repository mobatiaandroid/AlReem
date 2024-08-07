package com.nas.alreem.activity.bus_service.model

import com.google.gson.annotations.SerializedName

class DetailsResponseModel (
    @SerializedName("status") var status:String,
    @SerializedName("responseArray") var responseArray:DeatilsResponseArray,


)