package com.uk.jacob.containerdroid.models;

import android.util.Log;

import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.List;

public class Container {
    private String namespace;
    private String aliases;

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

    @JsonAnySetter
    public void handleUnknown(String key, Object value) {
        // Throw away any property we don't explicitally handle
    }
}
