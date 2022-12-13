package com.course.kafka.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class GeneralLedgerScheduler {

    private static final Logger LOG = LoggerFactory.getLogger(GeneralLedgerScheduler.class);

    //to start or pause consumer
    @Autowired
    private KafkaListenerEndpointRegistry registry;

    //To scheduling start and stop at certain time we will use spring annotation @Scheduled with cron expression
    //So if we want to turn off listeners at 11pm, we can use spring @Scheduled(cron = "0 0 23 * * ?")
    @Scheduled(cron = "0 0 23 * * ?")
    public void stop(){
        LOG.info("Stopping consumer");
        registry.getListenerContainer("general-ledger.one").pause();
    }

    @Scheduled(cron = "1 0 00 * * ?")
    public void start(){
        LOG.info("Starting consumer");
        registry.getListenerContainer("general-ledger.one").resume();
    }


}
