package com.uk.jacob.containerdroid.services;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.uk.jacob.containerdroid.services.interfaces.ICAdvisorService;

import com.uk.jacob.containerdroid.models.ContainerModel;

import com.uk.jacob.containerdroid.volley.VolleySingleton;

import java.io.IOException;
import java.util.Map;

public class CAdvisorService implements ICAdvisorService {
    private ObjectMapper mapper;

    public CAdvisorService() {
        this.mapper = new ObjectMapper();
    }

    public void fetchDataFromService(final Callback callback){
        VolleySingleton.getInstance().getRequestQueue().add(
                new StringRequest(
                        Request.Method.GET,
                        "http://jacob.uk.com:8080/api/v1.3/docker",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    Map<String, ContainerModel> map = mapper.readValue(response, new TypeReference<Map<String, ContainerModel>>() {});
                                    callback.callback(map);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                            }
                        }
                )
        );
    }
}
