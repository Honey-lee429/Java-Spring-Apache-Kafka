package com.course.kafka.producer;

import com.course.kafka.entity.CarLocation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CarLocationProducder {
    //we need to Autowired kafka template for sending message, and creat jackson object mapper
    //for converting CarLocation into json.
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void sendMessage(CarLocation carLocation) throws JsonProcessingException {
        //To convert Java objet to Json String we can use objectMapper.writeValueAsString
        var json = objectMapper.writeValueAsString(carLocation);
        kafkaTemplate.send("t-carLocation", json);
    }
}
