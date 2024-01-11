package com.alllexe.kafkademo.config;

import com.alllexe.kafkademo.config.kafka.serialization.OperationFlatDtoSerializer;
import com.alllexe.kafkademo.dto.OperationFlatDto;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Map;

@Configuration
public class KafkaProducerConfig extends AbstractKafkaConfig {

    @Bean
    public ProducerFactory<String, OperationFlatDto> operationProducerFactory() {
        Map<String, Object> configProps = getProducerConfig(producerTimeout, OperationFlatDtoSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, OperationFlatDto> kafkaOperationTemplate() {
        return new KafkaTemplate<>(operationProducerFactory());
    }

    private <T extends Serializer<?>> Map<String, Object> getProducerConfig(Integer timeout,
                                                                            Class<T> serializer) {
        Map<String, Object> configProps = getConfigMap();
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, serializer);
        configProps.put(ProducerConfig.ACKS_CONFIG, "all");
        configProps.put(ProducerConfig.RETRIES_CONFIG, "3");
        //configProps.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, timeout);
        return configProps;
    }
}
