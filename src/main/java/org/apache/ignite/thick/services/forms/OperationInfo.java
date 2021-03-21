package org.apache.ignite.thick.services.forms;

public class OperationInfo {

    private OperationStatus status;
    private String id;

    public OperationInfo(OperationStatus status, String id) {
        this.status = status;
        this.id = id;
    }

    public OperationInfo() {
    }

    public OperationStatus getStatus() {
        return status;
    }

    public void setStatus(OperationStatus status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
