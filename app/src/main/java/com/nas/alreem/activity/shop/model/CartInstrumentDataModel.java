package com.nas.alreem.activity.shop.model;

import java.io.Serializable;
import java.util.ArrayList;

public class CartInstrumentDataModel implements Serializable {
    private int instrumentId;
    private String instrumentName;
    private ArrayList<CartItemModel> cartData;

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

    public ArrayList<CartItemModel> getCartData() {
        return cartData;
    }

    public void setCartData(ArrayList<CartItemModel> cartData) {
        this.cartData = cartData;
    }
}
