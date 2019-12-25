package com.example.travelapp.api.service;

import com.example.travelapp.api.model.request.ReqCreateTour;
import com.example.travelapp.api.model.request.ReqFacebookLogin;
import com.example.travelapp.api.model.request.ReqLogin;
import com.example.travelapp.api.model.request.ReqPasswordRecovery;
import com.example.travelapp.api.model.request.ReqRegister;
import com.example.travelapp.api.model.request.ReqSetStopPoints;
import com.example.travelapp.api.model.request.ReqSuggestStopPoint;
import com.example.travelapp.api.model.request.ReqVerifyOtpRecovery;
import com.example.travelapp.api.model.response.ResHistoryStopPoints;
import com.example.travelapp.api.model.response.ResHistoryTourUser;
import com.example.travelapp.api.model.response.ResCreateTour;
import com.example.travelapp.api.model.response.ResFacebookLogin;
import com.example.travelapp.api.model.response.ResListTour;
import com.example.travelapp.api.model.response.ResLogin;
import com.example.travelapp.api.model.response.ResPasswordRecovery;
import com.example.travelapp.api.model.response.ResRegister;
import com.example.travelapp.api.model.response.ResSendVerifyCode;
import com.example.travelapp.api.model.response.ResSetStopPoints;
import com.example.travelapp.api.model.response.ResSuggestStopPoint;
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

    @POST("/tour/create")
    Call<ResCreateTour> createTour(@Header("Authorization") String string, @Body ReqCreateTour reqCreateTour);

    @POST("/tour/set-stop-points")
    Call<ResSetStopPoints> setStopPoints(@Header("Authorization") String string, @Body ReqSetStopPoints reqSetStopPoints);

    @POST("/tour/suggested-destination-list")
    Call<ResSuggestStopPoint> getSuggestedDestinations(@Header("Authorization")String string, @Body ReqSuggestStopPoint reqSuggestStopPoint);

    @GET("/tour/history-user")
    Call<ResHistoryTourUser> getHistoryTourUser(@Header("Authorization")String string, @Query("pageIndex")String number, @Query("pageSize") String size);
    
    @GET("/tour/info")
    Call<ResHistoryStopPoints> getHitoryStopPoint(@Header("Authorization")String string, @Query("tourId")String tourId);
}
