package com.example.travelapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.travelapp.R;
import com.example.travelapp.api.model.response.ResListTour;
import com.example.travelapp.api.service.RetrofitClient;
import com.example.travelapp.store.UserStore;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_list_tour, container, false);
        final ListView listView = root.findViewById(R.id.list_item);
        UserStore userStore = new UserStore(root.getContext());
        Call<ResListTour> call = RetrofitClient.getUserService().getListTour(userStore.getUser().getAccessToken());
        call.enqueue(new Callback<ResListTour>() {
            @Override
            public void onResponse(Call<ResListTour> call, Response<ResListTour> response) {
                if (response.isSuccessful()) {
                    CustomAdapter customAdapter = new CustomAdapter(root.getContext(), R.layout.list_tour, response.body().getTours());
                    listView.setAdapter(customAdapter);
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        Toast.makeText(getContext(), jsonObject.get("message").toString(), Toast.LENGTH_LONG).show();
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                        
                    }
                }
            }

            @Override
            public void onFailure(Call<ResListTour> call, Throwable t) {
                Toast.makeText(getContext(), "List tour: Failure", Toast.LENGTH_LONG).show();
            }
        });
        return root;
    }
}