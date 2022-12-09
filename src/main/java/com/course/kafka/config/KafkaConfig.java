package com.course.kafka.config;

import com.course.kafka.entity.CarLocation;
import com.course.kafka.handler.GlobalErrorHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.listener.adapter.RecordFilterStrategy;
import org.springframework.util.backoff.FixedBackOff;

public class KafkaConfig {
    @Autowired
    private KafkaProperties kafkaProperties;

    @Bean
    public ConsumerFactory<Object, Object> consumerFactory() {
        //we will take the properties configured on application.yml
        var properties = kafkaProperties.buildAdminProperties();
        //For rebalancing, we can use property METADATA_MAX_AGE_CONFIG
        properties.put(ConsumerConfig.METADATA_MAX_AGE_CONFIG, "120000");
        //the return value will be map of<String, Object> that we can modify
        return new DefaultKafkaConsumerFactory<>(properties);
    }

    @Autowired
    private ObjectMapper objectMapper;
// we use this custom container factory bay defining the bean name as "farLocationContainerFactory" and
    //refer to that name form @KafkaListener annotation
    @Bean(name = "farLocationContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<Object, Object> farLocationContainerFactory(
            ConcurrentKafkaListenerContainerFactoryConfigurer configurer) {
        var factory = new ConcurrentKafkaListenerContainerFactory<Object, Object>();
        configurer.configure(factory, consumerFactory());

        factory.setRecordFilterStrategy(new RecordFilterStrategy<Object, Object>() {
            @Override
            public boolean filter(ConsumerRecord<Object, Object> consumerRecord){
                CarLocation carLocation = null;
                try {
                    carLocation = objectMapper.readValue(consumerRecord.value().toString(), CarLocation.class);
                    return  carLocation.getDistance() <= 100;
                } catch (JsonProcessingException e) {
                    return false;
                }
            }
        });
        return factory;
    }

    @Bean(name = "farLocationContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<Object, Object> kafkaListenerContainerFactory (
            ConcurrentKafkaListenerContainerFactoryConfigurer configurer){
        var factory = new ConcurrentKafkaListenerContainerFactory<Object, Object>();
        configurer.configure(factory, consumerFactory());

        factory.setErrorHandler(new GlobalErrorHandler());

        return factory;
    }

    @Bean(name = "imageRetryContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<Object, Object> imageRetryContainerFactory (
            ConcurrentKafkaListenerContainerFactoryConfigurer configurer){
        var factory = new ConcurrentKafkaListenerContainerFactory<Object, Object>();
        configurer.configure(factory, consumerFactory());

        factory.setCommonErrorHandler(new DefaultErrorHandler(new FixedBackOff(10000, 3)));

        return factory;

    }
}

