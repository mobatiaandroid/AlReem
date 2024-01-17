package com.nas.alreem.activity.shop.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Term implements Serializable {
    private String termId;
    private String termName;
    private String remainingSlotCount;
    private ArrayList<Lesson> lessonData;

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

    public String getRemainingSlotCount() {
        return remainingSlotCount;
    }

    public void setRemainingSlotCount(String remainingSlotCount) {
        this.remainingSlotCount = remainingSlotCount;
    }

    public ArrayList<Lesson> getLessonData() {
        return lessonData;
    }

    public void setLessonData(ArrayList<Lesson> lessonData) {
        this.lessonData = lessonData;
    }
}

