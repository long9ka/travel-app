package com.example.travelapp.api.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResListTour {

    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("tours")
    @Expose
    private List<ResTour> tours = null;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<ResTour> getTours() {
        return tours;
    }

    public void setTours(List<ResTour> tours) {
        this.tours = tours;
    }

}
