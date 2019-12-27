package com.example.travelapp.api.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReqUpdatePassword {
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("currentPassword")
    @Expose
    private String currentPassword;
    @SerializedName("newPassword")
    @Expose
    private String newPassword;

    public ReqUpdatePassword(String userId, String currentPassword, String newPassword) {
        this.userId = userId;
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }
}
