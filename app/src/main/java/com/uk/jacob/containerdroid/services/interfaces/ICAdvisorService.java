package com.uk.jacob.containerdroid.services.interfaces;

import android.content.Context;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.uk.jacob.containerdroid.activities.adapters.ContainerListRecyclerViewAdapter;
import com.uk.jacob.containerdroid.models.ContainerModel;
import com.uk.jacob.containerdroid.models.ContainerSpecModel;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ICAdvisorService {
    String mapObjectToJsonString(ContainerSpecModel containerModels) throws JsonProcessingException;
    ContainerSpecModel mapJsonToPojo(String json) throws IOException;
    void fetchDataFromService(Context context, ContainerListRecyclerViewAdapter recyclerAdapter);
}
