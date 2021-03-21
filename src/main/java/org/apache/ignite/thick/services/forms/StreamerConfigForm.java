package org.apache.ignite.thick.services.forms;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StreamerConfigForm {

    @JsonProperty("ignite_id")
    private String igniteId;

    @JsonProperty("cache_name")
    private String cacheName;

    @JsonProperty("start")
    private int startKey;

    @JsonProperty("end")
    private int endKey;

    @JsonProperty("allow_overwrite")
    private boolean allowOverwrite;

    @JsonProperty("key_type")
    private String keyType;

    @JsonProperty("value_type")
    private String valueType;

    public StreamerConfigForm() {
    }

    public StreamerConfigForm(String igniteId, String cacheName, int startKey, int endKey, boolean allowOverwrite, String keyType, String valueType) {
        this.igniteId = igniteId;
        this.cacheName = cacheName;
        this.startKey = startKey;
        this.endKey = endKey;
        this.allowOverwrite = allowOverwrite;
        this.keyType = keyType;
        this.valueType = valueType;
    }

    public String getIgniteId() {
        return igniteId;
    }

    public void setIgniteId(String igniteId) {
        this.igniteId = igniteId;
    }

    public String getCacheName() {
        return cacheName;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    public int getStartKey() {
        return startKey;
    }

    public void setStartKey(int startKey) {
        this.startKey = startKey;
    }

    public int getEndKey() {
        return endKey;
    }

    public void setEndKey(int endKey) {
        this.endKey = endKey;
    }

    public boolean getAllowOverwrite() {
        return allowOverwrite;
    }

    public void setAllowOverwrite(boolean allowOverwrite) {
        this.allowOverwrite = allowOverwrite;
    }

    public String getKeyType() {
        return keyType;
    }

    public void setKeyType(String keyType) {
        this.keyType = keyType;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }
}
