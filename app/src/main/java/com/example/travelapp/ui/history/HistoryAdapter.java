package com.example.travelapp.ui.history;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.travelapp.R;
import com.example.travelapp.UpdateTourActivity;
import com.example.travelapp.api.model.request.Tour;
import com.example.travelapp.api.model.response.HistoryStopPoint;
import com.example.travelapp.api.model.response.ResHistoryStopPoints;
import com.example.travelapp.api.model.response.ResHistoryTourUser;
import com.example.travelapp.api.service.RetrofitClient;
import com.example.travelapp.api.service.UserService;
import com.example.travelapp.store.UserStore;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

public class HistoryAdapter extends ArrayAdapter<Tour> {
    private Context context;
    private int resource;
    private List<Tour> objects;
    
    private class ViewHolder {
        TextView tourName;
        TextView date;
        TextView cost;
        TextView people;
        TextView isHost;
        TextView isKicked;
    }
    private static String getDate(Long milliSeconds) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    HistoryAdapter(@NonNull Context context, int resource, @NonNull List<Tour> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @SuppressLint({"ViewHolder", "SetTextI18n"})
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        
        // convert list view
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.tourName = convertView.findViewById(R.id.tour_name);
        viewHolder.date = convertView.findViewById(R.id.date);
        viewHolder.cost = convertView.findViewById(R.id.cost);
        viewHolder.people = convertView.findViewById(R.id.people);
        viewHolder.isHost = convertView.findViewById(R.id.is_host);
        viewHolder.isKicked = convertView.findViewById(R.id.is_kicked);
        
        // set view
        viewHolder.tourName.setText("Tour name: " + objects.get(position).getName());
        viewHolder.date.setText("Date: " +objects.get(position).getStartDate() + " - " +objects.get(position).getEndDate());
        viewHolder.cost.setText("Costs: " + objects.get(position).getMinCost() + " - " + objects.get(position).getMaxCost());
        viewHolder.people.setText("Adults: " + objects.get(position).getAdults() + " Child: " + objects.get(position).getChilds());
        viewHolder.isHost.setText("Is host: " + objects.get(position).getIsHost());
        viewHolder.isKicked.setText("Is kicked: " + objects.get(position).getIsKicked());

        return convertView;
    }
}
