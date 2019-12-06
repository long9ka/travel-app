package com.example.travelapp.api.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResPasswordRecovery {

    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("expiredOn")
    @Expose
    private String expiredOn;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExpiredOn() {
        return expiredOn;
    }

    public void setExpiredOn(String expiredOn) {
        this.expiredOn = expiredOn;
    }

}
