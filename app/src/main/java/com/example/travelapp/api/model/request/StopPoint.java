package com.example.travelapp.api.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StopPoint {

    @SerializedName("arrivalAt")
    @Expose
    private String arrivalAt;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("leaveAt")
    @Expose
    private String leaveAt;
    @SerializedName("long")
    @Expose
    private String _long;
    @SerializedName("minCost")
    @Expose
    private String minCost;
    @SerializedName("maxCost")
    @Expose
    private String maxCost;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("serviceTypeId")
    @Expose
    private String serviceTypeId;

    public StopPoint(String name, String lat, String _long, String arrivalAt, String leaveAt, String serviceTypeId, String minCost, String maxCost) {
        this.name = name;
        this.lat = lat;
        this._long = _long;
        this.arrivalAt = arrivalAt;
        this.leaveAt = leaveAt;
        this.serviceTypeId = serviceTypeId;
        this.minCost = minCost;
        this.maxCost = maxCost;
    }
}
