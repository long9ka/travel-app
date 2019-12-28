package com.example.travelapp.ui;


import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.travelapp.R;
import com.example.travelapp.api.model.response.ResReviewList;
import com.example.travelapp.api.service.RetrofitClient;
import com.example.travelapp.api.service.UserService;
import com.example.travelapp.store.UserStore;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewTourActivity extends AppCompatActivity {

    private UserStore userStore;
    private UserService userService;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_tour);

        userStore = new UserStore(this);
        userService = RetrofitClient.getUserService();
        String accessToken = userStore.getUser().getAccessToken();
        String tourId = getIntent().getStringExtra("tourId");
        Log.i("ahihi", tourId);
        Call<ResReviewList> call = userService.getReviewList(accessToken, tourId, "1", "1000");
        call.enqueue(new Callback<ResReviewList>() {
            @Override
            public void onResponse(Call<ResReviewList> call, Response<ResReviewList> response) {
                if (response.isSuccessful()) {
                    Log.i("ahihi", String.valueOf(response.body().getReviewList().size()));
                    ListView listView = findViewById(R.id.list_item);
                    // set adapter
                    ReviewTourAdapter adapter = new ReviewTourAdapter(getApplicationContext(), R.layout.adapter_review_tour, response.body().getReviewList());
                    listView.setAdapter(adapter);
                    
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
            public void onFailure(Call<ResReviewList> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Review failure", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
