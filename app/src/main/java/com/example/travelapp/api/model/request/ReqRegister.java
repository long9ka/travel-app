package com.example.travelapp.api.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReqRegister {

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("dob")
    @Expose
    private String birthday;
    @SerializedName("gender")
    @Expose
    private String gender;

    public ReqRegister(
            String email,
            String phone,
            String password,
            String fullName,
            String address,
            String gender,
            String birthday
    ) {
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.fullName = fullName;
        this.address = address;
        this.birthday = birthday;
        this.gender = gender;
    }
}