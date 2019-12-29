package com.example.travelapp.ui.history;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.travelapp.R;
import com.example.travelapp.ui.StopPointActivity;
import com.example.travelapp.ui.UpdateTourActivity;
import com.example.travelapp.api.model.request.ReqUpdateTour;
import com.example.travelapp.api.model.response.ResHistoryStopPoints;
import com.example.travelapp.api.model.response.ResUpdateTour;
import com.example.travelapp.api.service.RetrofitClient;
import com.example.travelapp.api.service.UserService;
import com.example.travelapp.store.UserStore;
import com.example.travelapp.ui.followTour.TourFollow;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TourInfor extends AppCompatActivity {
    ImageButton delete;
    Button edit, stop, follow;
    TextView name, adult, child, minC, maxC;
    CheckBox is_private;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_infor);
//        anh xa
        delete = findViewById(R.id.tourinfo_delete);
        edit = findViewById(R.id.tourinfo_edit);
        stop = findViewById(R.id.tourinfo_stop);
        name = findViewById(R.id.tourinfo_name);
        adult = findViewById(R.id.tourinfo_adult);
        child = findViewById(R.id.tourinfo_childs);
        minC = findViewById(R.id.tourinfo_min_cost);
        maxC = findViewById(R.id.tourinfo_max_cost);
        edit= findViewById(R.id.tourinfo_edit);
        follow=findViewById(R.id.tourinfo_follow);
        is_private=findViewById(R.id.tourinfo_is_private);
//        get Intern
        final String tourID = getIntent().getStringExtra("tourID");
        Log.d("INFO", tourID);
        final UserService userService = RetrofitClient.getUserService();
        final UserStore userStore = new UserStore(this);
        Call<ResHistoryStopPoints> call = userService.getHitoryStopPoint(userStore.getUser().getAccessToken(), tourID);
        call.enqueue(new Callback<ResHistoryStopPoints>() {
            @Override
            public void onResponse(Call<ResHistoryStopPoints> call, final Response<ResHistoryStopPoints> response) {
                if (response.isSuccessful()) {
                    Log.d("INFO", "into");

                    // thanh cong
                    final ResHistoryStopPoints res = response.body();
//                    Set text
                    String tourName = res.getName();
                    name.setText(tourName);
                    Number count_Adults = res.getAdults();
                    adult.setText(count_Adults.toString());
                    Number count_Child = res.getChilds();
                    child.setText(count_Child.toString());
                    Boolean is=res.getIsPrivate();
                    if(is){
                        is_private.setChecked(true);
                    }
                    else {
                        is_private.setChecked(false);
                    }
                    String maxCost=res.getMaxCost();
                    maxC.setText(maxCost);
                    String minCost=res.getMinCost();
                    minC.setText(minCost);
                    delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showAlertDialog(res,userService,userStore);
                        }
                    });
//                    Edit tour
                } else {
                    // that bai
                }
            }

            @Override
            public void onFailure(Call<ResHistoryStopPoints> call, Throwable t) {
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_edit;
                intent_edit=new Intent(getApplicationContext(), UpdateTourActivity.class);
                intent_edit.putExtra("tourId", tourID);
                startActivity(intent_edit);
            }
        });



        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fol=new Intent(getApplicationContext(), TourFollow.class);
                startActivity(fol);
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent stop=new Intent(getApplicationContext(),ListStop.class);
                stop.putExtra("tourId",tourID);
                startActivity(stop);
            }
        });

    }
    public void showAlertDialog(final ResHistoryStopPoints res, final UserService userService, final UserStore userStore){
        Log.d("Info","go");
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Travel App");
        builder.setMessage("Are you want to delete this tour?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ReqUpdateTour reqUpdateTour=new ReqUpdateTour(
                        res.getId(),
                        res.getName(),
                        1232312323,
                        1232132312,
                        res.getAdults(),
                        res.getChilds(),
                        Integer.parseInt(res.getMinCost()),
                        Integer.parseInt(res.getMaxCost()),
                        res.getIsPrivate(),
                        -1
                );
                Call<ResUpdateTour> call=userService.sendData(userStore.getUser().getAccessToken(),reqUpdateTour);
                Log.d("TTTTS", "update: " + new Gson().toJson(reqUpdateTour));
                call.enqueue(new Callback<ResUpdateTour>() {
                    @Override
                    public void onResponse(Call<ResUpdateTour> call, Response<ResUpdateTour> response) {
                        Toast.makeText(getApplicationContext(),"success"+String.valueOf(response.code()),Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<ResUpdateTour> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"fail",Toast.LENGTH_LONG).show();

                    }
                });

                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog= builder.create();
        alertDialog.show();
    }

}
