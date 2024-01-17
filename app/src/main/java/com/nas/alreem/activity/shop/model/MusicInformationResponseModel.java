package com.nas.alreem.activity.shop.model;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MusicInformationResponseModel {
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

        @SerializedName("banner_image")
        private String bannerImage;

        @SerializedName("description")
        private String description;

        @SerializedName("contact_email")
        private String contactEmail;

        @SerializedName("data")
        private ArrayList<Data> data;

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

        public String getBannerImage() {
            return bannerImage;
        }

        public void setBannerImage(String bannerImage) {
            this.bannerImage = bannerImage;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getContactEmail() {
            return contactEmail;
        }

        public void setContactEmail(String contactEmail) {
            this.contactEmail = contactEmail;
        }

        public ArrayList<Data> getData() {
            return data;
        }

        public void setData(ArrayList<Data> data) {
            this.data = data;
        }
    }

    public static class Data {
        @SerializedName("id")
        private String id;

        @SerializedName("submenu")
        private String submenu;

        @SerializedName("filename")
        private String filename;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSubmenu() {
            return submenu;
        }

        public void setSubmenu(String submenu) {
            this.submenu = submenu;
        }

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }
    }


}
