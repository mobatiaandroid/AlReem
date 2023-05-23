package com.nas.alreem.activity.canteen.model.add_to_cart

import com.google.gson.annotations.SerializedName

class CanteenCartRemoveApiModel (
    @SerializedName("studentId") var studentId:String,
    @SerializedName("canteen_cart_id") var canteen_cart_id:String
)