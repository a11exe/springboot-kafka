package com.alllexe.kafkademo.service;

import com.alllexe.kafkademo.model.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OperationKafkaRunner implements OperationRunner {
    private final KafkaSender kafkaSender;

    @Override
    public OperationRunnerType getOperationRunnerType() {
        return OperationRunnerType.KAFKA;
    }

    @Override
    public void runOperations(List<Operation> operations) {
        operations.forEach(kafkaSender::sendMessage);
    }
}
