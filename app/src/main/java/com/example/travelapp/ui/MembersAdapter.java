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
import com.example.travelapp.api.model.response.Member;

import java.util.List;

public class MembersAdapter extends ArrayAdapter<Member> {

    private Context context;
    private int resource;
    private List<Member> objects;

    private class ViewHolder {
        TextView name;
        TextView phone;
        TextView isHost;
    }
    
    public MembersAdapter(@NonNull Context context, int resource, @NonNull List<Member> objects) {
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
        viewHolder.name = convertView.findViewById(R.id.name);
        viewHolder.phone = convertView.findViewById(R.id.phone);
        viewHolder.isHost = convertView.findViewById(R.id.is_host);
        
        viewHolder.name.setText(objects.get(position).getName());
        viewHolder.phone.setText(objects.get(position).getPhone());
        viewHolder.isHost.setText(String.valueOf(objects.get(position).getIsHost()));
        return convertView;
    }
}
