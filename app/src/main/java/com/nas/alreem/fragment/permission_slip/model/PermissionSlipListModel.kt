package com.nas.alreem.fragment.permission_slip.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.nas.alreem.fragment.intention.model.IntentionListAPIResponseModel

class PermissionSlipListModel (
    @SerializedName("id")val id:Int,
    @SerializedName("title")val title:String,
    @SerializedName("event_date")val event_date:String,
    @SerializedName("description")val consent:String,
    @SerializedName("status")val status:String,
    @SerializedName("form_url")val form_url:String,
    @SerializedName("selected_options")val selected_options:String,
    @SerializedName("options") val options: ArrayList<String>

    )
/*data class PermissionOption(
    @SerializedName("option")
    val option: String

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(option)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PermissionOption> {
        override fun createFromParcel(parcel: Parcel): PermissionOption {
            return PermissionOption(parcel)
        }

        override fun newArray(size: Int): Array<PermissionOption?> {
            return arrayOfNulls(size)
        }
    }
}*/
