package com.uk.jacob.containerdroid.services;

import android.util.Log;

import com.uk.jacob.containerdroid.models.Container;

import junit.framework.TestCase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class CAdvisorServiceTest extends TestCase {

    public void testGetContainersShouldReturnAListOfContainerModels() throws IOException {
        CAdvisorService cAdvisorService = new CAdvisorService();
        Map<String, Container> containers = cAdvisorService.getContainers();

        // Based on current data we expect 1 container in the list
        assertEquals(containers.size(), 1);

        // The constructed model should return a String when getAliases is called
        for(String key: containers.keySet()){
            assertEquals(containers.get(key).getAliases().getClass(), String.class);
        }
    }
}