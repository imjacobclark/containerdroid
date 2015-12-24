package com.uk.jacob.containerdroid.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uk.jacob.containerdroid.models.Container;

import java.io.IOException;
import java.util.Map;

public class CAdvisorService {
    public CAdvisorService() {

    }

    private String cAdvisorAPIRequest(){
        return  "{\n" +
                "    \"/system.slice/docker-44839237d723e176b0f1eb59c5e573c0de4e8439295443eb6c2b622aee02c14a.scope\": {\n" +
                "       \"aliases\":[\"jacob.uk.com\",\"44839237d723e176b0f1eb59c5e573c0de4e8439295443eb6c2b622aee02c14a\"]," +
                "       \"namespace\": \"Docker\"\n" +
                "    }" +
                "}";
    }

    public Map<String, Container> getContainers() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Container> map = mapper.readValue(this.cAdvisorAPIRequest(), new TypeReference<Map<String, Container>>() {});
        return map;
    }
}
