package com.course.kafka.consumer;

import com.course.kafka.entity.SimpleNumber;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class SimpleNumberConsumer {

    @Autowired
    private ObjectMapper objectMapper;

    private Logger Log = LoggerFactory.getLogger(SimpleNumberConsumer.class);

    @KafkaListener(topics = "t-simple-number", groupId = "")
    public void consume(String message) throws JsonProcessingException {
        // to convert Json String to Object class, we will use Object Mapper using readValue method
        var simpleNumber = objectMapper.readValue(message, SimpleNumber.class);

        if (simpleNumber.getNumber() %2 != 0){
            throw new IllegalArgumentException("Odd number: " + simpleNumber.getNumber());
        }
        Log.info("processing simpleNumber {}", simpleNumber);
    }
}
