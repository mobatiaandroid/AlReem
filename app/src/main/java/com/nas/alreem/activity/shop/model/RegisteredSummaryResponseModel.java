package com.nas.alreem.activity.shop.model;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RegisteredSummaryResponseModel {
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

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }
    }

    public static class Data {
        @SerializedName("instrument_data")
        private ArrayList<InstrumentData> instrumentData;

        public ArrayList<InstrumentData> getInstrumentData() {
            return instrumentData;
        }

        public void setInstrumentData(ArrayList<InstrumentData> instrumentData) {
            this.instrumentData = instrumentData;
        }
    }

    public static class InstrumentData {
        @SerializedName("instrument_id")
        private int instrumentId;

        @SerializedName("instrument_name")
        private String instrumentName;

        @SerializedName("order_data")
        private ArrayList<OrderDataModel> orderData;

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

        public ArrayList<OrderDataModel> getOrderData() {
            return orderData;
        }

        public void setOrderData(ArrayList<OrderDataModel> orderData) {
            this.orderData = orderData;
        }
    }

    public static class OrderDataModel {
        @SerializedName("order_id")
        private int orderId;

        @SerializedName("order_reference")
        private String orderReference;

        @SerializedName("student_id")
        private String studentId;

        @SerializedName("instrument_id")
        private String instrumentId;

        @SerializedName("lesson_id")
        private String lessonId;

        @SerializedName("lesson_name")
        private String lessonName;

        @SerializedName("term_id")
        private String termId;

        @SerializedName("term_name")
        private String termName;

        @SerializedName("learning_level")
        private String learningLevel;

        @SerializedName("learning_level_status")
        private String learningLevelStatus;

        @SerializedName("instrument_consent")
        private String instrumentConsent;

        @SerializedName("instrument_consent_status")
        private String instrumentConsentStatus;

        @SerializedName("amount")
        private String amount;

        @SerializedName("currency")
        private String currency;

        @SerializedName("currency_symbol")
        private String currencySymbol;

        @SerializedName("tax_type")
        private String taxType;

        @SerializedName("tax_amount")
        private String taxAmount;

        @SerializedName("tax_percentage")
        private String taxPercentage;

        @SerializedName("total_amount")
        private String totalAmount;

        @SerializedName("status")
        private String status;

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public String getOrderReference() {
            return orderReference;
        }

        public void setOrderReference(String orderReference) {
            this.orderReference = orderReference;
        }

        public String getStudentId() {
            return studentId;
        }

        public void setStudentId(String studentId) {
            this.studentId = studentId;
        }

        public String getInstrumentId() {
            return instrumentId;
        }

        public void setInstrumentId(String instrumentId) {
            this.instrumentId = instrumentId;
        }

        public String getLessonId() {
            return lessonId;
        }

        public void setLessonId(String lessonId) {
            this.lessonId = lessonId;
        }

        public String getLessonName() {
            return lessonName;
        }

        public void setLessonName(String lessonName) {
            this.lessonName = lessonName;
        }

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

        public String getLearningLevel() {
            return learningLevel;
        }

        public void setLearningLevel(String learningLevel) {
            this.learningLevel = learningLevel;
        }

        public String getLearningLevelStatus() {
            return learningLevelStatus;
        }

        public void setLearningLevelStatus(String learningLevelStatus) {
            this.learningLevelStatus = learningLevelStatus;
        }

        public String getInstrumentConsent() {
            return instrumentConsent;
        }

        public void setInstrumentConsent(String instrumentConsent) {
            this.instrumentConsent = instrumentConsent;
        }

        public String getInstrumentConsentStatus() {
            return instrumentConsentStatus;
        }

        public void setInstrumentConsentStatus(String instrumentConsentStatus) {
            this.instrumentConsentStatus = instrumentConsentStatus;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
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

        public String getTaxType() {
            return taxType;
        }

        public void setTaxType(String taxType) {
            this.taxType = taxType;
        }

        public String getTaxAmount() {
            return taxAmount;
        }

        public void setTaxAmount(String taxAmount) {
            this.taxAmount = taxAmount;
        }

        public String getTaxPercentage() {
            return taxPercentage;
        }

        public void setTaxPercentage(String taxPercentage) {
            this.taxPercentage = taxPercentage;
        }

        public String getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(String totalAmount) {
            this.totalAmount = totalAmount;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }


}
