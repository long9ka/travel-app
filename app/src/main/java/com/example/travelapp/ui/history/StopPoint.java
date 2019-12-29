package com.example.travelapp.ui.history;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travelapp.R;
import com.example.travelapp.api.model.response.ResFbService;
import com.example.travelapp.api.model.response.ResServiceDetail;
import com.example.travelapp.api.service.RetrofitClient;
import com.example.travelapp.api.service.UserService;
import com.example.travelapp.store.UserStore;
import com.example.travelapp.ui.FeedbackListAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StopPoint extends AppCompatActivity {



//    private TextView name, adress, contact, cost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_point2);


        final TextView name=findViewById(R.id.name2);
        final TextView address=findViewById(R.id.address2);
        final TextView contact=findViewById(R.id.contact2);
        final TextView cost=findViewById(R.id.cost2);

        userStore = new UserStore(this);
        userService = RetrofitClient.getUserService();

        String accessToken = userStore.getUser().getAccessToken();
        final String Id = getIntent().getStringExtra("Id");
        Log.i("xxx", Id);
        /**
         * Lay chi tiet cua diem dung theo id
         * /tour/get/service-detail
         *
         */
        Call<ResServiceDetail> call = userService.getServiceDetail(accessToken,Id);
        call.enqueue(new Callback<ResServiceDetail>() {
            @Override
            public void onResponse(Call<ResServiceDetail> call, Response<ResServiceDetail> response) {
                if (response.isSuccessful()) {
                    if(response.body().getName()!=null){
                        name.setText(response.body().getName());
                    }
                    address.setText("Address: "+response.body().getAddress());
                    cost.setText("Cost: "+response.body().getMinCost()+" - "+response.body().getMaxCost());
                    contact.setText("Contact: "+response.body().getContact());

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
            public void onFailure(Call<ResServiceDetail> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Load fail", Toast.LENGTH_LONG).show();
            }
        });
        /**
         * Lay danh sach cmt cua diem dung theo id
         * /tour/comment-list
         */
        Call<ResFbService> call_list = userService.getFbService(accessToken, Id,"1","1000");
        call_list.enqueue(new Callback<ResFbService>() {
            @Override
            public void onResponse(Call<ResFbService> call, Response<ResFbService> response) {
                if (response.isSuccessful()) {
                    ListView listView = findViewById(R.id.list_item);
                    FeedbackListAdapter adapter = new FeedbackListAdapter(getApplicationContext(), R.layout.feedback_adapter, response.body().getFeedbackList());
                    listView.setAdapter(adapter);
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
            public void onFailure(Call<ResFbService> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Load fail", Toast.LENGTH_LONG).show();
            }
        });
    }private UserService userService;
    private UserStore userStore;
}