package com.nas.alreem.activity.absence.model

import com.google.gson.annotations.SerializedName

class PickupListModel (

    @SerializedName("status") val status: Int,
    @SerializedName("early_pickups") val pickupListArray: ArrayList<EarlyPickupListArray>
)