package com.example.travelapp.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.travelapp.R;
import com.example.travelapp.api.model.response.ReviewList;

import java.util.List;

public class ReviewTourAdapter extends ArrayAdapter<ReviewList> {
    private Context context;
    private int resource;
    private List<ReviewList> objects;
    
    ReviewTourAdapter(@NonNull Context context, int resource, @NonNull List<ReviewList> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    private class ViewHolder {
        TextView name;
        TextView review;
        RatingBar point;

    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        // handle ViewHolder
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.name = convertView.findViewById(R.id.name);
        viewHolder.review = convertView.findViewById(R.id.review);
        viewHolder.point = convertView.findViewById(R.id.point);
        
        viewHolder.name.setText(objects.get(position).getName());
        viewHolder.review.setText(objects.get(position).getReview());
        viewHolder.point.setRating(objects.get(position).getPoint());
        return convertView;
    }
}
