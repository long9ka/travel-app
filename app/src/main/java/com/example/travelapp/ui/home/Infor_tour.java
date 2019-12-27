package com.example.travelapp.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.travelapp.R;
import com.example.travelapp.api.model.request.ReqReviewTour;
import com.example.travelapp.api.model.response.ResHistoryStopPoints;
import com.example.travelapp.api.model.response.ResSetStopPoints;
import com.example.travelapp.api.service.RetrofitClient;
import com.example.travelapp.api.service.UserService;
import com.example.travelapp.store.UserStore;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Infor_tour extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infor_tour);
        TextView tourname = findViewById(R.id.tour_name);
        ImageView backgound = findViewById(R.id.background);
        TextView costs = findViewById(R.id.costs);
        TextView people = findViewById(R.id.people);
        CheckBox isPrivate = findViewById(R.id.is_private);
        Button send = findViewById(R.id.send);
        final RatingBar ratingBar = findViewById(R.id.rating);
        final EditText feedback = findViewById(R.id.review);
        
        UserStore userStore = new UserStore(this);
        final UserService userService = RetrofitClient.getUserService();

        final String accessToken = userStore.getUser().getAccessToken();
        final String tourId = getIntent().getStringExtra("tourId");
        
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("ahihi", accessToken);
                Log.i("ahihi", "bien ne: " + tourId +" " + ratingBar.getRating()+ " " + feedback.getText());
                Call<ResSetStopPoints> call = userService.sendFeedback(accessToken, new ReqReviewTour(
                        tourId,
                        String.valueOf(Math.round(ratingBar.getRating())),
                        feedback.getText().toString()
                ));

                call.enqueue(new Callback<ResSetStopPoints>() {
                    @Override
                    public void onResponse(Call<ResSetStopPoints> call, Response<ResSetStopPoints> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Rating successful", Toast.LENGTH_SHORT).show();
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
                    public void onFailure(Call<ResSetStopPoints> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Review failure", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
