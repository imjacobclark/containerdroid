package com.uk.jacob.containerdroid.services.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.uk.jacob.containerdroid.repositories.interfaces.ICAdvisorRepository;

import java.io.IOException;
import java.util.Map;

public interface ICAdvisorService {
    interface Callback {
        void callback(Map containers);
    }

    void fetchDataFromService(Callback callback);
}
