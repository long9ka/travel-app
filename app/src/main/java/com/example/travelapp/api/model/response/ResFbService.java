package com.example.travelapp.api.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResFbService {
    @SerializedName("feedbackList")
    @Expose
    private List<FeedbackList> feedbackList = null;

    public List<FeedbackList> getFeedbackList() {
        return feedbackList;
    }

    public void setFeedbackList(List<FeedbackList> feedbackList) {
        this.feedbackList = feedbackList;
    }
}
