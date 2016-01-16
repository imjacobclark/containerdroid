package com.uk.jacob.containerdroid.services;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uk.jacob.containerdroid.adapters.ContainerListRecyclerViewAdapter;
import com.uk.jacob.containerdroid.models.ContainerModel;
import com.uk.jacob.containerdroid.services.interfaces.ICAdvisorService;
import com.uk.jacob.containerdroid.activities.controllers.ContainerListActivityController;
import com.uk.jacob.containerdroid.volley.VolleySingleton;

import java.io.IOException;
import java.util.Map;

public class CAdvisorService implements ICAdvisorService {
    private ContainerListRecyclerViewAdapter containerListRecyclerAdapter;
    private ContainerListActivityController containerListActivityController;

    public CAdvisorService(final ContainerListRecyclerViewAdapter containerListRecyclerAdapter) {
        this.containerListRecyclerAdapter = containerListRecyclerAdapter;
        this.containerListActivityController = new ContainerListActivityController(this.containerListRecyclerAdapter);
    }

    private void buildContainerListInterface(String response) throws IOException {
        containerListActivityController.buildInterface(getContainerObject(response));
    }

    private Map<String, ContainerModel> getContainerObject(String apiResponse) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, ContainerModel> map = mapper.readValue(apiResponse, new TypeReference<Map<String, ContainerModel>>() {});
        return map;
    }

    public void fetchDataFromService(Context context){
        RequestQueue queue = VolleySingleton.getInstance(context).getRequestQueue(); //Obtain the instance

        StringRequest volleyRequest = new StringRequest(Request.Method.GET,"http://jacob.uk.com:8080/api/v1.3/docker", //Change the url parameter
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            buildContainerListInterface(response);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        queue.add(volleyRequest);
    }
}
