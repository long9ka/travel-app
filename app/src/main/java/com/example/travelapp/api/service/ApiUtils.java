package com.example.travelapp.api.service;

public class ApiUtils {

    private static final String URL = "http://35.197.153.192:3000/";

    public static UserService getUserService() {
        return RetrofitClient.getClient(URL).create(UserService.class);
    }
}
