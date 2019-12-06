package com.example.travelapp.api.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResSendVerifyCode {
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("sendTo")
    @Expose
    private String sendTo;
    @SerializedName("expiredOn")
    @Expose
    private String expiredOn;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSendTo() {
        return sendTo;
    }

    public void setSendTo(String sendTo) {
        this.sendTo = sendTo;
    }

    public String getExpiredOn() {
        return expiredOn;
    }

    public void setExpiredOn(String expiredOn) {
        this.expiredOn = expiredOn;
    }
}
