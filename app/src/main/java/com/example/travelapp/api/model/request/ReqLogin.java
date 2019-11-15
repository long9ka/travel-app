package com.example.travelapp.api.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReqLogin {
    @SerializedName("emailPhone")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;

    public ReqLogin(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
