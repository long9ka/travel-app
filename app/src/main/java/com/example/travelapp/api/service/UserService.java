package com.example.travelapp.api.service;

import com.example.travelapp.api.model.RequestModels.Login;
import com.example.travelapp.api.model.ResponseModels.Sucess.UserInfo;
import com.example.travelapp.api.model.ResponseModels.Sucess.UserLogin;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserService {

    @POST("/user/login")
    Call<UserLogin> login(@Body Login login);

    @GET("/user/info")
    Call<UserInfo> auth(@Header("Authorization") String token);
}
