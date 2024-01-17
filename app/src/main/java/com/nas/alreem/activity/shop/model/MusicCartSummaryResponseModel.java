package com.nas.alreem.activity.shop.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MusicCartSummaryResponseModel {
    @SerializedName("responsecode")
    private String responseCode;

    @SerializedName("response")
    private ResponseData responseData;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public ResponseData getResponseData() {
        return responseData;
    }

    public void setResponseData(ResponseData responseData) {
        this.responseData = responseData;
    }

    public class ResponseData {
        @SerializedName("statuscode")
        private String statusCode;

        @SerializedName("response")
        private String response;

        @SerializedName("data")
        private InstrumentData instrumentData;

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

        public InstrumentData getInstrumentData() {
            return instrumentData;
        }

        public void setInstrumentData(InstrumentData instrumentData) {
            this.instrumentData = instrumentData;
        }
    }

    public class InstrumentData {
        @SerializedName("instrument_data")
        private ArrayList<Instrument> instrumentList;

        public ArrayList<Instrument> getInstrumentList() {
            return instrumentList;
        }

        public void setInstrumentList(ArrayList<Instrument> instrumentList) {
            this.instrumentList = instrumentList;
        }
    }

    public class Instrument {
        @SerializedName("instrument_id")
        private int instrumentId;

        @SerializedName("instrument_name")
        private String instrumentName;

        @SerializedName("cart_data")
        private ArrayList<Cart> cartData;

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

        public ArrayList<Cart> getCartData() {
            return cartData;
        }

        public void setCartData(ArrayList<Cart> cartData) {
            this.cartData = cartData;
        }
    }

    public class Cart {
        @SerializedName("cart_id")
        private int cartId;

        @SerializedName("student_id")
        private int studentId;

        @SerializedName("instrument_id")
        private int instrumentId;

        @SerializedName("lesson_id")
        private int lessonId;

        @SerializedName("lesson_name")
        private String lessonName;

        @SerializedName("term_id")
        private int termId;

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

        @SerializedName("expired_at")
        private String expiredAt;

        public int getCartId() {
            return cartId;
        }

        public void setCartId(int cartId) {
            this.cartId = cartId;
        }

        public int getStudentId() {
            return studentId;
        }

        public void setStudentId(int studentId) {
            this.studentId = studentId;
        }

        public int getInstrumentId() {
            return instrumentId;
        }

        public void setInstrumentId(int instrumentId) {
            this.instrumentId = instrumentId;
        }

        public int getLessonId() {
            return lessonId;
        }

        public void setLessonId(int lessonId) {
            this.lessonId = lessonId;
        }

        public String getLessonName() {
            return lessonName;
        }

        public void setLessonName(String lessonName) {
            this.lessonName = lessonName;
        }

        public int getTermId() {
            return termId;
        }

        public void setTermId(int termId) {
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

        public String getExpiredAt() {
            return expiredAt;
        }

        public void setExpiredAt(String expiredAt) {
            this.expiredAt = expiredAt;
        }
    }
}
