package com.course.kafka.consumer;

import com.course.kafka.entity.Image;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ImageConsumer {

    @Autowired
    private ObjectMapper objectMapper;

    private static final Logger log = LoggerFactory.getLogger(ImageConsumer.class);


    //we use the consumer record to see the partition
    @KafkaListener(topics = "t-image", containerFactory = "imageRetryContainerFactory", concurrency = "2")
    public void consume(ConsumerRecord<String, String> consumerRecord) throws JsonProcessingException {
        // to convert Json String to Object class, we will use Object Mapper using readValue method
        var image = objectMapper.readValue(consumerRecord.value(),Image.class);

        //we will simulate the failed API call if the image type is "svg".
        if (image.getType().equalsIgnoreCase("svg")){
            log.warn("Throwing exception on partition {} for image {}", consumerRecord.partition(), image);
            throw new IllegalArgumentException("Simulate API call failed");
        }
        log.info("Processing on partition {} for image {}",consumerRecord.partition(), image);

    }
}
