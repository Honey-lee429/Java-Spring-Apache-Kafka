package com.course.kafka.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaConfig {
    @Autowired
    private KafkaProperties kafkaProperties;

    //To create an object in Spring, we can create a method with return type is desired class
    //and mark the method as @Bean
    @Bean
    public ProducerFactory<String, String> producerFactory() {
        //we will take the properties configured on application.yml
        var properties = kafkaProperties.buildAdminProperties();
        //For rebalancing, we can use property METADATA_MAX_AGE_CONFIG
        properties.put(ProducerConfig.METADATA_MAX_AGE_CONFIG, "180000");
        //the return value will be map of<String, Object> that we can modify
        return new DefaultKafkaProducerFactory<String, String>(properties);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}