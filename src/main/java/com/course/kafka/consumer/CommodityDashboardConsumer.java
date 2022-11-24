package com.course.kafka.consumer;

import com.course.kafka.entity.Commodity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class CommodityDashboardConsumer {

    @Autowired
    private ObjectMapper objectMapper;

    private static final Logger LOG = LoggerFactory.getLogger(CommodityDashboardConsumer.class);

    @KafkaListener(topics = "t-commodity", groupId = "cg-dashboard")
    public void consume(String message) throws JsonProcessingException {
        // to convert Json String to Employee class, we will use Object Mapper using readValue method
        var commodity = objectMapper.readValue(message, Commodity.class);
        LOG.info("Dashboard logi for: {}", commodity);
    }


}
