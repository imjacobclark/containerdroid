package com.uk.jacob.containerdroid.volley;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleySingleton extends Application {
    private static VolleySingleton instance;

    private RequestQueue requestQueue;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    private VolleySingleton(Context context) {
        this.context = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized VolleySingleton getInstance(Context context) {
        if (instance == null) {
            instance = new VolleySingleton(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }

        requestQueue.getCache().clear();

        return requestQueue;
    }
}