package com.nas.alreem.activity.shop.model;

import java.io.Serializable;

public class Lesson implements Serializable {


    private int id;
    private String name;
    private String currency;
    private String currencySymbol;
    private String totalAmount;
    private int courseSelected;
    private boolean isSelected;
    private String termID;

    public String getTermID() {
        return termID;
    }

    public void setTermID(String termID) {
        this.termID = termID;
    }

    private int orderSuccess;

    public int getOrderSuccess() {
        return orderSuccess;
    }

    public void setOrderSuccess(int orderSuccess) {
        this.orderSuccess = orderSuccess;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}



