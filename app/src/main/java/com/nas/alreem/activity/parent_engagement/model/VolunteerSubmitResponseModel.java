package com.nas.alreem.activity.parent_engagement.model;

import com.google.gson.annotations.SerializedName;

public class VolunteerSubmitResponseModel {
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
        @SerializedName("response")
        private String response;

        @SerializedName("statuscode")
        private String statusCode;

        public String getResponse() {
            return response;
        }

        public void setResponse(String response) {
            this.response = response;
        }

        public String getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(String statusCode) {
            this.statusCode = statusCode;
        }
    }

    // Getters and setters for the outer class fields (responseCode, response, etc.)
    // ...
}