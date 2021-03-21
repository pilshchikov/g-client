package org.apache.ignite.thick.services;

import org.apache.ignite.thick.services.forms.OperationInfo;
import org.apache.ignite.thick.services.forms.OperationStatus;
import org.apache.ignite.thick.services.forms.StreamerConfigForm;

public interface IIgniteClientService {

    public OperationInfo start(String absSpringConfigPath);

    public OperationInfo startAsync(String absSpringConfigPath);

    public OperationInfo checkStartAsyncStatus(String nodeId);

    public OperationInfo stop(String nodeId);

    public OperationInfo startStreamer(StreamerConfigForm config);
    public OperationInfo checkOperationStatus(String operationId);
}
