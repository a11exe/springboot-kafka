package com.alllexe.kafkademo.config;

import com.alllexe.kafkademo.config.kafka.deserialization.OperationFlatDtoDeserializer;
import com.alllexe.kafkademo.dto.OperationFlatDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig extends AbstractKafkaConfig {

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, OperationFlatDto> operationContainerFactory(ConsumerFactory operationConsumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, OperationFlatDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(operationConsumerFactory);
        factory.setAutoStartup(operationKafkaAutoStartup);
        factory.setConcurrency(operationKafkaConsumerThreadCount);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        return factory;
    }

    @Bean
    public ConsumerFactory<String, OperationFlatDto> operationConsumerFactory() {
        Map<String, Object> props = getConsumerConfig(OperationFlatDtoDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, operationKafkaGroupId);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, operationKafkaOffsetReset);
        props.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, operationKafkaHeartbeatInterval);
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, operationKafkaSessionTimeout);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        return new DefaultKafkaConsumerFactory<>(props);
    }

    private <T extends Deserializer<?>> Map<String, Object> getConsumerConfig(Class<T> deserializer) {
        Map<String, Object> configProps = getConfigMap();
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);
        return configProps;
    }
}
