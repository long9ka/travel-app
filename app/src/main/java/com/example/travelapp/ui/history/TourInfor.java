package com.example.travelapp.ui.history;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.travelapp.R;

public class TourInfor extends AppCompatActivity {
    ImageButton delete;
    Button edit, stop;
    EditText name, adult, child, min, max;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_infor);
//        anh xa
        delete= (ImageButton) findViewById(R.id.tourinfo_delete);
        edit=(Button) findViewById(R.id.tourinfo_edit);
        stop= (Button) findViewById(R.id.tourinfo_stop);
        name=(EditText) findViewById(R.id.tourinfo_name);
        adult=(EditText) findViewById(R.id.tourinfo_adult);
        child=(EditText) findViewById(R.id.tourinfo_childs);
        min=(EditText) findViewById(R.id.tourinfo_min_cost);
        max=(EditText) findViewById(R.id.tourinfo_max_cost);
    }


}
