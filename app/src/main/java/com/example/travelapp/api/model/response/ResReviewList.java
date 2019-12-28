package com.example.travelapp.api.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResReviewList {
    @SerializedName("reviewList")
    @Expose
    private List<ReviewList> reviewList = null;

    public List<ReviewList> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<ReviewList> reviewList) {
        this.reviewList = reviewList;
    }
}
