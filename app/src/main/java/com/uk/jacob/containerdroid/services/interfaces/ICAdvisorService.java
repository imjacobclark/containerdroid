package com.uk.jacob.containerdroid.services.interfaces;

import android.content.Context;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.uk.jacob.containerdroid.activities.adapters.ContainerListRecyclerViewAdapter;

import java.io.IOException;

public interface ICAdvisorService {
    String mapObjectToJsonString(Object containerModels) throws JsonProcessingException;
    Object mapJsonToPojo(String json, Class pojo) throws IOException;
    void fetchDataFromService(Context context, ContainerListRecyclerViewAdapter recyclerAdapter);
}
