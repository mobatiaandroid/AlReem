package com.nas.alreem.activity.shop.model;

import java.io.Serializable;


public class InstrumentListModel implements Serializable {
    private String response;
    private String statuscode;
    private DataModel data;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(String statuscode) {
        this.statuscode = statuscode;
    }

    public DataModel getData() {
        return data;
    }

    public void setData(DataModel data) {
        this.data = data;
    }
// Getter and Setter methods
}









