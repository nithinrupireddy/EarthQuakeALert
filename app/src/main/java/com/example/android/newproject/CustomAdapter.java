package com.example.android.newproject;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

class CustomAdapter extends BaseAdapter {
    private final Context context;
    private final ArrayList<ArrayList<String>> earthquakes ;


    public CustomAdapter(Context context,ArrayList<ArrayList<String>> earthquakes) {
    this.context=context;
    this.earthquakes=earthquakes;
    }

    @Override
    public int getCount() {
        return earthquakes.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView=inflater.inflate(R.layout.listrowview,null);
        TextView magnitude = convertView.findViewById(R.id.magnitude);
        TextView location = convertView.findViewById(R.id.location);
        TextView date = convertView.findViewById(R.id.date);
        TextView time = convertView.findViewById(R.id.time);

        GradientDrawable magnitudeCircle = (GradientDrawable) magnitude.getBackground();
        int magnitudeColor = getMagnitudeColor(Double.parseDouble(earthquakes.get(position).get(0)));
        magnitudeCircle.setColor(magnitudeColor);

           magnitude.setText(earthquakes.get(position).get(0));
           location.setText(earthquakes.get(position).get(1));
           date.setText(earthquakes.get(position).get(2));
           time.setText(earthquakes.get(position).get(3));

        return convertView;
    }


    private int getMagnitudeColor(double magnitude)
    {
        int magnitideColor;

        int magnitudeFloorValue = (int) Math.floor(magnitude);

        switch (magnitudeFloorValue)
        {
            case 0:
            case 1:
                magnitideColor = R.color.magnitude1;
                break;
            case 2:
                magnitideColor = R.color.magnitude2;
                break;
            case 3:
                magnitideColor = R.color.magnitude3;
                break;
            case 4:
                magnitideColor = R.color.magnitude4;
                break;
            case 5:
                magnitideColor = R.color.magnitude5;
                break;
            case 6:
                magnitideColor = R.color.magnitude6;
                break;
            case 7:
                magnitideColor = R.color.magnitude7;
                break;
            case 8:
                magnitideColor = R.color.magnitude8;
                break;
            case 9:
                magnitideColor = R.color.magnitude9;
                break;
            default:
                magnitideColor = R.color.magnitude10plus;
                break;
        }

        return ContextCompat.getColor(context,magnitideColor);
    }
}
