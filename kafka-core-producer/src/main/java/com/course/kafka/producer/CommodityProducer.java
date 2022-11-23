package com.course.kafka.producer;

import com.course.kafka.entity.Commodity;
import com.course.kafka.entity.Employee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

public class CommodityProducer {
    /*
    * To convert Java class to Json
    */
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    /*
    * To convert Java objet to Json String we can use objectMapper.writeValueAsString
    */
    public void sendMessage(Commodity commodity) throws JsonProcessingException {
        var json = objectMapper.writeValueAsString(commodity);
        kafkaTemplate.send("t-commodity", commodity.getName(),json);
    }
}
