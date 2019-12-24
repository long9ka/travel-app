package com.example.travelapp.api.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResHistoryTourUser {
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("tours")
    @Expose
    private List<Tour> tours = null;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Tour> getTours() {
        return tours;
    }

    public void setTours(List<Tour> tours) {
        this.tours = tours;
    }
}
