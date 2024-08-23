package com.nas.alreem.activity.bus_service.model

import com.google.gson.annotations.SerializedName

class DeatilsResponseArray (
    @SerializedName("student_detail") var student_detail:StudentDetailsResponeModel,
    @SerializedName("parent1_additionaltelephone") var parent1_additionaltelephone:String,
    @SerializedName("parent1_address") var parent1_address1:String,
    @SerializedName("parent1_country") var parent1_country:String,
@SerializedName("parent1_email") var parent1_email:String,
@SerializedName("parent1_mobile") var parent1_mobile:String,
@SerializedName("parent1_name") var parent1_name:String,
@SerializedName("parent1_relationship") var parent1_relationship:String,
@SerializedName("parent2_additionaltelephone") var parent2_additionaltelephone:String,
@SerializedName("parent2_address1") var parent2_address1:String,
@SerializedName("parent2_country") var parent2_country:String,
@SerializedName("parent2_email") var parent2_email:String,
    @SerializedName("parent2_mobile") var parent2_mobile:String,
    @SerializedName("parent2_name") var parent2_name:String,
    @SerializedName("parent2_relationship") var parent2_relationship:String,
    @SerializedName("terms") var terms:ArrayList<TermList>,
    @SerializedName("eap_details") var eap_details:ArrayList<EAPList>)