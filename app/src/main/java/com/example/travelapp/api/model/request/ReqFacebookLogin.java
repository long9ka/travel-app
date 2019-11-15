package com.example.travelapp.api.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReqFacebookLogin {
    @SerializedName("accessToken")
    @Expose
    private String accessToken;

    public ReqFacebookLogin(String accessToken) {
        this.accessToken = accessToken;
    }
}
