package com.uk.jacob.containerdroid.models.container.spec;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ContainerSpecModel {
    private String image;
    @JsonProperty("creation_time")
    private String created;

    public String isImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public String isCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created.toString();
    }

    public String getCreated() {
        return created;
    }

    @JsonAnySetter
    public void handleUnknown(String key, Object value) {
        // Throw away any property we don't explicitally handle
    }
}
