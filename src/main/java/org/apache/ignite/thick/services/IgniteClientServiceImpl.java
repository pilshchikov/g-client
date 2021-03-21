package org.apache.ignite.thick.services;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteDataStreamer;
import org.apache.ignite.Ignition;
import org.apache.ignite.thick.models.datatypes.Account;
import org.apache.ignite.thick.services.forms.Operation;
import org.apache.ignite.thick.services.forms.OperationInfo;
import org.apache.ignite.thick.services.forms.StreamerConfigForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Callable;

import static org.apache.ignite.thick.services.forms.OperationStatus.ENDED;
import static org.apache.ignite.thick.services.forms.OperationStatus.FAILED;

@Service
public class IgniteClientServiceImpl implements IIgniteClientService {

    Logger logger = LoggerFactory.getLogger(IgniteClientServiceImpl.class);


    private HashMap<String, Operation> operations = new HashMap<String, Operation>();
    private HashMap<String, Ignite> ignites = new HashMap<>();

    public IgniteClientServiceImpl() {

    }


    private Operation initConnection(String operationId, String springConfigPath) {
        logger.info(String.format("Start cluster %s", operationId));
        Operation operation = operations.get(operationId);
        try {
            ignites.put(operationId, Ignition.start(springConfigPath));
            operation.setStatus(ENDED);
        } catch (Exception e) {
            logger.info(String.format("Failed to start cluster %s: %s", operationId, e.getMessage().toString()));
            e.printStackTrace();
            operation.setErrorMsg(e.getMessage());
            operation.setStatus(FAILED);
        }
        logger.info(String.format("Cluster started %s", operationId));
        return operation;
    }

    private String generateId() {
        return UUID.randomUUID().toString();
    }

    private Operation createOperation() {
        String id = generateId();
        Operation operation = new Operation(id);
        operations.put(id, operation);
        return operation;
    }

    @Override
    public OperationInfo start(String absSpringConfigPath) {
        Operation operation = createOperation();
        return initConnection(operation.getId(), absSpringConfigPath).getInfo();
    }

    @Override
    public OperationInfo startAsync(String absSpringConfigPath) {
        Operation operation = createOperation();
        Runnable runnable = () -> initConnection(operation.getId(), absSpringConfigPath);
        Thread thread = new Thread(runnable);
        List<Thread> backgroundTasks = operation.getBackgroundTasks();
        backgroundTasks.add(thread);
        operation.setBackgroundTasks(backgroundTasks);
        thread.start();
        return operation.getInfo();
    }

    @Override
    public OperationInfo checkStartAsyncStatus(String nodeId) {
        return operations.get(nodeId).getInfo();
    }

    @Override
    public OperationInfo stop(String nodeId) {
        Ignite ignite = ignites.get(nodeId);
        ignite.close();
        Operation operation = operations.get(nodeId);
        operation.setStatus(ENDED);
        return operation.getInfo();
    }

    @Override
    public OperationInfo startStreamer(StreamerConfigForm config) {
        Operation operation = createOperation();
        Runnable runnable = () -> startStreamerOperation(operation.getId(), config);
        Thread task = new Thread(runnable);
        operation.getBackgroundTasks().add(task);
        task.start();
        return operation.getInfo();
    }

    private void startStreamerOperation(String operationId, StreamerConfigForm config) {
        Ignite ignite = ignites.get(config.getIgniteId());
        String keyType = config.getKeyType();
        String valueType = config.getValueType();
        Random random = new Random();
        Operation operation = operations.get(operationId);
        ignite.getOrCreateCache(config.getCacheName());
        logger.info(String.format("Start streamer %s", operationId));
        try (IgniteDataStreamer stmr = ignite.dataStreamer(config.getCacheName())) {
            stmr.allowOverwrite(config.getAllowOverwrite());
            for (int i = config.getStartKey(); i < config.getEndKey(); i++) {
                try {
                    stmr.addData(
                            executeModelAction(keyType, random.nextLong()).call(),
                            executeModelAction(valueType, random.nextLong()).call()
                    );
                } catch (Exception e) {
                    logger.error(String.format("Failed to put data with streamer %s: %s", operationId, e.getMessage()));
                    operation.setStatus(FAILED);
                    operation.setErrorMsg(e.getMessage());
                    return;
                }
            }
            operation.setStatus(ENDED);
        } catch (Exception e) {
            logger.error(String.format("Failed to create streamer %s", e.getMessage()));
            operation.setStatus(FAILED);
            operation.setErrorMsg(e.getMessage());
        }
        logger.info(String.format("Streamer operation %s ended", operationId));
    }

    @Override
    public OperationInfo checkOperationStatus(String operationId) {
        return operations.get(operationId).getInfo();
    }

    private Callable executeModelAction(String modelType, long randomId) throws Exception {
        switch (modelType) {
            case "account":
                return () -> new Account(randomId);
            default:
                throw new Exception("Failed to find model");
        }
    }
}
