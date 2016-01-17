package com.uk.jacob.containerdroid.services;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uk.jacob.containerdroid.activities.adapters.ContainerListRecyclerViewAdapter;
import com.uk.jacob.containerdroid.models.ContainerModel;
import com.uk.jacob.containerdroid.models.ContainerSpecModel;
import com.uk.jacob.containerdroid.services.interfaces.ICAdvisorService;
import com.uk.jacob.containerdroid.activities.controllers.ContainerListActivityController;
import com.uk.jacob.containerdroid.volley.VolleySingleton;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CAdvisorService implements ICAdvisorService {
    private ObjectMapper mapper;

    public CAdvisorService() {
        this.mapper = new ObjectMapper();
    }

    @Override
    public String mapObjectToJsonString(ContainerSpecModel containerModels) throws JsonProcessingException {
        return mapper.writeValueAsString(containerModels);
    }

    @Override
    public ContainerSpecModel mapJsonToPojo(String json) throws IOException {
        return mapper.readValue(json, ContainerSpecModel.class);
    }

    /*
        TODO: Refactor ContainerListRecyclerViewAdapter dependency out
        TODO: Convert into singleton so can be called from all activities which require data
    */
    public void fetchDataFromService(Context context, final ContainerListRecyclerViewAdapter containerListRecyclerAdapter){
        final ContainerListActivityController containerListActivityController = new ContainerListActivityController(containerListRecyclerAdapter);
        RequestQueue queue = VolleySingleton.getInstance(context).getRequestQueue(); //Obtain the instance

        StringRequest volleyRequest = new StringRequest(Request.Method.GET,"http://jacob.uk.com:8080/api/v1.3/docker", //Change the url parameter
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Map<String, ContainerModel> map = mapper.readValue(response, new TypeReference<Map<String, ContainerModel>>() {});
                            containerListActivityController.buildInterface(map);
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
