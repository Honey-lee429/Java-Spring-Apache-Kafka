package com.course.kafka.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

public class KafkaKeyProducer {

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    public void send(String key, String value){
        kafkaTemplate.send("t-multiple-partitions", key, value);
    }

}
