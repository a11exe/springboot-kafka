package com.alllexe.kafkademo.service;

import com.alllexe.kafkademo.model.Operation;
import com.alllexe.kafkademo.model.OperationStatus;
import com.alllexe.kafkademo.repository.OperationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OperationProcessor {

    private final OperationRepository operationRepository;

    @Value("${server.name}")
    private String serverName;

    void processOperation(Operation operation) {
        operation.setStatus(OperationStatus.COMPLETED);
        operation.setCompleteDateTime(LocalDateTime.now());
        operation.setComment(serverName);
        operationRepository.save(operation);
    }
}
