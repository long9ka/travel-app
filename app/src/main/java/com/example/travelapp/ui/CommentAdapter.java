package com.example.travelapp.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.travelapp.R;
import com.example.travelapp.api.model.response.CommentList;
import com.example.travelapp.api.model.response.ReviewList;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class CommentAdapter extends ArrayAdapter<CommentList> {
    private Context context;
    private int resource;
    private List<CommentList> objects;

    private class ViewHolder {
        TextView name;
        TextView comment;
        ImageView avatar;
        TextView createOn;
    }

    CommentAdapter(@NonNull Context context, int resource, @NonNull List<CommentList> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
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
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.comment = convertView.findViewById(R.id.comment);
        viewHolder.name = convertView.findViewById(R.id.name);
        viewHolder.avatar = convertView.findViewById(R.id.avatar);
        viewHolder.createOn = convertView.findViewById(R.id.create_on);
        
        if (objects.get(position).getAvatar() != null) {
            String avt = objects.get(position).getAvatar();
            Picasso.get().load(avt).into(viewHolder.avatar);
        }
        if (objects.get(position).getName() != null) {
            viewHolder.comment.setText(objects.get(position).getComment());
        }
        if (objects.get(position).getComment() != null) {
            viewHolder.name.setText(objects.get(position).getName());
        }
        if (objects.get(position).getCreatedOn() != null) {
            viewHolder.createOn.setText(getDate(Long.parseLong(objects.get(position).getCreatedOn())));
        }
        return convertView;
    }
}
