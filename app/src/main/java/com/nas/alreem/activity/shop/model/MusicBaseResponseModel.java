package com.nas.alreem.activity.shop.model;

import com.google.gson.annotations.SerializedName;

public class MusicBaseResponseModel {
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


}

