package com.nas.alreem.activity.shop.model;

import java.util.ArrayList;

public class InstrumentDataModel {
    private int instrumentId;
    private String instrumentName;
    private ArrayList<RegisteredSummaryResponseModel.OrderDataModel> orderData;

    public int getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(int instrumentId) {
        this.instrumentId = instrumentId;
    }

    public String getInstrumentName() {
        return instrumentName;
    }

    public void setInstrumentName(String instrumentName) {
        this.instrumentName = instrumentName;
    }

    public ArrayList<RegisteredSummaryResponseModel.OrderDataModel> getOrderData() {
        return orderData;
    }

    public void setOrderData(ArrayList<RegisteredSummaryResponseModel.OrderDataModel> orderData) {
        this.orderData = orderData;
    }
}
