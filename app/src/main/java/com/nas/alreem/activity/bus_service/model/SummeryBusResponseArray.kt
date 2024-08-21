package com.nas.alreem.activity.bus_service.model

import com.google.gson.annotations.SerializedName

class SummeryBusResponseArray (

  @SerializedName("id") var id:Int,
@SerializedName("student_name") var student_name:String,
@SerializedName("registration_id") var registration_id:String,
@SerializedName("class_name") var class_name:String,
  @SerializedName("parent_name") var parent_name:String,
  @SerializedName("parent_email") var parent_email:String,
  @SerializedName("parent_mobile") var parent_mobile:String,
  @SerializedName("parent_country") var parent_country:String,
  @SerializedName("parent_address") var parent_address:String,
  @SerializedName("landmark") var landmark:String,
  @SerializedName("pickup_point") var pickup_point:String,
@SerializedName("drop_point") var drop_point:String,
@SerializedName("academic_year") var academic_year:String,
  @SerializedName("total_amount") var total_amount:String,
  @SerializedName("type") var type:String,
  @SerializedName("status") var status:String,
  @SerializedName("title") var title:String,
  @SerializedName("way") var way:String,
  @SerializedName("invoice") var invoice:ArrayList<InvoiceList>,
  @SerializedName("eap_dates") var eap_dates:ArrayList<String>)



