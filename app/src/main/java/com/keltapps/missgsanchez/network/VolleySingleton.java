package com.keltapps.missgsanchez.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


public class VolleySingleton {
    private static VolleySingleton ourInstance;
    private RequestQueue requestQueue;
    private static Context context;
    private static final String PROTOCOL = "http://";
    public static final String WORDPRESS = "192.168.1.17";
    private static final String API_NAME = "/wp-json";
    private static final String GET_POST = "/wp/v2/posts";
    public static final int BLOG_POST_PER_PAGE = 10;
    private static final String ARGUMENT_BLOG_PER_PAGE = "per_page=" + BLOG_POST_PER_PAGE;
    private static final String ARGUMENT_BLOG_PAGE = "page=";
    public static final String URL_GET_BLOG = PROTOCOL + WORDPRESS + API_NAME + GET_POST + "?" + ARGUMENT_BLOG_PER_PAGE + "&" + ARGUMENT_BLOG_PAGE;

    public static final int INSTAGRAM_POST_PER_PAGE = 20;
    public static final String URL_GET_INSTAGRAM = "https://www.instagram.com/missgsanchez/media/";
    public static final String ARGUMENT_INSTAGRAM_MAX_ID = "?max_id=";

    public static synchronized VolleySingleton getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new VolleySingleton(context.getApplicationContext());
        }
        return ourInstance;
    }

    private VolleySingleton(Context context) {
        VolleySingleton.context = context;
        requestQueue = getRequestQueue();
    }

    private RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

}
