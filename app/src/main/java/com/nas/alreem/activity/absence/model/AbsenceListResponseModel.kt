package com.nas.alreem.activity.absence.model

import com.google.gson.annotations.SerializedName

class AbsenceListResponseModel(
    @SerializedName("request")var request:ArrayList<AbsenceRequestListModel>
            )