package com.example.travelapp.api.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReqReviewTour {

    @SerializedName("tourId")
    @Expose
    private String tourId;
    @SerializedName("point")
    @Expose
    private String point;
    @SerializedName("review")
    @Expose
    private String review;

    public ReqReviewTour(String tourId, String point, String review) {
        this.tourId = tourId;
        this.point = point;
        this.review = review;
    }
}
