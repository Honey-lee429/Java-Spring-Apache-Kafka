package com.course.kafka.consumer;

import com.course.kafka.entity.FoodOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class FoodOrderConsumer {
    private static final int MAX_ORDER_AMOUNT = 7;
    private static final Logger LOG = LoggerFactory.getLogger(FoodOrderConsumer.class);

    @Autowired
    private ObjectMapper objectMapper;

    //this method will take string from t-food-order and convert it into java object
    @KafkaListener(topics = "t-food-order", errorHandler = "myFoodOrderErrorHandler")
    public void consume(String message) throws JsonProcessingException {
        // to convert Json String to Object class, we will use Object Mapper using readValue method
        var foodOrder = objectMapper.readValue(message, FoodOrder.class);

        //here, we will put a condition, if amount ordered is too many, we will throw a runtime exception
        if (foodOrder.getAmount() > MAX_ORDER_AMOUNT){
            throw new IllegalArgumentException("Order amount is greater then 7, is: " + foodOrder.getAmount());
        }

        LOG.info("leastinign {}", foodOrder);
    }
}
