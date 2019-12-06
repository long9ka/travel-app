package com.example.travelapp.api.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ReqPasswordRecovery {
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("value")
    @Expose
    private String value;

    public ReqPasswordRecovery(String type, String value) {
        this.type = type;
        this.value = value;
    }
}
