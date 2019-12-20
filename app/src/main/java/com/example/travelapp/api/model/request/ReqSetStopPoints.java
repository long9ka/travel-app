package com.example.travelapp.api.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReqSetStopPoints {
    @SerializedName("tourId")
    @Expose
    private String tourId;
    @SerializedName("stopPoints")
    @Expose
    private List<StopPoint> stopPoints = null;

    public ReqSetStopPoints(String tourId, List<StopPoint> stopPoints) {
        this.tourId = tourId;
        this.stopPoints = stopPoints;
    }
}
