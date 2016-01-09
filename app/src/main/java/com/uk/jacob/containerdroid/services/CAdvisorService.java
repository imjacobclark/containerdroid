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
import com.uk.jacob.containerdroid.volley.VolleySingleton;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class CAdvisorService {
    private ContainerListRecyclerViewAdapter containerListRecyclerAdapter;

    public CAdvisorService(final ContainerListRecyclerViewAdapter containerListRecyclerAdapter) {
        this.containerListRecyclerAdapter = containerListRecyclerAdapter;
    }

    private void buildContainerListInterface(String response) {
        containerListRecyclerAdapter.setRefreshing(true);

        try {
            Map<String, ContainerModel> containers = getContainerObject(response);
            Iterator<Map.Entry<String, ContainerModel>> iterator = containers.entrySet().iterator();
            int position = 0;

            while(iterator.hasNext()){
                containerListRecyclerAdapter.addItem(position, iterator.next().getValue());
                position++;
                iterator.remove();
            }

            containerListRecyclerAdapter.setRefreshing(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                        buildContainerListInterface(response);
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
