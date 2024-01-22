package com.nas.alreem.fragment.home.re_enrollment;

import java.util.ArrayList;

public class EnrollmentStatusResponseModel {
    private String status;
    private ResponseArray responseArray;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ResponseArray getResponseArray() {
        return responseArray;
    }

    public void setResponseArray(ResponseArray responseArray) {
        this.responseArray = responseArray;
    }

    public static class ResponseArray {
        private ArrayList<StudentEnrollList> students;

        public ArrayList<StudentEnrollList> getStudents() {
            return students;
        }

        public void setStudents(ArrayList<StudentEnrollList> students) {
            this.students = students;
        }
    }

}
