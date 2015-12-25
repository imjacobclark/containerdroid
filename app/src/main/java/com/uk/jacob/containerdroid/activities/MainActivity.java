package com.uk.jacob.containerdroid.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.uk.jacob.containerdroid.models.Container;
import com.uk.jacob.containerdroid.services.CAdvisorService;

import com.uk.jacob.containerdroid.R;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

                            TextView containerList = (TextView)  findViewById(R.id.container_list);
                            containerList.setText("");

                            while(iterator.hasNext()){
                                Map.Entry<String, Container> container = iterator.next();
                                containerList.append(container.getValue().getAliases().concat("\n"));
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
                        System.out.println(error);
                    }
                }
        );

        queue.add(stringRequest);
    }
}
