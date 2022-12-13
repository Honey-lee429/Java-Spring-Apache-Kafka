package com.course.kafka.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class GeneralLegderConsumer {
    private static final Logger logger = LoggerFactory.getLogger(GeneralLegderConsumer.class);

    @KafkaListener(id= "general-ledger.one", topics = "t-heneral-ledger")
    public void consumer(String message){
        logger.info("From consumer one: {}",message);
    }

    @KafkaListener(topics = "t-heneral-ledger")
    public void consumerTwo(String message){
        logger.info("From consumer two: {}",message);
    }


}
