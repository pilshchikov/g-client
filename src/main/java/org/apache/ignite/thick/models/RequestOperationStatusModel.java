package org.apache.ignite.thick.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestOperationStatusModel {

    @JsonProperty("operation_id")
    private String operationId;

    public RequestOperationStatusModel() {
    }

    public String getOperationId() {
        return operationId;
    }
}
