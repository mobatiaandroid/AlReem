package com.nas.alreem.fragment.time_table_new.model

import com.google.gson.annotations.SerializedName

class PeriodModelNew {
    @SerializedName("sortname") var sortname: String=""
    @SerializedName("starttime") var starttime: String=""
    @SerializedName("endtime") var endtime: String=""
    @SerializedName("period_id") var period_id: Int=0
    @SerializedName("sunday") var sunday: String=""
    @SerializedName("monday") var monday: String=""
    @SerializedName("tuesday") var tuesday: String=""
    @SerializedName("wednesday") var wednesday: String=""
    @SerializedName("thursday") var thursday: String=""
    @SerializedName("countS") var countS: Int=0
    @SerializedName("countM") var countM: Int=0
    @SerializedName("countT") var countT: Int=0
    @SerializedName("countW") var countW: Int=0
    @SerializedName("countTh") var countTh: Int=0

    @SerializedName("timeTableDayModel") var timeTableDayModel= ArrayList<DayModelNew>()
    @SerializedName("timeTableListS")  var timeTableListS= ArrayList<DayModelNew>()
    @SerializedName("timeTableListM") var timeTableListM= ArrayList<DayModelNew>()
    @SerializedName("timeTableListTu") var timeTableListTu= ArrayList<DayModelNew>()
    @SerializedName("timeTableListW") var timeTableListW= ArrayList<DayModelNew>()
    @SerializedName("timeTableListTh") var timeTableListTh= ArrayList<DayModelNew>()
}