package com.example.travelapp.api.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResReviewTour {

    @SerializedName("tourId")
    @Expose
    private String tourId;
    @SerializedName("point")
    @Expose
    private String point;
    @SerializedName("review")
    @Expose
    private String review;

    public ResReviewTour(String tourId, String point, String review) {
        this.tourId = tourId;
        this.point = point;
        this.review = review;
    }
}
