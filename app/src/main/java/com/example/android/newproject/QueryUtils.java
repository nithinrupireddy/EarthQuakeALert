package com.example.android.newproject;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public final class QueryUtils {

   public static ArrayList<ArrayList<String>> earthquakes;
    public static ArrayList<String> earthquake;
    private static String dateDisplay,timeDisplay;
    public static final String LOG_TAG=QueryUtils.class.getSimpleName();

    /** Sample JSON response for a USGS query */
   // private static final String SAMPLE_JSON_RESPONSE = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";


    public static ArrayList<ArrayList<String>> fetchEarthQuake(String JSONURL){

        try{
            Thread.sleep(1000);
        }catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        URL url = createURL(JSONURL);

        String jsonResponse = "";
        try
        {
            jsonResponse=makeHTTPRequest(url);
        }catch (IOException e)
        {
            Log.e(LOG_TAG,"IoException",e);
            e.printStackTrace();
        }
        return extractEarthquakes(jsonResponse);
    }

    public static String makeHTTPRequest(URL url) throws IOException
    {
        String jsonResponse ="";
        if(url==null)
        {
            return null;
        }

        HttpURLConnection urlConnection=null;
        InputStream inputStream = null;
        try
        {
            urlConnection= (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();
            if(urlConnection.getResponseCode()==200)
            {
                inputStream=urlConnection.getInputStream();
                jsonResponse=readFromStream(inputStream);
            }else{
                Log.e(LOG_TAG,"error response code"+urlConnection.getResponseCode());
            }
            return jsonResponse;
        }catch (IOException e)
        {
            Log.e(LOG_TAG,"IO Exception",e);
            e.printStackTrace();
        }
        finally {
            if(urlConnection!=null)
            {
                urlConnection.disconnect();
            }
            if(inputStream!=null)
            {
                inputStream.close();
            }
        }

        return null;
    }

    public static String readFromStream(InputStream inputStream) throws IOException
    {
        StringBuilder output = new StringBuilder();
        if (inputStream!=null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line =bufferedReader.readLine();
            while (line!=null){
                output.append(line);
                line = bufferedReader.readLine();
            }
        }

        return output.toString();
    }

    public static URL createURL(String stringURL)
    {
        URL url = null;
        try
        {
            url = new URL(stringURL);
        }catch (MalformedURLException exception)
        {
            Log.e(LOG_TAG,"URL Exception",exception);
            exception.printStackTrace();
        }

        return url;
    }


    public static ArrayList<ArrayList<String>> extractEarthquakes(String response) {


         earthquakes = new ArrayList<>();

        try {
            JSONObject root = new JSONObject(response);
            JSONArray featuresArray = root.optJSONArray("features");

            for(int i=0;i<featuresArray.length();i++)
            {
                earthquake = new ArrayList<>();

                JSONObject firstElement = featuresArray.optJSONObject(i);
                JSONObject propertiesOfFirstElement = firstElement.optJSONObject("properties");
                Double magnitude = propertiesOfFirstElement.getDouble("mag");
                String place = propertiesOfFirstElement.getString("place");
                long time = propertiesOfFirstElement.getLong("time");
                String url = propertiesOfFirstElement.getString("url");

                Date dateObject = new Date(time);
                SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM DD, yyyy");
                dateDisplay = dateFormatter.format(dateObject);

                SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
                timeDisplay=timeFormat.format(dateObject);


                earthquake.add(String.valueOf(magnitude));
                earthquake.add(place);
                earthquake.add(dateDisplay);
                earthquake.add(timeDisplay);
                earthquake.add(url);
                earthquakes.add(earthquake);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakes;
    }



}