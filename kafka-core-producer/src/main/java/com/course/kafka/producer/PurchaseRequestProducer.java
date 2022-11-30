package com.course.kafka.producer;

import com.course.kafka.entity.PurchaseRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PurchaseRequestProducer {

    //we need to Autowired kafka template for sending message, and creat jackson object mapper
    //for converting CarLocation into json.
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void sendMessage(PurchaseRequest purchaseRequest) throws JsonProcessingException {
        var json = objectMapper.writeValueAsString(purchaseRequest);
        kafkaTemplate.send("t-purchase-request", purchaseRequest.getPrNumer(), json);
    }
}
