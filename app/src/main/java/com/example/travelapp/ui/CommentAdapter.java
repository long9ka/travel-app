package com.example.travelapp.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.travelapp.R;
import com.example.travelapp.api.model.response.CommentList;
import com.example.travelapp.api.model.response.ReviewList;

import java.util.List;

public class CommentAdapter extends ArrayAdapter<CommentList> {
    private Context context;
    private int resource;
    private List<CommentList> objects;

    private class ViewHolder {
        TextView name;
        TextView comment;

    }

    public CommentAdapter(@NonNull Context context, int resource, @NonNull List<CommentList> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.comment = convertView.findViewById(R.id.comment);
        viewHolder.name = convertView.findViewById(R.id.name);
        viewHolder.comment.setText(objects.get(position).getComment());
        viewHolder.name.setText(objects.get(position).getName());
        return convertView;
    }
}
