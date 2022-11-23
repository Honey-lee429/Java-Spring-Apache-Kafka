package com.course.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class KafkaKeyConsumer {
    private static final Logger LOG = LoggerFactory.getLogger(KafkaKeyConsumer.class.getName());

    //Create a listener method, using ConsumerRecord as parameter
    //since we will need to see partition information
    @KafkaListener
    public void consume (ConsumerRecord<String, String> consumerRecord) throws InterruptedException {
        LOG.info("key : {}, Partition : {}, Message : {}", consumerRecord.key(),
                consumerRecord.partition(), consumerRecord.value());
        TimeUnit.SECONDS.sleep(1);
    }
}
