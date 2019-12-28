package com.example.travelapp.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.travelapp.R;
import com.example.travelapp.api.model.response.CommentList;
import com.example.travelapp.api.model.response.HistoryStopPoint;

import java.util.List;

public class AdapterStopPoints extends ArrayAdapter<HistoryStopPoint> {
    private Context context;
    private int resource;
    private List<HistoryStopPoint> objects;

    private class ViewHolder {
        TextView name;
        TextView serviceIdType;
        TextView costs;
    }
    
    AdapterStopPoints(@NonNull Context context, int resource, @NonNull List<HistoryStopPoint> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @SuppressLint({"ViewHolder", "SetTextI18n"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.name = convertView.findViewById(R.id.name);
        viewHolder.serviceIdType = convertView.findViewById(R.id.service);
        viewHolder.costs = convertView.findViewById(R.id.cost);
        
        viewHolder.name.setText(objects.get(position).getName());
        viewHolder.costs.setText("$: " + objects.get(position).getMinCost() + " - " + objects.get(position).getMaxCost());
        viewHolder.serviceIdType.setText(String.valueOf(objects.get(position).getServiceId()));
        return convertView;
    }
}
