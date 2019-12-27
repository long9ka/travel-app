package com.example.travelapp.ui.history;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.travelapp.R;
import com.example.travelapp.api.model.response.ResHistoryStopPoints;
import com.example.travelapp.api.service.RetrofitClient;
import com.example.travelapp.api.service.UserService;
import com.example.travelapp.store.UserStore;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TourInfor extends AppCompatActivity {
    ImageButton delete;
    Button edit, stop;
    EditText name, adult, child, min, max;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_infor);
//        anh xa
        delete = findViewById(R.id.tourinfo_delete);
        edit = findViewById(R.id.tourinfo_edit);
        stop = findViewById(R.id.tourinfo_stop);
        name = findViewById(R.id.tourinfo_name);
        adult = findViewById(R.id.tourinfo_adult);
        child = findViewById(R.id.tourinfo_childs);
        min = findViewById(R.id.tourinfo_min_cost);
        max = findViewById(R.id.tourinfo_max_cost);
//        get Intern
        final int tourID = getIntent().getIntExtra("internId", -1);
        UserService userService = RetrofitClient.getUserService();
        UserStore userStore = new UserStore(this);
        Call<ResHistoryStopPoints> call = userService.getHitoryStopPoint(userStore.getUser().getAccessToken(), String.valueOf(tourID));
        call.enqueue(new Callback<ResHistoryStopPoints>() {
            @Override
            public void onResponse(Call<ResHistoryStopPoints> call, Response<ResHistoryStopPoints> response) {
                if (response.isSuccessful()) {
                    // thanh cong
                    ResHistoryStopPoints res = response.body();
//                    Set text
                    String tourName = res.getName();
                    name.setText(tourName);
                    Number count_Adults = res.getAdults();
                    adult.setText(count_Adults.toString());
                    Number count_Child = res.getChilds();
                    adult.setText(count_Child.toString());
                    String max = res.getMaxCost();
                    adult.setText(max);
                    String min = res.getMinCost();
                    adult.setText(min);
                } else {
                    // that bai
                }
            }

            @Override
            public void onFailure(Call<ResHistoryStopPoints> call, Throwable t) {
                // thuong la loi cua he thong
            }
        });

    }


}
