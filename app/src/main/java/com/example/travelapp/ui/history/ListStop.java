package com.example.travelapp.ui.history;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.travelapp.R;
import com.example.travelapp.api.model.response.ResHistoryStopPoints;
import com.example.travelapp.api.model.response.ResSetStopPoints;
import com.example.travelapp.api.service.RetrofitClient;
import com.example.travelapp.api.service.UserService;
import com.example.travelapp.store.UserStore;
import com.example.travelapp.ui.AdapterStopPoints;
import com.example.travelapp.ui.DetailStopPointActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
        import com.example.travelapp.ui.AdapterStopPoints;
        import com.example.travelapp.ui.DetailStopPointActivity;

        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.IOException;

        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;


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

public class ListStop extends AppCompatActivity {

    private UserStore userStore;
    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_stop);

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
                        ListView listView = findViewById(R.id.lvHistoryStop);
                        final AdapterStopPoints adapter = new AdapterStopPoints(getApplicationContext(), R.layout.adapter_historystop, response.body().getStopPoints());
                        listView.setAdapter(adapter);
                        // click item
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                                // start activity
                                /*
                                startActivity(new Intent(getApplicationContext(), StopPoint.class)
                                        .putExtra("Id", String.valueOf(response.body().getStopPoints().get(position).getServiceId())));
                                        
                                 */
                                
                                new AlertDialog.Builder(ListStop.this)
                                        .setTitle("Delete")
                                        .setMessage("Delete it ?")
                                        .setNegativeButton("No", null)
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                // gui api xoa
                                                Call<ResSetStopPoints> call1 = userService.removeStopPoint(userStore.getUser().getAccessToken(), String.valueOf(response.body().getStopPoints().get(position).getId()));
                                                response.body().getStopPoints().remove(position);
                                                adapter.notifyDataSetChanged();
                                                call1.enqueue(new Callback<ResSetStopPoints>() {
                                                    @Override
                                                    public void onResponse(Call<ResSetStopPoints> call, Response<ResSetStopPoints> response) {
                                                        if (response.isSuccessful()) {
                                                            Toast.makeText(getApplicationContext(), "Remove Stop point successful", Toast.LENGTH_SHORT).show();
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
                                                    public void onFailure(Call<ResSetStopPoints> call, Throwable t) {
                                                        Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_LONG).show();
                                                    }
                                                });
                                            }
                                        })
                                        .show();
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


