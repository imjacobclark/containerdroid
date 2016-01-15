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
import com.uk.jacob.containerdroid.volley.VolleySingleton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CAdvisorService implements ICAdvisorService {
    private ContainerListRecyclerViewAdapter containerListRecyclerAdapter;
    private List<String> currentActiveContainerIds;
    private List<String> currentCAdvisorIds;

    public CAdvisorService(final ContainerListRecyclerViewAdapter containerListRecyclerAdapter) {
        this.containerListRecyclerAdapter = containerListRecyclerAdapter;
        this.currentActiveContainerIds = new ArrayList<String>();
        this.currentCAdvisorIds = new ArrayList<String>();
    }


    /* This needs moving to a controller level, should not be here */
    private void buildContainerListInterface(String response) {
        containerListRecyclerAdapter.setRefreshing(true);
        currentCAdvisorIds.clear();

        try {
            Map<String, ContainerModel> containers = getContainerObject(response);
            Iterator<Map.Entry<String, ContainerModel>> iterator = containers.entrySet().iterator();
            int position = 0;

            while(iterator.hasNext()){
                ContainerModel container = iterator.next().getValue();
                currentCAdvisorIds.add(container.getAliasId());

                if(!currentActiveContainerIds.contains(container.getAliasId())){
                    containerListRecyclerAdapter.addItem(position, container);
                    currentActiveContainerIds.add(container.getAliasId());
                }
            }

            if(!currentCAdvisorIds.equals(currentActiveContainerIds)){
                Iterator currentActiveContainerIdsIterator = currentActiveContainerIds.iterator();
                while(currentActiveContainerIdsIterator.hasNext()){
                    if(!currentCAdvisorIds.contains(currentActiveContainerIdsIterator.next())){
                        currentActiveContainerIdsIterator.remove();
                        containerListRecyclerAdapter.removeItem(position);
                    }
                }
            }

            position++;
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
