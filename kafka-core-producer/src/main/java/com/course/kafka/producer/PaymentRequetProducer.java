package com.course.kafka.producer;

import com.course.kafka.entity.PaymentRequest;
import com.course.kafka.entity.PurchaseRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentRequetProducer {

    //we need to Autowired kafka template for sending message, and creat jackson object mapper
    //for converting CarLocation into json.
    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void sendMessage(PaymentRequest paymentRequest) throws JsonProcessingException {
        var json = objectMapper.writeValueAsString(paymentRequest);
        kafkaTemplate.send("t-payment-request", paymentRequest.getPaymentNumber(), json);

    }
}
