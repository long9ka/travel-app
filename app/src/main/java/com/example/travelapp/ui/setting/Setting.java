package com.example.travelapp.ui.setting;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.travelapp.R;
import com.example.travelapp.api.model.request.ReqEditUserInfo;
import com.example.travelapp.api.model.response.ResSetStopPoints;
import com.example.travelapp.api.model.response.ResUserInfo;
import com.example.travelapp.api.service.RetrofitClient;
import com.example.travelapp.api.service.UserService;
import com.example.travelapp.store.UserStore;
import com.example.travelapp.ui.LoginActivity;
import com.example.travelapp.ui.SplashActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

public class Setting extends Fragment implements DatePickerDialog.OnDateSetListener {

    private ResUserInfo user;
    private TextView dob;
    private String isMale = "0";
    private String date;
    private UserService userService;
    private UserStore userStore;
    private TextView fullName, email;
    
    void callGetUserInfo() {
        final Call<ResUserInfo> call = userService.auth(userStore.getUser().getAccessToken());
        call.enqueue(new Callback<ResUserInfo>() {
            @Override
            public void onResponse(Call<ResUserInfo> call, Response<ResUserInfo> response) {
                if (response.isSuccessful()) {
                    user = response.body();
                    fullName.setText(user.getFullName());
                    email.setText(user.getEmail());
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
            public void onFailure(Call<ResUserInfo> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Load failed", Toast.LENGTH_LONG).show();
            }
        });
        
    }
    
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_setting, container, false);
        
        fullName = root.findViewById(R.id.full_name);
        email = root.findViewById(R.id.email);
        
        userStore = new UserStore(root.getContext());
        userService = RetrofitClient.getUserService();
        callGetUserInfo();
        
        // Edit
        ImageButton editProfile = root.findViewById(R.id.imageView);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                View view = getLayoutInflater().inflate(R.layout.popup_update_user, null);
                final EditText fullName = view.findViewById(R.id.full_name);
                final EditText email = view.findViewById(R.id.email);
                final EditText phone = view.findViewById(R.id.phone);
                final RadioGroup gender = view.findViewById(R.id.gender);
                // set user
                if (user.getFullName() != null && !user.getFullName().equals("")) {
                    fullName.setText(user.getFullName());
                }
                if (user.getEmail() != null && !user.getEmail().equals("")) {
                    email.setText(user.getEmail());
                }
                if (user.getPhone() != null && !user.getPhone().equals("")) {
                    phone.setText(user.getPhone());
                }
                if (user.getGender() == 0) {
                    isMale = "0";
                    gender.check(R.id.gender_male);
                } else {
                    isMale = "1";
                    gender.check(R.id.gender_female);
                }
                gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if (checkedId == R.id.gender_male) {
                            isMale = "0";
                        } else {
                            isMale = "1";
                        }
                    }
                });
                
                dob = view.findViewById(R.id.dob);
                dob.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDatePicker(root.getContext());
                    }
                });
                if (user.getDob() != null) {
                    String subDate = user.getDob();
                    subDate = subDate.substring(0, 10);
                    dob.setText(subDate);
                } else {
                    dob.setText("mm/dd/yy");
                }
                new AlertDialog.Builder(root.getContext())
                        .setView(view)
                        .setTitle("Update profile")
                        .setNegativeButton("Cancel", null)
                        .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Call<ResSetStopPoints> editUser = userService.updateUserInfo(
                                        userStore.getUser().getAccessToken(),
                                        new ReqEditUserInfo(
                                                fullName.getText().toString(),
                                                email.getText().toString(),
                                                phone.getText().toString(),
                                                isMale,
                                                date
                                        )
                                );
                                editUser.enqueue(new Callback<ResSetStopPoints>() {
                                    @Override
                                    public void onResponse(Call<ResSetStopPoints> call, Response<ResSetStopPoints> response) {
                                        if (response.isSuccessful()) {
                                            callGetUserInfo();
                                            Toast.makeText(getApplicationContext(), "Update user successful", Toast.LENGTH_SHORT).show();
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
                                    public void onFailure(Call<ResSetStopPoints> call, Throwable t) {
                                        Toast.makeText(getApplicationContext(), "Update User Info failed", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        })
                        .show();
            }
        });
        
        // Logout
        final Button logoutButton = root.findViewById(R.id.button_setting);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserStore userStore = new UserStore(root.getContext());
                userStore.removeUser();
                startActivity(new Intent(root.getContext(), LoginActivity.class));
                Objects.requireNonNull(getActivity()).finish();
            }
        });
        return root;
    }

    private void showDatePicker(Context context) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                context,
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
        date = year + "-" + month + "-" + dayOfMonth;
        dob.setText(dayOfMonth + "/" + month + "/" + year);
    }
}