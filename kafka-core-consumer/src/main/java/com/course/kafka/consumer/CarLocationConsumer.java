package com.course.kafka.consumer;

import com.course.kafka.entity.CarLocation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CarLocationConsumer {
    private Logger LOG = LoggerFactory.getLogger(CarLocationConsumer.class);

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "t-carLocation", groupId = "cg-all-location")
    public void consumeAll(String message) throws JsonProcessingException {
        var carLocation = objectMapper.readValue(message, CarLocation.class);
        LOG.info("listenAll: {} ", carLocation);
    }

    @KafkaListener(topics = "t-carLocation", groupId = "cg-far-location", containerFactory = "farLocationContainerFactory")
    public void consumeFar(String message) throws JsonProcessingException {
        var carLocation = objectMapper.readValue(message, CarLocation.class);
       /* Spring already provide cleaner way to do filtering,so when a message doesn't
       match criteria, it will never reach the listener method. We don't need if condition.
       The way we do it is by setting the Record filterStrategy on the container factory,
       so add one parameter which is containerFactory="factory name" defined at class config

       if (carLocation.getDistance() < 100){
            return;
        }*/
        LOG.info("listenFar : {}", carLocation);
    }
}
