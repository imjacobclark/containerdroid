package com.uk.jacob.containerdroid.models;

import com.fasterxml.jackson.annotation.JsonAnySetter;

public class ContainerSpec {
    private String image;

    public String isImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    @JsonAnySetter
    public void handleUnknown(String key, Object value) {
        // Throw away any property we don't explicitally handle
    }
}
