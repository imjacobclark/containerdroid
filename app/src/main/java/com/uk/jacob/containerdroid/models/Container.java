package com.uk.jacob.containerdroid.models;

import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Container {
    private String namespace;
    private String aliases;
    private ContainerSpec spec;

    public String isNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getNamespace(){
        return namespace;
    }

    public String isAliases(){
        return aliases;
    }

    public void setAliases(List<String> aliases) {
        this.aliases = aliases.get(0);
    }

    public String getAliases(){
        return aliases;
    }

    public ContainerSpec isSpec() {
        return spec;
    }

    public void setSpec(ContainerSpec spec) {
        this.spec = spec;
    }

    public ContainerSpec getSpec() {
        return spec;
    }

    @JsonAnySetter
    public void handleUnknown(String key, Object value) {
        // Throw away any property we don't explicitally handle
    }
}
