package com.course.kafka.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class FixedRateProducer {

    private static final Logger LOG = LoggerFactory.getLogger(FixedRateProducer.class.getName());

    //we need to Autowired kafka template for sending message, and creat jackson object mapper
    //for converting CarLocation into json.
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    private final AtomicInteger counter = new AtomicInteger();

    @Scheduled(fixedRate = 1000)
    public void sendMessage(){
        var i = counter.getAndIncrement();
        LOG.info("i is " + i);
        kafkaTemplate.send("t-fixed", "Fixed rate " +i);
    }
}
