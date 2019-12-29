package com.example.travelapp.ui.createTour;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.travelapp.ui.MapsActivity;
import com.example.travelapp.R;
import com.example.travelapp.api.model.request.ReqCreateTour;
import com.example.travelapp.api.model.response.ResCreateTour;
import com.example.travelapp.api.service.RetrofitClient;
import com.example.travelapp.store.UserStore;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateTourFragment extends Fragment implements DatePickerDialog.OnDateSetListener, NumberPicker.OnValueChangeListener {

    private TextView startDateTextView, endDateTextView;
    private String startDate, endDate, adults = "10", child = "10", isPrivate = "false";
        private int clickDatePicker;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_create_tour, container, false);

        //test
        //startActivity(new Intent(root.getContext(), MapsActivity.class));

        final EditText tourNameEditText = root.findViewById(R.id.tour_name);
        final EditText minCostEditText = root.findViewById(R.id.min_cost);
        final EditText maxCostEditText = root.findViewById(R.id.max_cost);
        final CheckBox isPrivateCheckBox = root.findViewById(R.id.is_private);
        final NumberPicker adultsPicker = root.findViewById(R.id.adults);
        final NumberPicker childPicker = root.findViewById(R.id.childs);

        adultsPicker.setMinValue(0);
        adultsPicker.setMaxValue(1000);
        adultsPicker.setValue(10);
        adultsPicker.setOnValueChangedListener(this);

        childPicker.setMinValue(0);
        childPicker.setMaxValue(1000);
        childPicker.setValue(10);
        childPicker.setOnValueChangedListener(this);

        startDateTextView = root.findViewById(R.id.start_date);
        startDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickDatePicker = 1;
                showDatePicker();
            }
        });

        endDateTextView = root.findViewById(R.id.end_date);
        endDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickDatePicker = 2;
                showDatePicker();
            }
        });

        isPrivateCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isPrivate = "true";
                } else {
                    isPrivate = "false";
                }
            }
        });

        final Button createTourButton = root.findViewById(R.id.create_tour_button);
        createTourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final UserStore userStore = new UserStore(root.getContext());
                Call<ResCreateTour> call = RetrofitClient.
                        getUserService().
                        createTour(
                                userStore.getUser().getAccessToken(),
                                new ReqCreateTour(
                                        tourNameEditText.getText().toString(),
                                        startDate,
                                        endDate,
                                        isPrivate,
                                        minCostEditText.getText().toString(),
                                        maxCostEditText.getText().toString(),
                                        adults,
                                        child
                                )
                        );
                call.enqueue(new Callback<ResCreateTour>() {
                    @Override
                    public void onResponse(Call<ResCreateTour> call, Response<ResCreateTour> response) {
                        if (response.isSuccessful()) {
                            Intent intent = new Intent(root.getContext(), MapsActivity.class);
                            intent.putExtra("id", response.body().getId());
                            Log.i("xxx id ne luon", response.body().getId());
                            startActivity(intent);
                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response.errorBody().string());
                                String msg = jsonObject.getJSONArray("message").getJSONObject(0).get("msg").toString();
                                Toast.makeText(root.getContext(), msg, Toast.LENGTH_LONG).show();
                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResCreateTour> call, Throwable t) {
                        Toast.makeText(root.getContext(), "Form Create Tour: Failure", Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

        return root;
    }



    private void showDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                Objects.requireNonNull(getActivity()),
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
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.YEAR, year);
        if (clickDatePicker == 1) {
            startDate = String.valueOf(cal.getTimeInMillis() / 1000);
            startDateTextView.setText(dayOfMonth + "/" + month + "/" + year);
        } else {
            endDate = String.valueOf(cal.getTimeInMillis() / 1000);
            endDateTextView.setText(dayOfMonth + "/" + month + "/" + year);
        }
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        if (picker.getId() == R.id.adults) {
            adults = String.valueOf(newVal);
        } else if (picker.getId() == R.id.childs ){
            child = String.valueOf(newVal);
        }
    }
}
