package com.example.travelapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.travelapp.R;
import com.example.travelapp.api.model.response.ResHistoryStopPoints;
import com.example.travelapp.api.service.RetrofitClient;
import com.example.travelapp.api.service.UserService;
import com.example.travelapp.store.UserStore;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StopPointActivity extends AppCompatActivity {

    private UserStore userStore;
    private UserService userService;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_point);

        userStore = new UserStore(this);
        userService = RetrofitClient.getUserService();
        final String accessToken = userStore.getUser().getAccessToken();
        final String tourId = getIntent().getStringExtra("tourId");
        Call<ResHistoryStopPoints> call = userService.getHitoryStopPoint(accessToken, tourId);
        call.enqueue(new Callback<ResHistoryStopPoints>() {
            @Override
            public void onResponse(Call<ResHistoryStopPoints> call, final Response<ResHistoryStopPoints> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStopPoints().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Empty", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        ListView listView = findViewById(R.id.list_item);
                        AdapterStopPoints adapter = new AdapterStopPoints(getApplicationContext(), R.layout.adapter_stop_points, response.body().getStopPoints());
                        listView.setAdapter(adapter);
                        // click item
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                // start activity
                                startActivity(new Intent(getApplicationContext(), DetailStopPointActivity.class)
                                        .putExtra("Id", String.valueOf(response.body().getStopPoints().get(position).getServiceId())));
                            }
                        });
                    }
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
                Toast.makeText(getApplicationContext(), "Load stop points failure", Toast.LENGTH_LONG).show();
            }
        });
    }
}
