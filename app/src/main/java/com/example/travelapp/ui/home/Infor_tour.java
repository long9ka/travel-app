package com.example.travelapp.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.travelapp.R;
import com.example.travelapp.ui.CommentListActivity;
import com.example.travelapp.ui.MembersActivity;
import com.example.travelapp.ui.ReviewTourActivity;
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

    String tourId, accessToken;
    UserStore userStore;
    UserService userService;
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_info_tour, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.review) {
            startActivity(new Intent(getApplicationContext(), ReviewTourActivity.class).putExtra("tourId", tourId));
        }
        if (item.getItemId() == R.id.comments) {
            startActivity(new Intent(getApplicationContext(), CommentListActivity.class).putExtra("tourId", tourId));
        }
        if (item.getItemId() == R.id.members) {
            startActivity(new Intent(getApplicationContext(), MembersActivity.class).putExtra("tourId", tourId));
        }
        if (item.getItemId() == R.id.stop_points) {
            Log.i("ahihi", "click stop points");
        }
        if (item.getItemId() == R.id.comments_stop_points) {
            Log.i("ahihi", "click comments stop points");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infor_tour);
        final TextView tourname = findViewById(R.id.tour_name);
        ImageView backgound = findViewById(R.id.background);
        final TextView costs = findViewById(R.id.costs);
        final TextView people = findViewById(R.id.people);
        final CheckBox isPrivate = findViewById(R.id.is_private);
        Button send = findViewById(R.id.send);
        
        final RatingBar ratingBar = findViewById(R.id.rating);
        final EditText feedback = findViewById(R.id.review);
        
        userStore = new UserStore(this);
        userService = RetrofitClient.getUserService();

        accessToken = userStore.getUser().getAccessToken();
        tourId = getIntent().getStringExtra("tourId");
        
        // get tourinfo
        Call<ResHistoryStopPoints> call = userService.getHitoryStopPoint(accessToken, tourId);
        call.enqueue(new Callback<ResHistoryStopPoints>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<ResHistoryStopPoints> call, Response<ResHistoryStopPoints> response) {
                if (response.isSuccessful()) {
                    ResHistoryStopPoints tour = response.body();
                    tourname.setText(tour.getName());
                    costs.setText("$: " + tour.getMinCost() + " - " + tour.getMaxCost());
                    people.setText("Adults: " + tour.getAdults() + ", Child: " + tour.getChilds());
                    isPrivate.setChecked(tour.getIsPrivate());
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
                Toast.makeText(getApplicationContext(), "Review failure", Toast.LENGTH_SHORT).show();
            }
        });
        
        // danh gia tour
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
