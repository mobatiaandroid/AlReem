package com.nas.alreem.fragment.about_us.model

import com.google.gson.annotations.SerializedName
import com.nas.alreem.fragment.contact_us.model.ContactUsModel

class AboutUsResponseArrayModel   (
    @SerializedName("data") var data:ArrayList<AboutUsDataModel>
        )


