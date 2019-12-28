package com.example.travelapp.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.travelapp.R;
import com.example.travelapp.api.model.response.ResCommentList;
import com.example.travelapp.api.service.RetrofitClient;
import com.example.travelapp.api.service.UserService;
import com.example.travelapp.store.UserStore;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentListActivity extends AppCompatActivity {

    private UserStore userStore;
    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_list);
        
        userStore = new UserStore(this);
        userService = RetrofitClient.getUserService();
        String accessToken = userStore.getUser().getAccessToken();
        String tourId = getIntent().getStringExtra("tourId");
        Log.i("ahihi", tourId);
        Call<ResCommentList> call = userService.getComments(accessToken, tourId, "1", "1000");
        call.enqueue(new Callback<ResCommentList>() {
            @Override
            public void onResponse(Call<ResCommentList> call, Response<ResCommentList> response) {
                if (response.isSuccessful()) {
                    if (response.body().getCommentList().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "empty", Toast.LENGTH_SHORT).show();
                    } else {
                        ListView listView = findViewById(R.id.list_item);
                        CommentAdapter adapter = new CommentAdapter(getApplicationContext(), R.layout.comment_list_adapter, response.body().getCommentList());
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
            public void onFailure(Call<ResCommentList> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Load comments failure", Toast.LENGTH_LONG).show();
            }
        });
    }
}
