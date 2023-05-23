package com.nas.alreem.activity.canteen.model.information

import com.google.gson.annotations.SerializedName

class InfoListModel (
    @SerializedName("id") var id:String,
    @SerializedName("title") var title:String,
    @SerializedName("file") var file:String
)