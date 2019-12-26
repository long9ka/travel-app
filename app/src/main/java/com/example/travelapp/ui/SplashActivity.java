package com.example.travelapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.travelapp.R;
import com.example.travelapp.api.model.response.ResUserInfo;
import com.example.travelapp.api.service.RetrofitClient;
import com.example.travelapp.api.service.UserService;
import com.example.travelapp.store.UserStore;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        authWithAccessToken(RetrofitClient.getUserService());
    }

    private void authWithAccessToken(UserService userService) {
        UserStore userStore = new UserStore(this);

        Call<ResUserInfo> call = userService.auth(userStore.getUser().getAccessToken());
        call.enqueue(new Callback<ResUserInfo>() {
            @Override
            public void onResponse(Call<ResUserInfo> call, Response<ResUserInfo> response) {
                redirect(response.isSuccessful());
            }

            @Override
            public void onFailure(Call<ResUserInfo> call, Throwable t) {
                Log.i("travel", t.getMessage());
                Toast.makeText(getApplicationContext(), "Load failed", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void redirect(boolean statusCode) {
        Intent intent = new Intent(this, (statusCode) ? MainActivity.class : LoginActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.nav_default_enter_anim, R.anim.nav_default_exit_anim);
    }
}
