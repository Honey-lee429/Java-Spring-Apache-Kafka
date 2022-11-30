package com.course.kafka.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.atomic.AtomicInteger;

public class RebalanceProducer {

    //we need to Autowired kafka template for sending message, and creat jackson object mapper
    //for converting CarLocation into json.

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private AtomicInteger counter = new AtomicInteger();

    @Scheduled(fixedRate = 1000)
    public void sendMessage(){
        kafkaTemplate.send("t-rebalance", "Counter " + counter.incrementAndGet());
    }
}
