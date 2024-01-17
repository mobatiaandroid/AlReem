package com.nas.alreem.activity.shop.model;

public class TermsSubmitModel {

    private String termId;
    private int lessonId;

    public TermsSubmitModel(String termId, int lessonId) {
        this.termId = termId;
        this.lessonId = lessonId;
    }

    public String getTermId() {
        return termId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    public int getLessonId() {
        return lessonId;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }
}
