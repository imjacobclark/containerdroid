package com.uk.jacob.containerdroid.models.container.stats;

import com.fasterxml.jackson.annotation.JsonAnySetter;

public class ContainerStatsModel {
    private String timestamp;

    public String isTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp.toString();
    }

    public String getTimestamp() {
        return timestamp;
    }

    @JsonAnySetter
    public void handleUnknown(String key, Object value) {
        // Throw away any property we don't explicitally handle
    }
}
