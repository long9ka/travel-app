package com.example.travelapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.travelapp.R;
import com.example.travelapp.api.model.request.ReqPasswordRecovery;
import com.example.travelapp.api.model.request.ReqVerifyOtpRecovery;
import com.example.travelapp.api.model.response.ResPasswordRecovery;
import com.example.travelapp.api.model.response.ResVerifyOtpRecovery;
import com.example.travelapp.api.service.RetrofitClient;
import com.example.travelapp.ui.valid.Validation;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PasswordRecovery extends AppCompatActivity {

    private String type = "email";
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_recovery);

        final EditText EmailEditText = findViewById(R.id.email);
        final Button sendToEmail = findViewById(R.id.send);

        sendToEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EmailEditText.getText().toString().contains("@")) {
                    type = "email";
                } else {
                    type = "phone";
                }
                Call<ResPasswordRecovery> call = RetrofitClient.getUserService().sendReqOtpRecovery(new ReqPasswordRecovery(type, EmailEditText.getText().toString()));
                call.enqueue(new Callback<ResPasswordRecovery>() {
                    @Override
                    public void onResponse(Call<ResPasswordRecovery> call, Response<ResPasswordRecovery> response) {
                        if (response.isSuccessful()) {
                            userId = response.body().getUserId().toString();
                            Toast.makeText(getApplicationContext(), "Send To " + EmailEditText.getText().toString(), Toast.LENGTH_SHORT).show();
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
                    public void onFailure(Call<ResPasswordRecovery> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Reset password failed", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        final EditText newPasswordEditText = findViewById(R.id.new_password);
        final EditText otpCode = findViewById(R.id.otp_code);
        final Button confirmButton = findViewById(R.id.confirm_button);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String newPassword = newPasswordEditText.getText().toString();

                if (!Validation.isPasswordValid(newPassword)) {
                    newPasswordEditText.setError("Password must be >5 characters");
                }
                confirmButton.setEnabled(Validation.isPasswordValid(newPassword));
            }
        };

        newPasswordEditText.addTextChangedListener(textWatcher);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<ResVerifyOtpRecovery> call = RetrofitClient.getUserService().verifyOtpRecovery(new ReqVerifyOtpRecovery(userId, newPasswordEditText.getText().toString(), otpCode.getText().toString()));
                call.enqueue(new Callback<ResVerifyOtpRecovery>() {
                    @Override
                    public void onResponse(Call<ResVerifyOtpRecovery> call, Response<ResVerifyOtpRecovery> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Reset password successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
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
                    public void onFailure(Call<ResVerifyOtpRecovery> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Reset password failed", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}
