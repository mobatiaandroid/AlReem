package com.nas.alreem.activity.shop.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class MusicPaymentHistoryModel implements Serializable {
    @SerializedName("order_reference")
    String id;
    @SerializedName("created_on")
    String date_time;
    @SerializedName("student_name")
    String name;
    @SerializedName("order_total")
    String amount;
    @SerializedName("status")
    String status;
    @SerializedName("keycode")
    String keycode;
    @SerializedName("bill_no")
    String bill_no;
    @SerializedName("invoice_note")
    String invoice;
    @SerializedName("trn_no")
    String trn_no;
    @SerializedName("payment_type")
    String payment_type;
    @SerializedName("order_summery")
    ArrayList<OrderSummary> order_summery;

    public ArrayList<OrderSummary> getOrder_summery() {
        return order_summery;
    }

    public void setOrder_summery(ArrayList<OrderSummary> order_summery) {
        this.order_summery = order_summery;
    }

    public String getTrn_no() {
        return trn_no;
    }

    public void setTrn_no(String trn_no) {
        this.trn_no = trn_no;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKeycode() {
        return keycode;
    }

    public void setKeycode(String keycode) {
        this.keycode = keycode;
    }

    public String getBill_no() {
        return bill_no;
    }

    public void setBill_no(String bill_no) {
        this.bill_no = bill_no;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }
}
