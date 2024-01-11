package com.alllexe.kafkademo.service;


import com.alllexe.kafkademo.dto.OperationFlatDto;
import com.alllexe.kafkademo.exception.OperationNotFoundException;
import com.alllexe.kafkademo.model.Operation;
import com.alllexe.kafkademo.repository.OperationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class OperationKafkaConsumer {

    private final OperationRepository operationRepository;
    private final OperationProcessor operationProcessor;

    @KafkaListener(topics = "#{ '${operation.kafka.topic}' }", containerFactory = "operationContainerFactory")
    public void receiveMessage(OperationFlatDto operationFlatDto, Acknowledgment acknowledgment) {
        if (Objects.isNull(operationFlatDto)) {
            acknowledge(acknowledgment).run();
            return;
        }
        log.info("Received operationFlatDto: " + operationFlatDto);
        Operation operation = operationRepository.findById(operationFlatDto.getId())
                .orElseThrow(OperationNotFoundException::new);
        operationProcessor.processOperation(operation);
        acknowledge(acknowledgment).run();
    }

    private Runnable acknowledge(Acknowledgment acknowledgment) {
        return () -> {
            if (acknowledgment != null) {
                acknowledgment.acknowledge();
            }
        };
    }
}
