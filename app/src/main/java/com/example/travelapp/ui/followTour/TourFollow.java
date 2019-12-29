package com.example.travelapp.ui.followTour;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.travelapp.R;
import com.example.travelapp.api.model.request.StopPoint;
import com.example.travelapp.api.model.response.ResStopPoint;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class TourFollow extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private LatLng myLocation;
    private FusedLocationProviderClient fusedLocationClient;
    private List<ResStopPoint> stopPointList;
    private List<StopPoint> stopPoints;
    private String startDate, endDate;
    private int clickDatePicker;
    private TextView startDateTextView, endDateTextView;
    private String id;
    private int locationRequestCode = 1000;
    private double wayLatitude = 0.0, wayLongitude = 0.0;
    private ListView lvStop;
    Button btnAudio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_follow);
        btnAudio=findViewById(R.id.audio);
        btnAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent=new Intent(getApplicationContext(),sound.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap=googleMap;
    }
}
