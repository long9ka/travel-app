package com.example.travelapp.api.service;

import com.example.travelapp.api.model.request.ReqFacebookLogin;
import com.example.travelapp.api.model.request.ReqLogin;
import com.example.travelapp.api.model.request.ReqRegister;
import com.example.travelapp.api.model.response.ResFacebookLogin;
import com.example.travelapp.api.model.response.ResLogin;
import com.example.travelapp.api.model.response.ResRegister;
import com.example.travelapp.api.model.response.ResUserInfo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserService {

    @POST("/user/login")
    Call<ResLogin> login(@Body ReqLogin reqLogin);

    @POST("/user/register")
    Call<ResRegister> register(@Body ReqRegister reqRegister);

    @POST("/user/login/by-facebook")
    Call<ResFacebookLogin> loginFacebook(@Body ReqFacebookLogin reqFacebookLogin);

    @GET("/user/info")
    Call<ResUserInfo> auth(@Header("Authorization") String string);
}
