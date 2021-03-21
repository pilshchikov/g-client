package org.apache.ignite.thick.endpoints;

import org.apache.ignite.thick.models.RequestOperationStatusModel;
import org.apache.ignite.thick.services.IIgniteClientService;
import org.apache.ignite.thick.models.RequestIgniteStartModel;
import org.apache.ignite.thick.services.forms.OperationInfo;
import org.apache.ignite.thick.services.forms.OperationStatus;
import org.apache.ignite.thick.services.forms.StreamerConfigForm;
import org.springframework.web.bind.annotation.*;

@RestController
public class OperationsController {

    private final IIgniteClientService IIgniteClientService;

    public OperationsController(IIgniteClientService IIgniteClientService) {
        this.IIgniteClientService = IIgniteClientService;
    }

    @PostMapping("/start_cluster_async")
    public OperationInfo startClusterAsync(@RequestBody RequestIgniteStartModel body) {
        return IIgniteClientService.startAsync(body.getConfigPath());
    }

    @PostMapping("/check_operation_status")
    public OperationInfo checkOperationStatus(@RequestBody RequestOperationStatusModel body) {
        return IIgniteClientService.checkOperationStatus(body.getOperationId());
    }

    @PostMapping("/start_streamer")
    public OperationInfo startStreamer(@RequestBody StreamerConfigForm body) {
        return IIgniteClientService.startStreamer(body);
    }
}
