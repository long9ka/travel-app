package com.example.travelapp.ui.home;

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
import com.example.travelapp.api.model.response.ResTour;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<ResTour> {

    private Context context;
    private int resource;
    private List<ResTour> objects;

    public static String getDate(long milliSeconds, String dateFormat) {

        // Create a DateFormatter object for displaying date in specified format.

        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);


        // Create a calendar object that will convert the date and time value in milliseconds to date.

        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(milliSeconds);

        return formatter.format(calendar.getTime());

    }


    public CustomAdapter(@NonNull Context context, int resource, @NonNull List<ResTour> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView name;
        TextView time;
        TextView adults;
        TextView childs;
        TextView costs;
    }

    @SuppressLint({"ViewHolder", "SetTextI18n"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, parent, false);

        // handle ViewHolder
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.imageView = convertView.findViewById(R.id.image);
        viewHolder.name = convertView.findViewById(R.id.full_name);
        viewHolder.time = convertView.findViewById(R.id.time);
        viewHolder.adults = convertView.findViewById(R.id.adults);
        viewHolder.childs = convertView.findViewById(R.id.childs);
        viewHolder.costs = convertView.findViewById(R.id.costs);

//        convert to dd/mm/yyyy
        String dayStart = objects.get(position).getStartDate();
        String con_dayStart = null, con_dayEnd = null;
        if (dayStart != null) {
            Long time_start;
            time_start = Long.parseLong(dayStart);
            con_dayStart = getDate(time_start, "dd/MM/yyyy");
        }
        String dayEnd = objects.get(position).getEndDate();
        if (dayEnd != null) {
            Long time_end;
            time_end = Long.parseLong(dayEnd);
            con_dayEnd = getDate(time_end, "dd/MM/yyyy");
        }
        //String avartar=objects.get(position).getAvatar().toString();
        viewHolder.name.setText(objects.get(position).getName());
        viewHolder.time.setText(con_dayStart + " - " + con_dayEnd);
        viewHolder.adults.setText("Adults: " + objects.get(position).getAdults());
        viewHolder.childs.setText("Child: " + objects.get(position).getChilds());
        viewHolder.costs.setText(objects.get(position).getMinCost() + " - " + objects.get(position).getMaxCost());
        return convertView;
    }
}
