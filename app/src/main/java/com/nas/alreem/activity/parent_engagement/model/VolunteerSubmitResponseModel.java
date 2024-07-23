package com.nas.alreem.activity.parent_engagement.model;

import com.google.gson.annotations.SerializedName;

public class VolunteerSubmitResponseModel {
    @SerializedName("status")
    private Integer responseCode;



    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }





    // Getters and setters for the outer class fields (responseCode, response, etc.)
    // ...
}