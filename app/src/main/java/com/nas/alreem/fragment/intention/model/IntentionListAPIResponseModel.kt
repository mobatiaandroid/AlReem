package com.nas.alreem.fragment.intention.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


data class IntentionListAPIResponseModel(
    @SerializedName("status")
    val status: Int,
    @SerializedName("responseArray")
    val responseArray: ResponseArray
) {
    data class ResponseArray(
        @SerializedName("intension")
        val intensions: ArrayList<Intention>
    )

    data class Intention(
        @SerializedName("intension_id")
        val intensionId: Int,
        @SerializedName("class")
        val intensionClass: String,
        @SerializedName("student_name")
        val studentName: String,
        @SerializedName("title")
        val title: String,
        @SerializedName("question")
        val question: String,
        @SerializedName("options")
        val options: ArrayList<Option>,
        @SerializedName("description")
        val description: String,
        @SerializedName("status")
        val status: String
    )

    data class Option(
        @SerializedName("option")
        val option: String,
        @SerializedName("question")
        val optionQuestion: String?
    ) : Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readString() ?: "",
            parcel.readString()
        )

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(option)
            parcel.writeString(optionQuestion)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Option> {
            override fun createFromParcel(parcel: Parcel): Option {
                return Option(parcel)
            }

            override fun newArray(size: Int): Array<Option?> {
                return arrayOfNulls(size)
            }
        }
    }

}

