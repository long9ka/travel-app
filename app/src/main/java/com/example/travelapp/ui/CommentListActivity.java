package com.example.travelapp.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.travelapp.R;
import com.example.travelapp.api.service.RetrofitClient;
import com.example.travelapp.api.service.UserService;
import com.example.travelapp.store.UserStore;

public class CommentListActivity extends AppCompatActivity {

    private UserStore userStore;
    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_list);
        
        userStore = new UserStore(this);
        userService = RetrofitClient.getUserService();
        String tourId = getIntent().getStringExtra("tourId");
        Log.i("ahihi", tourId);
    }
}
