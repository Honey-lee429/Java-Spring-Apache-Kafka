package com.course.kafka.consumer;

import com.course.kafka.entity.Invoice;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.spi.LoggerContextFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class InvoiceConsumer {
    @Autowired
    private ObjectMapper objectMapper;

    private static final Logger log = LoggerFactory.getLogger(InvoiceConsumer.class);

    @KafkaListener(topics = "t-invoice", concurrency = "2", containerFactory = "invoiceDltContainerFactory")
    public void conumer (String message) throws JsonProcessingException {
        var invoice = objectMapper.readValue(message, Invoice.class);

        if(invoice.getAmount() < 1){
            throw new IllegalArgumentException("Invalid amount for " + invoice);
        }
        log.info("Processing invoice: {}", invoice);
    }
}
