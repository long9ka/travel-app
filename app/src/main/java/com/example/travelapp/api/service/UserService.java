package com.example.travelapp.api.service;

import com.example.travelapp.api.model.request.ReqFacebookLogin;
import com.example.travelapp.api.model.request.ReqLogin;
import com.example.travelapp.api.model.request.ReqPasswordRecovery;
import com.example.travelapp.api.model.request.ReqRegister;
import com.example.travelapp.api.model.request.ReqVerifyOtpRecovery;
import com.example.travelapp.api.model.response.ResFacebookLogin;
import com.example.travelapp.api.model.response.ResListTour;
import com.example.travelapp.api.model.response.ResLogin;
import com.example.travelapp.api.model.response.ResPasswordRecovery;
import com.example.travelapp.api.model.response.ResRegister;
import com.example.travelapp.api.model.response.ResSendVerifyCode;
import com.example.travelapp.api.model.response.ResUserInfo;
import com.example.travelapp.api.model.response.ResVerifyCode;
import com.example.travelapp.api.model.response.ResVerifyOtpRecovery;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserService {

    @POST("/user/login")
    Call<ResLogin> login(@Body ReqLogin reqLogin);

    @POST("/user/register")
    Call<ResRegister> register(@Body ReqRegister reqRegister);

    @POST("/user/login/by-facebook")
    Call<ResFacebookLogin> loginFacebook(@Body ReqFacebookLogin reqFacebookLogin);

    @GET("/user/info")
    Call<ResUserInfo> auth(@Header("Authorization") String string);

    @GET("/tour/list")
    Call<ResListTour> getListTour(@Header("Authorization") String string);

    @GET("/user/send-active")
    Call<ResSendVerifyCode> sendVerifyCode(@Query("userId") String userId, @Query("type") String type);

    @GET("/user/active")
    Call<ResVerifyCode> verifyCode(@Query("userId") String userId, @Query("type") String type, @Query("verifyCode") String code);

    @POST("/user/request-otp-recovery")
    Call<ResPasswordRecovery> sendReqOtpRecovery(@Body ReqPasswordRecovery reqPasswordRecovery);

    @POST("/user/verify-otp-recovery")
    Call<ResVerifyOtpRecovery> verifyOtpRecovery(@Body ReqVerifyOtpRecovery reqVerifyOtpRecovery);
}
