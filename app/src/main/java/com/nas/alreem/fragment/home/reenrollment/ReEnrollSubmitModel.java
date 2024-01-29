package com.nas.alreem.fragment.home.reenrollment;

public class ReEnrollSubmitModel {
    String student_id;
    String status;

    public ReEnrollSubmitModel(String student_id, String status) {

        this.student_id = student_id;
        this.status = status;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
