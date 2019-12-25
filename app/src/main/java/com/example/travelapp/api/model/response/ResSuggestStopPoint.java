package com.example.travelapp.api.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResSuggestStopPoint {
    @SerializedName("stopPoints")
    @Expose
    private List<ResStopPoint> stopPoints = null;

    public List<ResStopPoint> getStopPoints() {
        return stopPoints;
    }

    public void setStopPoints(List<ResStopPoint> stopPoints) {
        this.stopPoints = stopPoints;
    }
}
