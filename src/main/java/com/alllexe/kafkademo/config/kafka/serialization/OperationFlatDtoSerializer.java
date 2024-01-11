package com.alllexe.kafkademo.config.kafka.serialization;

import com.alllexe.kafkademo.dto.OperationFlatDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

@Slf4j
public class OperationFlatDtoSerializer implements Serializer<OperationFlatDto> {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // nothing to do here
    }

    @Override
    public byte[] serialize(String topic, OperationFlatDto data) {
        try {
            return OBJECT_MAPPER.writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
            log.error("Error while serialization OperationFlatDto: {}", data, e);
            throw new SerializationException();
        }
    }
}
