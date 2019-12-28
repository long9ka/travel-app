package com.example.travelapp.api.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResCommentList {
    @SerializedName("commentList")
    @Expose
    private List<CommentList> commentList = null;

    public List<CommentList> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<CommentList> commentList) {
        this.commentList = commentList;
    }
}
