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
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelapp.R;
import com.example.travelapp.api.model.response.FeedbackList;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
public class FeedbackListAdapter extends ArrayAdapter<FeedbackList> {
    private Context context;
    private int resource;
    private List<FeedbackList> objects;

    private class ViewHolder{
        TextView name;
        ImageView avatar;
        TextView feedback;
        TextView phone;
        RatingBar point;
        TextView create_on;
    }

    public FeedbackListAdapter(@NonNull Context context, int resource, @NonNull List<FeedbackList> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }

    private static String getDate(long milliSeconds) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        ViewHolder viewHolder= new ViewHolder();
        viewHolder.avatar=convertView.findViewById(R.id.avatar);
        viewHolder.name=convertView.findViewById(R.id.name);
        viewHolder.phone=convertView.findViewById(R.id.phone);
        viewHolder.point=convertView.findViewById(R.id.point);
        viewHolder.create_on=convertView.findViewById(R.id.create_on);
        viewHolder.feedback=convertView.findViewById(R.id.feedback);

        if (objects.get(position).getAvatar() != null) {
            String avt = objects.get(position).getAvatar();
            Picasso.get().load(avt).into(viewHolder.avatar);
        }
        if (objects.get(position).getFeedback() != null) {
            viewHolder.feedback.setText(objects.get(position).getFeedback());
        }
        if (objects.get(position).getName() != null) {
            viewHolder.name.setText(objects.get(position).getName());
        }
        if (objects.get(position).getCreatedOn() != null) {
            viewHolder.create_on.setText(getDate(Long.parseLong(objects.get(position).getCreatedOn())));
        }
        if (objects.get(position).getPhone() != null) {
            viewHolder.phone.setText(objects.get(position).getPhone());
        }
        if (objects.get(position).getPoint() != null) {
            viewHolder.point.setRating(objects.get(position).getPoint());
        }
        return convertView;
    }
}
