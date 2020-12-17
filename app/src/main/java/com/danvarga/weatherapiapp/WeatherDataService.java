package com.danvarga.weatherapiapp;

import android.content.Context;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherDataService {

    public static final String QUERY_FOR_CITY_ID = "https://www.metaweather.com/api/location/search/?query=";

    Context context;
    String cityID;

    public WeatherDataService(Context context) {
        this.context = context;
    }

    public interface VolleyResponseListener {
        void onError(String message);

        void onResponse(String cityID);
    }

    public void getCityID(final String cityName, final VolleyResponseListener volleyResponseListener) {

        // https://developer.android.com/training/volley/request
        String url = QUERY_FOR_CITY_ID + cityName;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        String cityID = "";

                        try {
                            JSONObject cityInfo = response.getJSONObject(0);
                            cityID = cityInfo.getString("woeid");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        volleyResponseListener.onResponse(cityID);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                volleyResponseListener.onError("Something went wrong");
            }
        });

        // Instantiate the RequestQueue. Passing Activity-context.
        // Add the request to the RequestQueue.
        MySingleton.getInstance(context).addToRequestQueue(request);

    }

    //TODO: impl
//    public List<WeatherReportModel> getCiyForecastByID(String cityID) {
//
//    }
//
//    public List<WeatherReportModel> getCiyForecastByName(String cityName) {
//
//    }
}
