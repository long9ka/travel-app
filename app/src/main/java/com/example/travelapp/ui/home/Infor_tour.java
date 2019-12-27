package com.example.travelapp.ui.home;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.travelapp.R;
import com.example.travelapp.api.model.response.ResHistoryStopPoints;
import com.example.travelapp.api.service.RetrofitClient;
import com.example.travelapp.api.service.UserService;
import com.example.travelapp.store.UserStore;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Infor_tour extends AppCompatActivity {
    ImageButton delete;
    Button send;
    EditText adult, child, min, max, review;
    TextView name;
    RatingBar rate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infor_tour);
        //        anh xa
        delete = findViewById(R.id.tourinfo_delete);
        send = findViewById(R.id.submit_send);
        name = findViewById(R.id.tourinfo_name);
        adult = findViewById(R.id.tourinfo_adult);
        child = findViewById(R.id.tourinfo_childs);
        min = findViewById(R.id.tourinfo_min_cost);
        max = findViewById(R.id.tourinfo_max_cost);
        rate = findViewById(R.id.simpleRatingBar);
        review = findViewById(R.id.review);
        String str_rate;
//        get Intern
        final int tourID = getIntent().getIntExtra("ID", -1);
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
//                    Send review rating
                    send.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int star = rate.getNumStars();
                        }
                    });
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
