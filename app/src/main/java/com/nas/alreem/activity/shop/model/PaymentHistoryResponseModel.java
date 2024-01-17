package com.nas.alreem.activity.shop.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PaymentHistoryResponseModel {
    @SerializedName("responsecode")
    private String responseCode;

    @SerializedName("response")
    private Response response;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public static class Response {
        @SerializedName("statuscode")
        private String statusCode;

        @SerializedName("response")
        private String response;

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }
@SerializedName("data")
        private Data data;
        public String getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(String statusCode) {
            this.statusCode = statusCode;
        }

        public String getResponse() {
            return response;
        }

        public void setResponse(String response) {
            this.response = response;
        }
    }
    public class Data {
        public ArrayList<MusicPaymentHistoryModel> getPayment_history() {
            return payment_history;
        }

        public void setPayment_history(ArrayList<MusicPaymentHistoryModel> payment_history) {
            this.payment_history = payment_history;
        }

        @SerializedName("payment_history")
        private ArrayList<MusicPaymentHistoryModel> payment_history;
    }
}
