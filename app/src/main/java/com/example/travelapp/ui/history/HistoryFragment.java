package com.example.travelapp.ui.history;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.travelapp.R;
import com.example.travelapp.api.model.request.ResHistoryTourUser;
import com.example.travelapp.api.service.RetrofitClient;
import com.example.travelapp.api.service.UserService;
import com.example.travelapp.store.UserStore;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_history, container, false);
        ListView listView = root.findViewById(R.id.list_item_history);
        UserStore userStore = new UserStore(root.getContext());
        UserService userService = RetrofitClient.getUserService();
        Call<ResHistoryTourUser> call = userService.getHistoryTourUser(
                userStore.getUser().getAccessToken(),
                "1", "1000"
        );
        call.enqueue(new Callback<ResHistoryTourUser>() {
            @Override
            public void onResponse(Call<ResHistoryTourUser> call, Response<ResHistoryTourUser> response) {
                if (response.isSuccessful()) {
                    Log.i("ahihi", response.body().getTotal());
                } else {
                    Log.i("ahihi", "that bai");
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
