package com.course.kafka.producer;

import com.course.kafka.entity.SimpleNumber;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class SimpleNumberProducer {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void send(SimpleNumber simpleNumber) throws JsonProcessingException {
        //To convert Java objet to Json String we can use objectMapper.writeValueAsString
        var json = objectMapper.writeValueAsString(simpleNumber);
        kafkaTemplate.send("t-simple-number", json);
    }
}
