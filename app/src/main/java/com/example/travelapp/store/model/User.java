package com.example.travelapp.store.model;

public class User {
    private String userId;
    private String accessToken;

    public User(String userId, String accessToken) {
        this.userId = userId;
        this.accessToken = accessToken;
    }

    public String getUserId() {
        return userId;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
