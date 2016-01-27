package com.uk.jacob.containerdroid.services.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.uk.jacob.containerdroid.repositories.interfaces.ICAdvisorRepository;

import java.io.IOException;

public interface ICAdvisorService {
    String mapObjectToJsonString(Object containerModels) throws JsonProcessingException;
    Object mapJsonToPojo(String json, Class pojo) throws IOException;
    void fetchDataFromService(final ICAdvisorRepository.GetContainersCallback callback);
}
