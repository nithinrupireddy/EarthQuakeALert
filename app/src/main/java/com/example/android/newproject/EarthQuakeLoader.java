package com.example.android.newproject;

import android.content.AsyncTaskLoader;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.util.ArrayList;

public class EarthQuakeLoader extends AsyncTaskLoader<ArrayList<ArrayList<String>>> {

    private static final String LOG_TAG = EarthQuakeLoader.class.getName();
    private String JSONUrl ;

    public EarthQuakeLoader(@NonNull Context context,String Url) {
        super(context);
        this.JSONUrl=Url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public ArrayList<ArrayList<String>> loadInBackground() {
        ArrayList<ArrayList<String>> earthquakes = QueryUtils.fetchEarthQuake(JSONUrl);
        return earthquakes;
    }
}
