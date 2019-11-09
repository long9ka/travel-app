package com.example.travelapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.travelapp.R;
import com.example.travelapp.api.model.ResponseModels.Sucess.Auth;
import com.example.travelapp.api.model.ResponseModels.Sucess.UserInfo;
import com.example.travelapp.api.service.ApiUtils;
import com.example.travelapp.api.service.UserService;
import com.example.travelapp.local.UserLocal;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SplashActivity extends AppCompatActivity {

    final private int SPLASH_TIME_OUT = 500;
    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        userService = ApiUtils.getUserService();
        login();

        // TODO : load token & authentication, finish after 0.5 second
    }

    private void login() {
        UserLocal.getStore(getApplicationContext());
        Call<UserInfo> call = userService.auth(UserLocal.getToken());
        call.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                loginMainActivity(response.isSuccessful());
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                Toast.makeText(getApplicationContext(), R.string.login_failed, Toast.LENGTH_LONG).show();
            }
        });

    }

    private void loginMainActivity(final boolean classActivity) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), (classActivity)? MainActivity.class : LoginActivity.class));
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
