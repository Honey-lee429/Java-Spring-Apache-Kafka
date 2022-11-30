package com.course.kafka.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

public class KafkaKeyProducer {

    //we need to Autowired kafka template for sending message, and creat jackson object mapper
    //for converting CarLocation into json.

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    public void send(String key, String value){
        kafkaTemplate.send("t-multiple-partitions", key, value);
    }

}
