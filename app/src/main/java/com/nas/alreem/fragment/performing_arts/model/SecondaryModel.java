package com.nas.alreem.fragment.performing_arts.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class SecondaryModel implements Serializable {
    public ArrayList<PrimaryModel> getmPrimaryModel() {
        return mPrimaryModel;
    }

    public void setmPrimaryModel(ArrayList<PrimaryModel> mPrimaryModel) {
        this.mPrimaryModel = mPrimaryModel;
    }


    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmName() {
        return name;
    }

    public void setmName(String mName) {
        this.name = mName;
    }

    public String getmFile() {
        return file;
    }

    public void setmFile(String mFile) {
        this.file = mFile;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    String name;String file,mTitle,mDescription;

    String mId;
    ArrayList<PrimaryModel>mPrimaryModel;
    String title;
    String description;
    String createdon;
    String status;
    String id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedon() {
        return createdon;
    }

    public void setCreatedon(String createdon) {
        this.createdon = createdon;
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
}
