package com.nas.alreem.activity.parent_engagement.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ParentAssociationEventResponseModel {
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

        @SerializedName("facebookurl")
        private String facebookUrl;

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

        public String getFacebookUrl() {
            return facebookUrl;
        }

        public void setFacebookUrl(String facebookUrl) {
            this.facebookUrl = facebookUrl;
        }

        public ArrayList<EventData> getEventDataList() {
            return eventDataList;
        }

        public void setEventDataList(ArrayList<EventData> eventDataList) {
            this.eventDataList = eventDataList;
        }
    }

    public static class EventData {
        @SerializedName("even_id")
        private String eventId;

        @SerializedName("event")
        private String event;

        @SerializedName("date")
        private String date;

        @SerializedName("items")
        private ArrayList<FoodItem> foodItemList;

        public String getEventId() {
            return eventId;
        }

        public void setEventId(String eventId) {
            this.eventId = eventId;
        }

        public String getEvent() {
            return event;
        }

        public void setEvent(String event) {
            this.event = event;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public ArrayList<FoodItem> getFoodItemList() {
            return foodItemList;
        }

        public void setFoodItemList(ArrayList<FoodItem> foodItemList) {
            this.foodItemList = foodItemList;
        }
    }

    public static class FoodItem {
        @SerializedName("name")
        private String name;

        @SerializedName("timeslots")
        private ArrayList<TimeSlot> timeSlotList;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public ArrayList<TimeSlot> getTimeSlotList() {
            return timeSlotList;
        }

        public void setTimeSlotList(ArrayList<TimeSlot> timeSlotList) {
            this.timeSlotList = timeSlotList;
        }
    }

    public static class TimeSlot {
        @SerializedName("id")
        private String id;

        @SerializedName("start_time")
        private String startTime;

        @SerializedName("end_time")
        private String endTime;

        @SerializedName("status")
        private String status;

        @SerializedName("user_name")
        private String userName;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }


}
