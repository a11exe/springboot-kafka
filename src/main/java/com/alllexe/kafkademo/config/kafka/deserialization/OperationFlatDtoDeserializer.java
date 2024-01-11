package com.alllexe.kafkademo.config.kafka.deserialization;

import com.alllexe.kafkademo.dto.OperationFlatDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

@Slf4j
public class OperationFlatDtoDeserializer implements Deserializer<OperationFlatDto> {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // nothing to do here
    }

    @Override
    public OperationFlatDto deserialize(String topic, byte[] data) {
        try {
            return OBJECT_MAPPER.readValue(data, OperationFlatDto.class);
        } catch (Exception e) {
            log.error("Error while deserialization OperationFlatDto message: {}", new String(data), e);
            return null;
        }
    }
}
