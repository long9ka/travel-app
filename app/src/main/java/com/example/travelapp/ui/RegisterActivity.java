package com.example.travelapp.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.travelapp.R;
import com.example.travelapp.api.model.request.ReqRegister;
import com.example.travelapp.api.model.response.ResRegister;
import com.example.travelapp.api.service.RetrofitClient;
import com.example.travelapp.api.service.UserService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ImageButton backButton = findViewById(R.id.back);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final EditText fullNameEditText = findViewById(R.id.full_name);
        final EditText emailEditText = findViewById(R.id.email);
        final EditText phoneEditText = findViewById(R.id.phone);
        final EditText addressEditText = findViewById(R.id.address);
        final EditText genderEditText = findViewById(R.id.gender);
        final EditText dobEditText = findViewById(R.id.dob);
        final EditText passwordEditText = findViewById(R.id.password);
        Button registerButton = findViewById(R.id.register);

        userService = RetrofitClient.getUserService();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                execRegister(new ReqRegister(
                        emailEditText.getText().toString(),
                        phoneEditText.getText().toString(),
                        passwordEditText.getText().toString(),
                        fullNameEditText.getText().toString(),
                        addressEditText.getText().toString(),
                        genderEditText.getText().toString(),
                        dobEditText.getText().toString()
                ));
            }
        });

    }

    private void execRegister(ReqRegister reqRegister) {
        Call<ResRegister> call = userService.register(reqRegister);
        call.enqueue(new Callback<ResRegister>() {
            @Override
            public void onResponse(Call<ResRegister> call, Response<ResRegister> response) {
                if (response.isSuccessful()) {
                    redirectRegister();
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        String msg = jsonObject.getJSONArray("message").getJSONObject(0).get("msg").toString();
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResRegister> call, Throwable t) {
                Toast.makeText(getApplicationContext(), R.string.notification_register_failed, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void redirectRegister() {
        Toast.makeText(getApplicationContext(), R.string.notification_register_success, Toast.LENGTH_SHORT).show();
        finish();
    }
}
