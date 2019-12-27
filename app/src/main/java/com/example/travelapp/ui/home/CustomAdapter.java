package com.example.travelapp.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.travelapp.R;
import com.example.travelapp.api.model.response.ResTour;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class CustomAdapter extends ArrayAdapter<ResTour> {

    private Context context;
    private int resource;
    private List<ResTour> objects;
    private List<Integer> list;

    private static String getDate(long milliSeconds) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    CustomAdapter(@NonNull Context context, int resource, @NonNull List<ResTour> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;

        list = new ArrayList<>();
        list.add(R.drawable.background_01);
        list.add(R.drawable.background_02);
        list.add(R.drawable.background_03);
        list.add(R.drawable.background_04);
    }

    private class ViewHolder {
        ImageView imageView;
        TextView tourName;
        TextView date;
        TextView people;
        TextView cost;
    }

    @SuppressLint({"ViewHolder", "SetTextI18n"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        // handle ViewHolder
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.imageView = convertView.findViewById(R.id.background);
        viewHolder.tourName = convertView.findViewById(R.id.tour_name);
        viewHolder.date = convertView.findViewById(R.id.date);
        viewHolder.cost = convertView.findViewById(R.id.cost);
        viewHolder.people = convertView.findViewById(R.id.people);
        ResTour tour = objects.get(position);

        if (tour.getAvatar() == null) {
            Random rand = new Random();
            viewHolder.imageView.setBackgroundResource(list.get(rand.nextInt(1)));
        } else {
            //viewHolder.imageView.setBackgroundResource((Integer) tour.getAvatar());
        }

        if (!tour.getName().equals("")) {
            viewHolder.tourName.setText(tour.getName() + " ");
        }
        if (tour.getStartDate() != null && tour.getEndDate() != null) {
            String startDate = getDate(Long.parseLong(tour.getStartDate()));
            String endDate = getDate(Long.parseLong(tour.getEndDate()));
            viewHolder.date.setText(startDate + " - " + endDate + " ");
        }
        viewHolder.people.setText("Adults: " + tour.getAdults() + ", Child: " + tour.getChilds() + " ");
        viewHolder.cost.setText(tour.getMinCost() + "$ - " + tour.getMaxCost() + "$");
        
        return convertView;
    }
}
