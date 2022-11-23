package com.course.kafka.scheduler;

import com.course.kafka.entity.CarLocation;
import com.course.kafka.producer.CarLocationProducder;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/*
 * Simulate data feed
 * */
@Service
public class CarLocationScheduler {
    private static final Logger LOG = LoggerFactory.getLogger(CarLocationScheduler.class);

    private CarLocation carOne;
    private CarLocation carTwo;
    private CarLocation carThree;

    @Autowired
    private CarLocationProducder carLocationProducder;

    public CarLocationScheduler(){
        var now = System.currentTimeMillis();

        carOne = new CarLocation("car 1", now, 0);
        carTwo = new CarLocation("car 2", now, 110);
        carThree = new CarLocation("car 3", now, 95);
    }
    @Scheduled(fixedRate = 10000)
    public void generateCarLocation() throws JsonProcessingException {
        var now = System.currentTimeMillis();

        carOne.setTimestamp(now);
        carTwo.setTimestamp(now);
        carThree.setTimestamp(now);

        carOne.setDistance(carOne.getDistance() +1);
        carOne.setDistance(carTwo.getDistance() -1);
        carOne.setDistance(carThree.getDistance() +1);

        carLocationProducder.sendMessage(carOne);
        carLocationProducder.sendMessage(carTwo);
        carLocationProducder.sendMessage(carThree);

        LOG.info("Sent : {}", carOne);
        LOG.info("Sent : {}", carTwo);
        LOG.info("Sent : {}", carThree);
    }

}
