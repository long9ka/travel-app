package com.example.travelapp.ui;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.travelapp.R;
import com.example.travelapp.api.model.request.ReqRegister;
import com.example.travelapp.api.model.response.ResRegister;
import com.example.travelapp.api.service.RetrofitClient;
import com.example.travelapp.api.service.UserService;
import com.example.travelapp.ui.valid.Validation;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private UserService userService;

    private TextView datePickerTextView;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText fullNameEditText = findViewById(R.id.full_name);
        final EditText emailEditText = findViewById(R.id.email);
        final EditText phoneEditText = findViewById(R.id.phone);
        final EditText addressEditText = findViewById(R.id.address);
        final RadioGroup radioGroup = findViewById(R.id.gender);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button registerButton = findViewById(R.id.register);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String fullName = fullNameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String phone = phoneEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String address = addressEditText.getText().toString();

                if (!Validation.isFullNameValid(fullName)) {
                    fullNameEditText.setError("Full name is required");
                } else if (!Validation.isEmailValid(email)) {
                    emailEditText.setError("Email do not match");
                } else if (!Validation.isPhoneValid(phone)) {
                    phoneEditText.setError("Phone do not match");
                } else if (!Validation.isAddressValid(address)) {
                    addressEditText.setError("Address is required");
                } else if (!Validation.isPasswordValid(password)) {
                    passwordEditText.setError("Password must be >5 characters");
                }
                registerButton.setEnabled(
                        Validation.isPasswordValid(password) &&
                        Validation.isAddressValid(address) &&
                        Validation.isFullNameValid(fullName) &&
                        Validation.isEmailValid(email) &&
                        Validation.isPhoneValid(phone)
                );

            }
        };

        fullNameEditText.addTextChangedListener(textWatcher);
        emailEditText.addTextChangedListener(textWatcher);
        phoneEditText.addTextChangedListener(textWatcher);
        addressEditText.addTextChangedListener(textWatcher);
        passwordEditText.addTextChangedListener(textWatcher);

        userService = RetrofitClient.getUserService();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
                execRegister(new ReqRegister(
                        emailEditText.getText().toString(),
                        phoneEditText.getText().toString(),
                        passwordEditText.getText().toString(),
                        fullNameEditText.getText().toString(),
                        addressEditText.getText().toString(),
                        (radioButton.getText().toString().equals("Male")) ? "1" : "0",
                        date
                ));
            }
        });

        // DatePicker
        datePickerTextView = findViewById(R.id.dob);
        datePickerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
    }

    final void showDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        date = month + "/" + dayOfMonth + "/" + year;
        datePickerTextView.setText(dayOfMonth + "/" + month + "/" + year);
    }

    private void execRegister(ReqRegister reqRegister) {
        Call<ResRegister> call = userService.register(reqRegister);
        call.enqueue(new Callback<ResRegister>() {
            @Override
            public void onResponse(Call<ResRegister> call, Response<ResRegister> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Register successful, please login", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        String msg = jsonObject.getJSONArray("message").getJSONObject(0).get("msg").toString();
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResRegister> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Register failed", Toast.LENGTH_LONG).show();
            }
        });
    }
}
