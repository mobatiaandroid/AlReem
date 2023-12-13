package com.nas.alreem.fragment.home.re_enrollment;

import java.io.Serializable;

public class ReEnrollmentFormStudentModel implements Serializable {
    String id;
    String unique_id;
    String name;
    String class_name;
    String section;
    String house;
    String photo;

    String status;

    public String getParent_name() {
        return parent_name;
    }

    public void setParent_name(String parent_name) {
        this.parent_name = parent_name;
    }

    public String getParent_email() {
        return parent_email;
    }

    public void setParent_email(String parent_email) {
        this.parent_email = parent_email;
    }

    String parent_name;
    String parent_email;

    public String getEnrollment_status() {
        return enrollment_status;
    }

    public void setEnrollment_status(String enrollment_status) {
        this.enrollment_status = enrollment_status;
    }

    String enrollment_status;
    public ReEnrollmentFormStudentModel(String id, String unique_id, String name, String class_name, String section, String house, String photo,
                                        String status,String enrollment_status,String parent_name,String parent_email) {
        this.id = id;
        this.unique_id = unique_id;
        this.name = name;
        this.class_name = class_name;
        this.section = section;
        this.house = house;
        this.photo = photo;
        this.status = status;
        this.enrollment_status=enrollment_status;
        this.parent_name=parent_name;
        this.parent_email=parent_email;
    }

    public ReEnrollmentFormStudentModel() {
        this.id = "";
        this.unique_id = "";
        this.name = "";
        this.class_name = "";
        this.section = "";
        this.house = "";
        this.photo = "";
        this.status = "";
        this.enrollment_status="";
        this.parent_name="";
        this.parent_email="";

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUnique_id() {
        return unique_id;
    }

    public void setUnique_id(String unique_id) {
        this.unique_id = unique_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
