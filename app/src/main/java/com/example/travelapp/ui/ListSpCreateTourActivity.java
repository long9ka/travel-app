package com.example.travelapp.ui;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.travelapp.R;
import com.example.travelapp.api.model.request.ReqSetStopPoints;
import com.example.travelapp.api.model.request.StopPoint;
import com.example.travelapp.api.model.response.ResSetStopPoints;
import com.example.travelapp.api.service.RetrofitClient;
import com.example.travelapp.api.service.UserService;
import com.example.travelapp.store.UserStore;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListSpCreateTourActivity extends AppCompatActivity {
    
    private List<StopPoint> stopPoint;
    private String id;
    
    private ListSpCreateTourAdapter adapter;
    private SharedPreferences.Editor editor;
    private Gson gson;
    private Type type;
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_stop_point, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_stop_point) {
            /*
            SharedPreferences sharedPreferences = getSharedPreferences("list", MODE_PRIVATE);
            gson = new Gson();
            String json = sharedPreferences.getString("list", null);
            type =  new TypeToken<List<StopPoint>>(){}.getType();
            stopPoint = gson.fromJson(json, type);
            if (stopPoint == null) {
                stopPoint = new ArrayList<>();
            }
            
            for (int i = 0;  i< stopPoint.size(); i++) {
                Log.i("xxx", stopPoint.get(i).getName());
                Log.i("xxx", stopPoint.get(i).getMinCost());
                Log.i("xxx", stopPoint.get(i).getMinCost());
            }
            
            
             */
            id = getIntent().getStringExtra("id");
            Log.i("xxx id ne", id);
            
            UserStore userStore = new UserStore(getApplicationContext());
            UserService userService = RetrofitClient.getUserService();
            Call<ResSetStopPoints> call = userService.setStopPoints(
                    userStore.getUser().getAccessToken(),
                    new ReqSetStopPoints(id, stopPoint)
            );
            call.enqueue(new Callback<ResSetStopPoints>() {
                @Override
                public void onResponse(Call<ResSetStopPoints> call, Response<ResSetStopPoints> response) {
                    if (response.isSuccessful()) {
                        Log.i("xxx", "thanh cong");
                        Toast.makeText(getApplicationContext(), "Add stop points successful", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Add stop point failure", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ResSetStopPoints> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Add stop points failed", Toast.LENGTH_LONG).show();
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_sp_create_tour);
        
        id = getIntent().getStringExtra("Id");

        SharedPreferences sharedPreferences = getSharedPreferences("list", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        gson = new Gson();
        String json = sharedPreferences.getString("list", null);
        Type type =  new TypeToken<List<StopPoint>>(){}.getType();
        stopPoint = gson.fromJson(json, type);
        if (stopPoint == null) {
            stopPoint = new ArrayList<>();
        }
        
        Log.i("xxx", String.valueOf(stopPoint.size()));
        
        final ListView listView = findViewById(R.id.list_item);
        adapter = new ListSpCreateTourAdapter(this, R.layout.list_sp_createtour_adapter, stopPoint);
        listView.setAdapter(adapter);

        //final View viewModal = getLayoutInflater().inflate(R.layout.popup_listsp_create_tour, null);
        
        final AlertDialog.Builder dialog = new AlertDialog.Builder(ListSpCreateTourActivity.this);
        
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                dialog.setTitle("Delete" + stopPoint.get(position).getName())
                        .setMessage("Do you want to delete it?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                stopPoint.remove(position);
                                //editor.putString("list", gson.toJson(stopPoint)).apply();
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                /*
                new AlertDialog.Builder(ListSpCreateTourActivity.this)
                        .setTitle("Delete" + stopPoint.get(xPos).getName())
                        .setMessage("Do you want to delete it?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                stopPoint.remove(xPos);
                                //editor.putString("list", gson.toJson(stopPoint)).apply();
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                        
                 */
                return false;
            }
        });
    }
    
}
