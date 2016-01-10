package com.uk.jacob.containerdroid.models;

import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.List;

public class ContainerModel {
    private String namespace;
    private String aliases;
    private ContainerSpecModel spec;
    private String aliasId;

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

    public String isAliasId(){
        return aliasId;
    }

    public void setAliases(List<String> aliases) {
        this.aliases = aliases.get(0);
        this.aliasId = aliases.get(1);
    }

    public String getAliases(){
        return aliases;
    }

    public String getAliasId(){
        return aliasId;
    }

    public ContainerSpecModel isSpec() {
        return spec;
    }

    public void setSpec(ContainerSpecModel spec) {
        this.spec = spec;
    }

    public ContainerSpecModel getSpec() {
        return spec;
    }

    @JsonAnySetter
    public void handleUnknown(String key, Object value) {
        // Throw away any property we don't explicitally handle
    }
}
