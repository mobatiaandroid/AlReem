package com.nas.alreem.activity.parent_engagement.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class ParentAssociationResponseModel {
    @SerializedName("status")
    private Integer responseCode;

    @SerializedName("responseArray")
    private Response response;

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
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

        @SerializedName("banner_image")
        private String bannerImage;

        @SerializedName("description")
        private String description;

        @SerializedName("contact_email")
        private String contactEmail;

        @SerializedName("data")
        private ArrayList<EventData> eventDataList;

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

        public ArrayList<EventData> getEventDataList() {
            return eventDataList;
        }

        public void setEventDataList(ArrayList<EventData> eventDataList) {
            this.eventDataList = eventDataList;
        }
    }

    public static class EventData {
        @SerializedName("id")
        private String id;

        @SerializedName("title")
        private String title;

        @SerializedName("file")
        private String file;

        @SerializedName("filename")
        private String filename;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getFile() {
            return file;
        }

        public void setFile(String file) {
            this.file = file;
        }

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }
    }

}





