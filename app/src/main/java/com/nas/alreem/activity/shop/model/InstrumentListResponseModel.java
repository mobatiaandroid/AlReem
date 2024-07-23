package com.nas.alreem.activity.shop.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class InstrumentListResponseModel {
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
        private String responseMessage;

        @SerializedName("data")
        private Data data;

        public String getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(String statusCode) {
            this.statusCode = statusCode;
        }

        public String getResponseMessage() {
            return responseMessage;
        }

        public void setResponseMessage(String responseMessage) {
            this.responseMessage = responseMessage;
        }

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }
    }

    public static class Data {
        @SerializedName("instrument_data")
        private ArrayList<InstrumentData> instrumentDataList;

        public ArrayList<InstrumentData> getInstrumentDataList() {
            return instrumentDataList;
        }

        public void setInstrumentDataList(ArrayList<InstrumentData> instrumentDataList) {
            this.instrumentDataList = instrumentDataList;
        }
    }

    public static class InstrumentData {
        @SerializedName("instrument_id")
        private String instrumentId;

        @SerializedName("instrument_name")
        private String instrumentName;

        @SerializedName("instrument_selected")
        private int instrumentSelected;

        @SerializedName("term_data")
        private ArrayList<TermData> termDataList;

        public String getInstrumentId() {
            return instrumentId;
        }

        public void setInstrumentId(String instrumentId) {
            this.instrumentId = instrumentId;
        }

        public String getInstrumentName() {
            return instrumentName;
        }

        public void setInstrumentName(String instrumentName) {
            this.instrumentName = instrumentName;
        }

        public int getInstrumentSelected() {
            return instrumentSelected;
        }

        public void setInstrumentSelected(int instrumentSelected) {
            this.instrumentSelected = instrumentSelected;
        }

        public ArrayList<TermData> getTermDataList() {
            return termDataList;
        }

        public void setTermDataList(ArrayList<TermData> termDataList) {
            this.termDataList = termDataList;
        }
    }

    public static class TermData {
        @SerializedName("term_id")
        private String termId;

        @SerializedName("term_name")
        private String termName;

        @SerializedName("remaining_slot_count")
        private int remainingSlotCount;

        @SerializedName("lesson_data")
        private ArrayList<LessonData> lessonDataList;

        public String getTermId() {
            return termId;
        }

        public void setTermId(String termId) {
            this.termId = termId;
        }

        public String getTermName() {
            return termName;
        }

        public void setTermName(String termName) {
            this.termName = termName;
        }

        public int getRemainingSlotCount() {
            return remainingSlotCount;
        }

        public void setRemainingSlotCount(int remainingSlotCount) {
            this.remainingSlotCount = remainingSlotCount;
        }

        public ArrayList<LessonData> getLessonDataList() {
            return lessonDataList;
        }

        public void setLessonDataList(ArrayList<LessonData> lessonDataList) {
            this.lessonDataList = lessonDataList;
        }
    }

    public class LessonData {
        @SerializedName("id")
        private int id;

        @SerializedName("term_id")
        private String termId;

        @SerializedName("name")
        private String name;

        @SerializedName("currency")
        private String currency;

        @SerializedName("currency_symbol")
        private String currencySymbol;

        @SerializedName("total_amount")
        private String totalAmount;

        @SerializedName("course_selected")
        private int courseSelected;

        @SerializedName("order_success")
        private int orderSuccess;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTermId() {
            return termId;
        }

        public void setTermId(String termId) {
            this.termId = termId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getCurrencySymbol() {
            return currencySymbol;
        }

        public void setCurrencySymbol(String currencySymbol) {
            this.currencySymbol = currencySymbol;
        }

        public String getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(String totalAmount) {
            this.totalAmount = totalAmount;
        }

        public int getCourseSelected() {
            return courseSelected;
        }

        public void setCourseSelected(int courseSelected) {
            this.courseSelected = courseSelected;
        }

        public int getOrderSuccess() {
            return orderSuccess;
        }

        public void setOrderSuccess(int orderSuccess) {
            this.orderSuccess = orderSuccess;
        }
    }


}
