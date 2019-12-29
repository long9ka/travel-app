package com.example.travelapp.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.travelapp.R;

public class DetailStopPointActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_stop_point);
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
