package com.example.travelapp.api.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CoordStopPoint {
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("long")
    @Expose
    private String _long;

    public CoordStopPoint(String lat, String _long) {
        this.lat = lat;
        this._long = _long;
    }
}
