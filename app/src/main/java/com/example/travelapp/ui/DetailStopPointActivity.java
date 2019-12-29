package com.example.travelapp.ui;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewPropertyAnimatorListener;

import com.example.travelapp.R;
import com.example.travelapp.api.model.request.ReqEditUserInfo;
import com.example.travelapp.api.model.request.ReqUpdatePassword;
import com.example.travelapp.api.model.response.ResCommentList;
import com.example.travelapp.api.model.response.ResFbService;
import com.example.travelapp.api.model.response.ResServiceDetail;
import com.example.travelapp.api.model.response.ResSetStopPoints;
import com.example.travelapp.api.service.RetrofitClient;
import com.example.travelapp.api.service.UserService;
import com.example.travelapp.store.UserStore;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

public class DetailStopPointActivity extends AppCompatActivity {
    private UserService userService;
    private UserStore userStore;

//    private TextView name, adress, contact, cost;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_stop_point);


        final TextView name=findViewById(R.id.name);
        final TextView address=findViewById(R.id.address);
        final TextView contact=findViewById(R.id.contact);
        final TextView cost=findViewById(R.id.cost);

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
                    name.setText(response.body().getName());
                    address.setText(response.body().getAddress());
                    contact.setText((response.body().getContact()));
                    cost.setText(response.body().getMinCost()+" - "+response.body().getMaxCost());
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
        Call<ResFbService> call_list = userService.getFbService(accessToken,"1","1","1000");
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
    }
}
