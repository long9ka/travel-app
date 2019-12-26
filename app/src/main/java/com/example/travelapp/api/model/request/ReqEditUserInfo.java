package com.example.travelapp.api.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReqEditUserInfo {
    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("gender")
    @Expose
    private String gender;
    
    public ReqEditUserInfo(String fullName, String email, String phone, String gender, String dob) {
        this.fullName = fullName;
        this.dob = dob;
        this.phone = phone;
        this.gender = gender;
        this.email = email;
    }
}
