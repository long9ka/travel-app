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
import com.example.travelapp.api.model.response.CommentList;
import com.example.travelapp.api.model.response.HistoryStopPoint;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterStopPoints extends ArrayAdapter<HistoryStopPoint> {
    private Context context;
    private int resource;
    private List<HistoryStopPoint> objects;

    private class ViewHolder {
        TextView name;
        TextView serviceIdType;
        TextView costs;
        ImageView avatar;
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
        viewHolder.avatar = convertView.findViewById(R.id.avatar);
        viewHolder.name = convertView.findViewById(R.id.name);
        viewHolder.serviceIdType = convertView.findViewById(R.id.service_type);
        viewHolder.costs = convertView.findViewById(R.id.cost);

        if (objects.get(position).getAvatar() != null) {
            Picasso.get().load(objects.get(position).getAvatar()).into(viewHolder.avatar);
        }
        if (objects.get(position).getName() != null) {
            viewHolder.name.setText(objects.get(position).getName());
        }
        if (objects.get(position).getMinCost() != null || objects.get(position).getMaxCost() != null) {
            viewHolder.costs.setText("$: " + objects.get(position).getMinCost() + " - " + objects.get(position).getMaxCost());
        }
        if (objects.get(position).getServiceTypeId() != null) {
            if (objects.get(position).getServiceTypeId() == 1) {
                viewHolder.serviceIdType.setText("Restaurant");
            }
            if (objects.get(position).getServiceTypeId() == 2) {
                viewHolder.serviceIdType.setText("Hotel");
            }
            if (objects.get(position).getServiceTypeId() == 3) {
                viewHolder.serviceIdType.setText("Station");
            }
            if (objects.get(position).getServiceTypeId() == 4) {
                viewHolder.serviceIdType.setText("other");
            }
        }
        return convertView;
    }
}
