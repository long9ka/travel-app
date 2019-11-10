package com.example.travelapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.travelapp.R;
import com.example.travelapp.api.model.RequestModels.Register;
import com.example.travelapp.api.model.ResponseModels.Sucess.UserRegister;
import com.example.travelapp.api.service.ApiUtils;
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

        userService = ApiUtils.getUserService();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register(
                        emailEditText.getText().toString(),
                        phoneEditText.getText().toString(),
                        passwordEditText.getText().toString(),
                        fullNameEditText.getText().toString(),
                        addressEditText.getText().toString(),
                        genderEditText.getText().toString(),
                        dobEditText.getText().toString()
                );
            }
        });

    }

    private void register(String email, String phone, String password, String fullName, String address, String gender, String birthday) {
        Call<UserRegister> call = userService.register(new Register(email, phone, password, fullName, address, gender, birthday));
        call.enqueue(new Callback<UserRegister>() {
            @Override
            public void onResponse(Call<UserRegister> call, Response<UserRegister> response) {
                if (response.isSuccessful()) {
                    callBackLogin();
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
            public void onFailure(Call<UserRegister> call, Throwable t) {
                Toast.makeText(getApplicationContext(), R.string.register_failed, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void callBackLogin() {
        Toast.makeText(getApplicationContext(), R.string.register_success, Toast.LENGTH_SHORT).show();
        finish();
    }
}
