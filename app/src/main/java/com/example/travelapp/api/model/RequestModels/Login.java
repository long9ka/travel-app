package com.example.travelapp.api.model.RequestModels;

import com.google.gson.annotations.SerializedName;

public class Login {
    @SerializedName("emailPhone")
    private String username;
    @SerializedName("password")
    private String password;

    public Login(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
