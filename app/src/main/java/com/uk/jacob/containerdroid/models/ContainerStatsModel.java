package com.uk.jacob.containerdroid.models;

import com.fasterxml.jackson.annotation.JsonAnySetter;

public class ContainerStatsModel {
    @JsonAnySetter
    public void handleUnknown(String key, Object value) {
        // Throw away any property we don't explicitally handle
    }
}
