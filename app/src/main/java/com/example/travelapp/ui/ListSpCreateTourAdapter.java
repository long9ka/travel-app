package com.example.travelapp.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.travelapp.R;
import com.example.travelapp.api.model.request.StopPoint;

import java.util.List;

public class ListSpCreateTourAdapter  extends ArrayAdapter<StopPoint> {

    private Context context;
    private int resource;
    private List<StopPoint> objects;

    private class ViewHolder {
        TextView name;
        TextView date;
        TextView cost;
        TextView service;
    }
    
    ListSpCreateTourAdapter(@NonNull Context context, int resource, @NonNull List<StopPoint> objects) {
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
        viewHolder.date = convertView.findViewById(R.id.date);
        viewHolder.cost = convertView.findViewById(R.id.cost);
        viewHolder.service = convertView.findViewById(R.id.service_type);
        
        StopPoint stopPoint = objects.get(position);
        if (stopPoint.getName() != null) {
            viewHolder.name.setText(stopPoint.getName());
        }
        if (stopPoint.getArrivalAt() != null && stopPoint.getLeaveAt() != null ) {
            viewHolder.date.setText(stopPoint.getArrivalAt() + " - " + stopPoint.getLeaveAt());
        }
        if (stopPoint.getMinCost() != null && stopPoint.getMaxCost() != null) {
            viewHolder.cost.setText("Cost: " + stopPoint.getMinCost() + " - " + stopPoint.getMaxCost());
        }
        if (stopPoint.getServiceTypeId() != null) {
            if (stopPoint.getServiceTypeId().equals("1")) {
                viewHolder.service.setText("Restaurant");
            }
            if (stopPoint.getServiceTypeId().equals("2")) {
                viewHolder.service.setText("Hotel");
            }
            if (stopPoint.getServiceTypeId().equals("3")) {
                viewHolder.service.setText("Station");
            }
            if (stopPoint.getServiceTypeId().equals("4")) {
                viewHolder.service.setText("Other");
            }
        }
        
        return convertView;
    }
}
