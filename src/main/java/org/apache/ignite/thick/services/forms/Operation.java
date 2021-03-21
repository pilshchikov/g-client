package org.apache.ignite.thick.services.forms;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Operation {

    private String id;

    private OperationStatus status;

    @JsonProperty("error_message")
    private String errorMsg;

    private List<Thread> backgroundTasks;

    public Operation(String id) {
        this.id = id;
        this.status = OperationStatus.STARTED;
        backgroundTasks = new ArrayList<>();
    }

    public OperationInfo getInfo() {
        return new OperationInfo(status, id);
    }

    public Operation() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public OperationStatus getStatus() {
        return status;
    }

    public void setStatus(OperationStatus status) {
        this.status = status;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public List<Thread> getBackgroundTasks() {
        return backgroundTasks;
    }

    public void setBackgroundTasks(List<Thread> backgroundTasks) {
        this.backgroundTasks = backgroundTasks;
    }
}
