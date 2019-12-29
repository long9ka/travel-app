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
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
        ImageView avatar;
        TextView review;
        RatingBar point;
        TextView createOn;

    }

    private static String getDate(long milliSeconds) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
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
        viewHolder.avatar = convertView.findViewById(R.id.avatar);
        viewHolder.createOn = convertView.findViewById(R.id.create_on);
        
        if (objects.get(position).getAvatar() != null) {
            Picasso.get().load(objects.get(position).getAvatar()).into(viewHolder.avatar);
        }
        if (objects.get(position).getName() != null) {
            viewHolder.name.setText(objects.get(position).getName());
        }
        if (objects.get(position).getReview() != null) {
            viewHolder.review.setText(objects.get(position).getReview());
        }
        if (objects.get(position).getPoint() != null) {
            viewHolder.point.setRating(objects.get(position).getPoint());
        }
        if (objects.get(position).getCreatedOn() != null) {
            viewHolder.createOn.setText(getDate(Long.parseLong(objects.get(position).getCreatedOn())));
        }
        return convertView;
    }
}
