package com.example.travelapp.api.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String retrofitUrl = "http://35.197.153.192:3000/";
    private static Retrofit retrofit;

    static {
        if (retrofit == null) {
            retrofit = new Retrofit
                    .Builder()
                    .baseUrl(retrofitUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
    }

    public static UserService getUserService() {
        return retrofit.create(UserService.class);
    }
}
