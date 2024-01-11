package com.alllexe.kafkademo.service;

import com.alllexe.kafkademo.dto.OperationFlatDto;
import com.alllexe.kafkademo.model.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaSender {
    @Value("${operation.kafka.topic}")
    String operationTopic;

    @Autowired
    private KafkaTemplate<String, OperationFlatDto> kafkaTemplate;

    public void sendMessage(Operation operation) {
        OperationFlatDto operationFlatDto = OperationFlatDto.builder()
                .id(operation.getId())
                .build();
        kafkaTemplate.send(operationTopic, operationFlatDto);
    }
}
