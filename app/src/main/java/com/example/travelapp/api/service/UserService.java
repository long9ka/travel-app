package com.example.travelapp.api.service;

import com.example.travelapp.api.model.RequestModels.Login;
import com.example.travelapp.api.model.RequestModels.LoginFacebook;
import com.example.travelapp.api.model.RequestModels.Register;
import com.example.travelapp.api.model.ResponseModels.Sucess.UserInfo;
import com.example.travelapp.api.model.ResponseModels.Sucess.UserLogin;
import com.example.travelapp.api.model.ResponseModels.Sucess.UserLoginFacebook;
import com.example.travelapp.api.model.ResponseModels.Sucess.UserRegister;

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

    @POST("/user/register")
    Call<UserRegister> register(@Body Register register);

    @POST("/user/login/by-facebook")
    Call<UserLoginFacebook> loginFacebook(@Body LoginFacebook loginFacebook);
}
