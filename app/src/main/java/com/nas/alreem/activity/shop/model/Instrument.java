package com.nas.alreem.activity.shop.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Instrument implements Serializable {
    private String instrumentId;
    private String instrumentName;
    private int instrumentSelected;
    private ArrayList<Term> termData;

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

    public ArrayList<Term> getTermData() {
        return termData;
    }

    public void setTermData(ArrayList<Term> termData) {
        this.termData = termData;
    }

    public boolean hasAtLeastOneOrderSuccessInAllTerms() {
        for (Term term : termData) {
            boolean hasOrderSuccess = false;

            for (Lesson lesson : term.getLessonData()) {
                if (lesson.getOrderSuccess() == 1) {
                    hasOrderSuccess = true;
                    break;
                }
            }

            if (!hasOrderSuccess) {
                return false;
            }
        }

        return true;
    }
}
