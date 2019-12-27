package com.example.travelapp.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.appcompat.widget.SearchView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.travelapp.R;
import com.example.travelapp.api.model.response.ResListTour;
import com.example.travelapp.api.service.RetrofitClient;
import com.example.travelapp.store.UserStore;
import com.example.travelapp.ui.history.TourInfor;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;
import java.util.Random;
import java.util.logging.Handler;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private CustomAdapter customAdapter;

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
            public void onResponse(Call<ResListTour> call, final Response<ResListTour> response) {
                if (response.isSuccessful()) {
                    // get total tour
                    @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("totalTour", response.body().getTotal()).apply();
                    // set adapter
                    customAdapter = new CustomAdapter(root.getContext(), R.layout.list_tour, response.body().getTours());
                    listView.setAdapter(customAdapter);
                    // click item
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(root.getContext(), Infor_tour.class);
                            intent.putExtra("tourId", String.valueOf(response.body().getTours().get(position).getId()));
                            startActivity(intent);
                        }
                    });
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

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.search_bar) {
            SearchView searchView = (SearchView) item.getActionView();
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    customAdapter.getFilter().filter(s);
                    return false;
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }
}