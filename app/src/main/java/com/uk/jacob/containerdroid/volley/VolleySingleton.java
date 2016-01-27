package com.uk.jacob.containerdroid.volley;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.uk.jacob.containerdroid.activities.ContainerListActivity;

public class VolleySingleton extends Application {
    private static VolleySingleton instance;

    private RequestQueue requestQueue;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        this.context = null;
        instance = this;
    }

    private VolleySingleton() {
        requestQueue = getRequestQueue();
    }

    public static synchronized VolleySingleton getInstance() {
        if (instance == null) {
            instance = new VolleySingleton();
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(ContainerListActivity.getAppContext());
        }

        requestQueue.getCache().clear();

        return requestQueue;
    }
}