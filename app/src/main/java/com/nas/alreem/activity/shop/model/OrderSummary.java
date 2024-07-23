package com.nas.alreem.activity.shop.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderSummary implements Serializable {
    @SerializedName("id")

    String id;
    @SerializedName("actual_amount")

    String actual_amount;
    @SerializedName("tax_amount")

    String tax_amount;
    @SerializedName("total_amount")

    String total_amount;
    @SerializedName("instrument_name")

    String instrument_name;
    @SerializedName("term_name")

    String term_name;
    @SerializedName("lesson_name")

    String lesson_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActual_amount() {
        return actual_amount;
    }

    public void setActual_amount(String actual_amount) {
        this.actual_amount = actual_amount;
    }

    public String getTax_amount() {
        return tax_amount;
    }

    public void setTax_amount(String tax_amount) {
        this.tax_amount = tax_amount;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getInstrument_name() {
        return instrument_name;
    }

    public void setInstrument_name(String instrument_name) {
        this.instrument_name = instrument_name;
    }

    public String getTerm_name() {
        return term_name;
    }

    public void setTerm_name(String term_name) {
        this.term_name = term_name;
    }

    public String getLesson_name() {
        return lesson_name;
    }

    public void setLesson_name(String lesson_name) {
        this.lesson_name = lesson_name;
    }
}
