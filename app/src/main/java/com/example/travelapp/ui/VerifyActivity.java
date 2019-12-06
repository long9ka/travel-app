package com.example.travelapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.travelapp.R;
import com.example.travelapp.api.model.response.ResSendVerifyCode;
import com.example.travelapp.api.model.response.ResVerifyCode;
import com.example.travelapp.api.service.RetrofitClient;
import com.example.travelapp.store.UserStore;
import com.example.travelapp.store.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyActivity extends AppCompatActivity {

    private UserStore userStore;
    private String type = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);

        // Load userStore
        userStore = new UserStore(this);

        final String accessToken = getIntent().getStringExtra("accessToken");

        final RadioGroup radioGroup = findViewById(R.id.type);
        final Button sendCode = findViewById(R.id.send_code);
        final EditText codeEditText = findViewById(R.id.otp_code);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                final RadioButton radioButton = findViewById(checkedId);
                if (radioButton.getText().toString().equals("Via Email")) {
                    type = "email";
                } else {
                    type = "phone";
                }
            }
        });

        sendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<ResSendVerifyCode> call = RetrofitClient.getUserService().sendVerifyCode(userStore.getUser().getUserId(), type);
                call.enqueue(new Callback<ResSendVerifyCode>() {
                    @Override
                    public void onResponse(Call<ResSendVerifyCode> call, Response<ResSendVerifyCode> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Send To " + response.body().getSendTo(), Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response.errorBody().string());
                                Toast.makeText(getApplicationContext(), jsonObject.get("message").toString(), Toast.LENGTH_LONG).show();
                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResSendVerifyCode> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Verify account failed", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        final Button confirmButton = findViewById(R.id.confirm_button);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<ResVerifyCode> call = RetrofitClient.getUserService().verifyCode(userStore.getUser().getUserId(), type, codeEditText.getText().toString());
                call.enqueue(new Callback<ResVerifyCode>() {
                    @Override
                    public void onResponse(Call<ResVerifyCode> call, Response<ResVerifyCode> response) {
                        if (response.isSuccessful()) {
                            userStore.setUser(new User(userStore.getUser().getUserId(), accessToken));
                            Toast.makeText(getApplicationContext(), "Verify " + type + " successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), SplashActivity.class));
                            finish();
                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response.errorBody().string());
                                Toast.makeText(getApplicationContext(), jsonObject.get("message").toString(), Toast.LENGTH_LONG).show();
                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResVerifyCode> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Verify account failed", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}
