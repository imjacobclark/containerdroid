package com.uk.jacob.containerdroid.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uk.jacob.containerdroid.models.Container;

import java.io.IOException;
import java.util.Map;

public class CAdvisorService {
    public CAdvisorService() {

    }

    public Map<String, Container> getContainers(String apiResponse) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Container> map = mapper.readValue(apiResponse, new TypeReference<Map<String, Container>>() {});
        return map;
    }
}
