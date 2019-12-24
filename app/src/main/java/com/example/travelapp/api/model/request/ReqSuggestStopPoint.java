package com.example.travelapp.api.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReqSuggestStopPoint {
    @SerializedName("hasOneCoordinate")
    @Expose
    private Boolean hasOneCoordinate;
    @SerializedName("coordList")
    @Expose
    private CoordStopPoint coordList;

    public ReqSuggestStopPoint(Boolean hasOneCoordinate, CoordStopPoint coordList) {
        this.hasOneCoordinate = hasOneCoordinate;
        this.coordList = coordList;
    }
}
