package com.nas.alreem.activity.shop.model;

import java.io.Serializable;
import java.util.ArrayList;

public class DataModel implements Serializable {
    private ArrayList<Instrument> instrument_data;

    public ArrayList<Instrument> getInstrument_data() {
        return instrument_data;
    }

    public void setInstrument_data(ArrayList<Instrument> instrument_data) {
        this.instrument_data = instrument_data;
    }
// Getter and Setter methods
}