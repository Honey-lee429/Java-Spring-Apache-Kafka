package com.course.kafka.producer;

import com.course.kafka.entity.FoodOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class FoodOrderProducer {

    //we need to Autowired kafka template for sending message, and creat jackson object mapper
    //for converting CarLocation into json.

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void send(FoodOrder foodOrder) throws JsonProcessingException {
        //To convert Java objet to Json String we can use objectMapper.writeValueAsString
        var json = objectMapper.writeValueAsString(foodOrder);
        kafkaTemplate.send("t-food-order", json);
    }

}
