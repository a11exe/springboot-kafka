package com.alllexe.kafkademo.service;

import com.alllexe.kafkademo.model.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OperationRegularRunner implements OperationRunner {

    private final OperationProcessor operationProcessor;

    @Override
    public OperationRunnerType getOperationRunnerType() {
        return OperationRunnerType.REGULAR;
    }

    @Override
    public void runOperations(List<Operation> operations) {
        for(Operation operation: operations) {
            operationProcessor.processOperation(operation);
        }
    }
}
