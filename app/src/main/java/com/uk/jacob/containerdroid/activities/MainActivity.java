package com.uk.jacob.containerdroid.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.uk.jacob.containerdroid.adapters.ContainerListRecyclerViewAdapter;
import com.uk.jacob.containerdroid.models.Container;
import com.uk.jacob.containerdroid.services.CAdvisorService;

import com.uk.jacob.containerdroid.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MainActivity extends ActionBarActivity {
    private RecyclerView containerListRecyclerView;
    private ContainerListRecyclerViewAdapter containerListRecyclerAdapter;
    private RecyclerView.LayoutManager containerListLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get a handle on our RecyclerView for interactin and setup
        containerListRecyclerView = (RecyclerView) findViewById(R.id.container_list_recyclerview);

        // Grab a new LayoutManager
        containerListLayoutManager = new LinearLayoutManager(this);

        // Grab a new adapter
        List<Container> containers = new ArrayList<>();
        containerListRecyclerAdapter = new ContainerListRecyclerViewAdapter(containers);

        // Better performance as the size of our RecyclerView does not change
        containerListRecyclerView.setHasFixedSize(true);

        // Attach our LayoutManager to our RecyclerView
        containerListRecyclerView.setLayoutManager(containerListLayoutManager);

        // Wire up adapter for RecyclerView
        containerListRecyclerView.setAdapter(containerListRecyclerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume(){
        super.onResume();

        containerListRecyclerAdapter.clear();

         /*
            Convenience method to build a new Volley request queue
            Passes in current context, deriving the application context from it
            http://developer.android.com/training/volley/requestqueue.html
        */
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://jacob.uk.com:8080/api/v1.3/docker";

        /*
            We request a String because we use Jackson to map it into a POJO
            Passes in the method (GET), the URL and a response listener and error listener
         */
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {

                    // On response event where the response is passed into
                    @Override
                    public void onResponse(String response) {
                        CAdvisorService cAdvisorService = new CAdvisorService();
                        try {
                            Map<String, Container> containers = cAdvisorService.getContainers(response);
                            Iterator<Map.Entry<String, Container>> iterator = containers.entrySet().iterator();
                            int position = 0;

                            while(iterator.hasNext()){
                                Map.Entry<String, Container> container = iterator.next();
                                containerListRecyclerAdapter.addItem(position, container.getValue());
                                position++;
                                iterator.remove();
                            }


                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {

                    // On error event where the error is passed into
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        containerListRecyclerView.setVisibility(View.GONE);
                        TextView activityTitle = (TextView)findViewById(R.id.activity_title);
                        activityTitle.setText("There was an issue connecting to cAdvisor.");
                        System.out.println(error);
                    }
                }
        );

        queue.add(stringRequest);
    }
}
