package com.example.travelapp.api.model.RequestModels;

import com.google.gson.annotations.SerializedName;

public class LoginFacebook {
    @SerializedName("accessToken")
    private String accessToken;

    public LoginFacebook(String accessToken) {
        this.accessToken = accessToken;
    }
}
