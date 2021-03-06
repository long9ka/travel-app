package com.example.travelapp.ui;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.travelapp.api.model.request.ReqUpdateTour;
import com.example.travelapp.api.model.response.ResHistoryStopPoints;
import com.example.travelapp.api.model.response.ResUpdateTour;
import com.example.travelapp.api.service.RetrofitClient;
import com.example.travelapp.api.service.UserService;
import com.example.travelapp.store.UserStore;
import com.google.gson.Gson;
import com.example.travelapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateTourActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, NumberPicker.OnValueChangeListener {


    private TextView startDateTextView, endtDateTextView;
    private String startDate, endDate, adults = "10", child = "10", isPrivate = "false";
    private int clickDatePicker;
    Button btnSubmit;

    private static String getDate(long milliSeconds) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_tour);
        String tourId = getIntent().getStringExtra("tourId");
        final UserStore userStore = new UserStore(this);
        final UserService userService = RetrofitClient.getUserService();

        final EditText tourNameEditText = findViewById(R.id.tour_name);
        final EditText minCostEditText = findViewById(R.id.min_cost);
        final EditText maxCostEditText = findViewById(R.id.max_cost);
        final CheckBox isPrivateCheckBox = findViewById(R.id.is_private);
        final NumberPicker adultsPicker = findViewById(R.id.adults);
        final NumberPicker childPicker = findViewById(R.id.childs);
        btnSubmit=findViewById(R.id.update_tour_button);
        startDateTextView = findViewById(R.id.start_date);
        endtDateTextView = findViewById(R.id.end_date);

        String token = userStore.getUser().getAccessToken();
        Call<ResHistoryStopPoints> call = userService.getHitoryStopPoint(token, tourId);
        call.enqueue(new Callback<ResHistoryStopPoints>() {
            @Override
            public void onResponse(Call<ResHistoryStopPoints> call, Response<ResHistoryStopPoints> response) {
                if (response.isSuccessful()) {
                    tourNameEditText.setText(response.body().getName());
                    minCostEditText.setText(response.body().getMinCost());
                    maxCostEditText.setText(response.body().getMaxCost());
                    startDateTextView.setText("abc");
                    endtDateTextView.setText("xyz");
                    isPrivateCheckBox.setChecked(response.body().getIsPrivate());
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

                    adultsPicker.setMinValue(0);
                    adultsPicker.setMaxValue(1000);
                    adultsPicker.setValue(response.body().getAdults());
                    //adultsPicker.setOnValueChangedListener(this);

                    childPicker.setMinValue(0);
                    childPicker.setMaxValue(1000);
                    childPicker.setValue(response.body().getChilds());
                    //childPicker.setOnValueChangedListener(this);

                    startDateTextView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            clickDatePicker = 1;
                            showDatePicker();
                        }
                    });

                    endtDateTextView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            clickDatePicker = 2;
                            showDatePicker();
                        }
                    });
//                    update
                    final ResHistoryStopPoints res=response.body();

                    btnSubmit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int updateId=res.getId();
                            String updateName=tourNameEditText.getText().toString();
                            long updateStartDate=1232312323;
                            long updateEndDate=1232312323;
                            int updateAdults=adultsPicker.getValue();
                            int updateChilds=childPicker.getValue();
                            int updateMincost=Integer.parseInt(minCostEditText.getText().toString());
                            int updateMaxcost=Integer.parseInt(maxCostEditText.getText().toString());
                            int updateStatus=res.getStatus();
                            boolean updateIsprivate;
                            if(isPrivateCheckBox.isChecked()){
                                updateIsprivate=true;
                            }else{
                                updateIsprivate=false;
                            }
                            Log.d("INFO",updateName);
                            if(updateName.length() == 0){
                                Toast.makeText(getApplicationContext(),"Fill out tour's name",Toast.LENGTH_LONG).show();
                            }else {
                                final ReqUpdateTour reqUpdateTour=new ReqUpdateTour(
                                        updateId,
                                        updateName,
                                        updateStartDate,
                                        updateEndDate,
                                        updateAdults,
                                        updateChilds,
                                        updateMincost,
                                        updateMaxcost,
                                        updateIsprivate,
                                        updateStatus

                                );
                                Log.d("info",updateName);
                                Log.d("info",updateName);
                                Log.d("info",updateName);
                                update(reqUpdateTour,res,userService,userStore);
                            }
                        }
                    });
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
            public void onFailure(Call<ResHistoryStopPoints> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Get History Tour: Failure", Toast.LENGTH_LONG).show();
            }
        });



    }
    public  void update(ReqUpdateTour reqUpdateTour,final ResHistoryStopPoints res, final UserService userService, final UserStore userStore){

        Toast.makeText(getApplicationContext(), "" + new Gson().toJson(reqUpdateTour), Toast.LENGTH_SHORT).show();
        Log.d("TTTTS", "update: " + new Gson().toJson(reqUpdateTour));
        Call<ResUpdateTour> call=userService.sendData(userStore.getUser().getAccessToken(),reqUpdateTour);
        call.enqueue(new Callback<ResUpdateTour>() {
            @Override
            public void onResponse(Call<ResUpdateTour> call, Response<ResUpdateTour> response) {
                if(response.code() ==200)
                    Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_LONG).show();
                else {
                    Toast.makeText(getApplicationContext(), ""+response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResUpdateTour> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"fail",Toast.LENGTH_LONG).show();

            }
        });
        finish();
    }

    private void showDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                Objects.requireNonNull(this),
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
            endtDateTextView.setText(dayOfMonth + "/" + month + "/" + year);
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
