package com.course.kafka.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PaymentRequestCachKey {
    @Autowired
    private ObjectMapper objectMapper;

    private Logger LOG = LoggerFactory.getLogger(PaymentRequestCachKey.class);

    public PaymentRequestCachKey(String paymentNumber, int amount, String transactionType) {
    }

    @KafkaListener(topics = "t-payment-request", groupId = "payr")
    public void consume(String message) throws JsonProcessingException {
        var paymentRequest = objectMapper.readValue(message, PaymentRequest.class);
        LOG.info("payment reequest number is {}", paymentRequest.getPaymentNumber());
    }
}
