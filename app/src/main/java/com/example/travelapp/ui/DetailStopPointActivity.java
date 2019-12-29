package com.example.travelapp.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.travelapp.R;
import com.example.travelapp.api.service.RetrofitClient;
import com.example.travelapp.api.service.UserService;
import com.example.travelapp.store.UserStore;

public class DetailStopPointActivity extends AppCompatActivity {
    private UserService userService;
    private UserStore userStore;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_stop_point);
        
        userStore = new UserStore(this);
        userService = RetrofitClient.getUserService();
        
        String accessToken = userStore.getUser().getAccessToken();
        String Id = getIntent().getStringExtra("Id");
        /**
         * Lay chi tiet cua diem dung theo id
         * /tour/get/service-detail
         * 
         */

        /**
         * Lay danh sach cmt cua diem dung theo id
         * /tour/comment-list
         */

    }
}
