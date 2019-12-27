package com.example.travelapp.ui.history;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.travelapp.R;
import com.example.travelapp.api.model.response.ResHistoryTourUser;
import com.example.travelapp.api.service.RetrofitClient;
import com.example.travelapp.api.service.UserService;
import com.example.travelapp.store.UserStore;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

public class HistoryFragment extends Fragment {
    
    private HistoryAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_history, container, false);
        final ListView listView = root.findViewById(R.id.list_item_history);
        UserStore userStore = new UserStore(root.getContext());
        UserService userService = RetrofitClient.getUserService();
        Call<ResHistoryTourUser> call = userService
                .getHistoryTourUser(userStore.getUser().getAccessToken(), "1", "1000");
        call.enqueue(new Callback<ResHistoryTourUser>() {
            @Override
            public void onResponse(Call<ResHistoryTourUser> call, final Response<ResHistoryTourUser> response) {
                if (response.isSuccessful()) {
                    adapter = new HistoryAdapter(root.getContext(), R.layout.history_adapter, response.body().getTours());
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            startActivity(new Intent(root.getContext(), TourInfor.class));
                        }
                    });
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
            public void onFailure(Call<ResHistoryTourUser> call, Throwable t) {
                Toast.makeText(getContext(), "Get History Tour: Failure", Toast.LENGTH_LONG).show();
            }
        });
        return root;
    }

}