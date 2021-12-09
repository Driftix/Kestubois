package com.example.kestubois;


import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

public class NearbySearch {
    public NearbySearch(){


// Instantiate the RequestQueue.
        String url ="https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522%2C151.1957362&radius=1500&type=restaurant&keyword=cruise&key=AIzaSyAXofIr3WX1CCl-mfSiVtW8MAywsYpaxh8";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.wtf("wtf",response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.wtf("wtf","requête qui a pas marché");
                    }
                });

// Add the request to the RequestQueue.

    }
}
