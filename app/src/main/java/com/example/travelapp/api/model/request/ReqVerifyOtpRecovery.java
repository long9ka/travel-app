package com.example.travelapp.api.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReqVerifyOtpRecovery {
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("newPassword")
    @Expose
    private String newPassword;
    @SerializedName("verifyCode")
    @Expose
    private String verifyCode;

    public ReqVerifyOtpRecovery(String userId, String newPassword, String verifyCode) {
        this.userId = userId;
        this.newPassword = newPassword;
        this.verifyCode = verifyCode;
    }
}
