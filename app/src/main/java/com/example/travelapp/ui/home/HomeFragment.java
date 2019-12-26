package com.example.travelapp.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.travelapp.R;
import com.example.travelapp.api.model.response.ResListTour;
import com.example.travelapp.api.service.RetrofitClient;
import com.example.travelapp.store.UserStore;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.list_tour_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_list_tour, container, false);
        final ListView listView = root.findViewById(R.id.list_item);
        // get total
        final SharedPreferences sharedPreferences = Objects.requireNonNull(getContext()).getSharedPreferences("Tour", Context.MODE_PRIVATE);
        String total = sharedPreferences.getString("totalTour", "1000");

        Random rand = new Random();
        String number = String.valueOf(rand.nextInt(Integer.parseInt(total)/50));
        UserStore userStore = new UserStore(root.getContext());
        Call<ResListTour> call = RetrofitClient.getUserService().getListTour(userStore.getUser().getAccessToken(), "50", number);
        call.enqueue(new Callback<ResListTour>() {
            @Override
            public void onResponse(Call<ResListTour> call, Response<ResListTour> response) {
                if (response.isSuccessful()) {
                    @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("totalTour", response.body().getTotal()).apply();
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