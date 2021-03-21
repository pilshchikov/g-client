package org.apache.ignite.thick.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestIgniteStartModel {

    @JsonProperty("config_path")
    private String configPath;

    public RequestIgniteStartModel() {
    }

    public String getConfigPath() {
        return configPath;
    }
}
