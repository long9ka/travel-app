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
import com.example.travelapp.api.model.request.StopPoint;

import java.util.List;

public class StopPointAdapter extends ArrayAdapter<StopPoint> {
    private Context context;
    private int resource;
    private List<StopPoint> objects;

    public StopPointAdapter(@NonNull Context context, int resource, @NonNull List<StopPoint> objects) {
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

        ViewHolder viewHolder = new ViewHolder();
        viewHolder.stopName = convertView.findViewById(R.id.stop_point);

        viewHolder.stopName.setText("Stop point: ");
        return convertView;
    }

    private class ViewHolder {
        TextView stopName;
    }
}
