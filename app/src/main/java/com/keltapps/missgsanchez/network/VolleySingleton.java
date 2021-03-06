package com.keltapps.missgsanchez.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


public class VolleySingleton {
    private static VolleySingleton ourInstance;
    private RequestQueue requestQueue;
    private static Context context;

    public static final String WORDPRESS = "192.168.1.65";


    public static synchronized VolleySingleton getInstance(Context context) {
        if (ourInstance == null)
            ourInstance = new VolleySingleton(context.getApplicationContext());
        return ourInstance;
    }

    private VolleySingleton(Context context) {
        VolleySingleton.context = context;
        requestQueue = getRequestQueue();
    }

    private RequestQueue getRequestQueue() {
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

}
