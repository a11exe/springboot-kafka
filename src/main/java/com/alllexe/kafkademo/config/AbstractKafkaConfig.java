package com.alllexe.kafkademo.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractKafkaConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    String bootstrapAddress;
    @Value("${spring.kafka.producer.timeout.ms}")
    Integer producerTimeout;
    @Value("${operation.kafka.auto-startup}")
    Boolean operationKafkaAutoStartup;
    @Value("${operation.kafka.consumer.threads}")
    Integer operationKafkaConsumerThreadCount;
    @Value("${operation.kafka.offset.reset}")
    String operationKafkaOffsetReset;
    @Value("${operation.kafka.heartbeat.interval.ms}")
    Integer operationKafkaHeartbeatInterval;
    @Value("${operation.kafka.session.timeout.ms}")
    Integer operationKafkaSessionTimeout;
    @Value("${operation.kafka.group.id}")
    String operationKafkaGroupId;

    Map<String, Object> getConfigMap() {
        Map<String, Object> configMap = new HashMap<>();
        configMap.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return configMap;
    }
}
