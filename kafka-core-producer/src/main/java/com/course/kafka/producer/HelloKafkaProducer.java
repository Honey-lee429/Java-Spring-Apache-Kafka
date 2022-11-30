package com.course.kafka.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class HelloKafkaProducer {

    //we need to Autowired kafka template for sending message, and creat jackson object mapper
    //for converting CarLocation into json.

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendHello(String name) {
       System.out.println("Hello " + name);

    }
}
