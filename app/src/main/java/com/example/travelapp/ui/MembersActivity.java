package com.example.travelapp.ui;

import android.os.Bundle;
import android.util.Log;
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

public class MembersActivity extends AppCompatActivity {

    private UserStore userStore;
    private UserService userService;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members);
        userStore = new UserStore(this);
        userService = RetrofitClient.getUserService();
        String accessToken = userStore.getUser().getAccessToken();
        String tourId = getIntent().getStringExtra("tourId");
        Call<ResHistoryStopPoints> call = userService.getHitoryStopPoint(accessToken, tourId);
        call.enqueue(new Callback<ResHistoryStopPoints>() {
            @Override
            public void onResponse(Call<ResHistoryStopPoints> call, Response<ResHistoryStopPoints> response) {
                if (response.isSuccessful()) {
                    Log.i("ahihi", String.valueOf(response.body().getMembers().size()));
                    if (response.body().getMembers().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Empty", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        ListView listView = findViewById(R.id.list_item);
                        MembersAdapter adapter = new MembersAdapter(getApplicationContext(), R.layout.member_adapter, response.body().getMembers());
                        listView.setAdapter(adapter);
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
                Toast.makeText(getApplicationContext(), "Load members failure", Toast.LENGTH_LONG).show();
            }
        });
        
    }
}
